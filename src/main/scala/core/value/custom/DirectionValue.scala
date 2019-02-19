package core.value.custom

import core.entity.Entity.PositionHolder
import core.entity.EntityHolder
import core.entity.properties.position.Direction
import core.value.Value
import core.value.basic.Implicits._
import core.value.basic.StringValue
import json.JValue

abstract class DirectionValue extends Value {
    override final type T = Direction
}

object DirectionValue {
//    implicit def direction2V(value: Direction): DirectionConstant = DirectionConstant(value)
//
//    implicit class Direction2V(value: Direction) {
//        def toValue: DirectionValue = value
//
//        def toCoordinatesValue: DirectionValue = value
//
//        def toStringValue: StringValue = value.toString
//    }
    
    final case object DirectionNull extends DirectionValue {
        override def get(implicit entityHolder: EntityHolder): Option[Direction] = {
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
        override def get(implicit entityHolder: EntityHolder): Option[Direction] = {
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
        override def get(implicit entityHolder: EntityHolder): Option[Direction] = {
            entityHolder.getById(entityId) match {
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
    
}