package core.value

import core.entity.Entity.{MultiState, Positioned}
import core.entity.properties.position.{Coordinates, Direction}
import core.entity.properties.state.State
import core.value.Value.BasicValue.BooleanValue
import core.value.Value.BasicValue.BooleanValue._
import core.value.Value.BasicValue.ByteValue._
import core.value.Value.BasicValue.CharValue._
import core.value.Value.BasicValue.DoubleValue._
import core.value.Value.BasicValue.FloatValue._
import core.value.Value.BasicValue.IntValue._
import core.value.Value.BasicValue.LongValue._
import core.value.Value.BasicValue.ShortValue._
import core.value.Value.BasicValue.StringValue._
import core.value.Value.CustomValue.CoordinatesValue._
import core.value.Value.CustomValue.DirectionValue._
import core.value.Value.CustomValue.StateValue._
import core.world.WorldFrame
import json.{JSONParsable, JValue}

import scala.language.implicitConversions

sealed abstract class Value extends JSONParsable {
    type T
    
    def getValue(implicit mapFrame: WorldFrame): Option[T]
    
    def ===(that: Value): BooleanValue = Equals(this, that)
    
    def =!=(that: Value): BooleanValue = Unequals(this, that)
}

object Value {
    def apply(value: Boolean): BooleanConstant = BooleanConstant(value)
    
    def apply(value: Byte): ByteConstant = ByteConstant(value)
    
    def apply(value: Short): ShortConstant = ShortConstant(value)
    
    def apply(value: Int): IntConstant = IntConstant(value)
    
    def apply(value: Long): LongConstant = LongConstant(value)
    
    def apply(value: Float): FloatConstant = FloatConstant(value)
    
    def apply(value: Double): DoubleConstant = DoubleConstant(value)
    
    def apply(value: Char): CharConstant = CharConstant(value)
    
    def apply(value: String): StringConstant = StringConstant(value)
    
    object BasicValue {
        
        sealed trait OrderedValue extends Value {
            def <(that: OrderedValue): Less = Less(this, that)
            
            def <=(that: OrderedValue): LessEqual = LessEqual(this, that)
            
            def >(that: OrderedValue): Greater = Greater(this, that)
            
            def >=(that: OrderedValue): GreaterEqual = GreaterEqual(this, that)
        }
        
        sealed trait NumericValue extends Value with OrderedValue
        
        sealed abstract class BooleanValue extends Value {
            override final type T = Boolean
            
            final def unary_! : BooleanValue = NOT(this)
            
            final def &&(that: BooleanValue): BooleanValue = AND(this, that)
            
            final def !&&(that: BooleanValue): BooleanValue = NAND(this, that)
            
            final def ||(that: BooleanValue): BooleanValue = OR(this, that)
            
            final def !||(that: BooleanValue): BooleanValue = NOR(this, that)
            
            final def <>(that: BooleanValue): BooleanValue = XOR(this, that)
            
            final def !<>(that: BooleanValue): BooleanValue = XNOR(this, that)
        }
        
        sealed abstract class ByteValue extends Value with NumericValue {
            override final type T = Byte
            
            final def unary_+ : ByteValue = this
            
            final def unary_- : ByteValue = ByteNegate(this)
            
            // add
            final def +(that: ByteValue): ByteValue = ByteAdd(this, that)
            
            final def +(that: ShortValue): ShortValue = ShortAdd(this, that)
            
            final def +(that: IntValue): IntValue = IntAdd(this, that)
            
            final def +(that: LongValue): LongValue = LongAdd(this, that)
            
            final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
            
            // subtract
            final def -(that: ByteValue): ByteValue = ByteSubtract(this, that)
            
            final def -(that: ShortValue): ShortValue = ShortSubtract(this, that)
            
            final def -(that: IntValue): IntValue = IntSubtract(this, that)
            
            final def -(that: LongValue): LongValue = LongSubtract(this, that)
            
            final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
            
            // multiply
            final def *(that: ByteValue): ByteValue = ByteMultiply(this, that)
            
            final def *(that: ShortValue): ShortValue = ShortMultiply(this, that)
            
            final def *(that: IntValue): IntValue = IntMultiply(this, that)
            
            final def *(that: LongValue): LongValue = LongMultiply(this, that)
            
            final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
            
            // divide
            final def /(that: ByteValue): ByteValue = ByteDivide(this, that)
            
            final def /(that: ShortValue): ShortValue = ShortDivide(this, that)
            
            final def /(that: IntValue): IntValue = IntDivide(this, that)
            
            final def /(that: LongValue): LongValue = LongDivide(this, that)
            
            final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
            
            // modulo
            final def %(that: ByteValue): ByteValue = ByteMod(this, that)
            
            final def %(that: ShortValue): ShortValue = ShortMod(this, that)
            
            final def %(that: IntValue): IntValue = IntMod(this, that)
            
            final def %(that: LongValue): LongValue = LongMod(this, that)
        }
        
        sealed abstract class ShortValue extends Value with NumericValue {
            override final type T = Short
            
            final def unary_+ : ShortValue = this
            
            final def unary_- : ShortValue = ShortNegate(this)
            
            // add
            final def +(that: ByteValue): ShortValue = ShortAdd(this, that)
            
            final def +(that: ShortValue): ShortValue = ShortAdd(this, that)
            
            final def +(that: IntValue): IntValue = IntAdd(this, that)
            
