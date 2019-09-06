package core.parts.value.basic

import core.parts.value.Value
import core.parts.value.basic.BooleanValue.{Greater, GreaterEqual, Less, LessEqual}

trait ComparableValue extends Value {
    def <(that: ComparableValue): Less = Less(this, that)
    
    def <=(that: ComparableValue): LessEqual = LessEqual(this, that)
    
    def >(that: ComparableValue): Greater = Greater(this, that)
    
    def >=(that: ComparableValue): GreaterEqual = GreaterEqual(this, that)
}