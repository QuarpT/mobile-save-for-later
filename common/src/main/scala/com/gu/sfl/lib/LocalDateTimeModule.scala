package com.gu.sfl.lib

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.fasterxml.jackson.core.{JsonGenerator, JsonParser, JsonTokenId}
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer, JsonSerializer, SerializerProvider}
import com.gu.sfl.lib.LocalDateTimeModule.format

import scala.util.{Failure, Success, Try}

object LocalDateTimeModule {
  val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
}

class LocalDateTimeModule extends SimpleModule {
  addDeserializer[LocalDateTime](classOf[LocalDateTime], new JsonDeserializer[LocalDateTime] {
    override def deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime = {
      parser.getCurrentTokenId match {
        case JsonTokenId.ID_STRING => {
          Try {
            val text = parser.getText
            LocalDateTime.parse(text, format)
          } match {
            case Success(value) => value
            case Failure(exception) => context.reportInputMismatch(classOf[LocalDateTime], s"${exception.getMessage}")
          }
        }
        case _ => context.reportInputMismatch(handledType, "Unexpected token (%s), expected String value", parser.getCurrentToken)
      }
    }
  })
  addSerializer(classOf[LocalDateTime], new JsonSerializer[LocalDateTime] {
    override def serialize(value: LocalDateTime, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
      gen.writeString(value.format(format))
    }
  })
}
