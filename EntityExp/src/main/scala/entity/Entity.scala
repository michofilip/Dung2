package entity

import parts.{Animation, Category, Position, Direction, Physics, State}


case class Entity(id: Int, category: Category, timestamp: Long,
                  stateOpt: Option[State] = None,
                  positionOpt: Option[Position] = None,
                  directionOpt: Option[Direction] = None,
                  physicsOpt: Option[Physics] = None,
                  animationOpt: Option[Animation] = None)