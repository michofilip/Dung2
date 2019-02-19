package core.value.custom

import core.entity.properties.position.{Coordinates, Direction}
import core.entity.properties.state.State
import core.value.basic.Implicits._
import core.value.basic.StringValue
import core.value.custom.CoordinatesValue.CoordinatesConstant
import core.value.custom.DirectionValue.DirectionConstant
import core.value.custom.StateValue.StateConstant

import scala.language.implicitConversions

object Implicits {
    implicit def coordinates2Value(value: Coordinates): CoordinatesValue = {
        CoordinatesConstant(value)
    }
    
    implicit def direction2Value(value: Direction): DirectionConstant = {
        DirectionConstant(value)
    }
    
    implicit def state2Value(value: State): StateValue = {
        StateConstant(value)
    }
    
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
