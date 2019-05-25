package math

import org.scalatest.FunSuite

class MyMathTest extends FunSuite {
    
//    test("testMod") {
//
//    }
//
//    test("testMod") {
//
//    }
    
    test("testRestrict") {
        assert(MyMath.restrict(5, 0, 10) == 5)
        assert(MyMath.restrict(-5, 0, 10) == 0)
        assert(MyMath.restrict(15, 0, 10) == 9)
    }
    
}