            final def +(that: LongValue): LongValue = LongAdd(this, that)
            
            final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
            
            // subtract
            final def -(that: ByteValue): ShortValue = ShortSubtract(this, that)
            
            final def -(that: ShortValue): ShortValue = ShortSubtract(this, that)
            
            final def -(that: IntValue): IntValue = IntSubtract(this, that)
            
            final def -(that: LongValue): LongValue = LongSubtract(this, that)
            
            final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
            
            // multiply
            final def *(that: ByteValue): ShortValue = ShortMultiply(this, that)
            
            final def *(that: ShortValue): ShortValue = ShortMultiply(this, that)
            
            final def *(that: IntValue): IntValue = IntMultiply(this, that)
            
            final def *(that: LongValue): LongValue = LongMultiply(this, that)
            
            final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
            
            // divide
            final def /(that: ByteValue): ShortValue = ShortDivide(this, that)
            
            final def /(that: ShortValue): ShortValue = ShortDivide(this, that)
            
            final def /(that: IntValue): IntValue = IntDivide(this, that)
            
            final def /(that: LongValue): LongValue = LongDivide(this, that)
            
            final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
            
            // modulo
            final def %(that: ByteValue): ShortValue = ShortMod(this, that)
            
            final def %(that: ShortValue): ShortValue = ShortMod(this, that)
            
            final def %(that: IntValue): IntValue = IntMod(this, that)
            
            final def %(that: LongValue): LongValue = LongMod(this, that)
        }
        
        sealed abstract class IntValue extends Value with NumericValue {
            override final type T = Int
            
            final def unary_+ : IntValue = this
            
            final def unary_- : IntValue = IntNegate(this)
            
            // add
            final def +(that: ByteValue): IntValue = IntAdd(this, that)
            
            final def +(that: ShortValue): IntValue = IntAdd(this, that)
            
            final def +(that: IntValue): IntValue = IntAdd(this, that)
            
            final def +(that: LongValue): LongValue = LongAdd(this, that)
            
            final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
            
            // subtract
            final def -(that: ByteValue): IntValue = IntSubtract(this, that)
            
            final def -(that: ShortValue): IntValue = IntSubtract(this, that)
            
            final def -(that: IntValue): IntValue = IntSubtract(this, that)
            
            final def -(that: LongValue): LongValue = LongSubtract(this, that)
            
            final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
            
            // multiply
            final def *(that: ByteValue): IntValue = IntMultiply(this, that)
            
            final def *(that: ShortValue): IntValue = IntMultiply(this, that)
            
            final def *(that: IntValue): IntValue = IntMultiply(this, that)
            
            final def *(that: LongValue): LongValue = LongMultiply(this, that)
            
            final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
            
            // divide
            final def /(that: ByteValue): IntValue = IntDivide(this, that)
            
            final def /(that: ShortValue): IntValue = IntDivide(this, that)
            
            final def /(that: IntValue): IntValue = IntDivide(this, that)
            
            final def /(that: LongValue): LongValue = LongDivide(this, that)
            
            final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
            
            // modulo
            final def %(that: ByteValue): IntValue = IntMod(this, that)
            
            final def %(that: ShortValue): IntValue = IntMod(this, that)
            
            final def %(that: IntValue): IntValue = IntMod(this, that)
            
            final def %(that: LongValue): LongValue = LongMod(this, that)
        }
        
        sealed abstract class LongValue extends Value with NumericValue {
            override final type T = Long
            
            final def unary_+ : LongValue = this
            
            final def unary_- : LongValue = LongNegate(this)
            
            // add
            final def +(that: ByteValue): LongValue = LongAdd(this, that)
            
            final def +(that: ShortValue): LongValue = LongAdd(this, that)
            
            final def +(that: IntValue): LongValue = LongAdd(this, that)
            
            final def +(that: LongValue): LongValue = LongAdd(this, that)
            
            final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
            
            // subtract
            final def -(that: ByteValue): LongValue = LongSubtract(this, that)
            
            final def -(that: ShortValue): LongValue = LongSubtract(this, that)
            
            final def -(that: IntValue): LongValue = LongSubtract(this, that)
            
            final def -(that: LongValue): LongValue = LongSubtract(this, that)
            
            final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
            
            // multiply
            final def *(that: ByteValue): LongValue = LongMultiply(this, that)
            
            final def *(that: ShortValue): LongValue = LongMultiply(this, that)
            
            final def *(that: IntValue): LongValue = LongMultiply(this, that)
            
            final def *(that: LongValue): LongValue = LongMultiply(this, that)
            
            final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
            
            
            // divide
            final def /(that: ByteValue): LongValue = LongDivide(this, that)
            
            final def /(that: ShortValue): LongValue = LongDivide(this, that)
            
            final def /(that: IntValue): LongValue = LongDivide(this, that)
            
            final def /(that: LongValue): LongValue = LongDivide(this, that)
            
            final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
            
            // modulo
            final def %(that: ByteValue): LongValue = LongMod(this, that)
            
            final def %(that: ShortValue): LongValue = LongMod(this, that)
            
            final def %(that: IntValue): LongValue = LongMod(this, that)
            
            final def %(that: LongValue): LongValue = LongMod(this, that)
        }
        
        sealed abstract class FloatValue extends Value with NumericValue {
            override type T = Float
            
            final def unary_+ : FloatValue = this
            
