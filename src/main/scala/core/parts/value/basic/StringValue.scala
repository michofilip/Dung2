package core.parts.value.basic

import core.entities.repositoy.EntityRepository
import core.parts.value.Value
import core.parts.value.basic.StringValue.{Concatenate, Length}
import json.JValue

abstract class StringValue extends Value {
    override type T = String
    
    final def +(that: StringValue): StringValue = Concatenate(this, that)
    
    final def length: IntValue = Length(this)
}

object StringValue {
    
    final case object StringNull extends StringValue {
        override def get(implicit entityRepository: EntityRepository): Option[String] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class StringConstant(value: String) extends StringValue {
        override def get(implicit entityRepository: EntityRepository): Option[String] = {
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
    
    final case class Concatenate(value1: StringValue, value2: StringValue) extends StringValue {
        override def get(implicit entityRepository: EntityRepository): Option[String] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 + v2)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class Length(value: StringValue) extends IntValue {
        override def get(implicit entityRepository: EntityRepository): Option[Int] = {
            value.get match {
                case Some(v) => Some(v.length)
                case _ => None
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
    
    final case class NumericToString(value: NumericValue) extends StringValue {
        override def get(implicit entityRepository: EntityRepository): Option[String] = {
            value.get match {
                case Some(v: Byte) => Some(v.toString)
                case Some(v: Short) => Some(v.toString)
                case Some(v: Int) => Some(v.toString)
                case Some(v: Long) => Some(v.toString)
                case Some(v: Float) => Some(v.toString)
                case Some(v: Double) => Some(v.toString)
                case _ => None
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