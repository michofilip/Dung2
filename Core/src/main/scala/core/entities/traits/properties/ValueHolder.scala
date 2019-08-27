package core.entities.traits.properties

import core.entities.Entity
import value.{NullValue, Value}

trait ValueHolder extends Entity {
    protected val values: Map[String, Value]
    
    def getValue(name: String): Value = {
        values.getOrElse(name, NullValue)
    }
    
    def setValue(name: String, value: Value): ValueHolder
    
    def removeValue(name: String): ValueHolder
}
