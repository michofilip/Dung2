package value

abstract class ShortValue extends Value with NumericValue {
    override final protected type T = Short
    
    final def unary_+ : ShortValue = this
    
    final def unary_- : ShortValue = ShortValue.ShortNegate(this)
    
    // add
    final def +(that: ByteValue): ShortValue = ShortValue.ShortAdd(this, that.toShortValue)
    
    final def +(that: ShortValue): ShortValue = ShortValue.ShortAdd(this, that)
    
    final def +(that: IntValue): IntValue = IntValue.IntAdd(this.toIntValue, that)
    
    final def +(that: LongValue): LongValue = LongValue.LongAdd(this.toLongValue, that)
    
    final def +(that: FloatValue): FloatValue = FloatValue.FloatAdd(this.toFloatValue, that)
    
    final def +(that: DoubleValue): DoubleValue = DoubleValue.DoubleAdd(this.toDoubleValue, that)
    
    // subtract
    final def -(that: ByteValue): ShortValue = ShortValue.ShortSubtract(this, that.toShortValue)
    
    final def -(that: ShortValue): ShortValue = ShortValue.ShortSubtract(this, that)
    
    final def -(that: IntValue): IntValue = IntValue.IntSubtract(this.toIntValue, that)
    
    final def -(that: LongValue): LongValue = LongValue.LongSubtract(this.toLongValue, that)
    
    final def -(that: FloatValue): FloatValue = FloatValue.FloatSubtract(this.toFloatValue, that)
    
    final def -(that: DoubleValue): DoubleValue = DoubleValue.DoubleSubtract(this.toDoubleValue, that)
    
    // multiply
    final def *(that: ByteValue): ShortValue = ShortValue.ShortMultiply(this, that.toShortValue)
    
    final def *(that: ShortValue): ShortValue = ShortValue.ShortMultiply(this, that)
    
    final def *(that: IntValue): IntValue = IntValue.IntMultiply(this.toIntValue, that)
    
    final def *(that: LongValue): LongValue = LongValue.LongMultiply(this.toLongValue, that)
    
    final def *(that: FloatValue): FloatValue = FloatValue.FloatMultiply(this.toFloatValue, that)
    
    final def *(that: DoubleValue): DoubleValue = DoubleValue.DoubleMultiply(this.toDoubleValue, that)
    
    // divide
    final def /(that: ByteValue): ShortValue = ShortValue.ShortDivide(this, that.toShortValue)
    
    final def /(that: ShortValue): ShortValue = ShortValue.ShortDivide(this, that)
    
    final def /(that: IntValue): IntValue = IntValue.IntDivide(this.toIntValue, that)
    
    final def /(that: LongValue): LongValue = LongValue.LongDivide(this.toLongValue, that)
    
    final def /(that: FloatValue): FloatValue = FloatValue.FloatDivide(this.toFloatValue, that)
    
    final def /(that: DoubleValue): DoubleValue = DoubleValue.DoubleDivide(this.toDoubleValue, that)
    
    // modulo
    final def %(that: ByteValue): ShortValue = ShortValue.ShortMod(this, that.toShortValue)
    
    final def %(that: ShortValue): ShortValue = ShortValue.ShortMod(this, that)
    
    final def %(that: IntValue): IntValue = IntValue.IntMod(this.toIntValue, that)
    
    final def %(that: LongValue): LongValue = LongValue.LongMod(this.toLongValue, that)
}

object ShortValue {
    
    final case object ShortNull extends ShortValue {
        override def get: Option[Short] = None
    }
    
    final case class ShortConstant(value: Short) extends ShortValue {
        override def get: Option[Short] = Some(value)
    }
    
    final case class ShortNegate(value: ShortValue) extends ShortValue {
        override def get: Option[Short] = value.get match {
            case Some(v) => Some((-v).toShort)
            case _ => None
        }
    }
    
    final case class ShortAdd(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get: Option[Short] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some((v1 + v2).toShort)
            case _ => None
        }
    }
    
    final case class ShortSubtract(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get: Option[Short] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some((v1 - v2).toShort)
            case _ => None
        }
    }
    
    final case class ShortMultiply(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get: Option[Short] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some((v1 * v2).toShort)
            case _ => None
        }
    }
    
    final case class ShortDivide(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get: Option[Short] = (value1.get, value2.get) match {
            case (_, Some(0)) => None
            case (Some(v1), Some(v2)) => Some((v1 / v2).toShort)
            case _ => None
        }
    }
    
    final case class ShortMod(value1: ShortValue, value2: ShortValue) extends ShortValue {
        override def get: Option[Short] = (value1.get, value2.get) match {
            case (_, Some(0)) => None
            case (Some(v1), Some(v2)) => Some((v1 % v2).toShort)
            case _ => None
        }
    }
    
    final case class NumericToShort(value: NumericValue) extends ShortValue {
        override def get: Option[Short] = value.get match {
            case Some(v: Byte) => Some(v.toShort)
            case Some(v: Short) => Some(v.toShort)
            case Some(v: Int) => Some(v.toShort)
            case Some(v: Long) => Some(v.toShort)
            case Some(v: Float) => Some(v.toShort)
            case Some(v: Double) => Some(v.toShort)
            case _ => None
        }
    }
    
}