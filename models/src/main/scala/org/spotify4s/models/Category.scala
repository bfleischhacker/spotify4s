package org.spotify4s.models


/**
  * @param href A link to the Web API endpoint returning full details of the category.
  * @param icons
  * @param id
  * @param name The name of the category.
  **/
case class Category(href: String, icons: List[Image], id: SpotifyCategoryId, name: String)

