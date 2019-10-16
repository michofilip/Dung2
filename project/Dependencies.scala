import sbt._

object Dependencies {
    
    private val scalaTest = "org.scalatest" %% "scalatest" % "3.2.0-SNAP10"
    
    private val commonDependencies: Seq[ModuleID] = Seq(scalaTest % Test)
    
    val coreDependencies: Seq[ModuleID] = commonDependencies
    val commonsDependencies: Seq[ModuleID] = commonDependencies
    val valueDependencies: Seq[ModuleID] = commonDependencies
}
