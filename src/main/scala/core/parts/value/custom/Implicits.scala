package core.parts.value.custom

import core.parts.position.{Coordinates, Direction}
import core.parts.state.State
import core.parts.value.basic.Implicits._
import core.parts.value.basic._
import core.parts.value.custom.CoordinatesValue.{CoordinatesConstant, CoordinatesNull}
import core.parts.value.custom.DirectionValue.{DirectionConstant, DirectionNull}
import core.parts.value.custom.StateValue.{StateConstant, StateNull}

import scala.language.implicitConversions

object Implicits {
    // null converters
    implicit def null2CoordinatesValue(v: NullValue.type): CoordinatesValue = {
        CoordinatesNull
    }
    
    implicit def null2DirectionValue(v: NullValue.type): DirectionValue = {
        DirectionNull
    }
    
    implicit def null2StateValue(v: NullValue.type): StateValue = {
        StateNull
    }
    
    // constant converters
    implicit def coordinates2Value(value: Coordinates): CoordinatesValue = {
        CoordinatesConstant(value)
    }
    
    implicit def direction2Value(value: Direction): DirectionConstant = {
        DirectionConstant(value)
    }
    
    implicit def state2Value(value: State): StateValue = {
        StateConstant(value)
    }
    
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
