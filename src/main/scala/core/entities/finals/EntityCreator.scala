package core.entities.finals

import core.entities.properties.EntityCreatorHolder
import json.{JValue, MyJ}

final class EntityCreator(override val id: String) extends EntityCreatorHolder[EntityCreator] {
    override def toJSON: JValue = {
        MyJ.jObject(
            "class" -> this.getClass.getSimpleName,
            "id" -> id
        )
    }
}