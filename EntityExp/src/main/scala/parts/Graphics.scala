package parts

case class Graphics(animation: Animation, initialTimestamp: Long) {
    def getFrame(timestamp: Long): Frame = animation.getFrame(timestamp - initialTimestamp)
}
