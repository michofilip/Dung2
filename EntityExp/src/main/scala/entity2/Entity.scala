package entity2

import entity2.parts2.{Animation, Physics, Position}


case class Entity(id: Int,
                  position: Option[Position] = None,
                  physics: Option[Physics] = None,
                  animation: Option[Animation] = None)