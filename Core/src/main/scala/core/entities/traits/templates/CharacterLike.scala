package core.entities.traits.templates

import core.entities.Entity
import core.entities.traits.properties.{InventoryProperty, StateProperty}

import scala.language.implicitConversions

trait CharacterLike extends SimpleEntity with StateProperty with InventoryProperty {
    //TODO incomplete
    
    implicit private def toCharacterLike(entity: Entity): CharacterLike = {
        entity.asInstanceOf[CharacterLike]
    }
}
