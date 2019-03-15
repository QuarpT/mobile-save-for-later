package com.gu.sfl.lib

import java.time.{LocalDateTime, Month}

import org.specs2.mutable.Specification

class JacksonTest extends Specification {

  "Jackson mapper" should {
    "render dates as expected" in {
      val localDateTime: LocalDateTime = LocalDateTime.of(2019, Month.JANUARY, 1, 1, 1, 1)
      val times = List(localDateTime)
      val timesJson = Jackson.mapper.writeValueAsString(times)
      //      Jackson.mapper.readValue[List[LocalDateTime]](timesJson) must beEqualTo(List(localDateTime.truncatedTo(ChronoUnit.SECONDS)))
      timesJson must beEqualTo("[\"2019-01-01T01:01:01Z\"]")
    }
  }

}
