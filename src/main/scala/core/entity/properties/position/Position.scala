package core.entity.properties.position

import json.MyJ.jObject
import json.{JSONParsable, JValue}

case class Position(coordinates: Coordinates, direction: Direction) extends JSONParsable {
    override def toJSON: JValue = {
        jObject(
            "coordinates" -> coordinates,
            "direction" -> direction
        )
    }
}
