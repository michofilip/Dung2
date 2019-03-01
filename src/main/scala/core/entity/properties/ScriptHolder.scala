package core.entity.properties

import core.entity.Entity
import core.parts.program.Script

trait ScriptHolder[T <: ScriptHolder[T]] extends Entity[T] {
    protected val scripts: Map[String, Script]
    
    def getScript(name: String): Script = scripts.getOrElse(name, Script.emptyScript)
}