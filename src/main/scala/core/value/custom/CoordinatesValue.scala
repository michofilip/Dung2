package core.value.custom

import core.entity.Entity.PositionHolder
import core.entity.EntityHolder
import core.entity.properties.position.Coordinates
import core.value.Value
import core.value.basic.StringValue
import json.JValue

abstract class CoordinatesValue extends Value {
    override final type T = Coordinates
}

object CoordinatesValue {
    implicit def coordinates2V(value: Coordinates): CoordinatesValue = CoordinatesConstant(value)
    
    implicit class Coordinates2V(value: Coordinates) {
        def toValue: CoordinatesValue = value
        
        def toCoordinatesValue: CoordinatesValue = value
        
        def toStringValue: StringValue = value.toString
    }
    
    final case class CoordinatesConstant(value: Coordinates) extends CoordinatesValue {
        override def get(implicit entityHolder: EntityHolder): Option[Coordinates] = {
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
        override def get(implicit entityHolder: EntityHolder): Option[Coordinates] = {
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