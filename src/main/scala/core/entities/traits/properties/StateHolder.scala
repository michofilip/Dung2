package core.entities.traits.properties

import core.entities.Entity
import core.parts.state.State

trait StateHolder extends Entity {
    val state: State
    
    protected def setState(state: State): StateHolder
}
