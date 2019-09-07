package value

abstract class LongValue extends Value with NumericValue {
    override final protected type T = Long
    
    override final def calculate: LongValue = LongValue.LongCalculate(this)
    
    final def unary_+ : LongValue = this
    
    final def unary_- : LongValue = LongValue.LongNegate(this)
    
    // add
    final def +(that: ByteValue): LongValue = LongValue.LongAdd(this, that.toLongValue)
    
    final def +(that: ShortValue): LongValue = LongValue.LongAdd(this, that.toLongValue)
    
    final def +(that: IntValue): LongValue = LongValue.LongAdd(this, that.toLongValue)
    
    final def +(that: LongValue): LongValue = LongValue.LongAdd(this, that)
    
    final def +(that: FloatValue): DoubleValue = DoubleValue.DoubleAdd(this.toDoubleValue, that.toDoubleValue)
    
    final def +(that: DoubleValue): DoubleValue = DoubleValue.DoubleAdd(this.toDoubleValue, that)
    
    // subtract
    final def -(that: ByteValue): LongValue = LongValue.LongSubtract(this, that.toLongValue)
    
    final def -(that: ShortValue): LongValue = LongValue.LongSubtract(this, that.toLongValue)
    
    final def -(that: IntValue): LongValue = LongValue.LongSubtract(this, that.toLongValue)
    
    final def -(that: LongValue): LongValue = LongValue.LongSubtract(this, that)
    
    final def -(that: FloatValue): DoubleValue = DoubleValue.DoubleSubtract(this.toDoubleValue, that.toDoubleValue)
    
    final def -(that: DoubleValue): DoubleValue = DoubleValue.DoubleSubtract(this.toDoubleValue, that)
    
    // multiply
    final def *(that: ByteValue): LongValue = LongValue.LongMultiply(this, that.toLongValue)
    
    final def *(that: ShortValue): LongValue = LongValue.LongMultiply(this, that.toLongValue)
    
    final def *(that: IntValue): LongValue = LongValue.LongMultiply(this, that.toLongValue)
    
    final def *(that: LongValue): LongValue = LongValue.LongMultiply(this, that)
    
    final def *(that: FloatValue): DoubleValue = DoubleValue.DoubleMultiply(this.toDoubleValue, that.toDoubleValue)
    
    final def *(that: DoubleValue): DoubleValue = DoubleValue.DoubleMultiply(this.toDoubleValue, that)
    
    // divide
    final def /(that: ByteValue): LongValue = LongValue.LongDivide(this, that.toLongValue)
    
    final def /(that: ShortValue): LongValue = LongValue.LongDivide(this, that.toLongValue)
    
    final def /(that: IntValue): LongValue = LongValue.LongDivide(this, that.toLongValue)
    
    final def /(that: LongValue): LongValue = LongValue.LongDivide(this, that)
    
    final def /(that: FloatValue): DoubleValue = DoubleValue.DoubleDivide(this.toDoubleValue, that.toDoubleValue)
    
    final def /(that: DoubleValue): DoubleValue = DoubleValue.DoubleDivide(this.toDoubleValue, that)
    
    // modulo
    final def %(that: ByteValue): LongValue = LongValue.LongMod(this, that.toLongValue)
    
    final def %(that: ShortValue): LongValue = LongValue.LongMod(this, that.toLongValue)
    
    final def %(that: IntValue): LongValue = LongValue.LongMod(this, that.toLongValue)
    
    final def %(that: LongValue): LongValue = LongValue.LongMod(this, that)
}

object LongValue {
    
    final case object LongNull extends LongValue {
        override def get: Option[Long] = None
    }
    
    final case class LongConstant(value: Long) extends LongValue {
        override def get: Option[Long] = Some(value)
    }
    
    final case class LongCalculate(value: LongValue) extends LongValue {
        private val calculated: Option[Long] = value.get
        
        override def get: Option[Long] = calculated
    }
    
    final case class LongNegate(value: LongValue) extends LongValue {
        override def get: Option[Long] = value.get match {
            case Some(v) => Some(-v)
            case _ => None
        }
    }
    
    final case class LongAdd(value1: LongValue, value2: LongValue) extends LongValue {
        override def get: Option[Long] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some(v1 + v2)
            case _ => None
        }
    }
    
    final case class LongSubtract(value1: LongValue, value2: LongValue) extends LongValue {
        override def get: Option[Long] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some(v1 - v2)
            case _ => None
        }
    }
    
    final case class LongMultiply(value1: LongValue, value2: LongValue) extends LongValue {
        override def get: Option[Long] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some(v1 * v2)
            case _ => None
        }
    }
    
    final case class LongDivide(value1: LongValue, value2: LongValue) extends LongValue {
        override def get: Option[Long] = (value1.get, value2.get) match {
            case (_, Some(0)) => None
            case (Some(v1), Some(v2)) => Some(v1 / v2)
            case _ => None
        }
    }
    
    final case class LongMod(value1: LongValue, value2: LongValue) extends LongValue {
        override def get: Option[Long] = (value1.get, value2.get) match {
            case (_, Some(0)) => None
            case (Some(v1), Some(v2)) => Some(v1 % v2)
            case _ => None
        }
    }
    
    final case class NumericToLong(value: NumericValue) extends LongValue {
        override def get: Option[Long] = value.get match {
            case Some(v: Byte) => Some(v.toLong)
            case Some(v: Short) => Some(v.toLong)
            case Some(v: Int) => Some(v.toLong)
            case Some(v: Long) => Some(v.toLong)
            case Some(v: Float) => Some(v.toLong)
            case Some(v: Double) => Some(v.toLong)
            case _ => None
        }
    }
    
}