package core.parts.value.custom

import core.entities.properties.{StateHolder, ValueHolder}
import core.entities.repositoy.EntityRepository
import core.parts.state.State
import core.parts.value.Value
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
                case Some(en: StateHolder[_]) => Some(en.state)
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
    
    final case class GetStateValue(entityId: String, name: String) extends StateValue {
        override def get(implicit entityHolder: EntityRepository): Option[State] = {
            entityHolder.getById(entityId) match {
                case en: ValueHolder[_] => en.getValue(name) match {
                    case value: StateValue => value.get
                    case _ => None
                }
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "name" -> name
            )
        }
    }
    
}