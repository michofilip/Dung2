package core.entity.properties.position

import json.{JSONParsable, JValue}

case class Position(coordinates: Coordinates, direction: Direction) extends JSONParsable {
    override def toJSON: JValue = {
        import json.MyJ._
        jObject(
            "coordinates" -> coordinates,
            "direction" -> direction
        )
    }
}
