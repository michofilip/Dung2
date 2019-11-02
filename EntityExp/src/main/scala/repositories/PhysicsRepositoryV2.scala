package repositories

import db.Data
import model.PhysicsV2

class PhysicsRepositoryV2(implicit data: Data) {
    def findById(id: Int): Option[PhysicsV2] = data.physics.get(id)
}
