package value

trait NumericValue extends Value with ComparableValue {
    final def toByteValue: ByteValue = ByteValue.NumericToByte(this)
    
    final def toShortValue: ShortValue = ShortValue.NumericToShort(this)
    
    final def toIntValue: IntValue = IntValue.NumericToInt(this)
    
    final def toLongValue: LongValue = LongValue.NumericToLong(this)
    
    final def toFloatValue: FloatValue = FloatValue.NumericToFloat(this)
    
    final def toDoubleValue: DoubleValue = DoubleValue.NumericToDouble(this)
}
