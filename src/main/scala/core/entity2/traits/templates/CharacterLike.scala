package core.entity2.traits.templates

import core.entity2.Entity2
import core.entity2.traits.properties.{InventoryHolder2, StateHolder2}

import scala.language.implicitConversions

trait CharacterLike extends SimpleEntity with StateHolder2 with InventoryHolder2 {
    //TODO incomplete
    
    implicit private def toCharacterLike(entity: Entity2): CharacterLike = {
        entity.asInstanceOf[CharacterLike]
    }
}
