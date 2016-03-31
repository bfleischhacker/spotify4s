package org.spotify4j.generation;

import org.spotify4j.generation.scala.*;
import org.spotify4j.generation.scala.types.GenericType;
import org.spotify4j.generation.scala.types.ReferenceType;
import org.spotify4j.generation.scala.types.ScalaTypes;
import org.spotify4j.generation.scala.types.Type;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RamlFile {
    public final String fileName;
    public final String type;
    public final String description;
    public final String displayName;
    private Map<Object, Object> properties;
    public final String pattern;
    private Map<String, String> propertyNameOverrides;
    private Map<Type, Type> typeOverrides;

    public static final Yaml YAML = new Yaml();

    private static final HashMap<String, Type> RamlTypeToJavaTypeMap;

    static {
        RamlTypeToJavaTypeMap = new HashMap<>();
        RamlTypeToJavaTypeMap.put("string", ScalaTypes.String);
        RamlTypeToJavaTypeMap.put("integer", ScalaTypes.Int);
        RamlTypeToJavaTypeMap.put("number", ScalaTypes.Double);
        RamlTypeToJavaTypeMap.put("boolean", ScalaTypes.Boolean);
    }

    public RamlFile(String fileName, String type, String description, String displayName, Map<Object, Object> properties, String pattern) {
        this.fileName = fileName;
        this.type = type;
        this.description = description;
        this.displayName = displayName;
        this.properties = properties;
        this.pattern = pattern;
        this.propertyNameOverrides = new HashMap<>();
        this.typeOverrides = new HashMap<>();
    }

    public static RamlFile parse(Path path) throws FileNotFoundException {
        final Map<String, Object> yaml = YAML.loadAs(new FileReader(path.toFile()), Map.class);
        return new RamlFile(
                path.getFileName().toString(),
                (String) yaml.get("type"),
                (String) yaml.get("description"),
                (String) yaml.get("displayName"),
                (Map<Object, Object>) yaml.getOrDefault("properties", new HashMap<>()),
                (String) yaml.get("pattern"));
    }


    protected void parseProperty(String packageName, ScalaClass containing, Map.Entry<Object, Object> property) {
        if (property.getKey() instanceof ArrayList) {
            return;
        }

        String propertyName = (String) property.getKey();
        String propertyMemberName = propertyNameOverrides.getOrDefault(propertyName, propertyName);
        final String typeName;
        final String baseTypeName;
        final String description;
        Type type = null;
        boolean isRequired = true;

        if (property.getKey() instanceof String && property.getValue() instanceof String) {
            typeName = (String) property.getValue();
            baseTypeName = typeName.replace("[]", "");
            description = null;
        } else if (property.getKey() instanceof String && property.getValue() instanceof Map) {
            final Map<String, Object> propertyFields = (Map<String, Object>) property.getValue();
            typeName = (String) propertyFields.get("type");
            description = (String) propertyFields.get("description");
            baseTypeName = typeName.replace("[]", "");
            if (baseTypeName.equals("Page")) {
                String innerTypeName = (String) propertyFields.get("(pagedObject)");
                type = new GenericType("Page", packageName, new ReferenceType(innerTypeName, packageName));
            } else if (baseTypeName.equals("string") && propertyFields.containsKey("enum")) {
                final List<String> values = (List<String>) propertyFields.get("enum");
                String enumName = Util.snakeToCamelCase(propertyName);
                containing.getEnums().add(new ScalaEnum(enumName, values));
                type = new ReferenceType(containing.getType().getName() + "." + enumName, packageName);
            }

            isRequired = (boolean) propertyFields.getOrDefault("required", true);
        } else {
            return;
        }

        if (type == null) {
            if (RamlTypeToJavaTypeMap.containsKey(baseTypeName)) {
                type = RamlTypeToJavaTypeMap.get(baseTypeName);
            } else {
                type = new ReferenceType(baseTypeName, packageName);
            }
        }

        if (typeOverrides.containsKey(type)) {
            type = typeOverrides.get(type);
        }

        if (typeName.endsWith("[]")) {
            type = ScalaTypes.List(type);
        }

        if (!isRequired) {
            type = ScalaTypes.Option(type);
        }

        final ArrayList<ScalaAnnotation> annotations = new ArrayList<>();

        containing.getVariables().add(
                new ScalaField(AccessModifier.PUBLIC, true, false, Util.snakeToLowerCamelCase(propertyMemberName), type)
                        .withComment(description)
                        .withAnnotations(annotations));
    }


    private String getClassName() {
        return Util.snakeToCamelCase(fileName.substring(0, fileName.length() - 5));
    }

    private void buildStringWrapper(ScalaClass clazz) {
        clazz.setConstructorAccessModifier(AccessModifier.PRIVATE);
        String fieldName = clazz.getName().substring(0, 1).toLowerCase() + clazz.getName().substring(1);
        ScalaField valueField = new ScalaField(AccessModifier.PUBLIC, true, false, fieldName, ScalaTypes.String);
        clazz.getVariables().add(valueField);

        if (pattern != null) {
            clazz.getCompanionFields().add(
                    new ScalaField(AccessModifier.PRIVATE, true, false, "pattern", new ReferenceType("Regex", "scala.util.matching"))
                            .withAssignment("\"" + pattern + "$\".r"));

            clazz.getCompanionMethods().add(new ScalaMethod(
                    AccessModifier.PUBLIC,
                    false,
                    ScalaTypes.Option(clazz.getType()),
                    "verified")
                    .withParams(Stream.of(valueField).collect(Collectors.toList()))
                    .withBody(Stream.of(
                            String.format("pattern.findFirstMatchIn(%s).map(_ => %s(%s))",
                                    valueField.getName(),
                                    clazz.getName(),
                                    valueField.getName())).collect(Collectors.toList()))
            );

            Type encoderType = new GenericType("Encoder", "io.circe", clazz.getType());
            Type decoderType = new GenericType("Decoder", "io.circe", clazz.getType());
            clazz.getCompanionFields().add(new ScalaField(AccessModifier.PUBLIC, true, true, "encoder", encoderType)
                    .withAssignment("Encoder.encodeString.contramap(_." + fieldName + ")"));
            clazz.getCompanionFields().add(new ScalaField(AccessModifier.PUBLIC, true, true, "decoder", decoderType)
                    .withAssignment("Decoder.decodeString.emap(str => verified(str).fold[$(cats.data).Xor[String, " + getClassName() + "]](s\"invalid " + getClassName() + " format: $str doesn't match regex ${pattern.regex}\".left)(_.right))"));

            clazz.getPackageImports().add("cats.syntax.xor._");
        }
    }

    //
    public static void generateDecoder(ScalaClass clazz) {
        clazz.getPackageImports().add("cats.syntax.cartesian._");
        clazz.getPackageImports().add("io.circe.Decoder");
        clazz.getCompanionFields().add(new ScalaField(
                AccessModifier.PUBLIC,
                true,
                true,
                "decoder",
                new GenericType("Decoder", "io.circe", clazz.getType())).
                withAssignment(
                        clazz.getVariables().stream().map(var ->
                                "Decoder.instance(_.get[" + var.getType().getDeclaration() + "](\"" + Util.camelCaseToSnake(var.getName().replace("`", "")) + "\"))"
                        ).collect(Collectors.joining(" |@| \n", "(", ").map(" + clazz.getType().getDeclaration() + ".apply)")))
        );
    }

    public static void generateEncoder(ScalaClass clazz) {
        final String lowerClassClassName = clazz.getName().substring(0, 1).toLowerCase() + clazz.getName().substring(1);

        clazz.getPackageImports().add("cats.syntax.cartesian._");
        clazz.getPackageImports().add("io.circe.Encoder");

        List<String> body = new ArrayList<>();
        body.add("Encoder.instance(" + lowerClassClassName + " =>");
        body.add("$(io.circe).Json.obj(");
        body.add(clazz.getVariables().stream().map(scalaField ->
                String.format("\"%s\" -> Encoder[%s].apply(%s.%s)",
                        Util.camelCaseToSnake(scalaField.getName().replace("`", "")),
                        scalaField.getType().getDeclaration(),
                        lowerClassClassName,
                        scalaField.getName()
                )
        ).collect(Collectors.joining(",")));
        body.add("))");

        clazz.getCompanionFields().add(new ScalaField(
                AccessModifier.PUBLIC,
                true,
                true,
                "encoder",
                new GenericType("Encoder", "io.circe", clazz.getType())).
                withAssignment(body.stream().collect(Collectors.joining("\n"))));

    }

    public Optional<String> getParentType() {
        return type.equals("string") || type.equals("object") ? Optional.empty() : Optional.of(type);
    }


    public ScalaClass toClass(String packagePath, Map<String, ScalaClass> classPath) {
        ScalaClass clazz = new ScalaClass(getClassName(), packagePath);


        if (type.equals("string")) {
            buildStringWrapper(clazz);
        } else {
            if (!type.equals("object")) {
                assert (classPath.containsKey(type));
                for (ScalaField scalaField : classPath.get(type).getVariables()) {
                    clazz.getVariables().add(scalaField);
                }
            }
            for (Map.Entry<Object, Object> prop : properties.entrySet()) {
                parseProperty(packagePath, clazz, prop);
            }
            generateDecoder(clazz);
            generateEncoder(clazz);
        }

        clazz.withComment(new ScalaDocComment(description, clazz.getVariables().stream().collect(Collectors.toList())));
        return clazz;
    }

    public RamlFile withPropertyNameOverride(String name, String alternative) {
        this.propertyNameOverrides.put(name, alternative);
        return this;
    }

    public RamlFile withTypeOverride(Type type, Type overriden) {
        this.typeOverrides.put(type, overriden);
        return this;
    }
}
