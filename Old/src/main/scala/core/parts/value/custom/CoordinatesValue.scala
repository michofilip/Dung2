package core.parts.value.custom

import core.entities.traits.properties.{PositionHolder, ValueHolder}
import core.parts.position.Coordinates
import core.parts.value.Value
import core.repository.EntityRepository
import json.JValue

abstract class CoordinatesValue extends Value {
    override final type T = Coordinates
}

object CoordinatesValue {
    
    final case object CoordinatesNull extends CoordinatesValue {
        override def get(implicit entityRepository: EntityRepository): Option[Coordinates] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class CoordinatesConstant(value: Coordinates) extends CoordinatesValue {
        override def get(implicit entityRepository: EntityRepository): Option[Coordinates] = {
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
    
    final case class GetCoordinates(entityId: Long) extends CoordinatesValue {
        override def get(implicit entityRepository: EntityRepository): Option[Coordinates] = {
            entityRepository.getById(entityId) match {
                case Some(en: PositionHolder) => Some(en.position.coordinates)
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
    
    final case class GetCoordinatesValue(entityId: Long, name: String) extends CoordinatesValue {
        override def get(implicit entityRepository: EntityRepository): Option[Coordinates] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
                    case value: CoordinatesValue => value.get
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