package core.entity.properties

import core.entity.Entity

trait EntityCreatorHolder[T <: EntityCreatorHolder[T]] extends Entity[T]