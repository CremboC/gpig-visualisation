package gpig

import google.maps._
import org.scalajs.dom.document
import org.scalajs.jquery.{JQueryXHR, jQuery}

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExportTopLevel


object Front extends JSApp {
  var map: google.maps.Map = _

  def main(): Unit = {
    println("Hello!")
  }

  def loadData(data: js.Array[Any], textStatus: String, jqXHR: JQueryXHR): Unit = {
    val traffics = data.asInstanceOf[js.Array[Traffic]]
    println(traffics.length) // do something with this..

    // init directions service
    val dirService = new google.maps.DirectionsService()
    val dirRenderer = new google.maps.DirectionsRenderer(DirectionsRendererOptions(
      suppressMarkers = true,
      map = map
    ))
    dirRenderer.setMap(map)

    val request = js.Dynamic.literal(
      origin = "48.1252,11.5407",
      destination = "48.13376,11.5535",
      travelMode = google.maps.TravelMode.DRIVING
    ).asInstanceOf[DirectionsRequest]

    val callback = (result: DirectionsResult, status: DirectionsStatus) => {
      if (status == DirectionsStatus.OK) {
        dirRenderer.setDirections(result)
      }
    }
    dirService.route(request, callback)
  }

  @JSExportTopLevel("initMap")
  def initMap(): Unit = {
    map = new google.maps.Map(document.getElementById("map"),
      MapOptions(
        zoom = 2,
        center = new google.maps.LatLng(2.8,-187.3),
        mapTypeId = MapTypeId.ROADMAP
      )
    )

    val succ = loadData _
    jQuery.getJSON("http://localhost:8080/year/2016", success = succ)
  }
}

@js.native
trait Traffic extends js.Object {
  var year: Int = js.native // Year
  var cp: Int = js.native // CP
  var estMethod: String = js.native // Estimation_method
  var estMethodDetailed: String = js.native // Estimation_method_detailed
  var region: String = js.native // Region
  var authority: String = js.native // LocalAuthority
  var road: String = js.native // Road
  var roadCategory: String = js.native // RoadCategory
  var easting: Int = js.native // Easting
  var northing: Int = js.native // Northing
  var startJunction: String = js.native // StartJunction
  var endJunction: String = js.native // EndJunction
  var linkLength: Double = js.native // LinkLength_miles
  var bicycles: Double = js.native // PedalCycles
  var bikes: Float = js.native // Motorcycles
  var carsTaxis: Float = js.native // CarsTaxis
  var buses: Float = js.native // BusesCoaches
  var lgv: Float = js.native // LightGoodsVehicles
  var hgv: Float = js.native // V2AxleRigidHGV
  var hgv2: Float = js.native // V3AxleRigidHGV
  var hgv3: Float = js.native // V4or5AxleRigidHGV
  var hgv4: Float = js.native // V3or4AxleArticHGV
  var hgv5: Float = js.native // V5AxleArticHGV
  var hgv6: Float = js.native // V6orMoreAxleArticHGV
  var allHgv: Float = js.native // AllHGVs
  var allVehicles: Float = js.native // AllMotorVehicles
}
