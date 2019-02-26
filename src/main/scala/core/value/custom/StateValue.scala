package core.value.custom

import core.entity.properties.state.State
import core.entity.properties.StateHolder
import core.entity.repositoy.EntityRepository
import core.value.Value
import json.JValue

sealed abstract class StateValue extends Value {
    override final type T = State
}

object StateValue {
    
    final case object StateNull extends StateValue {
        override def get(implicit entityHolder: EntityRepository): Option[State] = {
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
        override def get(implicit entityHolder: EntityRepository): Option[State] = {
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
        override def get(implicit entityHolder: EntityRepository): Option[State] = {
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