            final def unary_- : FloatValue = FloatNegate(this)
            
            // add
            final def +(that: ByteValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: ShortValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: IntValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: LongValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: FloatValue): FloatValue = FloatAdd(this, that)
            
            final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
            
            // subtract
            final def -(that: ByteValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: ShortValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: IntValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: LongValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: FloatValue): FloatValue = FloatSubtract(this, that)
            
            final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
            
            // multiply
            final def *(that: ByteValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: ShortValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: IntValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: LongValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: FloatValue): FloatValue = FloatMultiply(this, that)
            
            final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
            
            // divide
            final def /(that: ByteValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: ShortValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: IntValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: LongValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: FloatValue): FloatValue = FloatDivide(this, that)
            
            final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
        }
        
        sealed abstract class DoubleValue extends Value with NumericValue {
            override type T = Double
            
            final def unary_+ : DoubleValue = this
            
            final def unary_- : DoubleValue = DoubleNegate(this)
            
            // add
            final def +(that: ByteValue): DoubleValue = DoubleAdd(this, that)
            
            final def +(that: ShortValue): DoubleValue = DoubleAdd(this, that)
            
            final def +(that: IntValue): DoubleValue = DoubleAdd(this, that)
            
            final def +(that: LongValue): DoubleValue = DoubleAdd(this, that)
            
            final def +(that: FloatValue): DoubleValue = DoubleAdd(this, that)
            
            final def +(that: DoubleValue): DoubleValue = DoubleAdd(this, that)
            
            // subtract
            final def -(that: ByteValue): DoubleValue = DoubleSubtract(this, that)
            
            final def -(that: ShortValue): DoubleValue = DoubleSubtract(this, that)
            
            final def -(that: IntValue): DoubleValue = DoubleSubtract(this, that)
            
            final def -(that: LongValue): DoubleValue = DoubleSubtract(this, that)
            
            final def -(that: FloatValue): DoubleValue = DoubleSubtract(this, that)
            
            final def -(that: DoubleValue): DoubleValue = DoubleSubtract(this, that)
            
            // multiply
            final def *(that: ByteValue): DoubleValue = DoubleMultiply(this, that)
            
            final def *(that: ShortValue): DoubleValue = DoubleMultiply(this, that)
            
            final def *(that: IntValue): DoubleValue = DoubleMultiply(this, that)
            
            final def *(that: LongValue): DoubleValue = DoubleMultiply(this, that)
            
            final def *(that: FloatValue): DoubleValue = DoubleMultiply(this, that)
            
            final def *(that: DoubleValue): DoubleValue = DoubleMultiply(this, that)
            
            // divide
            final def /(that: ByteValue): DoubleValue = DoubleDivide(this, that)
            
            final def /(that: ShortValue): DoubleValue = DoubleDivide(this, that)
            
            final def /(that: IntValue): DoubleValue = DoubleDivide(this, that)
            
            final def /(that: LongValue): DoubleValue = DoubleDivide(this, that)
            
            final def /(that: FloatValue): DoubleValue = DoubleDivide(this, that)
            
            final def /(that: DoubleValue): DoubleValue = DoubleDivide(this, that)
        }
        
        sealed abstract class CharValue extends Value {
            override type T = Char
        }
        
        sealed abstract class StringValue extends Value {
            override type T = String
            
            final def +(that: StringValue): StringValue = Concatenate(this, that)
            
            final def length: IntValue = Length(this)
        }
        
        object BooleanValue {
            implicit def bool2V(value: Boolean): BooleanValue = BooleanConstant(value)
            
            implicit class Bool2V(value: Boolean) {
                def toValue: BooleanValue = value
                
                def toBooleanValue: BooleanValue = value
                
                def toStringValue: StringValue = value.toString
            }
            
