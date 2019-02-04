package core

import core.entity.EntityFactory
import core.timer.Timer

class Core {
    val clock: Timer = new Timer(0, true)
    val entityFactory = new EntityFactory(clock)
    
}
