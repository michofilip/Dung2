package value

import scala.language.implicitConversions

object ValueImports {
    // null converters
    implicit def null2BooleanValue(v: NullValue.type): BooleanValue = BooleanValue.BooleanNull
    
    implicit def null2ByteValue(v: NullValue.type): ByteValue = ByteValue.ByteNull
    
    implicit def null2ShortValue(v: NullValue.type): ShortValue = ShortValue.ShortNull
    
    implicit def null2IntValue(v: NullValue.type): IntValue = IntValue.IntNull
    
    implicit def null2LongValue(v: NullValue.type): LongValue = LongValue.LongNull
    
    implicit def null2FloatValue(v: NullValue.type): FloatValue = FloatValue.FloatNull
    
    implicit def null2DoubleValue(v: NullValue.type): DoubleValue = DoubleValue.DoubleNull
    
    implicit def null2CharValue(v: NullValue.type): CharValue = CharValue.CharNull
    
    implicit def null2StringValue(v: NullValue.type): StringValue = StringValue.StringNull
    
    // constant converters
    implicit def boolean2Value(value: Boolean): BooleanValue = BooleanValue.BooleanConstant(value)
    
    implicit def bite2Value(value: Byte): ByteValue = ByteValue.ByteConstant(value)
    
    implicit def short2Value(value: Short): ShortValue = ShortValue.ShortConstant(value)
    
    implicit def int2Value(value: Int): IntValue = IntValue.IntConstant(value)
    
    implicit def long2Value(value: Long): LongValue = LongValue.LongConstant(value)
    
    implicit def float2Value(value: Float): FloatValue = FloatValue.FloatConstant(value)
    
    implicit def double2Value(value: Double): DoubleValue = DoubleValue.DoubleConstant(value)
    
    implicit def char2Value(value: Char): CharValue = CharValue.CharConstant(value)
    
    implicit def string2Value(value: String): StringValue = StringValue.StringConstant(value)
    
    // numeric converters
    implicit def numeric2ByteValue(value: NumericValue): ByteValue = ByteValue.NumericToByte(value)
    
    implicit def numeric2ShortValue(value: NumericValue): ShortValue = ShortValue.NumericToShort(value)
    
    implicit def numeric2IntValue(value: NumericValue): IntValue = IntValue.NumericToInt(value)
    
    implicit def numeric2LongValue(value: NumericValue): LongValue = LongValue.NumericToLong(value)
    
    implicit def numeric2FloatValue(value: NumericValue): FloatValue = FloatValue.NumericToFloat(value)
    
    implicit def numeric2DoubleValue(value: NumericValue): DoubleValue = DoubleValue.NumericToDouble(value)
    
    implicit def numeric2StringValue(value: NumericValue): StringValue = StringValue.NumericToString(value)
    
    // additional methods
    implicit class Boolean2Value(value: Boolean) {
        def toValue: BooleanValue = value
        
        def toBooleanValue: BooleanValue = value
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class Bite2Value(value: Byte) {
        def toValue: ByteValue = value
        
        def toByteValue: ByteValue = value.toByte
        
        def toShortValue: ShortValue = value.toShort
        
        def toIntValue: IntValue = value.toInt
        
        def toLongValue: LongValue = value.toLong
        
        def toFloatValue: FloatValue = value.toFloat
        
        def toDoubleValue: DoubleValue = value.toDouble
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class Short2Value(value: Short) {
        def toValue: ShortValue = value
        
        def toByteValue: ByteValue = value.toByte
        
        def toShortValue: ShortValue = value.toShort
        
        def toIntValue: IntValue = value.toInt
        
        def toLongValue: LongValue = value.toLong
        
        def toFloatValue: FloatValue = value.toFloat
        
        def toDoubleValue: DoubleValue = value.toDouble
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class Int2Value(value: Int) {
        def toValue: IntValue = value
        
        def toByteValue: ByteValue = value.toByte
        
        def toShortValue: ShortValue = value.toShort
        
        def toIntValue: IntValue = value.toInt
        
        def toLongValue: LongValue = value.toLong
        
        def toFloatValue: FloatValue = value.toFloat
        
        def toDoubleValue: DoubleValue = value.toDouble
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class Long2Value(value: Long) {
        def toValue: LongValue = value
        
        def toByteValue: ByteValue = value.toByte
        
        def toShortValue: ShortValue = value.toShort
        
        def toIntValue: IntValue = value.toInt
        
        def toLongValue: LongValue = value.toLong
        
        def toFloatValue: FloatValue = value.toFloat
        
        def toDoubleValue: DoubleValue = value.toDouble
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class Float2Value(value: Float) {
        def toValue: FloatValue = value
        
        def toByteValue: ByteValue = value.toByte
        
        def toShortValue: ShortValue = value.toShort
        
        def toIntValue: IntValue = value.toInt
        
        def toLongValue: LongValue = value.toLong
        
        def toFloatValue: FloatValue = value.toFloat
        
        def toDoubleValue: DoubleValue = value.toDouble
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class Double2Value(value: Double) {
        def toValue: DoubleValue = value
        
        def toByteValue: ByteValue = value.toByte
        
        def toShortValue: ShortValue = value.toShort
        
        def toIntValue: IntValue = value.toInt
        
        def toLongValue: LongValue = value.toLong
        
        def toFloatValue: FloatValue = value.toFloat
        
        def toDoubleValue: DoubleValue = value.toDouble
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class Char2Value(value: Char) {
        def toValue: CharValue = value
        
        def toCharValue: CharValue = value
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class String2Value(value: String) {
        def toValue: StringValue = value
        
        def toStringValue: StringValue = value
    }
    
}
