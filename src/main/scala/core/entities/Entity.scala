package core.entities

import json.JSONParsable

abstract class Entity[T <: Entity[T]] extends JSONParsable {
    val id: String
}

object Entity {
    //todo container (extends Openable)
    //todo npc, possibly merge with player
}
