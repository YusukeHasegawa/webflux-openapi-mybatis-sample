package io.github.yusukehasegawa.openapi

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Webflux extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost")

  val scn = scenario("Webflux")
    .exec(http("request_0")
      .get("/webflux/api/users/1"))

  setUp(scn.inject(constantUsersPerSec(200) during (30))).protocols(httpProtocol)
}