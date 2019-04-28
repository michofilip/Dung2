package core.parts.value.custom

import core.entities.properties.{PositionHolder, ValueHolder}
import core.entities.repositoy.EntityRepository
import core.parts.position.Direction
import core.parts.value.Value
import json.JValue

abstract class DirectionValue extends Value {
    override final type T = Direction
}

object DirectionValue {
    
    final case object DirectionNull extends DirectionValue {
        override def get(implicit entityRepository: EntityRepository): Option[Direction] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class DirectionConstant(value: Direction) extends DirectionValue {
        override def get(implicit entityRepository: EntityRepository): Option[Direction] = {
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
    
    final case class GetDirection(entityId: String) extends DirectionValue {
        override def get(implicit entityRepository: EntityRepository): Option[Direction] = {
            entityRepository.getById(entityId) match {
                case Some(en: PositionHolder[_]) => Some(en.position.direction)
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
    
    final case class GetDirectionValue(entityId: String, name: String) extends DirectionValue {
        override def get(implicit entityRepository: EntityRepository): Option[Direction] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder[_] => en.getValue(name) match {
                    case value: DirectionValue => value.get
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