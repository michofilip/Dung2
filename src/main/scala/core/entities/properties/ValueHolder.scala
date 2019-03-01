package core.entities.properties

import core.entities.Entity
import core.parts.value.Value

trait ValueHolder[T <: ValueHolder[T]] extends Entity[T] {
    val value: Value
    
    def setValue(value: Value): T
}