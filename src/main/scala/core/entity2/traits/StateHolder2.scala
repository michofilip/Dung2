package core.entity2.traits

import core.entity2.Entity2
import core.parts.state.State

trait StateHolder2 extends Entity2 {
    val state: State
    
    protected def setState(state: State): StateHolder2
}