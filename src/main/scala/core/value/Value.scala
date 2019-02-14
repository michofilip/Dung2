package core.value

import core.entity.EntityHolder
import core.entity.properties.position.{Coordinates, Direction}
import core.entity.properties.state.State
import core.value.basic.BooleanValue
import core.value.basic.BooleanValue.{BooleanConstant, Equals, Unequals}
import core.value.basic.ByteValue.ByteConstant
import core.value.basic.CharValue.CharConstant
import core.value.basic.DoubleValue.DoubleConstant
import core.value.basic.FloatValue.FloatConstant
import core.value.basic.IntValue.IntConstant
import core.value.basic.LongValue.LongConstant
import core.value.basic.ShortValue.ShortConstant
import core.value.basic.StringValue.StringConstant
import core.value.custom.CoordinatesValue.CoordinatesConstant
import core.value.custom.DirectionValue.DirectionConstant
import core.value.custom.StateValue.StateConstant
import json.JSONParsable

import scala.language.implicitConversions

abstract class Value extends JSONParsable {
    type T
    
    def get(implicit entityHolder: EntityHolder): Option[T]
    
    def getOrElse(default: T)(implicit entityHolder: EntityHolder): T = {
        get match {
            case Some(value) => value
            case None => default
        }
    }
    
    def ===(that: Value): BooleanValue = {
        Equals(this, that)
    }
    
    def !==(that: Value): BooleanValue = {
        Unequals(this, that)
    }
}

object Value {
    // basic values
    def apply(value: Boolean): BooleanConstant = BooleanConstant(value)
    
    def apply(value: Byte): ByteConstant = ByteConstant(value)
    
    def apply(value: Short): ShortConstant = ShortConstant(value)
    
    def apply(value: Int): IntConstant = IntConstant(value)
    
    def apply(value: Long): LongConstant = LongConstant(value)
    
    def apply(value: Float): FloatConstant = FloatConstant(value)
    
    def apply(value: Double): DoubleConstant = DoubleConstant(value)
    
    def apply(value: Char): CharConstant = CharConstant(value)
    
    def apply(value: String): StringConstant = StringConstant(value)
    
    // custom values
    def apply(value: State): StateConstant = StateConstant(value)
    
    def apply(value: Coordinates): CoordinatesConstant = CoordinatesConstant(value)
    
    def apply(value: Direction): DirectionConstant = DirectionConstant(value)
    
}
