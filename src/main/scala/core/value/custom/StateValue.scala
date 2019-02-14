package core.value.custom

import core.entity.Entity.StateHolder
import core.entity.EntityHolder
import core.entity.properties.state.State
import core.value.Value
import core.value.basic.StringValue
import json.JValue

sealed abstract class StateValue extends Value {
    override final type T = State
}

object StateValue {
    implicit def state2V(value: State): StateValue = StateConstant(value)
    
    implicit class State2V(value: State) {
        def toValue: StateValue = value
        
        def toStateValue: StateValue = value
        
        def toStringValue: StringValue = value.toString
    }
    
    final case object StateNull extends StateValue {
        override def get(implicit entityHolder: EntityHolder): Option[State] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class StateConstant(value: State) extends StateValue {
        override def get(implicit entityHolder: EntityHolder): Option[State] = {
            Some(value)
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
    final case class GetState(entityId: String) extends StateValue {
        override def get(implicit entityHolder: EntityHolder): Option[State] = {
            entityHolder.getById(entityId) match {
                case Some(en: StateHolder) => Some(en.state)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
}