package core.parts.value.custom

import core.entities.traits.properties.{StateHolder, ValueHolder}
import core.parts.state.State
import core.parts.value.Value
import core.repository.EntityRepository
import json.JValue

sealed abstract class StateValue extends Value {
    override final type T = State
}

object StateValue {
    
    final case object StateNull extends StateValue {
        override def get(implicit entityRepository: EntityRepository): Option[State] = {
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
        override def get(implicit entityRepository: EntityRepository): Option[State] = {
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
    
    final case class GetState(entityId: Long) extends StateValue {
        override def get(implicit entityRepository: EntityRepository): Option[State] = {
            entityRepository.getById(entityId) match {
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
    
    final case class GetStateValue(entityId: Long, name: String) extends StateValue {
        override def get(implicit entityRepository: EntityRepository): Option[State] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
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