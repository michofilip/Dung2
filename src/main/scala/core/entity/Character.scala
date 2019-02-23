package core.entity

import core.entity.properties.state.State.CharacterState
import core.entity.properties.StateHolder

abstract class Character extends MapEntity with StateHolder {
    // TODO basic template
    def setCharacterState(characterState: CharacterState, timeStamp: Long): T =
        if (characterState != state)
            setState(characterState, timeStamp)
        else
            setState(state, timeStamp)
}