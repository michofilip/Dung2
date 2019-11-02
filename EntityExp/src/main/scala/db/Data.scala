package db

import model.PhysicsV2

class Data {
    val physics: Map[Int, PhysicsV2] = Map(
        0 -> PhysicsV2(id = 0, solid = false, opaque = false),
        1 -> PhysicsV2(id = 1, solid = false, opaque = true),
        2 -> PhysicsV2(id = 2, solid = true, opaque = false),
        3 -> PhysicsV2(id = 3, solid = true, opaque = true)
    )
}
