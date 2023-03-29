package tech.shuyi.qsos

import spock.lang.Specification
import spock.lang.Unroll

class ComplexCalculatorTest extends Specification {
    @Unroll
    def "complex calculator with Stub #name"() {
        given: "a complex calculator"
            ComplexCalculator complexCalculator = new ComplexCalculator()
        and: "stub switch service"
            // stub a switch service return with isOpen
            SwitchService switchService = Stub(SwitchService)
            switchService.isOpen() >> isOpen
            // set switch service to calculator
            complexCalculator.setSwitchService(switchService)
        expect: "should return true"
            complexCalculator.add(num1, num2) == result
        where: "possible values"
            name                | isOpen | num1 | num2 || result
            "when switch open"  | true   | 2    | 3    || 10
            "when switch close" | false  | 2    | 3    || 5
    }

    @Unroll
    def "complex calculator with Mock #name "() {
        given: "a complex calculator"
            ComplexCalculator complexCalculator = new ComplexCalculator()
        and: "stub switch service"
            // replace Stub with Mock
            SwitchService switchService = Mock(SwitchService)
            switchService.isOpen() >> isOpen
            complexCalculator.setSwitchService(switchService)
        expect: "should return true"
            complexCalculator.add(num1, num2) == result
        where: "possible values"
            name                | isOpen | num1 | num2 || result
            "when switch open"  | true   | 2    | 3    || 10
            "when switch close" | false  | 2    | 3    || 5
    }

    @Unroll
    def "complex calculator with Mock examine action #name "() {
        given: "a complex calculator"
            ComplexCalculator complexCalculator = new ComplexCalculator()
        and: "stub switch service"
            SwitchService switchService = Mock(SwitchService)
            complexCalculator.setSwitchService(switchService)
        when: "call add method"
            def realRs = complexCalculator.add(num1, num2)
        then: "should return true and should call isOpen() only once"
            // 校验 isOpen() 方法是否只被调用 1 次
            1 * switchService.isOpen() >> isOpen
            realRs == result
        where: "possible values"
            name                | isOpen | num1 | num2 || result
            "when switch open"  | true   | 2    | 3    || 10
            "when switch close" | false  | 2    | 3    || 5
    }
}