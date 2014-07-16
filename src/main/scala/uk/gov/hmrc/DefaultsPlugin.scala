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

object DefaultsPlugin extends AutoPlugin with ScalaTestSettings {

  import uk.gov.hmrc.GitStampPlugin._

  override def requires: Plugins = JvmPlugin
  override def trigger: PluginTrigger = allRequirements

  object autoImports {
    lazy val defaultsTargetJvm = settingKey[String]("Target Jvm")
  }

  import autoImports._

  lazy val defaultsSettings: Seq[Def.Setting[_]] = {

    defaultsTargetJvm := "jvm-1.8"

    Seq(
      scalaVersion := "2.11.1",
      scalacOptions ++= Seq(
        "-unchecked",
        "-deprecation",
        "-Xlint",
        "-language:_",
        "-target:" + defaultsTargetJvm.value,
        "-Xmax-classfile-name", "100",
        "-encoding", "UTF-8"
      ),
      retrieveManaged := true,
      initialCommands in console := "import " + organization + "._",
      shellPrompt := ShellPrompt.buildShellPrompt(version.value),
      fork in Test := false,
      isSnapshot := version.value.contains("SNAPSHOT"),
      testOptions in Test += addTestReportOption()
    ) ++ gitStampSettings
  }

  override lazy val buildSettings = inConfig(Compile)(defaultsSettings)
}

trait ScalaTestSettings {

  def addTestReportOption(directory: String = "test-reports") = {
    val testResultDir = "target/" + directory
    Tests.Argument("-o", "-u", testResultDir, "-h", testResultDir + "/html-report")
  }
}

object ScalaTestSettings extends ScalaTestSettings