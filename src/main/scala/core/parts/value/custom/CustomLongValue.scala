package core.parts.value.custom

import core.entities.properties.{TimeCounterHolder, TurnCounterHolder, ValueHolder}
import core.entities.repositoy.EntityRepository
import core.parts.value.basic.LongValue
import json.JValue

object CustomLongValue {
    
    final case class GetLongValue(entityId: String, name: String) extends LongValue {
        override def get(implicit entityHolder: EntityRepository): Option[Long] = {
            entityHolder.getById(entityId) match {
                case en: ValueHolder[_] => en.getValue(name) match {
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