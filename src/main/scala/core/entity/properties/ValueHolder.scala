package core.entity.properties

import core.entity.Entity
import core.parts.value.Value

trait ValueHolder[T <: ValueHolder[T]] extends Entity[T] {
    val value: Value
    
    def setValue(value: Value): T
}