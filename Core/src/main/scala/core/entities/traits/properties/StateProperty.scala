package core.entities.traits.properties

import core.entities.Entity
import core.parts.state.State

trait StateProperty extends Entity {
    val state: State
    
    protected def setState(state: State): StateProperty
}
