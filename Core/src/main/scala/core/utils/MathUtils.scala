package core.utils

object MathUtils {
    
    def bound(x: Int, lowerBound: Int, upperBound: Int): Int =
        if (x < lowerBound) lowerBound else if (x < upperBound) x else upperBound - 1
    
    def mod(x: Int, n: Int): Int =
        if (x < 0) x % n + n else if (x < n) x else x % n
    
    implicit class Mod(x: Int) {
        def %%(n: Int): Int = mod(x, n)
    }
    
}
