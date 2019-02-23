package core.entity.properties

import core.entity.Entity
import core.program.Script

trait ScriptHolder extends Entity {
    protected val scripts: Map[String, Script]
    
    def getScript(name: String): Script = scripts.getOrElse(name, Script.emptyScript)
}