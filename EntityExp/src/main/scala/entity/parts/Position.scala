package entity.parts

case class Position(coordinates: Coordinates, direction: Direction, canMove: Boolean, canRotate: Boolean)