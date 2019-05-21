package core.entities.traits.templates

import core.entities.Entity
import core.entities.traits.properties.{InventoryHolder, StateHolder}

import scala.language.implicitConversions

trait CharacterLike extends SimpleEntity with StateHolder with InventoryHolder {
    //TODO incomplete
    
    implicit private def toCharacterLike(entity: Entity): CharacterLike = {
        entity.asInstanceOf[CharacterLike]
    }
}
