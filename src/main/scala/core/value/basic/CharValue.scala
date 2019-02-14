package core.value.basic

import core.entity.EntityHolder
import core.value.Value
import json.JValue

import scala.language.implicitConversions

abstract class CharValue extends Value {
    override type T = Char
}

object CharValue {
    implicit def char2V(value: Char): CharValue = CharConstant(value)
    
    implicit class Char2V(value: Char) {
        def toValue: CharValue = value
        
        def toCharValue: CharValue = value
        
        def toStringValue: StringValue = value.toString
    }
    
    final case object CharNull extends CharValue {
        override def get(implicit entityHolder: EntityHolder): Option[Char] = {
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
        override def get(implicit entityHolder: EntityHolder): Option[Char] = Some(value)
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
}