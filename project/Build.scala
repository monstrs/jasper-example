import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object BuildSettings {
  val buildOrganization = "ru.monstrs"
  val buildVersion      = "0.1.0"
  val buildScalaVersion = "2.10.3"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion,
    crossPaths := false
  )
}

object Dependencies {
  var logbackVersion = "1.0.13"
  val jasperVersion = "6.1.0"
  val jasperFontsVersion = "6.0.0"
  val poiVersion = "3.10.1"
  val groovyVersion = "2.4.3"
  val springVersion = "4.0.0.RELEASE"
  val jettyVersion = "9.2.0.v20140526"
}

object JasperExample extends Build {
  import Dependencies._
  import BuildSettings._

  val commonDeps = Seq (
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "net.sf.jasperreports" % "jasperreports" % jasperVersion,
    "net.sf.jasperreports" % "jasperreports-fonts" % jasperFontsVersion,
    "org.apache.poi" % "poi" % poiVersion,
    "org.codehaus.groovy" % "groovy-all" % groovyVersion,
    "javax.servlet" % "servlet-api" % "2.5",
    "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
    "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
    "org.eclipse.jetty" % "jetty-server" % jettyVersion,
    "org.eclipse.jetty" % "jetty-servlet" % jettyVersion,
    "org.eclipse.jetty" % "jetty-servlets" % jettyVersion,
    "org.springframework" % "spring-webmvc" % springVersion
  )

  lazy val security = Project(
    "jasper-example",
    file("."),
    settings = buildSettings ++
      Seq (libraryDependencies ++= commonDeps) ++
      Seq (resolvers ++= Seq(
        "jasperreports" at "http://jasperreports.sourceforge.net/maven2/",
        "jaspersoft" at "http://jaspersoft.artifactoryonline.com/jaspersoft/third-party-ce-artifacts/"
      )) ++
      assemblySettings ++ 
      Seq (mergeStrategy in assembly := {
        case m if m.toLowerCase.matches("meta-inf/.*\\.sf$") => MergeStrategy.discard
        case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
        case _ => MergeStrategy.first
      }) ++
      addArtifact(Artifact("jasper-example", "all"), AssemblyKeys.assembly) ++
      Seq (jarName in assembly := "jasper-example.jar") ++
      Seq (mainClass in assembly := Some("ru.monstrs.jasper.JettyRunner"))
  )
}
