package core

import core.entities.EntityFactory
import core.parts.timer.Timer

class Core {
    val clock: Timer = new Timer(0, true)
    val entityFactory = new EntityFactory(clock)
    
}
