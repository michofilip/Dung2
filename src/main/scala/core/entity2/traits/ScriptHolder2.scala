package core.entity2.traits

import core.entity2.Entity2
import core.parts.scripts.Script

trait ScriptHolder2 extends Entity2 {
    protected val scripts: Map[String, Script]
    
    def getScript(name: String): Script = scripts.getOrElse(name, Script.emptyScript)
}