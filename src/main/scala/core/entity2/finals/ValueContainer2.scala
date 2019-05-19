package core.entity2.finals

import core.entity2.traits.ValueHolder2
import core.parts.value.Value

final class ValueContainer2(override val id: Long,
                            override protected val values: Map[String, Value]
                           ) extends ValueHolder2 {
    
    private def update(values: Map[String, Value] = values): ValueContainer2 = {
        new ValueContainer2(id, values)
    }
    
    override def setValue(name: String, value: Value): ValueContainer2 = {
        update(values = values + (name -> value))
    }
    
    override def removeValue(name: String): ValueContainer2 = {
        update(values = values - name)
    }
}