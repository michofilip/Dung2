package core.entity.properties

import core.entity.Entity
import core.parts.state.State

trait StateHolder[T <: StateHolder[T]] extends Entity[T] {
    val state: State
    
    protected def setState(state: State): T
}