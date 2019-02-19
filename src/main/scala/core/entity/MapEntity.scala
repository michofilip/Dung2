package core.entity

import core.entity.traits.{AnimationHolder, PhysicsHolder, PositionHolder}

abstract class MapEntity extends Entity with PositionHolder with PhysicsHolder with AnimationHolder