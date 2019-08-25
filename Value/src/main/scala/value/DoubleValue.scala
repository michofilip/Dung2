package value

abstract class DoubleValue extends Value with NumericValue {
    override final protected type T = Double
    
    final def unary_+ : DoubleValue = this
    
    final def unary_- : DoubleValue = DoubleValue.DoubleNegate(this)
    
    // add
    final def +(that: ByteValue): DoubleValue = DoubleValue.DoubleAdd(this, that.toDoubleValue)
    
    final def +(that: ShortValue): DoubleValue = DoubleValue.DoubleAdd(this, that.toDoubleValue)
    
    final def +(that: IntValue): DoubleValue = DoubleValue.DoubleAdd(this, that.toDoubleValue)
    
    final def +(that: LongValue): DoubleValue = DoubleValue.DoubleAdd(this, that.toDoubleValue)
    
    final def +(that: FloatValue): DoubleValue = DoubleValue.DoubleAdd(this, that.toDoubleValue)
    
    final def +(that: DoubleValue): DoubleValue = DoubleValue.DoubleAdd(this, that)
    
    // subtract
    final def -(that: ByteValue): DoubleValue = DoubleValue.DoubleSubtract(this, that.toDoubleValue)
    
    final def -(that: ShortValue): DoubleValue = DoubleValue.DoubleSubtract(this, that.toDoubleValue)
    
    final def -(that: IntValue): DoubleValue = DoubleValue.DoubleSubtract(this, that.toDoubleValue)
    
    final def -(that: LongValue): DoubleValue = DoubleValue.DoubleSubtract(this, that.toDoubleValue)
    
    final def -(that: FloatValue): DoubleValue = DoubleValue.DoubleSubtract(this, that.toDoubleValue)
    
    final def -(that: DoubleValue): DoubleValue = DoubleValue.DoubleSubtract(this, that)
    
    // multiply
    final def *(that: ByteValue): DoubleValue = DoubleValue.DoubleMultiply(this, that.toDoubleValue)
    
    final def *(that: ShortValue): DoubleValue = DoubleValue.DoubleMultiply(this, that.toDoubleValue)
    
    final def *(that: IntValue): DoubleValue = DoubleValue.DoubleMultiply(this, that.toDoubleValue)
    
    final def *(that: LongValue): DoubleValue = DoubleValue.DoubleMultiply(this, that.toDoubleValue)
    
    final def *(that: FloatValue): DoubleValue = DoubleValue.DoubleMultiply(this, that.toDoubleValue)
    
    final def *(that: DoubleValue): DoubleValue = DoubleValue.DoubleMultiply(this, that)
    
    // divide
    final def /(that: ByteValue): DoubleValue = DoubleValue.DoubleDivide(this, that.toDoubleValue)
    
    final def /(that: ShortValue): DoubleValue = DoubleValue.DoubleDivide(this, that.toDoubleValue)
    
    final def /(that: IntValue): DoubleValue = DoubleValue.DoubleDivide(this, that.toDoubleValue)
    
    final def /(that: LongValue): DoubleValue = DoubleValue.DoubleDivide(this, that.toDoubleValue)
    
    final def /(that: FloatValue): DoubleValue = DoubleValue.DoubleDivide(this, that.toDoubleValue)
    
    final def /(that: DoubleValue): DoubleValue = DoubleValue.DoubleDivide(this, that)
}

object DoubleValue {
    
    final case object DoubleNull extends DoubleValue {
        override def get: Option[Double] = None
    }
    
    final case class DoubleConstant(value: Double) extends DoubleValue {
        override def get: Option[Double] = Some(value)
    }
    
    final case class DoubleNegate(value: DoubleValue) extends DoubleValue {
        override def get: Option[Double] = value.get match {
            case Some(v) => Some(-v)
            case _ => None
        }
    }
    
    final case class DoubleAdd(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
        override def get: Option[Double] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some(v1 + v2)
            case _ => None
        }
    }
    
    final case class DoubleSubtract(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
        override def get: Option[Double] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some(v1 - v2)
            case _ => None
        }
    }
    
    final case class DoubleMultiply(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
        override def get: Option[Double] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some(v1 * v2)
            case _ => None
        }
    }
    
    final case class DoubleDivide(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
        override def get: Option[Double] = (value1.get, value2.get) match {
            case (_, Some(0)) => None
            case (Some(v1), Some(v2)) => Some(v1 / v2)
            case _ => None
        }
    }
    
    final case class NumericToDouble(value: NumericValue) extends DoubleValue {
        override def get: Option[Double] = value.get match {
            case Some(v: Byte) => Some(v.toDouble)
            case Some(v: Short) => Some(v.toDouble)
            case Some(v: Int) => Some(v.toDouble)
            case Some(v: Long) => Some(v.toDouble)
            case Some(v: Float) => Some(v.toDouble)
            case Some(v: Double) => Some(v.toDouble)
            case _ => None
        }
    }
    
}