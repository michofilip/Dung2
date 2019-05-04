package core.entity2.traits

import core.entity2.Entity2

import scala.language.implicitConversions

trait CharacterLike extends Entity2 with StateHolder2 {
    //TODO incomplete
}

object CharacterLike {
    implicit private def toCharacterLike(entity: Entity2): CharacterLike = {
        entity.asInstanceOf[CharacterLike]
    }
}
