package core.entities.properties

import core.entities.Entity
import core.parts.value.Value

trait ValueHolder[T <: ValueHolder[T]] extends Entity {
    val value: Value
    
    def setValue(value: Value): T
}