package core.parts.value.custom

import core.entities.properties.{TimeCounterHolder, TurnCounterHolder}
import core.entities.repositoy.EntityRepository
import core.parts.value.basic.LongValue
import json.JValue

object CustomLongValue {
    
    final case class GetTime() extends LongValue {
        override def get(implicit entityHolder: EntityRepository): Option[Long] = {
            entityHolder.getById("TimeCounter") match {
                case Some(en: TimeCounterHolder) => Some(en.getTime)
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
    
    final case class GetTurn() extends LongValue {
        override def get(implicit entityHolder: EntityRepository): Option[Long] = {
            entityHolder.getById("TurnCounter") match {
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