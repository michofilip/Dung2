package core.parts.value.basic

import core.entities.repositoy.EntityRepository
import core.parts.value.Value
import core.parts.value.basic.BooleanValue._
import json.JValue

import scala.language.implicitConversions

abstract class BooleanValue extends Value {
    override final type T = Boolean
    
    final def unary_! : BooleanValue = NOT(this)
    
    final def &&(that: BooleanValue): BooleanValue = AND(this, that)
    
    final def !&&(that: BooleanValue): BooleanValue = NAND(this, that)
    
    final def ||(that: BooleanValue): BooleanValue = OR(this, that)
    
    final def !||(that: BooleanValue): BooleanValue = NOR(this, that)
    
    final def <>(that: BooleanValue): BooleanValue = XOR(this, that)
    
    final def !<>(that: BooleanValue): BooleanValue = XNOR(this, that)
}

object BooleanValue {
    
    final case object BooleanNull extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            None
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class BooleanConstant(value: Boolean) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            Some(value)
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
    final case class NOT(value: BooleanValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            value.get match {
                case Some(v) => Some(!v)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
    final case class AND(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 && v2)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class NAND(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(!(v1 && v2))
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class OR(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 || v2)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class NOR(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(!(v1 || v2))
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class XOR(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some((v1 && !v2) || (!v1 && v2))
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class XNOR(value1: BooleanValue, value2: BooleanValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some((v1 || !v2) && (!v1 || v2))
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class Equals(value1: Value, value2: Value) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 == v2)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class Unequals(value1: Value, value2: Value) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
                case (Some(v1), Some(v2)) => Some(v1 != v2)
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class Less(value1: ComparableValue, value2: ComparableValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
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
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class LessEqual(value1: ComparableValue, value2: ComparableValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
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
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class Greater(value1: ComparableValue, value2: ComparableValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
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
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
    final case class GreaterEqual(value1: ComparableValue, value2: ComparableValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            (value1.get, value2.get) match {
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
                "class" -> this.getClass.getSimpleName,
                "value1" -> value1,
                "value2" -> value2
            )
        }
    }
    
}