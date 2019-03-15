package com.gu.sfl.lib

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

object Jackson {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(new Jdk8Module())

}
