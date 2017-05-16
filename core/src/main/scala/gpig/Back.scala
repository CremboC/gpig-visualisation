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

import org.cts.CRSFactory
import org.cts.registry.EPSGRegistry
import org.cts.registry.RegistryManager
import org.cts.crs.CoordinateReferenceSystem

object HelloWorld {
  val allowOrigin = Header("Access-Control-Allow-Origin", "*")

  val cRSFactory = new CRSFactory
  val registryManager = cRSFactory.getRegistryManager
  registryManager.addRegistry(new EPSGRegistry)
  val crs = cRSFactory.getCRS("EPSG:27700")

  val data = Parsing.data().map { t =>
    t
  }

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

case class Traffic(
    year: Int, // Year
    cp: Int, // CP
    estMethod: String, // Estimation_method
    estMethodDetailed: String, // Estimation_method_detailed
    region: String, // Region
    authority: String, // LocalAuthority
    road: String, // Road
    roadCategory: String, // RoadCategory
    easting: String, // Easting
    northing: String, // Northing
    startJunction: String, // StartJunction
    endJunction: String, // EndJunction
    linkLength: Double, // LinkLength_miles
    bicycles: Double, // PedalCycles
    bikes: Float, // Motorcycles
    carsTaxis: Float, // CarsTaxis
    buses: Float, // BusesCoaches
    lgv: Float, // LightGoodsVehicles
    hgv: Float, // V2AxleRigidHGV
    hgv2: Float, // V3AxleRigidHGV
    hgv3: Float, // V4or5AxleRigidHGV
    hgv4: Float, // V3or4AxleArticHGV
    hgv5: Float, // V5AxleArticHGV
    hgv6: Float, // V6orMoreAxleArticHGV
    allHgv: Float, // AllHGVs
    allVehicles: Float // AllMotorVehicles
)

object Parsing {
  import kantan.csv._
  import kantan.csv.ops._
  import kantan.csv.generic._

  final val trafficCsv: URL = getClass.getResource("/London.csv")

  def data(): Seq[Traffic] = {
    val reader = trafficCsv.asCsvReader[Traffic](rfc.withHeader)

    reader.collect {
      case Success(s) => s
    }.to
  }
}
