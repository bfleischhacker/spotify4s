package org.spotify4s.util

import io.circe.Json

object json {
  def camelToSnakeCase(str: String): String =
    str.replaceAll("([A-Z])", "_$1")
      .split("_")
      .map(s => s.headOption.map(_.toLower).getOrElse(' ') + s.tail.mkString(""))
      .mkString("_")
      .stripPrefix("_")
      .stripSuffix("_")

  def snakeToCamelCase(str: String) = {
    val tokens: Array[String] = str.split("_")
    val cameledTail = tokens.tail.map(str => str.headOption.map(_.toUpper).getOrElse("") + str.tail).mkString("")
    tokens.headOption.getOrElse("") + cameledTail
  }

  def camelCaseKeys(json: Json): Json = {
    json.mapObject(obj => obj.toMap.foldLeft(obj) {
      case (objAcc, (key, value)) => objAcc.add(snakeToCamelCase(key), camelCaseKeys(value))
    }).mapArray(_.map(camelCaseKeys))
  }
}
