package core.value.basic

import core.value.Value
import core.value.basic.BooleanValue.{Greater, GreaterEqual, Less, LessEqual}

trait ComparableValue extends Value {
    def <(that: ComparableValue): Less = Less(this, that)
    
    def <=(that: ComparableValue): LessEqual = LessEqual(this, that)
    
    def >(that: ComparableValue): Greater = Greater(this, that)
    
    def >=(that: ComparableValue): GreaterEqual = GreaterEqual(this, that)
}