package core.parts.value.basic

import core.parts.value.Value
import core.parts.value.basic.ByteValue.NumericToByte
import core.parts.value.basic.DoubleValue.NumericToDouble
import core.parts.value.basic.FloatValue.NumericToFloat
import core.parts.value.basic.IntValue.NumericToInt
import core.parts.value.basic.LongValue.NumericToLong
import core.parts.value.basic.ShortValue.NumericToShort

trait NumericValue extends Value with ComparableValue {
    def toByteValue: ByteValue = NumericToByte(this)
    
    def toShortValue: ShortValue = NumericToShort(this)
    
    def toIntValue: IntValue = NumericToInt(this)
    
    def toLongValue: LongValue = NumericToLong(this)
    
    def toFloatValue: FloatValue = NumericToFloat(this)
    
    def toDoubleValue: DoubleValue = NumericToDouble(this)
}