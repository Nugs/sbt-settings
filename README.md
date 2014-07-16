sbt-utils
=========

SBT plugin that provides common utility functions and configurations

### Features
* **DefaultsPlugin** This will provide some useful default configurations as an ```AutoPlugin``` which requires the ```JvmPlugin```.
Some of the defaults set on ```sbt.Keys``` are detailed:
    * ```shellPrompt``` set to use ```uk.gov.hmrc.ShellPrompt```
    * ```testOptions``` set to use [ScalaTest](http://www.scalatest.org/) with arguments to produce html test report
* **SbtBuildInfo** This is a configuation for [sbt-buildinfo](https://github.com/sbt/sbt-buildinfo)

### Adding sbt-utils plugin

In your project/plugins.sbt:

For AutoPlugin (sbt +0.13.5)

```scala
addSbtPlugin("uk.gov.hmrc" % "sbt-utils" % "2.0.0")
```

For the same functionality for pre-AutoPlugin style use:

```scala
addSbtPlugin("uk.gov.hmrc" % "sbt-utils" % "1.4.0")
```
