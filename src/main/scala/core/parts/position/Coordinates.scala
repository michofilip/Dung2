package core.parts.position

import json.{JSONParsable, JValue}

case class Coordinates(x: Int, y: Int) extends JSONParsable {
    override def toJSON: JValue = {
        import json.MyJ._
        jObject(
            "x" -> x,
            "y" -> y
        )
    }
}
