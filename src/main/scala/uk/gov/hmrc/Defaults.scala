/*
 * Copyright 2014 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.hmrc

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object Defaults extends AutoPlugin {

  import uk.gov.hmrc.GitStampPlugin._
  import net.virtualvoid.sbt.graph.Plugin.graphSettings

  override def requires: Plugins = JvmPlugin
  override def trigger: PluginTrigger = allRequirements

  object autoImports {
    val defaultsTargetJvm = settingKey[Boolean]("Target Jvm")
    val defaultsBuildShellPrompt = settingKey[(State) => String]("Build shell prompt")
    val defaultsAddScalaTestReports = settingKey[Boolean]("Add scalatest report")
  }

  import autoImports._

  lazy val defaultsSettings: Seq[Def.Setting[_]] = {

    defaultsBuildShellPrompt := ShellPrompt.buildShellPrompt(version.value)

    defaultsAddScalaTestReports := true

    Seq(
      scalaVersion := "2.11.1",
      scalacOptions ++= Seq(
        "-unchecked",
        "-deprecation",
        "-Xlint",
        "-language:_",
        "-target:" + defaultsTargetJvm,
        "-Xmax-classfile-name", "100",
        "-encoding", "UTF-8"
      ),
      retrieveManaged := true,
      initialCommands in console := "import " + organization + "._",
      shellPrompt := defaultsBuildShellPrompt.value,
      parallelExecution in Test := false,
      fork in Test := false,
      isSnapshot := version.value.contains("SNAPSHOT"),
      defaultsAddScalaTestReports := true,
      testOptions in Test += addTestReportOption()
    ) ++ gitStampSettings ++ graphSettings
  }

  def addTestReportOption(directory: String = "test-reports") = {
    val testResultDir = "target/" + directory
    Tests.Argument("-o", "-u", testResultDir, "-h", testResultDir + "/html-report")
  }

}
