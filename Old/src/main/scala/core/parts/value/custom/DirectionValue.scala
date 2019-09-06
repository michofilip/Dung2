package core.parts.value.custom

import core.entities.traits.properties.{PositionHolder, ValueHolder}
import core.parts.position.Direction
import core.parts.value.Value
import core.repository.EntityRepository
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
    
    final case class GetDirection(entityId: Long) extends DirectionValue {
        override def get(implicit entityRepository: EntityRepository): Option[Direction] = {
            entityRepository.getById(entityId) match {
                case Some(en: PositionHolder) => Some(en.position.direction)
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
    
    final case class GetDirectionValue(entityId: Long, name: String) extends DirectionValue {
        override def get(implicit entityRepository: EntityRepository): Option[Direction] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
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