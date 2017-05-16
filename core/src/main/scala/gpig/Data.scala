package gpig

import java.net.URL

import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import org.geotools.geometry.Envelope2D
import org.geotools.referencing.CRS
import org.opengis.referencing.crs.{
  CRSAuthorityFactory,
  CoordinateReferenceSystem
}
import org.opengis.referencing.operation.MathTransform

/**
  * @author paulius
  */
object Data {
  final val trafficCsv: URL = getClass.getResource("/London.csv")

  final val factory: CRSAuthorityFactory = CRS.getAuthorityFactory(true)
  final val wsg86: CoordinateReferenceSystem =
    factory.createCoordinateReferenceSystem("EPSG:4326")
  final val bng: CoordinateReferenceSystem =
    factory.createCoordinateReferenceSystem("EPSG:27700")
  final val transform: MathTransform = CRS.findMathTransform(bng, wsg86)

  def get(): Seq[Traffic] = {
    val reader = trafficCsv.asCsvReader[Traffic](rfc.withHeader)

    reader.collect {
      case Success(s) =>
        val transform1 = CRS.transform(
          transform,
          new Envelope2D(bng, s.x, s.y, 0, 0)
        )

        val lat = transform1.getUpperCorner.getOrdinate(0)
        val lon = transform1.getUpperCorner.getOrdinate(1)
        s.copy(x = lat, y = lon)
    }.to
  }
}