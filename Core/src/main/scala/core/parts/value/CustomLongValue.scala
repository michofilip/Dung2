package core.parts.value

import core.entities.traits.properties.{TimeCounterHolder, TurnCounterHolder, ValueHolder}
import core.repository.EntityRepository
import value.LongValue

object CustomLongValue {
    
    final case class GetLongValue(entityId: Long, name: String)
                                 (implicit entityRepository: EntityRepository) extends LongValue {
        override def get: Option[Long] = entityRepository.getById(entityId) match {
            case en: ValueHolder => en.getValue(name) match {
                case value: LongValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
    final case class GetTime(entityId: Long)
                            (implicit entityRepository: EntityRepository) extends LongValue {
        override def get: Option[Long] = entityRepository.getById(entityId) match {
            case Some(en: TimeCounterHolder) => Some(en.getTimeStamp.milliseconds)
            case _ => None
        }
    }
    
    final case class GetTurn(entityId: Long)
                            (implicit entityRepository: EntityRepository) extends LongValue {
        override def get: Option[Long] = entityRepository.getById(entityId) match {
            case Some(en: TurnCounterHolder) => Some(en.turn)
            case _ => None
        }
    }
    
}