            final case class BooleanConstant(value: Boolean) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class NOT(value: BooleanValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    value.getValue match {
                        case Some(v) => Some(!v)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class AND(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 && v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class NAND(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(!(v1 && v2))
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class OR(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 || v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class NOR(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(!(v1 || v2))
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class XOR(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 && !v2) || (!v1 && v2))
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class XNOR(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 || !v2) && (!v1 || v2))
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class Equals(value1: Value, value2: Value) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 == v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class Unequals(value1: Value, value2: Value) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 != v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class Less(value1: OrderedValue, value2: OrderedValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1: Byte), Some(v2: Byte)) => Some(v1 < v2)
                        case (Some(v1: Byte), Some(v2: Short)) => Some(v1 < v2)
                        case (Some(v1: Byte), Some(v2: Int)) => Some(v1 < v2)
                        case (Some(v1: Byte), Some(v2: Long)) => Some(v1 < v2)
                        case (Some(v1: Byte), Some(v2: Float)) => Some(v1 < v2)
                        case (Some(v1: Byte), Some(v2: Double)) => Some(v1 < v2)
                        
                        case (Some(v1: Short), Some(v2: Byte)) => Some(v1 < v2)
                        case (Some(v1: Short), Some(v2: Short)) => Some(v1 < v2)
                        case (Some(v1: Short), Some(v2: Int)) => Some(v1 < v2)
                        case (Some(v1: Short), Some(v2: Long)) => Some(v1 < v2)
                        case (Some(v1: Short), Some(v2: Float)) => Some(v1 < v2)
                        case (Some(v1: Short), Some(v2: Double)) => Some(v1 < v2)
                        
                        case (Some(v1: Int), Some(v2: Byte)) => Some(v1 < v2)
                        case (Some(v1: Int), Some(v2: Short)) => Some(v1 < v2)
                        case (Some(v1: Int), Some(v2: Int)) => Some(v1 < v2)
                        case (Some(v1: Int), Some(v2: Long)) => Some(v1 < v2)
                        case (Some(v1: Int), Some(v2: Float)) => Some(v1 < v2)
                        case (Some(v1: Int), Some(v2: Double)) => Some(v1 < v2)
                        
                        case (Some(v1: Long), Some(v2: Byte)) => Some(v1 < v2)
                        case (Some(v1: Long), Some(v2: Short)) => Some(v1 < v2)
                        case (Some(v1: Long), Some(v2: Int)) => Some(v1 < v2)
                        case (Some(v1: Long), Some(v2: Long)) => Some(v1 < v2)
                        case (Some(v1: Long), Some(v2: Float)) => Some(v1 < v2)
                        case (Some(v1: Long), Some(v2: Double)) => Some(v1 < v2)
                        
                        case (Some(v1: Float), Some(v2: Byte)) => Some(v1 < v2)
                        case (Some(v1: Float), Some(v2: Short)) => Some(v1 < v2)
                        case (Some(v1: Float), Some(v2: Int)) => Some(v1 < v2)
                        case (Some(v1: Float), Some(v2: Long)) => Some(v1 < v2)
                        case (Some(v1: Float), Some(v2: Float)) => Some(v1 < v2)
                        case (Some(v1: Float), Some(v2: Double)) => Some(v1 < v2)
                        
                        case (Some(v1: Double), Some(v2: Byte)) => Some(v1 < v2)
                        case (Some(v1: Double), Some(v2: Short)) => Some(v1 < v2)
                        case (Some(v1: Double), Some(v2: Int)) => Some(v1 < v2)
                        case (Some(v1: Double), Some(v2: Long)) => Some(v1 < v2)
                        case (Some(v1: Double), Some(v2: Float)) => Some(v1 < v2)
                        case (Some(v1: Double), Some(v2: Double)) => Some(v1 < v2)
                        
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class LessEqual(value1: OrderedValue, value2: OrderedValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1: Byte), Some(v2: Byte)) => Some(v1 <= v2)
                        case (Some(v1: Byte), Some(v2: Short)) => Some(v1 <= v2)
                        case (Some(v1: Byte), Some(v2: Int)) => Some(v1 <= v2)
                        case (Some(v1: Byte), Some(v2: Long)) => Some(v1 <= v2)
                        case (Some(v1: Byte), Some(v2: Float)) => Some(v1 <= v2)
                        case (Some(v1: Byte), Some(v2: Double)) => Some(v1 <= v2)
                        
                        case (Some(v1: Short), Some(v2: Byte)) => Some(v1 <= v2)
                        case (Some(v1: Short), Some(v2: Short)) => Some(v1 <= v2)
                        case (Some(v1: Short), Some(v2: Int)) => Some(v1 <= v2)
                        case (Some(v1: Short), Some(v2: Long)) => Some(v1 <= v2)
                        case (Some(v1: Short), Some(v2: Float)) => Some(v1 <= v2)
                        case (Some(v1: Short), Some(v2: Double)) => Some(v1 <= v2)
                        
                        case (Some(v1: Int), Some(v2: Byte)) => Some(v1 <= v2)
                        case (Some(v1: Int), Some(v2: Short)) => Some(v1 <= v2)
                        case (Some(v1: Int), Some(v2: Int)) => Some(v1 <= v2)
                        case (Some(v1: Int), Some(v2: Long)) => Some(v1 <= v2)
                        case (Some(v1: Int), Some(v2: Float)) => Some(v1 <= v2)
                        case (Some(v1: Int), Some(v2: Double)) => Some(v1 <= v2)
                        
                        case (Some(v1: Long), Some(v2: Byte)) => Some(v1 <= v2)
                        case (Some(v1: Long), Some(v2: Short)) => Some(v1 <= v2)
                        case (Some(v1: Long), Some(v2: Int)) => Some(v1 <= v2)
                        case (Some(v1: Long), Some(v2: Long)) => Some(v1 <= v2)
                        case (Some(v1: Long), Some(v2: Float)) => Some(v1 <= v2)
                        case (Some(v1: Long), Some(v2: Double)) => Some(v1 <= v2)
                        
                        case (Some(v1: Float), Some(v2: Byte)) => Some(v1 <= v2)
                        case (Some(v1: Float), Some(v2: Short)) => Some(v1 <= v2)
                        case (Some(v1: Float), Some(v2: Int)) => Some(v1 <= v2)
                        case (Some(v1: Float), Some(v2: Long)) => Some(v1 <= v2)
                        case (Some(v1: Float), Some(v2: Float)) => Some(v1 <= v2)
                        case (Some(v1: Float), Some(v2: Double)) => Some(v1 <= v2)
                        
                        case (Some(v1: Double), Some(v2: Byte)) => Some(v1 <= v2)
                        case (Some(v1: Double), Some(v2: Short)) => Some(v1 <= v2)
                        case (Some(v1: Double), Some(v2: Int)) => Some(v1 <= v2)
                        case (Some(v1: Double), Some(v2: Long)) => Some(v1 <= v2)
                        case (Some(v1: Double), Some(v2: Float)) => Some(v1 <= v2)
                        case (Some(v1: Double), Some(v2: Double)) => Some(v1 <= v2)
                        
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class Greater(value1: OrderedValue, value2: OrderedValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1: Byte), Some(v2: Byte)) => Some(v1 > v2)
                        case (Some(v1: Byte), Some(v2: Short)) => Some(v1 > v2)
                        case (Some(v1: Byte), Some(v2: Int)) => Some(v1 > v2)
                        case (Some(v1: Byte), Some(v2: Long)) => Some(v1 > v2)
                        case (Some(v1: Byte), Some(v2: Float)) => Some(v1 > v2)
                        case (Some(v1: Byte), Some(v2: Double)) => Some(v1 > v2)
                        
                        case (Some(v1: Short), Some(v2: Byte)) => Some(v1 > v2)
                        case (Some(v1: Short), Some(v2: Short)) => Some(v1 > v2)
                        case (Some(v1: Short), Some(v2: Int)) => Some(v1 > v2)
                        case (Some(v1: Short), Some(v2: Long)) => Some(v1 > v2)
                        case (Some(v1: Short), Some(v2: Float)) => Some(v1 > v2)
                        case (Some(v1: Short), Some(v2: Double)) => Some(v1 > v2)
                        
                        case (Some(v1: Int), Some(v2: Byte)) => Some(v1 > v2)
                        case (Some(v1: Int), Some(v2: Short)) => Some(v1 > v2)
                        case (Some(v1: Int), Some(v2: Int)) => Some(v1 > v2)
                        case (Some(v1: Int), Some(v2: Long)) => Some(v1 > v2)
                        case (Some(v1: Int), Some(v2: Float)) => Some(v1 > v2)
                        case (Some(v1: Int), Some(v2: Double)) => Some(v1 > v2)
                        
                        case (Some(v1: Long), Some(v2: Byte)) => Some(v1 > v2)
                        case (Some(v1: Long), Some(v2: Short)) => Some(v1 > v2)
                        case (Some(v1: Long), Some(v2: Int)) => Some(v1 > v2)
                        case (Some(v1: Long), Some(v2: Long)) => Some(v1 > v2)
                        case (Some(v1: Long), Some(v2: Float)) => Some(v1 > v2)
                        case (Some(v1: Long), Some(v2: Double)) => Some(v1 > v2)
                        
                        case (Some(v1: Float), Some(v2: Byte)) => Some(v1 > v2)
                        case (Some(v1: Float), Some(v2: Short)) => Some(v1 > v2)
                        case (Some(v1: Float), Some(v2: Int)) => Some(v1 > v2)
                        case (Some(v1: Float), Some(v2: Long)) => Some(v1 > v2)
                        case (Some(v1: Float), Some(v2: Float)) => Some(v1 > v2)
                        case (Some(v1: Float), Some(v2: Double)) => Some(v1 > v2)
                        
                        case (Some(v1: Double), Some(v2: Byte)) => Some(v1 > v2)
                        case (Some(v1: Double), Some(v2: Short)) => Some(v1 > v2)
                        case (Some(v1: Double), Some(v2: Int)) => Some(v1 > v2)
                        case (Some(v1: Double), Some(v2: Long)) => Some(v1 > v2)
                        case (Some(v1: Double), Some(v2: Float)) => Some(v1 > v2)
                        case (Some(v1: Double), Some(v2: Double)) => Some(v1 > v2)
                        
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class GreaterEqual(value1: OrderedValue, value2: OrderedValue) extends BooleanValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Boolean] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1: Byte), Some(v2: Byte)) => Some(v1 >= v2)
                        case (Some(v1: Byte), Some(v2: Short)) => Some(v1 >= v2)
                        case (Some(v1: Byte), Some(v2: Int)) => Some(v1 >= v2)
                        case (Some(v1: Byte), Some(v2: Long)) => Some(v1 >= v2)
                        case (Some(v1: Byte), Some(v2: Float)) => Some(v1 >= v2)
                        case (Some(v1: Byte), Some(v2: Double)) => Some(v1 >= v2)
                        
