package repositories

import scala.io.Source

class GraphicsRepository {
    private val rawGraphics: Vector[Char] = Source.fromResource("graphics.txt").toVector
//    rawGraphics.foreach(println)
}
