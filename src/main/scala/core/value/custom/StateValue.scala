package core.value.custom

import core.entity.EntityHolder
import core.entity.properties.state.State
import core.entity.traits.StateHolder
import core.value.Value
import json.JValue

sealed abstract class StateValue extends Value {
    override final type T = State
}

object StateValue {
    
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