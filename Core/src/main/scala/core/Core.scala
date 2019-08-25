package core

import core.entities.EntityFactory
import core.parts.timer.Timer

class Core {
    val timer: Timer = new Timer().start
    val entityFactory = new EntityFactory(timer)
    
}
