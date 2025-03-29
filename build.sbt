val toolkitV = "0.1.29"
val toolkit = "org.typelevel" %% "toolkit" % toolkitV
// val toolkitTest = "org.typelevel" %% "toolkit-test" % toolkitV

ThisBuild / scalaVersion := "3.6.4"
libraryDependencies += toolkit
// libraryDependencies += (toolkitTest % Test)
libraryDependencies += "com.github.j-mie6" %% "parsley" % "4.6.0"
libraryDependencies += "com.github.j-mie6" %% "parsley-cats" % "1.5.0"
libraryDependencies += "com.disneystreaming" %% "weaver-cats" % "0.8.4" % Test
libraryDependencies += "com.disneystreaming" %% "weaver-scalacheck" % "0.8.4" % Test

testFrameworks += new TestFramework("weaver.framework.CatsEffect")
