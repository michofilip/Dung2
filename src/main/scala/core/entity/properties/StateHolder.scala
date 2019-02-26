package core.entity.properties

import core.entity.Entity
import core.entity.properties.state.State

trait StateHolder extends Entity {
    val state: State
}