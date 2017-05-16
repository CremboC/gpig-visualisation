import sbt.Keys.{libraryDependencies, resolvers, version}

//enablePlugins(ScalaJSPlugin)

name := "gpig-visualisation"
version := "1.0"

scalaVersion in ThisBuild := "2.11.11"

lazy val js = (project in file("js")).settings(
  // This is an application with a main method
  scalaJSUseMainModuleInitializer := true,
  mainClass in Compile := Some("gpig.Front"),

  resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
//  resolvers += Resolver.url("typesafe", url("http://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns),

  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  libraryDependencies += "io.surfkit" %%% "scalajs-google-maps" % "0.0.2-SNAPSHOT",
  libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.1",

  skip in packageJSDependencies := false,
  jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js"
).enablePlugins(ScalaJSPlugin)

val Http4sVersion = "0.15.11a"
val circeVersion = "0.8.0"

lazy val core = (project in file("core")).settings(

  libraryDependencies += "com.nrinaudo" %% "kantan.csv" % "0.1.19",
  libraryDependencies += "com.nrinaudo" %% "kantan.csv-generic" % "0.1.19",

  libraryDependencies ++= Seq(
    "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
    "org.http4s"     %% "http4s-circe"        % Http4sVersion,
    "org.http4s"     %% "http4s-dsl"          % Http4sVersion,
    "ch.qos.logback" %  "logback-classic"     % "1.2.1"
  ),

  libraryDependencies ++= Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)
)