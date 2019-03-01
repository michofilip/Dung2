package core.entities.templates

import core.entities.properties.StateHolder

// TODO finish it
abstract class Character[T <: Character[T]] extends MapEntity[T] with StateHolder[T]