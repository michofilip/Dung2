package core.entities.traits.properties

import core.entities.Entity
import core.parts.scripts.Script

trait ScriptProperty extends Entity {
    protected val scripts: Map[String, Script]
    
    def getScript(name: String): Script = scripts.getOrElse(name, Script.emptyScript)
}
