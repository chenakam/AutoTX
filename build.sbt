// format: off
name := baseDirectory.value.getName
organization := "hobby.chenai.nakam"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.12"
crossScalaVersions := Seq(
  /*"2.11.7", 多余，不需要两个*/
  "2.11.12",
  /*"2.12.2", 有一些编译问题：`the interface is not a direct parent`。*/
  "2.12.12")

lazy val scalaSettings = Seq(
  scalaVersion := "2.11.12"
)

//lazy val root = Project(id = "txdsl", base = file("."))
//  .dependsOn(/*lang*/)
//  .settings(scalaSettings,
//    aggregate in update := false
//  )

// 启用对 java8 lambda 语法的支持。
scalacOptions += "-Xexperimental"

exportJars := true

offline := true

// 解决生成文档报错导致 jitpack.io 出错的问题。
publishArtifact in packageDoc := false

// 如果要用 jitpack 打包的话就加上，打完了再注掉。
resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "com.github.dedge-space" % "scala-lang" % "15e0ab6a79",
  "com.github.dedge-space" % "reflow" % "75cdaafe0b",

  "junit" % "junit" % "[4.12,)" % Test,
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP7" % Test
)
