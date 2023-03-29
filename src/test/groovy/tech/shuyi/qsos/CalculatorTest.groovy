package tech.shuyi.qsos

import spock.lang.Specification
import spock.lang.Unroll

class CalculatorTest extends Specification {

    Calculator calculator = new Calculator()

    def "test add method, 1 add 1 should equals 2."() {
        given: "init input data"
            def num1 = 1
            def num2 = 1
        when: "call add method"
            def result = calculator.add(num1, num2)
        then: "result should equals 2"
            result == 2
    }

    def "test add method, 1+1=2, 2+3=5"() {
        given: "init num1 and num2"
        def num1 = 1
        def num2 = 1
        and: "init num3 and num4"
        def num3 = 2
        def num4 = 3
        when: "call add method(num1, num2)"
        def result1 = calculator.add(num1, num2)
        and: "call add method(num3, num4)"
        def result2 = calculator.add(num3, num4)
        then: "1 add 1 should equals 2"
        result1 == 2
        and: "2 add 3 should equals 5"
        result2 == 5
    }

    def "test add method, 1 add 1 should equals."() {
        given: "init input data"
        def num1 = 1
        def num2 = 1
        expect: "1 add 1 should equals 2"
        calculator.add(num1, num2) == 2
    }

    def "test add method, -1 add -1 should equals -2."() {
        expect: "1 add 1 should equals 2"
        calculator.add(-1, -1) == -2
    }

    def "test add method with multi inputs and outputs"() {
        expect: "1 add 1 should equals 2"
        calculator.add(num1, num2) == result
        where: "some possible situation"
        num1 | num2 || result
        1    | 1    || 2
        -1   | -1   || -2
    }

    @Unroll
    def "test add method #name"() {
        expect: "1 add 1 should equals 2"
        calculator.add(num1, num2) == result
        where: "some possible situation"
        name              | num1 | num2 || result
        "positive number" | 1    | 1    || 2
        "negative number" | -1   | -1   || -2
    }

    def "test sub"() {
        expect:
        calculator.sub(5, 4) == 1
    }

    def "test mul"() {
        expect:
        calculator.mul(5, 4) == 20
    }

    def "test div"() {
        when:
        calculator.div(1, 0)
        then:
        def ex = thrown(ArithmeticException)
        ex.message == "/ by zero"
    }
}