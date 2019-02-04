package core.entity.properties.position

import core.entity.properties.position.Direction._

sealed abstract class Direction {
    val azimuth: Int
    
    def turnClockwise90: Direction =
        this match {
            case North => East
            case East => South
            case South => West
            case West => North
        }
    
    def turnCounterClockwise90: Direction =
        this match {
            case North => West
            case East => North
            case South => East
            case West => South
        }
    
    def turn180: Direction =
        this match {
            case North => South
            case East => West
            case South => North
            case West => East
        }
}

object Direction {
    
    case object North extends Direction {
        override val azimuth: Int = 0
    }
    
    case object East extends Direction {
        override val azimuth: Int = 90
    }
    
    case object South extends Direction {
        override val azimuth: Int = 180
    }
    
    case object West extends Direction {
        override val azimuth: Int = 270
    }
    
}
