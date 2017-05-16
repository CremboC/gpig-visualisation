package gpig

import java.net.URL

import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.headers._
import org.http4s.dsl._

import scalaz.concurrent.Task

object HelloWorld {
  val allowOrigin = Header("Access-Control-Allow-Origin", "*")

  final val data = Data.get()

  val service = HttpService {
    case GET -> Root / "data" =>
      Ok(data.asJson)
        .putHeaders(allowOrigin)

    case GET -> Root / "year" / year =>
      Ok(Task {
        val yearly = data.groupBy(_.year).get(year.toInt)
        yearly.asJson
      }).putHeaders(allowOrigin)
  }
}


