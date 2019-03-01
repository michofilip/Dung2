package core.entities.properties

import core.entities.Entity
import core.parts.state.State

trait StateHolder[T <: StateHolder[T]] extends Entity[T] {
    val state: State
    
    protected def setState(state: State): T
}