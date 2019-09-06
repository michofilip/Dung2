package core.parts.value.custom

import core.entities.traits.properties.{TimeCounterHolder, TurnCounterHolder, ValueHolder}
import core.parts.value.basic.LongValue
import core.repository.EntityRepository
import json.JValue

object CustomLongValue {
    
    final case class GetLongValue(entityId: Long, name: String) extends LongValue {
        override def get(implicit entityRepository: EntityRepository): Option[Long] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
                    case value: LongValue => value.get
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
    
    final case class GetTime(entityId: Long) extends LongValue {
        override def get(implicit entityRepository: EntityRepository): Option[Long] = {
            entityRepository.getById(entityId) match {
                case Some(en: TimeCounterHolder) => Some(en.getTimeStamp.milliseconds)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class GetTurn(entityId: Long) extends LongValue {
        override def get(implicit entityRepository: EntityRepository): Option[Long] = {
            entityRepository.getById(entityId) match {
                case Some(en: TurnCounterHolder) => Some(en.turn)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
}