package entity

import parts.{Category, Direction, Graphics, Physics, Position, State}

case class Entity(id: Int, category: Category,
                  stateOpt: Option[State] = None,
                  positionOpt: Option[Position] = None,
                  directionOpt: Option[Direction] = None,
                  physicsOpt: Option[Physics] = None,
                  graphicsOpt: Option[Graphics] = None)