package core.value.custom

import core.entity.properties.position.Coordinates
import core.entity.properties.PositionHolder
import core.entity.repositoy.EntityRepository
import core.value.Value
import json.JValue

abstract class CoordinatesValue extends Value {
    override final type T = Coordinates
}

object CoordinatesValue {
    
    final case object CoordinatesNull extends CoordinatesValue {
        override def get(implicit entityHolder: EntityRepository): Option[Coordinates] = {
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
        override def get(implicit entityHolder: EntityRepository): Option[Coordinates] = {
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
    
    final case class GetCoordinates(entityId: String) extends CoordinatesValue {
        override def get(implicit entityHolder: EntityRepository): Option[Coordinates] = {
            entityHolder.getById(entityId) match {
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
    
}