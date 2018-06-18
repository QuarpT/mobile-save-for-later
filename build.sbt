import sbtassembly.MergeStrategy

val awsSdkVersion = "1.11.307"
val log4j2Version = "2.10.0"

name := "mobile-save-for-later"

organization := "com.gu"

version := "22"

scalaVersion := "2.12.5"

description:= "lambdas that implement save for later"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-target:jvm-1.8",
  "-Ypartial-unification",
  "-Ywarn-dead-code"
)

fork := true // was hitting deadlock, fxxund similar complaints online, disabling concurrency helps: https://github.com/sbt/sbt/issues/3022, https://github.com/mockito/mockito/issues/1067


libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.0",
  "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.307",
  "com.amazonaws" % "aws-lambda-java-log4j2" % "1.1.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.4",
  "com.amazonaws" % "aws-java-sdk-ec2" % "1.11.307",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-cbor" % "2.9.4",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.9.4",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.9.4",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4j2Version,
  "commons-io" % "commons-io" % "2.6",
  "com.gu" %% "scanamo" % "1.0.0-M6",
  "com.gu" %% "simple-configuration-ssm" % "1.4.3",
  "com.squareup.okhttp3" % "okhttp" % "3.10.0",
  "org.specs2" %% "specs2-core" % "4.0.3" % "test",
  "org.specs2" %% "specs2-scalacheck" % "4.0.3" % "test",
  "org.specs2" %% "specs2-mock" % "4.0.3" % "test"
)

dependencyOverrides ++= List(
  "commons-logging" % "commons-logging" % "1.2",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.apache.logging.log4j" % "log4j-core" % log4j2Version,
  "org.apache.logging.log4j" % "log4j-api" % log4j2Version % "provided"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Guardian Platform Bintray" at "https://dl.bintray.com/guardian/platforms"
)

assemblyMergeStrategy in assembly := {
  case "META-INF/MANIFEST.MF" => MergeStrategy.discard
  case "META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat" => new MergeFilesStrategy
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

enablePlugins(RiffRaffArtifact)

assemblyJarName := s"${name.value}.jar"
riffRaffPackageType := assembly.value
riffRaffUploadArtifactBucket := Option("riffraff-artifact")
riffRaffUploadManifestBucket := Option("riffraff-builds")
riffRaffArtifactResources += (file("cfn.yaml"), s"${name.value}-cfn/cfn.yaml")

