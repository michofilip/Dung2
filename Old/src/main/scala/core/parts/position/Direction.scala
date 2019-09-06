package core.parts.position

sealed abstract class Direction {
    val azimuth: Int
    
    def next: Direction
    
    def previous: Direction
    
    def opposite: Direction
}

object Direction {
    
    case object North extends Direction {
        override val azimuth: Int = 0
        
        override def next: Direction = NorthEast
        
        override def previous: Direction = NorthWest
        
        override def opposite: Direction = South
    }
    
    case object NorthEast extends Direction {
        override val azimuth: Int = 45
        
        override def next: Direction = East
        
        override def previous: Direction = North
        
        override def opposite: Direction = SouthWest
    }
    
    case object East extends Direction {
        override val azimuth: Int = 90
        
        override def next: Direction = SouthEast
        
        override def previous: Direction = NorthEast
        
        override def opposite: Direction = West
    }
    
    case object SouthEast extends Direction {
        override val azimuth: Int = 135
        
        override def next: Direction = South
        
        override def previous: Direction = East
        
        override def opposite: Direction = NorthWest
    }
    
    case object South extends Direction {
        override val azimuth: Int = 180
        
        override def next: Direction = SouthWest
        
        override def previous: Direction = SouthEast
        
        override def opposite: Direction = North
    }
    
    case object SouthWest extends Direction {
        override val azimuth: Int = 225
        
        override def next: Direction = West
        
        override def previous: Direction = South
        
        override def opposite: Direction = NorthEast
    }
    
    case object West extends Direction {
        override val azimuth: Int = 270
        
        override def next: Direction = NorthWest
        
        override def previous: Direction = SouthWest
        
        override def opposite: Direction = East
    }
    
    case object NorthWest extends Direction {
        override val azimuth: Int = 315
        
        override def next: Direction = NorthEast
        
        override def previous: Direction = NorthWest
        
        override def opposite: Direction = SouthEast
    }
    
}
