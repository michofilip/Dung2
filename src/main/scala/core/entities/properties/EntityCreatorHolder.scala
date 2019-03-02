package core.entities.properties

import core.entities.Entity

trait EntityCreatorHolder[T <: EntityCreatorHolder[T]] extends Entity