package core.entity.properties

import core.entity.Entity
import core.parts.state.State

trait StateHolder extends Entity {
    val state: State
    
    protected def setState(state: State): T
}