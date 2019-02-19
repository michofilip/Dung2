package core.value.basic

import core.entity.EntityHolder
import core.value.Value
import core.value.basic.DoubleValue._
import core.value.basic.FloatValue._
import core.value.basic.Implicits._
import core.value.basic.LongValue._
import json.JValue

import scala.language.implicitConversions

abstract class LongValue extends Value with NumericValue {
    override final type T = Long
    
    final def unary_+ : LongValue = this
    
    final def unary_- : LongValue = LongNegate(this)
    
    // add
    final def +(that: ByteValue): LongValue = LongAdd(this, that)
    
    final def +(that: ShortValue): LongValue = LongAdd(this, that)
    
    final def +(that: IntValue): LongValue = LongAdd(this, that)
    
    final def +(that: LongValue): LongValue = LongAdd(this, that)
    
    final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
    
    final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
    
    // subtract
    final def -(that: ByteValue): LongValue = LongSubtract(this, that)
    
    final def -(that: ShortValue): LongValue = LongSubtract(this, that)
    
    final def -(that: IntValue): LongValue = LongSubtract(this, that)
    
    final def -(that: LongValue): LongValue = LongSubtract(this, that)
    
    final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
    
    final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
    
    // multiply
    final def *(that: ByteValue): LongValue = LongMultiply(this, that)
    
    final def *(that: ShortValue): LongValue = LongMultiply(this, that)
    
    final def *(that: IntValue): LongValue = LongMultiply(this, that)
    
    final def *(that: LongValue): LongValue = LongMultiply(this, that)
    
    final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
    
    final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
    
    
    // divide
    final def /(that: ByteValue): LongValue = LongDivide(this, that)
    
    final def /(that: ShortValue): LongValue = LongDivide(this, that)
    
    final def /(that: IntValue): LongValue = LongDivide(this, that)
    
    final def /(that: LongValue): LongValue = LongDivide(this, that)
    
    final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
    
    final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
    
    // modulo
    final def %(that: ByteValue): LongValue = LongMod(this, that)
    
    final def %(that: ShortValue): LongValue = LongMod(this, that)
    
    final def %(that: IntValue): LongValue = LongMod(this, that)
    
    final def %(that: LongValue): LongValue = LongMod(this, that)
}

object LongValue {
    
    final case object LongNull extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class LongConstant(value: Long) extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
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
    
    final case class LongNegate(value: LongValue) extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
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
    
    final case class LongAdd(value1: LongValue, value2: LongValue) extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
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
    
    final case class LongSubtract(value1: LongValue, value2: LongValue) extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
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
    
    final case class LongMultiply(value1: LongValue, value2: LongValue) extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
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
    
    final case class LongDivide(value1: LongValue, value2: LongValue) extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
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
    
    final case class LongMod(value1: LongValue, value2: LongValue) extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 % v2)
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
    
    final case class NumericToLong(value: NumericValue) extends LongValue {
        override def get(implicit entityHolder: EntityHolder): Option[Long] = {
            value.get match {
                case Some(v: Byte) => Some(v.toLong)
                case Some(v: Short) => Some(v.toLong)
                case Some(v: Int) => Some(v.toLong)
                case Some(v: Long) => Some(v.toLong)
                case Some(v: Float) => Some(v.toLong)
                case Some(v: Double) => Some(v.toLong)
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