                        case (Some(v1: Short), Some(v2: Byte)) => Some(v1 >= v2)
                        case (Some(v1: Short), Some(v2: Short)) => Some(v1 >= v2)
                        case (Some(v1: Short), Some(v2: Int)) => Some(v1 >= v2)
                        case (Some(v1: Short), Some(v2: Long)) => Some(v1 >= v2)
                        case (Some(v1: Short), Some(v2: Float)) => Some(v1 >= v2)
                        case (Some(v1: Short), Some(v2: Double)) => Some(v1 >= v2)
                        
                        case (Some(v1: Int), Some(v2: Byte)) => Some(v1 >= v2)
                        case (Some(v1: Int), Some(v2: Short)) => Some(v1 >= v2)
                        case (Some(v1: Int), Some(v2: Int)) => Some(v1 >= v2)
                        case (Some(v1: Int), Some(v2: Long)) => Some(v1 >= v2)
                        case (Some(v1: Int), Some(v2: Float)) => Some(v1 >= v2)
                        case (Some(v1: Int), Some(v2: Double)) => Some(v1 >= v2)
                        
                        case (Some(v1: Long), Some(v2: Byte)) => Some(v1 >= v2)
                        case (Some(v1: Long), Some(v2: Short)) => Some(v1 >= v2)
                        case (Some(v1: Long), Some(v2: Int)) => Some(v1 >= v2)
                        case (Some(v1: Long), Some(v2: Long)) => Some(v1 >= v2)
                        case (Some(v1: Long), Some(v2: Float)) => Some(v1 >= v2)
                        case (Some(v1: Long), Some(v2: Double)) => Some(v1 >= v2)
                        
