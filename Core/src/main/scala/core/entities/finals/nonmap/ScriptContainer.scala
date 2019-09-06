package core.entities.finals.nonmap

import core.entities.traits.properties.ScriptProperty
import core.parts.scripts.Script

class ScriptContainer(override val id: Long, override protected val scripts: Map[String, Script]) extends ScriptProperty