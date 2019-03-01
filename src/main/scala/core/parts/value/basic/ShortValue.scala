package core.parts.value.basic

import core.entity.repositoy.EntityRepository
import core.parts.value.Value
import core.parts.value.basic.DoubleValue._
import core.parts.value.basic.FloatValue._
import core.parts.value.basic.Implicits._
import core.parts.value.basic.IntValue._
import core.parts.value.basic.LongValue._
import core.parts.value.basic.ShortValue._
import json.JValue

import scala.language.implicitConversions

abstract class ShortValue extends Value with NumericValue {
    override final type T = Short
    
    final def unary_+ : ShortValue = this
    
    final def unary_- : ShortValue = ShortNegate(this)
    
    // add
    final def +(that: ByteValue): ShortValue = ShortAdd(this, that)
    
    final def +(that: ShortValue): ShortValue = ShortAdd(this, that)
    
    final def +(that: IntValue): IntValue = IntAdd(this, that)
    
    final def +(that: LongValue): LongValue = LongAdd(this, that)
    
    final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
    
    final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
    
    // subtract
    final def -(that: ByteValue): ShortValue = ShortSubtract(this, that)
    
    final def -(that: ShortValue): ShortValue = ShortSubtract(this, that)
    
    final def -(that: IntValue): IntValue = IntSubtract(this, that)
    
    final def -(that: LongValue): LongValue = LongSubtract(this, that)
    
    final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
    
    final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
    
    // multiply
    final def *(that: ByteValue): ShortValue = ShortMultiply(this, that)
    
    final def *(that: ShortValue): ShortValue = ShortMultiply(this, that)
    
    final def *(that: IntValue): IntValue = IntMultiply(this, that)
    
    final def *(that: LongValue): LongValue = LongMultiply(this, that)
    
    final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
    
    final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
    
    // divide
    final def /(that: ByteValue): ShortValue = ShortDivide(this, that)
    
    final def /(that: ShortValue): ShortValue = ShortDivide(this, that)
    
    final def /(that: IntValue): IntValue = IntDivide(this, that)
    
    final def /(that: LongValue): LongValue = LongDivide(this, that)
    
    final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
    
    final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
    
    // modulo
    final def %(that: ByteValue): ShortValue = ShortMod(this, that)
    
    final def %(that: ShortValue): ShortValue = ShortMod(this, that)
    
    final def %(that: IntValue): IntValue = IntMod(this, that)
    
    final def %(that: LongValue): LongValue = LongMod(this, that)
}

object ShortValue {
    
    final case object ShortNull extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class ShortConstant(value: Short) extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
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
    
    final case class ShortNegate(value: ShortValue) extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
            value.get match {
                case Some(v) => Some((-v).toShort)
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
    
    final case class ShortAdd(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some((v1 + v2).toShort)
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
    
    final case class ShortSubtract(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some((v1 - v2).toShort)
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
    
    final case class ShortMultiply(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some((v1 * v2).toShort)
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
    
    final case class ShortDivide(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some((v1 / v2).toShort)
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
    
    final case class ShortMod(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some((v1 % v2).toShort)
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
    
    final case class NumericToShort(value: NumericValue) extends ShortValue {
        override def get(implicit entityHolder: EntityRepository): Option[Short] = {
            value.get match {
                case Some(v: Byte) => Some(v.toShort)
                case Some(v: Short) => Some(v.toShort)
                case Some(v: Int) => Some(v.toShort)
                case Some(v: Long) => Some(v.toShort)
                case Some(v: Float) => Some(v.toShort)
                case Some(v: Double) => Some(v.toShort)
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