                        case (Some(v1: Float), Some(v2: Byte)) => Some(v1 >= v2)
                        case (Some(v1: Float), Some(v2: Short)) => Some(v1 >= v2)
                        case (Some(v1: Float), Some(v2: Int)) => Some(v1 >= v2)
                        case (Some(v1: Float), Some(v2: Long)) => Some(v1 >= v2)
                        case (Some(v1: Float), Some(v2: Float)) => Some(v1 >= v2)
                        case (Some(v1: Float), Some(v2: Double)) => Some(v1 >= v2)
                        
                        case (Some(v1: Double), Some(v2: Byte)) => Some(v1 >= v2)
                        case (Some(v1: Double), Some(v2: Short)) => Some(v1 >= v2)
                        case (Some(v1: Double), Some(v2: Int)) => Some(v1 >= v2)
                        case (Some(v1: Double), Some(v2: Long)) => Some(v1 >= v2)
                        case (Some(v1: Double), Some(v2: Float)) => Some(v1 >= v2)
                        case (Some(v1: Double), Some(v2: Double)) => Some(v1 >= v2)
                        
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
        }
        
        object ByteValue {
            implicit def num2b(value: NumericValue): ByteValue = NumericToByte(value)
            
            implicit def b2V(value: Byte): ByteValue = ByteConstant(value)
            
            implicit class B2V(value: Byte) {
                def toValue: ByteValue = value
                
                def toByteValue: ByteValue = value.toByte
                
                def toShortValue: ShortValue = value.toShort
                
                def toIntValue: IntValue = value.toInt
                
                def toLongValue: LongValue = value.toLong
                
                def toFloatValue: FloatValue = value.toFloat
                
                def toDoubleValue: DoubleValue = value.toDouble
                
                def toStringValue: StringValue = value.toString
            }
            
            final case class ByteConstant(value: Byte) extends ByteValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Byte] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class ByteNegate(value: ByteValue) extends ByteValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Byte] = {
                    value.getValue match {
                        case Some(v) => Some((-v).toByte)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class ByteAdd(value1: ByteValue, value2: ByteValue) extends ByteValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Byte] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 + v2).toByte)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class ByteSubtract(value1: ByteValue, value2: ByteValue) extends ByteValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Byte] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 - v2).toByte)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class ByteMultiply(value1: ByteValue, value2: ByteValue) extends ByteValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Byte] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 * v2).toByte)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class ByteDivide(value1: ByteValue, value2: ByteValue) extends ByteValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Byte] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 / v2).toByte)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class ByteMod(value1: ByteValue, value2: ByteValue) extends ByteValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Byte] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 % v2).toByte)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class NumericToByte(value: NumericValue) extends ByteValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Byte] = {
                    value.getValue match {
                        case Some(v: Byte) => Some(v.toByte)
                        case Some(v: Short) => Some(v.toByte)
                        case Some(v: Int) => Some(v.toByte)
                        case Some(v: Long) => Some(v.toByte)
                        case Some(v: Float) => Some(v.toByte)
                        case Some(v: Double) => Some(v.toByte)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
        }
        
        object ShortValue {
            implicit def num2s(value: NumericValue): ShortValue = NumericToShort(value)
            
            implicit def s2V(value: Short): ShortValue = ShortConstant(value)
            
            implicit class S2V(value: Short) {
                def toValue: ShortValue = value
                
                def toByteValue: ByteValue = value.toByte
                
                def toShortValue: ShortValue = value.toShort
                
                def toIntValue: IntValue = value.toInt
                
                def toLongValue: LongValue = value.toLong
                
                def toFloatValue: FloatValue = value.toFloat
                
                def toDoubleValue: DoubleValue = value.toDouble
                
                def toStringValue: StringValue = value.toString
            }
            
            final case class ShortConstant(value: Short) extends ShortValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Short] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class ShortNegate(value: ShortValue) extends ShortValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Short] = {
                    value.getValue match {
                        case Some(v) => Some((-v).toShort)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class ShortAdd(value1: ShortValue, value2: ShortValue) extends ShortValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Short] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 + v2).toShort)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class ShortSubtract(value1: ShortValue, value2: ShortValue) extends ShortValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Short] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 - v2).toShort)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class ShortMultiply(value1: ShortValue, value2: ShortValue) extends ShortValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Short] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 * v2).toShort)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class ShortDivide(value1: ShortValue, value2: ShortValue) extends ShortValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Short] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 / v2).toShort)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class ShortMod(value1: ShortValue, value2: ShortValue) extends ShortValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Short] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some((v1 % v2).toShort)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class NumericToShort(value: NumericValue) extends ShortValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Short] = {
                    value.getValue match {
                        case Some(v: Byte) => Some(v.toShort)
                        case Some(v: Short) => Some(v.toShort)
                        case Some(v: Int) => Some(v.toShort)
                        case Some(v: Long) => Some(v.toShort)
                        case Some(v: Float) => Some(v.toShort)
                        case Some(v: Double) => Some(v.toShort)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
        }
        
        object IntValue {
            implicit def num2i(value: NumericValue): IntValue = NumericToInt(value)
            
            implicit def i2V(value: Int): IntValue = IntConstant(value)
            
            implicit class I2V(value: Int) {
                def toValue: IntValue = value
                
                def toByteValue: ByteValue = value.toByte
                
                def toShortValue: ShortValue = value.toShort
                
                def toIntValue: IntValue = value.toInt
                
                def toLongValue: LongValue = value.toLong
                
                def toFloatValue: FloatValue = value.toFloat
                
                def toDoubleValue: DoubleValue = value.toDouble
                
                def toStringValue: StringValue = value.toString
            }
            
            final case class IntConstant(value: Int) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class IntNegate(value: IntValue) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = {
                    value.getValue match {
                        case Some(v) => Some(-v)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class IntAdd(value1: IntValue, value2: IntValue) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 + v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class IntSubtract(value1: IntValue, value2: IntValue) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 - v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class IntMultiply(value1: IntValue, value2: IntValue) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 * v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class IntDivide(value1: IntValue, value2: IntValue) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 / v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class IntMod(value1: IntValue, value2: IntValue) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 % v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class NumericToInt(value: NumericValue) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = {
                    value.getValue match {
                        case Some(v: Byte) => Some(v.toInt)
                        case Some(v: Short) => Some(v.toInt)
                        case Some(v: Int) => Some(v.toInt)
                        case Some(v: Long) => Some(v.toInt)
                        case Some(v: Float) => Some(v.toInt)
                        case Some(v: Double) => Some(v.toInt)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
        }
        
        object LongValue {
            implicit def num2l(value: NumericValue): LongValue = NumericToLong(value)
            
            implicit def l2V(value: Long): LongValue = LongConstant(value)
            
            implicit class L2V(value: Long) {
                def toValue: LongValue = value
                
                def toByteValue: ByteValue = value.toByte
                
                def toShortValue: ShortValue = value.toShort
                
                def toIntValue: IntValue = value.toInt
                
                def toLongValue: LongValue = value.toLong
                
                def toFloatValue: FloatValue = value.toFloat
                
                def toDoubleValue: DoubleValue = value.toDouble
                
                def toStringValue: StringValue = value.toString
            }
            
            final case class LongConstant(value: Long) extends LongValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Long] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class LongNegate(value: LongValue) extends LongValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Long] = {
                    value.getValue match {
                        case Some(v) => Some(-v)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class LongAdd(value1: LongValue, value2: LongValue) extends LongValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Long] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 + v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class LongSubtract(value1: LongValue, value2: LongValue) extends LongValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Long] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 - v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class LongMultiply(value1: LongValue, value2: LongValue) extends LongValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Long] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 * v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class LongDivide(value1: LongValue, value2: LongValue) extends LongValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Long] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 / v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class LongMod(value1: LongValue, value2: LongValue) extends LongValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Long] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 % v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class NumericToLong(value: NumericValue) extends LongValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Long] = {
                    value.getValue match {
                        case Some(v: Byte) => Some(v.toLong)
                        case Some(v: Short) => Some(v.toLong)
                        case Some(v: Int) => Some(v.toLong)
                        case Some(v: Long) => Some(v.toLong)
                        case Some(v: Float) => Some(v.toLong)
                        case Some(v: Double) => Some(v.toLong)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
        }
        
        object FloatValue {
            implicit def num2f(value: NumericValue): FloatValue = NumericToFloat(value)
            
            implicit def f2V(value: Float): FloatValue = FloatConstant(value)
            
            implicit class F2V(value: Float) {
                def toValue: FloatValue = value
                
                def toByteValue: ByteValue = value.toByte
                
                def toShortValue: ShortValue = value.toShort
                
                def toIntValue: IntValue = value.toInt
                
                def toLongValue: LongValue = value.toLong
                
                def toFloatValue: FloatValue = value.toFloat
                
                def toDoubleValue: DoubleValue = value.toDouble
                
                def toStringValue: StringValue = value.toString
            }
            
            final case class FloatConstant(value: Float) extends FloatValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Float] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class FloatNegate(value: FloatValue) extends FloatValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Float] = {
                    value.getValue match {
                        case Some(v) => Some(-v)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class FloatAdd(value1: FloatValue, value2: FloatValue) extends FloatValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Float] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 + v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class FloatSubtract(value1: FloatValue, value2: FloatValue) extends FloatValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Float] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 - v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class FloatMultiply(value1: FloatValue, value2: FloatValue) extends FloatValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Float] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 * v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class FloatDivide(value1: FloatValue, value2: FloatValue) extends FloatValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Float] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 / v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class NumericToFloat(value: NumericValue) extends FloatValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Float] = {
                    value.getValue match {
                        case Some(v: Byte) => Some(v.toFloat)
                        case Some(v: Short) => Some(v.toFloat)
                        case Some(v: Int) => Some(v.toFloat)
                        case Some(v: Long) => Some(v.toFloat)
                        case Some(v: Float) => Some(v.toFloat)
                        case Some(v: Double) => Some(v.toFloat)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
        }
        
        object DoubleValue {
            implicit def num2d(value: NumericValue): DoubleValue = NumericToDouble(value)
            
            implicit def d2V(value: Double): DoubleValue = DoubleConstant(value)
            
            implicit class D2V(value: Double) {
                def toValue: DoubleValue = value
                
                def toByteValue: ByteValue = value.toByte
                
                def toShortValue: ShortValue = value.toShort
                
                def toIntValue: IntValue = value.toInt
                
                def toLongValue: LongValue = value.toLong
                
                def toFloatValue: FloatValue = value.toFloat
                
                def toDoubleValue: DoubleValue = value.toDouble
                
                def toStringValue: StringValue = value.toString
            }
            
            final case class DoubleConstant(value: Double) extends DoubleValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Double] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class DoubleNegate(value: DoubleValue) extends DoubleValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Double] = {
                    value.getValue match {
                        case Some(v) => Some(-v)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class DoubleAdd(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Double] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 + v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class DoubleSubtract(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Double] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 - v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class DoubleMultiply(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Double] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 * v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class DoubleDivide(value1: DoubleValue, value2: DoubleValue) extends DoubleValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Double] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 / v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class NumericToDouble(value: NumericValue) extends DoubleValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Double] = {
                    value.getValue match {
                        case Some(v: Byte) => Some(v.toDouble)
                        case Some(v: Short) => Some(v.toDouble)
                        case Some(v: Int) => Some(v.toDouble)
                        case Some(v: Long) => Some(v.toDouble)
                        case Some(v: Float) => Some(v.toDouble)
                        case Some(v: Double) => Some(v.toDouble)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
        }
        
        object CharValue {
            implicit def char2V(value: Char): CharValue = CharConstant(value)
            
            implicit class Char2V(value: Char) {
                def toValue: CharValue = value
                
                def toCharValue: CharValue = value
                
                def toStringValue: StringValue = value.toString
            }
            
            final case class CharConstant(value: Char) extends CharValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Char] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
        }
        
        object StringValue {
            implicit def num2str(value: NumericValue): StringValue = NumericToString(value)
            
            implicit def str2V(value: String): StringValue = StringConstant(value)
            
            implicit class String2V(value: String) {
                def toValue: StringValue = value
                
                def toStringValue: StringValue = value
            }
            
            final case class StringConstant(value: String) extends StringValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[String] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class Concatenate(value1: StringValue, value2: StringValue) extends StringValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[String] = {
                    (value1.getValue, value2.getValue) match {
                        case (Some(v1), Some(v2)) => Some(v1 + v2)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter1" -> value1,
                        "parameter2" -> value2
                    )
                }
            }
            
            final case class Length(value: StringValue) extends IntValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Int] = {
                    value.getValue match {
                        case Some(v) => Some(v.length)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class NumericToString(value: NumericValue) extends StringValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[String] = {
                    value.getValue match {
                        case Some(v: Byte) => Some(v.toString)
                        case Some(v: Short) => Some(v.toString)
                        case Some(v: Int) => Some(v.toString)
                        case Some(v: Long) => Some(v.toString)
                        case Some(v: Float) => Some(v.toString)
                        case Some(v: Double) => Some(v.toString)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
        }
        
        case object UnitValue extends Value {
            override type T = Unit
            
            override def getValue(implicit mapFrame: WorldFrame): Option[Unit] = None
            
            override def toJSON: JValue = {
                import json.MyJ._
                jObject(
                    "value" -> this.getClass.getSimpleName
                )
            }
        }
        
    }
    
    
    def apply(value: State): StateConstant = StateConstant(value)
    
    def apply(value: Coordinates): CoordinatesConstant = CoordinatesConstant(value)
    
    def apply(value: Direction): DirectionConstant = DirectionConstant(value)
    
    object CustomValue {
        
        sealed abstract class StateValue extends Value {
            override final type T = State
        }
        
        sealed abstract class CoordinatesValue extends Value {
            override final type T = Coordinates
        }
        
        sealed abstract class DirectionValue extends Value {
            override final type T = Direction
        }
        
        object StateValue {
            
            final case class StateConstant(value: State) extends StateValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[State] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class GetState(entityId: String) extends StateValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[State] = {
                    mapFrame.getEntityById(entityId) match {
                        case Some(en: MultiState) => Some(en.state)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "entityId" -> entityId
                    )
                }
            }
            
        }
        
        object CoordinatesValue {
            
            final case class CoordinatesConstant(value: Coordinates) extends CoordinatesValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Coordinates] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class GetConcatenate(entityId: String) extends CoordinatesValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Coordinates] = {
                    mapFrame.getEntityById(entityId) match {
                        case Some(en: Positioned) => Some(en.position.coordinates)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "entityId" -> entityId
                    )
                }
            }
            
        }
        
        object DirectionValue {
            
            final case class DirectionConstant(value: Direction) extends DirectionValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Direction] = Some(value)
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "parameter" -> value
                    )
                }
            }
            
            final case class GetDirection(entityId: String) extends DirectionValue {
                override def getValue(implicit mapFrame: WorldFrame): Option[Direction] = {
                    mapFrame.getEntityById(entityId) match {
                        case Some(en: Positioned) => Some(en.position.direction)
                        case _ => None
                    }
                }
                
                override def toJSON: JValue = {
                    import json.MyJ._
                    jObject(
                        "value" -> this.getClass.getSimpleName,
                        "entityId" -> entityId
                    )
                }
            }
            
        }
        
    }
    
}
