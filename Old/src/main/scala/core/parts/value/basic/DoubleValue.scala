package core.parts.value.basic

import core.repository.EntityRepository
import core.parts.value.Value
import core.parts.value.basic.DoubleValue._
import core.parts.value.basic.Implicits._
import json.JValue

import scala.language.implicitConversions

abstract class DoubleValue extends Value with NumericValue {
    override type T = Double
    
    final def unary_+ : DoubleValue = this
    
    final def unary_- : DoubleValue = DoubleNegate(this)
    
    // add
    final def +(that: ByteValue): DoubleValue = DoubleAdd(this, that)
    
    final def +(that: ShortValue): DoubleValue = DoubleAdd(this, that)
    
    final def +(that: IntValue): DoubleValue = DoubleAdd(this, that)
    
    final def +(that: LongValue): DoubleValue = DoubleAdd(this, that)
    
    final def +(that: FloatValue): DoubleValue = DoubleAdd(this, that)
    
    final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
    
    // subtract
    final def -(that: ByteValue): DoubleValue = DoubleSubtract(this, that)
    
    final def -(that: ShortValue): DoubleValue = DoubleSubtract(this, that)
    
    final def -(that: IntValue): DoubleValue = DoubleSubtract(this, that)
    
    final def -(that: LongValue): DoubleValue = DoubleSubtract(this, that)
    
    final def -(that: FloatValue): DoubleValue = DoubleSubtract(this, that)
    
    final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
    
    // multiply
    final def *(that: ByteValue): DoubleValue = DoubleMultiply(this, that)
    
    final def *(that: ShortValue): DoubleValue = DoubleMultiply(this, that)
    
    final def *(that: IntValue): DoubleValue = DoubleMultiply(this, that)
    
    final def *(that: LongValue): DoubleValue = DoubleMultiply(this, that)
    
    final def *(that: FloatValue): DoubleValue = DoubleMultiply(this, that)
    
    final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
    
    // divide
    final def /(that: ByteValue): DoubleValue = DoubleDivide(this, that)
    
    final def /(that: ShortValue): DoubleValue = DoubleDivide(this, that)
    
    final def /(that: IntValue): DoubleValue = DoubleDivide(this, that)
    
    final def /(that: LongValue): DoubleValue = DoubleDivide(this, that)
    
    final def /(that: FloatValue): DoubleValue = DoubleDivide(this, that)
    
    final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
}

object DoubleValue {
    
    final case object DoubleNull extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class DoubleConstant(value: Double) extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
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
    
    final case class DoubleNegate(value: DoubleValue) extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
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
    
    final case class DoubleAdd(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
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
    
    final case class DoubleSubtract(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
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
    
    final case class DoubleMultiply(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
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
    
    final case class DoubleDivide(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
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
    
    final case class NumericToDouble(value: NumericValue) extends DoubleValue {
        override def get(implicit entityRepository: EntityRepository): Option[Double] = {
            value.get match {
                case Some(v: Byte) => Some(v.toDouble)
                case Some(v: Short) => Some(v.toDouble)
                case Some(v: Int) => Some(v.toDouble)
                case Some(v: Long) => Some(v.toDouble)
                case Some(v: Float) => Some(v.toDouble)
                case Some(v: Double) => Some(v.toDouble)
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