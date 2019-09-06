package core.parts.value

import core.parts.position.{Coordinates, Direction}
import core.parts.state.State
import value.ValueImports._
import value.{NullValue, StringValue}

import scala.language.implicitConversions

object CustomValueImports {
    // null converters
    implicit def null2CoordinatesValue(v: NullValue.type): CoordinatesValue = CoordinatesValue.CoordinatesNull
    
    implicit def null2DirectionValue(v: NullValue.type): DirectionValue = DirectionValue.DirectionNull
    
    implicit def null2StateValue(v: NullValue.type): StateValue = StateValue.StateNull
    
    // constant converters
    implicit def coordinates2Value(value: Coordinates): CoordinatesValue = CoordinatesValue.CoordinatesConstant(value)
    
    implicit def direction2Value(value: Direction): DirectionValue = DirectionValue.DirectionConstant(value)
    
    implicit def state2Value(value: State): StateValue = StateValue.StateConstant(value)
    
    // additional methods
    implicit class Coordinates2Value(value: Coordinates) {
        def toValue: CoordinatesValue = value
        
        def toCoordinatesValue: CoordinatesValue = value
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class Direction2Value(value: Direction) {
        def toValue: DirectionValue = value
        
        def toCoordinatesValue: DirectionValue = value
        
        def toStringValue: StringValue = value.toString
    }
    
    implicit class State2Value(value: State) {
        def toValue: StateValue = value
        
        def toStateValue: StateValue = value
        
        def toStringValue: StringValue = value.toString
    }
    
}
