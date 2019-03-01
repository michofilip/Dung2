package core.entity.properties

import core.entity.Entity
import core.parts.value.Value

trait ValueHolder extends Entity {
    val value: Value
    
    def setValue(value: Value): T
}