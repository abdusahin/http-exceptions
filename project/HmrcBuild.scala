import sbt._
import sbt.Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings
  import DefaultBuildSettings._
  import BuildDependencies._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}

  val appName = "http-exceptions"
  val appVersion = "0.2.0-SNAPSHOT"

  lazy val mongoCache = Project(appName, file("."))
    .settings(version := appVersion)
    .settings(scalaSettings: _*)
    .settings(defaultSettings(): _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(appVersion),
      libraryDependencies ++= AppDependencies(),
      resolvers := Seq(
        Opts.resolver.sonatypeReleases,
        Opts.resolver.sonatypeSnapshots
      ),
      crossScalaVersions := Seq("2.11.2", "2.10.4")
    )
    .settings(SbtBuildInfo(): _*)
    .settings(SonatypeBuild(): _*)

}

private object AppDependencies {

  val compile = Seq.empty

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test: Seq[ModuleID] = ???
  }

  object Test {
    def apply() = new TestDependencies {
      override lazy val test = Seq(
        "org.scalatest" %% "scalatest" % "2.2.1" % scope,
        "org.pegdown" % "pegdown" % "1.4.2" % scope
      )
    }.test
  }

  def apply() = compile ++ Test()
}

object SonatypeBuild {

  private def sonatypeCredentials = (for {
    username <- Option(System.getenv().get("SONATYPE_USERNAME"))
    password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
  } yield credentials += Credentials(
      "Sonatype Nexus Repository Manager",
      "oss.sonatype.org",
      username,
      password)
    ).getOrElse(credentials ++= Seq())

  def apply() = {
    Seq(
      sonatypeCredentials,
      publishTo := {
        val nexus = "https://oss.sonatype.org/"
        if (isSnapshot.value)
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
      pomExtra := (<url>https://www.gov.uk/government/organisations/hm-revenue-customs</url>
        <licenses>
          <license>
            <name>Apache 2</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          </license>
        </licenses>
        <scm>
          <connection>scm:git@github.com:hmrc/mongo-cache.git</connection>
          <developerConnection>scm:git@github.com:hmrc/mongo-cache.git</developerConnection>
          <url>git@github.com:hmrc/mongo-cache.git</url>
        </scm>
        <developers>
          <developer>
            <id>duncancrawford</id>
            <name>Duncan Crawford</name>
            <url>http://www.equalexperts.com</url>
          </developer>
          <developer>
            <id>jakobgrunig</id>
            <name>Jakob Grunig</name>
            <url>http://www.equalexperts.com</url>
          </developer>
        </developers>)
    )
  }
}

