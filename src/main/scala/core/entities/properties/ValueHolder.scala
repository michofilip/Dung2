package core.entities.properties

import core.entities.Entity
import core.parts.value.Value
import core.parts.value.basic.NullValue

trait ValueHolder[T <: ValueHolder[T]] extends Entity {
    protected val values: Map[String, Value]
    
    def getValue(name: String): Value = {
        values.getOrElse(name, NullValue)
    }
    
    def setValue(name: String, value: Value): T
    
    def removeValue(name: String): T
}