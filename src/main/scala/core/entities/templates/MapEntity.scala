package core.entities.templates

import core.entities.properties.{AnimationHolder, PhysicsHolder, PositionHolder}

abstract class MapEntity[T <: MapEntity[T]] extends PositionHolder[T] with PhysicsHolder[T] with AnimationHolder[T]
