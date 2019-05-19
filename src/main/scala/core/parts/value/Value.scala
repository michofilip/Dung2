package core.parts.value

import core.parts.value.basic.BooleanValue
import core.parts.value.basic.BooleanValue.{Equals, Unequals}
import core.repository.EntityRepository2
import json.JSONParsable

import scala.language.implicitConversions

abstract class Value extends JSONParsable {
    type T
    
    def get(implicit entityRepository: EntityRepository2): Option[T]
    
    def getOrElse(default: => T)(implicit entityRepository: EntityRepository2): T = {
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