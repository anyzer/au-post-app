name := "bran_job"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe"                        % "config"            % "1.3.2",
  "net.databinder.dispatch"             %% "dispatch-core"    % "0.13.4",
  "org.json4s"                          %% "json4s-native"    % "3.6.5",
  "org.json4s"                          %% "json4s-jackson"   % "3.6.5",
  "org.apache.httpcomponents"           % "httpclient"        % "4.5",
  "com.google.code.gson"                % "gson"              % "2.8.5",
  "org.scalaj"                          %% "scalaj-http"      % "2.4.1",
  "io.jvm.uuid"                         %% "scala-uuid"       % "0.3.0"
)