package core.parts.value.custom

import core.entities.Entity
import core.entities.properties.{PhysicsHolder, ValueHolder}
import core.entities.repositoy.EntityRepository
import core.parts.value.basic.BooleanValue
import json.JValue

object CustomBooleanValue {
    
    final case class Exists(entityId: String) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            Some(entityHolder.contains(entityId))
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    final case class GetBooleanValue(entityId: String, name: String) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            entityHolder.getById(entityId) match {
                case en: ValueHolder[_] => en.getValue(name) match {
                    case value: BooleanValue => value.get
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
    
    final case class IsSolidAtCoordinates(value: CoordinatesValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            val condition: Entity => Boolean = {
                case en: PhysicsHolder[_] if en.physics.solid => true
                case _ => false
            }
            value.get match {
                case Some(v) => Some(entityHolder.existsAtCoordinates(v, condition))
                case None => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
    final case class IsOpaqueAtCoordinates(value: CoordinatesValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            val condition: Entity => Boolean = {
                case en: PhysicsHolder[_] if en.physics.opaque => true
                case _ => false
            }
            value.get match {
                case Some(v) => Some(entityHolder.existsAtCoordinates(v, condition))
                case None => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
}