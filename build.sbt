name := """play_admin"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test
libraryDependencies += jdbc
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"
libraryDependencies += "com.typesafe.play" %% "anorm" % "2.5.3"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"

resolvers += "Aliyun" at "http://maven.aliyun.com/nexus/content/groups/public/"
