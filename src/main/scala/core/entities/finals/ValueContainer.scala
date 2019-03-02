package core.entities.finals

import core.entities.properties.ValueHolder
import core.parts.value.Value
import json.{JValue, MyJ}

final class ValueContainer(override val id: String,
                           override val value: Value
                          ) extends ValueHolder[ValueContainer] {
    
    private def update(value: Value = value): ValueContainer = {
        new ValueContainer(id, value)
    }
    
    override def setValue(value: Value): ValueContainer = {
        update(value = value)
    }
    
    override def toJSON: JValue = {
        MyJ.jObject(
            "class" -> "ValueContainer",
            "id" -> id,
            "value" -> value
        )
    }
}