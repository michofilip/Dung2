package entity

import entity.parts.{Category, Graphics, Physics, Position, State}


case class Entity(id: Int, category: Category,
                  state: Option[State] = None,
                  position: Option[Position] = None,
                  physics: Option[Physics] = None,
                  graphics: Option[Graphics] = None)