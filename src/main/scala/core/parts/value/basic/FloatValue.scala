package core.parts.value.basic

import core.entity.repositoy.EntityRepository
import core.parts.value.Value
import core.parts.value.basic.DoubleValue._
import core.parts.value.basic.FloatValue._
import core.parts.value.basic.Implicits._
import json.JValue

import scala.language.implicitConversions

abstract class FloatValue extends Value with NumericValue {
    override type T = Float
    
    final def unary_+ : FloatValue = this
    
    final def unary_- : FloatValue = FloatNegate(this)
    
    // add
    final def +(that: ByteValue): FloatValue = FloatAdd(this, that)
    
    final def +(that: ShortValue): FloatValue = FloatAdd(this, that)
    
    final def +(that: IntValue): FloatValue = FloatAdd(this, that)
    
    final def +(that: LongValue): FloatValue = FloatAdd(this, that)
    
    final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
    
    final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
    
    // subtract
    final def -(that: ByteValue): FloatValue = FloatSubtract(this, that)
    
    final def -(that: ShortValue): FloatValue = FloatSubtract(this, that)
    
    final def -(that: IntValue): FloatValue = FloatSubtract(this, that)
    
    final def -(that: LongValue): FloatValue = FloatSubtract(this, that)
    
    final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
    
    final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
    
    // multiply
    final def *(that: ByteValue): FloatValue = FloatMultiply(this, that)
    
    final def *(that: ShortValue): FloatValue = FloatMultiply(this, that)
    
    final def *(that: IntValue): FloatValue = FloatMultiply(this, that)
    
    final def *(that: LongValue): FloatValue = FloatMultiply(this, that)
    
    final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
    
    final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
    
    // divide
    final def /(that: ByteValue): FloatValue = FloatDivide(this, that)
    
    final def /(that: ShortValue): FloatValue = FloatDivide(this, that)
    
    final def /(that: IntValue): FloatValue = FloatDivide(this, that)
    
    final def /(that: LongValue): FloatValue = FloatDivide(this, that)
    
    final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
    
    final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
}

object FloatValue {
    
    final case object FloatNull extends FloatValue {
        override def get(implicit entityHolder: EntityRepository): Option[Float] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class FloatConstant(value: Float) extends FloatValue {
        override def get(implicit entityHolder: EntityRepository): Option[Float] = {
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
    
    final case class FloatNegate(value: FloatValue) extends FloatValue {
        override def get(implicit entityHolder: EntityRepository): Option[Float] = {
            value.get match {
                case Some(v) => Some(-v)
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
    
    final case class FloatAdd(value1: FloatValue, value2: FloatValue) extends FloatValue {
        override def get(implicit entityHolder: EntityRepository): Option[Float] = {
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
    
    final case class FloatSubtract(value1: FloatValue, value2: FloatValue) extends FloatValue {
        override def get(implicit entityHolder: EntityRepository): Option[Float] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 - v2)
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
    
    final case class FloatMultiply(value1: FloatValue, value2: FloatValue) extends FloatValue {
        override def get(implicit entityHolder: EntityRepository): Option[Float] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 * v2)
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
    
    final case class FloatDivide(value1: FloatValue, value2: FloatValue) extends FloatValue {
        override def get(implicit entityHolder: EntityRepository): Option[Float] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 / v2)
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
    
    final case class NumericToFloat(value: NumericValue) extends FloatValue {
        override def get(implicit entityHolder: EntityRepository): Option[Float] = {
            value.get match {
                case Some(v: Byte) => Some(v.toFloat)
                case Some(v: Short) => Some(v.toFloat)
                case Some(v: Int) => Some(v.toFloat)
                case Some(v: Long) => Some(v.toFloat)
                case Some(v: Float) => Some(v.toFloat)
                case Some(v: Double) => Some(v.toFloat)
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
