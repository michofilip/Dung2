package core.entity.traits

import core.entity.Entity
import core.value.Value

trait ValueHolder extends Entity {
    val value: Value
    
    def setValue(value: Value): T
}