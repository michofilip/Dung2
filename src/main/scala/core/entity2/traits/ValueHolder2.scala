package core.entity2.traits

import core.entity2.Entity2
import core.parts.value.Value
import core.parts.value.basic.NullValue

trait ValueHolder2 extends Entity2 {
    protected val values: Map[String, Value]
    
    def getValue(name: String): Value = {
        values.getOrElse(name, NullValue)
    }
    
    def setValue(name: String, value: Value): ValueHolder2
    
    def removeValue(name: String): ValueHolder2
}