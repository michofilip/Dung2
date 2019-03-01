package core.parts.value.basic

import core.entity.repositoy.EntityRepository
import core.parts.value.Value
import json.JValue

import scala.language.implicitConversions

abstract class CharValue extends Value {
    override type T = Char
}

object CharValue {
    
    final case object CharNull extends CharValue {
        override def get(implicit entityHolder: EntityRepository): Option[Char] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class CharConstant(value: Char) extends CharValue {
        override def get(implicit entityHolder: EntityRepository): Option[Char] = Some(value)
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
}