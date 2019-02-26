package core.value

import core.entity.repositoy.EntityRepository
import core.value.basic.BooleanValue
import core.value.basic.BooleanValue.{Equals, Unequals}
import json.JSONParsable

import scala.language.implicitConversions

abstract class Value extends JSONParsable {
    type T
    
    def get(implicit entityHolder: EntityRepository): Option[T]
    
    def getOrElse(default: => T)(implicit entityHolder: EntityRepository): T = {
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