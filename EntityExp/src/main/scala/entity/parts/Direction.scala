package entity.parts

import entity.parts.Direction.{East, North, NorthEast, NorthWest, South, SouthEast, SouthWest, West}

sealed abstract class Direction {
    def turnRight: Direction = this match {
        case North => NorthEast
        case NorthEast => East
        case East => SouthEast
        case SouthEast => South
        case South => SouthWest
        case SouthWest => West
        case West => NorthWest
        case NorthWest => North
    }
    
    def turnLeft: Direction = this match {
        case North => NorthWest
        case NorthEast => North
        case East => NorthEast
        case SouthEast => East
        case South => SouthEast
        case SouthWest => South
        case West => SouthWest
        case NorthWest => West
    }
    
    def turnBack: Direction = this match {
        case North => South
        case NorthEast => SouthWest
        case East => West
        case SouthEast => NorthWest
        case South => North
        case SouthWest => NorthEast
        case West => East
        case NorthWest => SouthEast
    }
}

object Direction {
    
    case object North extends Direction
    
    case object NorthEast extends Direction
    
    case object East extends Direction
    
    case object SouthEast extends Direction
    
    case object South extends Direction
    
    case object SouthWest extends Direction
    
    case object West extends Direction
    
    case object NorthWest extends Direction
    
}
