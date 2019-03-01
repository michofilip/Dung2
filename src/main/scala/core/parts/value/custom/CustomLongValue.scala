package core.parts.value.custom

import core.entities.properties.{TimeCounterHolder, TurnCounterHolder}
import core.entities.repositoy.EntityRepository
import core.parts.value.basic.LongValue
import json.JValue

object CustomLongValue {
    
    final case class GetTime(entityId: String) extends LongValue {
        override def get(implicit entityHolder: EntityRepository): Option[Long] = {
            entityHolder.getById(entityId) match {
                case Some(en: TimeCounterHolder[_]) => Some(en.getTime)
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
    
    final case class GetTurn(entityId: String) extends LongValue {
        override def get(implicit entityHolder: EntityRepository): Option[Long] = {
            entityHolder.getById(entityId) match {
                case Some(en: TurnCounterHolder[_]) => Some(en.turn)
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