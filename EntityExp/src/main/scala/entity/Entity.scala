package entity

import parts.{Animation, Category, Coordinates, Direction, Physics, State}


case class Entity(id: Int, category: Category, timestamp: Long,
                  state: Option[State] = None,
                  coordinates: Option[Coordinates] = None,
                  direction: Option[Direction] = None,
                  physics: Option[Physics] = None,
                  animation: Option[Animation] = None)