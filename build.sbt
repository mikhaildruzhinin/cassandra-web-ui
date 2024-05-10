ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"
ThisBuild / organization     := "ru.mikhaildruzhinin"
ThisBuild / organizationName := "mikhaildruzhinin"

val scalatraVersion = "3.0.0"

lazy val root = (project in file("."))
  .settings(
    name := "cassandra-web-ui",
    libraryDependencies := Seq(
      "com.typesafe.play" %% "twirl-api" % "1.6.6",
      "org.scalatra" %% "scalatra-jakarta" % scalatraVersion,
      "org.scalatra" %% "scalatra-json-jakarta" % scalatraVersion,
      "org.scalatra" %% "scalatra-forms-jakarta" % scalatraVersion,
      "org.scalatra" %% "scalatra-scalatest-jakarta" % scalatraVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.5.6" % "runtime",
      "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided",
      "org.eclipse.jetty" % "jetty-webapp" % "11.0.20", // % "container;compile",
      "com.datastax.oss" % "java-driver-core" % "4.17.0"
    )
  )

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
