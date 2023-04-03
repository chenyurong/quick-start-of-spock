package tech.shuyi.qsos

import org.junit.runner.RunWith
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
@PrepareForTest([MockPrivateClass.class])
class MockPrivateCalculatorTest extends Specification {

    MockPrivateClass mockPrivateClass

    def setup() {
        mockPrivateClass = PowerMockito.spy(new MockPrivateClass())
    }

    @Unroll
    def "mock private method example"() {
        given: "调用私有方法返回特定数据"
            PowerMockito.when(mockPrivateClass, "privateFunc").thenReturn("test")
        expect: "应该成功返回特定数据"
            mockPrivateClass.mockPrivateFunc() == "test"
    }
}