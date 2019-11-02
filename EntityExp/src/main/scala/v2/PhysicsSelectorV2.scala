package v2

import parts.{Graphics, State}

case class PhysicsSelectorV2(physics: Map[Option[State], Graphics]) {
    def select(stateOpt: Option[State]): Option[Graphics] = physics.get(stateOpt)
}
