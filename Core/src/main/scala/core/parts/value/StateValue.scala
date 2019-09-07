package core.parts.value

import core.entities.traits.properties.{StateProperty, ValueProperty}
import core.parts.state.State
import core.repository.EntityRepository
import value.Value

sealed abstract class StateValue extends Value {
    override final protected type T = State
    
    override final def calculate: StateValue = StateValue.StateCalculate(this)
}

object StateValue {
    
    final case object StateNull extends StateValue {
        override def get: Option[State] = None
    }
    
    final case class StateConstant(value: State) extends StateValue {
        override def get: Option[State] = Some(value)
    }
    
    final case class StateCalculate(value: StateValue) extends StateValue {
        private val calculated: Option[State] = value.get
        
        override def get: Option[State] = calculated
    }
    
    final case class GetState(entityId: Long)(implicit entityRepository: EntityRepository) extends StateValue {
        override def get: Option[State] = entityRepository.getById(entityId) match {
            case Some(en: StateProperty) => Some(en.state)
            case _ => None
        }
    }
    
    final case class GetStateValue(entityId: Long, name: String)(implicit entityRepository: EntityRepository) extends StateValue {
        override def get: Option[State] = entityRepository.getById(entityId) match {
            case en: ValueProperty => en.getValue(name) match {
                case value: StateValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}