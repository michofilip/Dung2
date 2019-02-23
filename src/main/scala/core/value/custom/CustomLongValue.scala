package core.value.custom

import core.entity.EntityHolder
import core.entity.properties.{TimeHolder, TurnHolder}
import core.value.basic.LongValue
import json.JValue

object CustomLongValue {
    
    final case class GetTime() extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
            entityHolder.getById("TimeCounter") match {
                case Some(en: TimeHolder) => Some(en.getTime)
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
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
            entityHolder.getById("TurnCounter") match {
                case Some(en: TurnHolder) => Some(en.turn)
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