package core.value.basic

import core.entity.EntityHolder
import core.value.Value
import core.value.basic.DoubleValue._
import core.value.basic.FloatValue._
import core.value.basic.IntValue._
import core.value.basic.LongValue._
import json.JValue

import scala.language.implicitConversions

abstract class IntValue extends Value with NumericValue {
    override final type T = Int
    
    final def unary_+ : IntValue = this
    
    final def unary_- : IntValue = IntNegate(this)
    
    // add
    final def +(that: ByteValue): IntValue = IntAdd(this, that)
    
    final def +(that: ShortValue): IntValue = IntAdd(this, that)
    
    final def +(that: IntValue): IntValue = IntAdd(this, that)
    
    final def +(that: LongValue): LongValue = LongAdd(this, that)
    
    final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
    
    final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
    
    // subtract
    final def -(that: ByteValue): IntValue = IntSubtract(this, that)
    
    final def -(that: ShortValue): IntValue = IntSubtract(this, that)
    
    final def -(that: IntValue): IntValue = IntSubtract(this, that)
    
    final def -(that: LongValue): LongValue = LongSubtract(this, that)
    
    final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
    
    final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
    
    // multiply
    final def *(that: ByteValue): IntValue = IntMultiply(this, that)
    
    final def *(that: ShortValue): IntValue = IntMultiply(this, that)
    
    final def *(that: IntValue): IntValue = IntMultiply(this, that)
    
    final def *(that: LongValue): LongValue = LongMultiply(this, that)
    
    final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
    
    final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
    
    // divide
    final def /(that: ByteValue): IntValue = IntDivide(this, that)
    
    final def /(that: ShortValue): IntValue = IntDivide(this, that)
    
    final def /(that: IntValue): IntValue = IntDivide(this, that)
    
    final def /(that: LongValue): LongValue = LongDivide(this, that)
    
    final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
    
    final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
    
    // modulo
    final def %(that: ByteValue): IntValue = IntMod(this, that)
    
    final def %(that: ShortValue): IntValue = IntMod(this, that)
    
    final def %(that: IntValue): IntValue = IntMod(this, that)
    
    final def %(that: LongValue): LongValue = LongMod(this, that)
}

object IntValue {
    implicit def num2i(value: NumericValue): IntValue = NumericToInt(value)
    
    implicit def i2V(value: Int): IntValue = IntConstant(value)
    
    implicit class I2V(value: Int) {
        def toValue: IntValue = value
        
        def toByteValue: ByteValue = value.toByte
        
        def toShortValue: ShortValue = value.toShort
        
        def toIntValue: IntValue = value.toInt
        
        def toLongValue: LongValue = value.toLong
        
        def toFloatValue: FloatValue = value.toFloat
        
        def toDoubleValue: DoubleValue = value.toDouble
        
        def toStringValue: StringValue = value.toString
    }
    
    final case object IntNull extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class IntConstant(value: Int) extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
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
    
    final case class IntNegate(value: IntValue) extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
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
    
    final case class IntAdd(value1: IntValue, value2: IntValue) extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
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
    
    final case class IntSubtract(value1: IntValue, value2: IntValue) extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
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
    
    final case class IntMultiply(value1: IntValue, value2: IntValue) extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
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
    
    final case class IntDivide(value1: IntValue, value2: IntValue) extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
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
    
    final case class IntMod(value1: IntValue, value2: IntValue) extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
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
    
    final case class NumericToInt(value: NumericValue) extends IntValue {
        override def get(implicit entityHolder: EntityHolder): Option[Int] = {
            value.get match {
                case Some(v: Byte) => Some(v.toInt)
                case Some(v: Short) => Some(v.toInt)
                case Some(v: Int) => Some(v.toInt)
                case Some(v: Long) => Some(v.toInt)
                case Some(v: Float) => Some(v.toInt)
                case Some(v: Double) => Some(v.toInt)
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