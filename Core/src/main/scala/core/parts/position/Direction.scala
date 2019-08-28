package core.parts.position

sealed abstract class Direction {
    val azimuth: Int
    
    final def turnRight: Direction = Direction.turnRight(this)
    
    final def turnLeft: Direction = Direction.turnLeft(this)
    
    final def opposite: Direction = Direction.opposite(this)
}

object Direction {
    
    private def turnRight(direction: Direction): Direction = direction match {
        case North => NorthEast
        case NorthEast => East
        case East => SouthEast
        case SouthEast => South
        case South => SouthWest
        case SouthWest => West
        case West => NorthWest
        case NorthWest => North
    }
    
    private def turnLeft(direction: Direction): Direction = direction match {
        case North => NorthWest
        case NorthEast => North
        case East => NorthEast
        case SouthEast => East
        case South => SouthEast
        case SouthWest => South
        case West => SouthWest
        case NorthWest => West
    }
    
    private def opposite(direction: Direction): Direction = direction match {
        case North => South
        case NorthEast => SouthWest
        case East => West
        case SouthEast => NorthWest
        case South => North
        case SouthWest => NorthEast
        case West => East
        case NorthWest => SouthEast
    }
    
    case object North extends Direction {
        override val azimuth: Int = 0
    }
    
    case object NorthEast extends Direction {
        override val azimuth: Int = 45
    }
    
    case object East extends Direction {
        override val azimuth: Int = 90
    }
    
    case object SouthEast extends Direction {
        override val azimuth: Int = 135
    }
    
    case object South extends Direction {
        override val azimuth: Int = 180
    }
    
    case object SouthWest extends Direction {
        override val azimuth: Int = 225
    }
    
    case object West extends Direction {
        override val azimuth: Int = 270
    }
    
    case object NorthWest extends Direction {
        override val azimuth: Int = 315
    }
    
}
