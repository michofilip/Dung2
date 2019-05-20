package math

object MyMath {
    
    def restrict(x: Int, lowerBound: Int, upperBound: Int): Int = {
        if (x < lowerBound) {
            lowerBound
        } else if (x < upperBound) {
            x
        } else {
            upperBound - 1
        }
    }
    
    def mod(x: Int, y: Int): Int = {
        val m = x % y
        if (m < 0) {
            y + m
        } else {
            m
        }
    }
    
    implicit class Mod(x: Int) {
        def %%(y: Int): Int = mod(x, y)
    }
    
}
