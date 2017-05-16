package gpig

/**
  * @author paulius
  */
case class Traffic(
                    year: Int, // Year
                    cp: Int, // CP
                    estMethod: String, // Estimation_method
                    estMethodDetailed: String, // Estimation_method_detailed
                    region: String, // Region
                    authority: String, // LocalAuthority
                    road: String, // Road
                    roadCategory: String, // RoadCategory
                    x: Double, // Easting
                    y: Double, // Northing
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
                    allVehicles: Float // AllMotorVehicles,
)


