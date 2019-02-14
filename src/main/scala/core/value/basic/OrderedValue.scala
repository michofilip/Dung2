package core.value.basic

import core.value.Value
import core.value.basic.BooleanValue.{Greater, GreaterEqual, Less, LessEqual}

trait OrderedValue extends Value {
    def <(that: OrderedValue): Less = Less(this, that)
    
    def <=(that: OrderedValue): LessEqual = LessEqual(this, that)
    
    def >(that: OrderedValue): Greater = Greater(this, that)
    
    def >=(that: OrderedValue): GreaterEqual = GreaterEqual(this, that)
}