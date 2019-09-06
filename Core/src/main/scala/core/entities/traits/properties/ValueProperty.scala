package core.entities.traits.properties

import core.entities.Entity
import value.{NullValue, Value}

trait ValueProperty extends Entity {
    protected val values: Map[String, Value]
    
    def getValue(name: String): Value = values.getOrElse(name, NullValue)
    
    def setValue(name: String, value: Value): ValueProperty
    
    def removeValue(name: String): ValueProperty
}
