package tech.shuyi.qsos

import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Ronald Chan
 * @date 2023-04-03
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Sputnik.class)
@PrepareForTest([NumberUtil.class])
class StaticCalculatorTest extends Specification {

    def setup() {
        PowerMockito.mockStatic(NumberUtil.class)
    }

    @Unroll
    def "static calculator, correct example #name"() {
        given: "a static calculator"
            StaticCalculator staticCalculator = new StaticCalculator()
        and: "return true when call isEven method"
            PowerMockito.when(NumberUtil.isEven(Mockito.any())).thenReturn(isEven);
        expect: "should return true"
            staticCalculator.add(num1, num2) == result
        where: "possible values"
            name                | isEven | num1 | num2 || result
            "when switch open, should pass."  | true   | 2    | 3    || 10
            "when switch close, should pass." | false  | 2    | 2    || 4
    }
}