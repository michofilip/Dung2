package core.parts.position

import json.{JSONParsable, JValue}

case class Coordinates(x: Int, y: Int) extends JSONParsable {
    private def update(x: Int = x, y: Int = y): Coordinates =
        Coordinates(x, y)
    
    def moveTo(x: Int = x, y: Int = y): Coordinates =
        update(x, y)
    
    def moveBy(dx: Int = 0, dy: Int = 0): Coordinates =
        update(x + dx, y + dy)
    
    override def toJSON: JValue = {
        import json.MyJ._
        jObject(
            "x" -> x,
            "y" -> y
        )
    }
}
