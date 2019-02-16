package core.value.basic

import core.value.Value
import core.value.basic.ByteValue.NumericToByte
import core.value.basic.DoubleValue.NumericToDouble
import core.value.basic.FloatValue.NumericToFloat
import core.value.basic.IntValue.NumericToInt
import core.value.basic.LongValue.NumericToLong
import core.value.basic.ShortValue.NumericToShort

trait NumericValue extends Value with ComparableValue {
    def toByteValue: ByteValue = NumericToByte(this)
    
    def toShortValue: ShortValue = NumericToShort(this)
    
    def toIntValue: IntValue = NumericToInt(this)
    
    def toLongValue: LongValue = NumericToLong(this)
    
    def toFloatValue: FloatValue = NumericToFloat(this)
    
    def toDoubleValue: DoubleValue = NumericToDouble(this)
}