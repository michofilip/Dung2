package core.entities.finals.nonmap

import core.entities.traits.properties.ValueHolder
import core.parts.value.Value

final class ValueContainer(override val id: Long,
                           override protected val values: Map[String, Value]
                           ) extends ValueHolder {
    
    private def update(values: Map[String, Value] = values): ValueContainer = {
        new ValueContainer(id, values)
    }
    
    override def setValue(name: String, value: Value): ValueContainer = {
        update(values = values + (name -> value))
    }
    
    override def removeValue(name: String): ValueContainer = {
        update(values = values - name)
    }
}