# 基于 Spock 的单测快速入门

本文主要探讨 Java 技术栈的单元测试解决方案，并深入分享基于 Spock 框架的单测解决方案。

## 背景知识

对于研发同学来说，日常接触最多的几种测试方式是：**单元测试、集成测试、系统测试。**

单元测试，指的是针对一个单元（方法）的测试。单元测试不牵扯到外部组件，一般而言只在内存中执行，执行速度很快。简单地说，它成本低、速度快、单个测试的覆盖面小。

集成测试，相比于单元测试来说，集成测试的涉及面要广一些，设置起来就比较麻烦。例如我们朝昔后端项目大部分同学写的就是集成测试，需要依赖 Spring 容器，需要依赖开发或测试数据库。这样设置起来比较麻烦一些。同时，无论是组件多还是集成外部组件，这都意味着执行速度要比单元测试慢。所以相比于单元测试，集成测试成本要高一些、速度要慢一点，单个测试的覆盖面要大一些。

系统测试，对应的就是我们测试同学经常做的工作。它需要我们将各个组件或服务部署好，还要配置各种信息，这样才能保证功能测试能正常运行。所以，系统测试的特点就是成本高、速度慢，但单个测试覆盖面大。

总的来说，这几种测试方式的特点如下图所示。

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16800996838860.jpg)

根据经验来说，单元测试由于范围小、依赖少、执行速度快，因此可以花费较少的时间就定位并解决问题。而越往后面，集成测试或系统测试的范围越大、依赖越多、执行速度越慢，因此需要花费几十倍于单测的时间去定位、解决问题。**正是由于单测的这些优点，因此在一些对软件质量要求很高的项目中，单测都是必不可少的软件质量保证工具。** 根据业界经验，一个较好的测试种类配比是：70% 单测、20% 集成测试、10% 系统测试。

## 单测的价值

谈起单测，很多人都隐隐约约觉得好像又有，但也有不少人觉得单测没用，纯粹就是为了 KPI 指标。从我写单测的经验来看，单测有如下几个很明显的好处：

1. 提升系统稳定性
2. 系统更加健壮
3. 提升代码编写能力
4. 有利于稳定迭代
5. 有利于深入了解技术与业务
6. 单测让你快速定位问题
7. 提高前后端联调效率

### 提升系统稳定性

如果你每一个方法函数都经过了测试，并且其分支覆盖率都达到了较高水平，那么你很难写出有问题的代码，因为每种情况你都考虑到了。而在后续的迭代中，由于单测用例的存在，它就可以保证修改后的代码不会影响到之前的业务逻辑。简单来说，由于单测的存在，违背之前业务逻辑的代码无法运行通过，因此提高了系统稳定性。

### 系统更加健壮

系统的健壮，其实是得通过分支覆盖率来实现的。分支覆盖率，其实就是每一种可能的情况你都考虑到了，那么系统会更加健壮。举一个很简单的例子，有一个计算器类的除法函数，简单的测试可能只会用正数进行测试，更进一步地可能会用负数，但是如果用 0 去测试呢？是否能成功呢？通过对分支覆盖率的要求，使得我们考虑到更多异常情况，从而使得系统更加健壮。

### 提升代码编写能力

当我们开始对单测覆盖率和分支覆盖率有要求，并且开始写单测代码之后，我们会被迫站在另一个视角去审视我们写的代码。如果我们的代码写得一点逻辑都没有，那我们的单测会很难写，那我们会忍不住去重写它，这就间接地提升了我们的代码编写能力。与代码逻辑类似，代码的结构以及设计也会影响单测的编写，写单测可以让我们重新审视自己写得代码，从而间接提升我们的代码编写能力。

### 有利于稳定迭代

如果你写得代码质量很高，只有非常少的 bug，甚至一个 bug 都没有。那么测你需求的测试肯定很开心，因为直接一把过呀！从项目管理层面来看，如果每个人都能做到这样的程度，那么项目的迭代是非常稳定、并且高效的！

### 有利于深入理解技术与业务

当我们写单测的时候，我们必须去了解某个东西是怎么运行的。如果你没有了解清楚某块业务，就开始去修改这块业务的代码，那之前的单测用例会教你做人，频频报错的单测信息会让你寸步难行。

单测用例的存在让你必须弄清楚这块业务的逻辑，才可以写新的业务逻辑，这间接促进了我们对于业务的了解。此外，我们写单测的时候经常会进行 Stub 和 Mock，这会要求你必须弄清楚项目中用到的技术原理，不然你无法成功编写单测代码，这也同样间接促进你去了解项目中用到的技术。

### 单测让你更快定位问题

在实际工作中，开发人员不想进行单元测试，认为没有必要且效率不高，其实错误发生和被发现之间的时间与发现和改正该错误的成本是指数关系，频繁的单元测试能使开发人员排错的范围缩得很小，大大节约排错所需的时间，同时错误尽可能早地被发现和消灭会减少由于错误而引起的连锁反应。

在某一功能点上进行准备测试、执行测试和修改缺陷的时间，单元测试的效率大约是集成测试的两倍、系统测试的三倍。

### 提高前后端联调效率

在单测都通过切覆盖率足够的情况下，接口质量会比较高，联调会非常顺利。但如果接口质量较差，就会导致联调的时候发现一个问题就解决一个，最终导致联调效率低下。

## 单测的缺陷
说完了单测的价值，也有必要来说说单测的不足，毕竟有好就有坏，一个东西不可能十全十美。

### 单测确实耗时
相对比于只写业务代码来说，要多写一些单测代码确实需要耗费更多时间，特别对于刚刚开始写单测的同学来说，会耗费更长的时间。即使作为一个老手，对写单测非常熟悉了，写单测还是会花一点时间。

### 单测作用有限

当你深入了解单测，并且在几个迭代的需求开发中实践了之后，你会发现 —— 其实单测并不是银弹。在对我们过去 5 个迭代的有效缺陷进行分析之后，我发现缺陷的原因多种多样，分别有：

1. 需求不清晰
2. 第三方接口问题
3. 需求理解不正确
4. 对其他业务理解不清晰
5. 技术编码问题

对于单测来说，它只能解决最后一类问题，即：技术编码问题。更加准确地说，单测只能解决 —— 你觉得自己写对了，但是实际上你没写对的问题！对于需求不清晰的问题，单测无能为力，毕竟你自己都不知道，代码里都没体现，那单测怎么帮你解决呢。

### 单测有维护成本

当我们推行单测之后，每次代码变更肯定需求所有单测用例都执行通过，不然单测就失去了其作用。当业务变化时，我们就需要去维护原有的单测用例，调整其中的逻辑，使其符合现有的业务现状。对于研发人员来说，这也是一个维护成本，需要投入时间和精力去做。特别是对于变化大、特别频繁的业务来说，这个成本更是成倍上升。

###单测有学习成本

一个成熟的单测体系，需要有一套合适的框架以及相对应的知识体系来支撑。这时候需要有相对资深的研发人员去进行技术调研，并且摸索出一套最佳实践。一个单测框架即使多方便，还是需要开发人员投入时间去学习，而学习以及应用实践都是需要学习成本的。

## 单测的评估维度

在我看来，是否推行单测以及单测的要求有多高，主要取决于几个维度：代码复用率、业务变化率、人员变化率、业务重要性。

1. 代码复用率。代码复用率越高，越有必要推行单测，越有必要提升单测的要求。因此这些代码被很多业务引用，因此其一旦有问题便会影响很多业务方，在这样的代码推行单测是收益较高的。
2. 业务变化率。 业务变化越快，越不适合用单测。如果业务变化非常快，一个单测的内容上线了没几天就又要修改，那么你不仅仅需要修改业务代码，还需要修改单测代码，那就是双倍的工作量了。
3. 人员变化率。 人员变化率指的是某个模块负责人的变化情况。如果某个模块的负责人经常变来变去，那么也是不太适合推行单测的。因为新负责的人需要花大量的时间去熟悉单测的内容，这会导致需求开发的时间变得非常长。
4. 业务重要性。 越是核心的业务，越有必要推行单测，并且越有必要以高标准要求。因为核心业务的稳定性、健壮性对于公司来说肯定非常重要，而单测确实是能够在最小单元去提升系统稳定性和系统健壮性。

**上面提到的 4 个衡量维度，我们不能单一地去看待，而是要根据实际情况去综合判断。** 例如某个业务的人员变化就是很频繁，那就一定不适合推行单测吗？其实并不是，而是说对于人员变化非常频繁的业务，其推行单测成本会很高。

但如果这块业务就是非常重要，但他的人员变化就是很频繁，那强制以高标准的单测要求确实可以提高系统健壮性和系统稳定性，但是代价就是研发时间很长。如果产研团队能够接受这个结果，那么这么做就是合理的。

**总的来看，是否推行单测以及推行单测的标准高低，需要我们根据上面几个维度去综合考量，得出一个最适合的标准！**

## 单测的适用场景

经过上面对于单测的价值以及缺陷的探讨，我们基本上对单测有了一个深入的理解，自然而然我们会思考一个问题：单测的最佳适用场景是什么？

### 基础框架组件或服务

对于基础框架组件或服务来说，它们被很多其他系统或模块引用，因此它们的代码复用率很高。一旦它们出现问题，就会出现连锁反应，导致所有与此相关的服务或组件出问题。所以，为了保证这些组件或服务的稳定性和健壮性，一般基础组件或服务都比较适合以较高标准推行单测。

### 核心业务模块

对于核心业务模块来说，这些功能模块是否稳定往往关系公司的营收。如果因为某个异常边缘场景没有覆盖到，或者说新上线一个功能导致核心业务出现问题，那么会直接导致公司遭受金钱损失。因此对于核心业务模块来说，比较适合以较高标准推行单测。

## 单测技术选型

对于单测来说，目前常用的单测框架有：

- JUnit
- Mockito
- Spock
- PowerMock
- JMockit
- TestableMock

其中 JUnit 不支持 Mock，因此基本不会只用 JUnit，而是结合其他有 Mock 功能的框架一起使用。从知名度及使用率来说，Mockito 和 Spock 使用较多，而 PowerMock、JMockit、TestableMock 使用较少。下面我们将主要对比 Mockito 和 Spock 两种框架的差异。

### Mockito
Mockito 是 Java 单元测试中的 Mock 框架，一般都是与 JUnit 一起使用。Mockito 功能强大，几乎所有你能想到的功能都支持，并且由于发布时间较长，因此使用的人非常多。

* 优点：功能强大、使用人数多、资料丰富。
* 缺点：代码不够简洁、没有统一的单测结构、不支持静态方法和私有方法 Mock。

更多信息详见官网：[https://site.mockito.org/](https://site.mockito.org/)

### Spock

Spock 是一个企业级的测试规范框架，可用来测试 Java 和 Groovy 应用。Spock 最大的特色是其简洁美观的语言规范。Spock 兼容绝大多数 IDE、编译工具和 CI 集成服务器。Spock 框架使用 Groovy 语言编写，而 Groovy 语言则是 Java 语言的超集，绝大多数 Java 语言语法在 Groovy 中都支持。

* 优点：单测结构统一、代码简洁、异常测试及参数测试支持更好。
* 缺点：学习成本略高、不支持静态方法和私有方法 Mock。

更多信息详见官网：[https://spockframework.org/](https://spockframework.org/)

### Mockito vs Spock

在 [Spock vs JUnit 5 - the ultimate feature comparison](https://blog.solidsoft.pl/2020/04/15/spock-vs-junit-5-the-ultimate-feature-comparison/) 中详细对比了 Mokito 与 Spock 的差异，他们在发展情况、学习曲线、工具支持等方面的比较如下图所示。

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16800999130484.jpg)

从上图可以看到，Mockito 框架在发展、学习曲线、工具支持、从 JUnit4 迁移几方面比较有优势。而 Spock 框架则在测试结构、异常测试、条件测试等方面比较有优势。因此，选择哪个测试框架完全基于实际情况。例如，如果你目前的情况是：

1. Java 是唯一的语言。
2. 想要更强的编译时错误检查。
3. 更稳定、更主流的实现方式。

那么选择 JUnit + Mockito 的方式是更好的选择。但如果你目前的情况是：

1. 希望单测跟简单易读
2. 更简洁的参数测试与异常测试

那么选择 Spock 会是更好的选择。

## 为啥选择 Spock？

根据前面的分析，Mockito 的主要优势在于比较稳定、主流，缺点在于不够简洁易读。而 Spock 虽然使用人群没有 Mockito 那么多，但国内也有一些大厂在使用 Spock，例如美团等（可参考：[Spock单元测试框架介绍以及在美团优选的实践](https://tech.meituan.com/2021/08/06/spock-practice-in-meituan.html)）。

**我们重视写单测，但是又不希望写单测花费太多时间，毕竟业务才是第一位的。因此，我们希望单测代码尽可能简洁、可维护。** 基于这个原因，我们选择了 Spock 框架作为朝昔后端的单测框架解决方案。而 Spock 不支持 static 方法及 private 方法 Mock 的缺陷，则尝试通过整合 PowerMock 或 TestableMock 来解决。

### 可维护性更强

在极客时间[《程序员的测试课》](https://time.geekbang.org/column/intro/100085101?tab=catalog)中，有一节关于讲了[一个好的自动化测试长什么样？](https://time.geekbang.org/column/article/407452)在这里面，作者提到一个好的单测应该由 `准备、执行、断言、清理` 4 个阶段组成。

对于 Mockito 而言，它并没有规定具体的代码规范，因此只能依靠注释来标注哪些代码是准备阶段的代码，哪些是执行阶段的代码，哪些是断言阶段的代码，如下代码所示。

```java
class SimpleCalculatorTest {
    @Test
    void shouldAddTwoNumbers() {
        //given 准备
        Calculator calculator = new Calculator();
        //when 执行
        int result = calculator.add(1, 2);
        //then 断言
        assertEquals(3, result);
    }
}
```

对于 Spock 而言，其通过 given-when-then 的结构，强制要求编写者将不同阶段的代码放到不同的位置，从而增强了可读性。同样是用于测试计算器的加法函数的单测用例，使用 Spock 框架编写的单测如下代码所示。

```java
class SimpleCalculatorSpec extends Specification {
    def "should add two numbers"() {
        given: "create a calculater instance"
            Calculator calculator = new Calculator()
        when: "get calculating result via the calculater"
            int result = calculator.add(1, 2)
        then: "assert the result is right"
            result == 3
    }
}
```

可以看到，通过 `given-when-then` 结构的划分，我们可以更加快速地弄清楚单测的内容，从而提高单测的可读性，使得单测更加容易维护。

### 代码更加简洁

对于 Mockito 与 Spock 而言，它们之间的一个很大的差别是：Spock 的代码更加简洁。这个特性可以让我们编写比 Mockito 更少的代码，从而实现同样的功能。例如在 Mockito 中，我们 Mock 某个接口实现时，通常需要写一长串的 `give(...).return(...)` 代码。而在进行断言的时候，也需要写比较长的 `then(xx).should(xx).checkxx()` 代码，如下图所示。

```java
@Test
public void should_not_call_remote_service_if_found_in_cache() {
    //given
    given(cacheMock.getCachedOperator(CACHED_MOBILE_NUMBER)).willReturn(Optional.of(PLUS));
    //when
    service.checkOperator(CACHED_MOBILE_NUMBER);
    //then
    then(webserviceMock).should(never()).checkOperator(CACHED_MOBILE_NUMBER);
    verify(webserviceMock, never()).checkOperator(CACHED_MOBILE_NUMBER);  
}
```

但在 Spock 中的代码就相对比较简洁，如下所示代码实现了上述 Mockito 代码同样的功能。

```java
def "should not hit remote service if found in cache"() {
    given:
        cacheMock.getCachedOperator(CACHED_MOBILE_NUMBER) >> Optional.of(PLUS)
    when:
        service.checkOperator(CACHED_MOBILE_NUMBER)
    then:
        0 * webserviceMock.checkOperator(CACHED_MOBILE_NUMBER)
}
```

可以看到，Spock 没有 given、willReturn 等关键词，而是取而用 >> 等符号来实现，这样代码更加简洁，阅读起来也更加明了。

案例代码对比：[https://www.yuque.com/lugew/spock/wkxhvk](https://www.yuque.com/lugew/spock/wkxhvk)

## 快速入门 Spock

使用 Spock 非常简单，只需要引入对应的 Spock 依赖包就可以写 Spock 单测代码了。下面我将演示一个使用 Spock 进行单测的最小项目，帮助大家最快上手 Spock。本文档所有例子可在 Github 项目中找到，地址：[chenyurong/quick-start-of-spock: 深入浅出 Spock 单测](https://github.com/chenyurong/quick-start-of-spock)

首先，我们使用 Spring Initializr 初始化一个项目，不需要引入任何依赖应用，这里我命名为 quick-start-of-spock。项目初始化完成之后，在 pom.xml 文件中添加 Spock 依赖，如下代码所示。

```java
<dependency>
    <groupId>org.spockframework</groupId>
    <artifactId>spock-core</artifactId>
    <version>1.2-groovy-2.4</version>
</dependency>
```

接着，我们编写一个计算器类，用来演示 Spock 单测的使用，代码如下所示。

```java
package tech.shuyi.qsos

public class Calculator {
    public int add(int num1, int num2) {  
        return num1 + num2;  
    }
    public int sub(int num1, int num2) {  
        return num1 - num2;  
    }
    public int mul(int num1, int num2) {  
        return num1 * num2;  
    }
    public int div(int num1, int num2) {  
        return num1 / num2;  
    }  
}
```

接着，我们为 Calculator 生成一个测试类，放在 test 目录下即可，名称命名为 CalculatorTest.groovy，代码如下所示。

```java
package tech.shuyi.qsos

import spock.lang.Specification

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
```

这个测试类中，针对 Calculator 类的 4 个加减乘除方法都配置了对应的单测用例。到这里，Spock 的代码就编写完成了。我们直接点击 CalculatorTest 类左边的运行按钮即可运行整个单测用例，如下图所示。

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16801018454020.jpg)

正常情况下，所有单测用例都应该通过测试，都显示绿色的图标，如下图所示。

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16801018724015.jpg)

我们还可以用来计算一下单测覆盖率，运行入口如下图所示。

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16801018994526.jpg)

点击运行之后，会弹出单测覆盖率结果，我这里对所有方法都覆盖了，因此覆盖率是 100%，如下图所示。

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16801023152923.jpg)

到这里，一个最小单元的 Spock 示例项目就结束了。

## Spock 语法块

对于 Spock 来说，其最大的特点是使用 give-when-then 等结构来规范了单测的写法，这也是一种非常好的单测规范。因此，了解 Spock 的语法块，知道每个关键词代表的意思就显得非常重要了。

### 基础语法

对于 Spock 来说，最常用的几个语法块关键词有：
- given
- when
- then
- and
- expect

#### given 

given 代码块通常用来进行数据准备，以及准备 mock 数据。例如上面计算器加法单测的例子：

```groovy
def "test add method, 1 add 1 should equals 2."() {
    given: "init input data"
        def num1 = 1
        def num2 = 1
    when: "call add method"
        def result = calculator.add(num1, num2)
    then: "result should equals 2"
        result == 2
}
```

我们在 given 代码块中初始化了 num1 和 num2 两个数据，用于后续计算加法的入参。一般情况下，given 标签都是位于单测的最前面，但 given 代码块并不是必须的。因为如果初始化的数据并不复杂，那么它可以直接被省略。

例如我们这个例子中，初始化的数据只是两个变量，并且数据很简单，那么我们就可以不需要定义变量，而是直接写在入参处，如下代码所示。

```groovy
def "test add method, 1 add 1 should equals 2."() {
    when: "call add method"
        def result = calculator.add(1, 1)
    then: "result should equals 2"
        result == 2
}
```

#### when

when 代码块主要用于被测试类的调用，例如我们计算器的例子中，我们在 when 代码块中就调用了 Calculator 类的 add 方法，如下代码所示。

```groovy
def "test add method, 1 add 1 should equals 2."() {
    given: "init input data"
        def num1 = 1
        def num2 = 1
    when: "call add method"
        // 调用 Calculator 类的 add 方法
        def result = calculator.add(num1, num2)
    then: "result should equals 2"
        result == 2
}
```

#### then

then 代码块主要用于进行结果的判断，例如我们计算器的例子中，我们就在 then 代码块中判断了 result 的结果，如下代码所示。

```groovy
def "test add method, 1 add 1 should equals 2."() {
    given: "init input data"
        def num1 = 1
        def num2 = 1
    when: "call add method"
        def result = calculator.add(num1, num2)
    then: "result should equals 2"
        // 判断 result 结果
        result == 2
}
```

#### and

and 代码块主要用于跟在 given、when、then 代码块后，用于将大块的代码分割开来，易于阅读。例如我们计算器的例子，我们假设初始化的数据很多，那么都堆在 given 代码中不易于理解，那么我们可以将其拆分成多个代码块。同理，我们在 when 和 then 代码块中的代码也可以进行同样的拆分，如下代码所示。

```groovy
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
```

#### expect

expect 代码块是 when-then 代码块的精简版本，有时候我们的测试逻辑很简单，并不需要把触发被测试类和校验结果的逻辑分开，这时候就可以用 expect 替代 when-then 代码块。例如计算器的例子中，我们就可以用如下的 expect 代码块来替换 when-then 代码块。

```groovy
def "test add method, 1 add 1 should equals."() {
    given: "init input data"
        def num1 = 1
        def num2 = 1
    expect: "1 add 1 should equals 2"
        calculator.add(num1, num2) == 2
}
```

到这里，关于 Spock 语法块的基础语法介绍就结束了。

### 最佳实践

看完了 Spock 语法块的介绍之后，是不是觉得有点懵，不知道应该怎样搭配使用？没关系，其实你用多了之后就会发现，其实常用的搭配就那几种。这里我总结几种代码块的最佳实践，记住这几种就可以了。

#### given-when-then

given-when-then 组合是使用最多的一种，也是普适性最强的一种。你可以不记得其他的语法块，但这一种你必须记住。对于 given-when-then 组合来说，它的用法如下：

- given：用来定义初始数据、以及 Mock 信息。
- when：用来触发被测试类的方法。
- then：用来进行结果的校验。

根据测试逻辑的复杂程度，我们可以自由地在这三个代码块的后面加上 and 代码块，从而使得代码更加地简洁易读。given-when-then 组合的示例如下代码所示。

```groovy
def "test add method, 1 add 1 should equals 2."() {
    given: "init input data"
        def num1 = 1
        def num2 = 1
    when: "call add method"
        def result = calculator.add(num1, num2)
    then: "result should equals 2"
        result == 2
}
```

#### given-expect

given-expect 是 given-when-then 的简化版本，主要用于简化代码，提升我们写代码的效率。本质上来说，其就是把 when-then 组合在一起，换成了 expect 代码块。对于 given-expect 组合来说，它的用法如下：

- given：用来定义初始数据、以及 Mock 信息。
- expect：用来触发被测试类的方法，并进行结果校验。

如果触发被测试类以及结果校验的逻辑很简单，那么你可以尝试用 given-expect 组合来简化代码。given-expect 组合的示例如下代码所示。

```groovy
def "test add method, 1 add 1 should equals."() {
    given: "init input data"
        def num1 = 1
        def num2 = 1
    expect: "1 add 1 should equals 2"
        calculator.add(num1, num2) == 2
}
```

更进一步，如果单测逻辑中初始化数据的逻辑也很简单，那么你可以直接省略 given 代码块，直接写一个 expect 代码块即可！

```groovy
def "test add method, 1 add 1 should equals 2."() {
    expect: "1 add 1 should equals 2"
        calculator.add(1, 1) == 2
}
```

### 高级语法

#### where

where 代码块是 Spock 用于简化代码的又一利器，它能以数据表格的形式一次性写多个测试用例。还是拿上面的计算器加法函数的例子，我们可能会测试正数是否运算正确，也需要测试负数是否运算正确。如果没有用 where 代码块，那么我们需要重复写两个测试函数，如下代码所示：

```groovy
def "test add method, 1 add 1 should equals 2."() {
    expect: "1 add 1 should equals 2"
        calculator.add(1, 1) == 2
}
def "test add method, -1 add -1 should equals -2."() {
    expect: "1 add 1 should equals 2"
        calculator.add(-1, -1) == -2
}
```

如果使用了 where 代码块，那么可以将其合并成一个测试函数，如下代码所示：

```groovy
def "test add method with multi inputs and outputs"() {
    expect: "1 add 1 should equals 2"
        calculator.add(num1, num2) == result
    where: "some possible situation"
        num1 | num2 || result
        1    | 1    || 2
        -1   | -1   || -2
}
```

上面代码运行的结果如下图所示：

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16801024743823.jpg)

可以看到两个测试用例都整合在一行了，这样不当某行数据出错的时候，我们不知道到底是哪个出错。其实我们可以使用 @Unroll 注解给每个行测试数据起个名字，这样方便后续知道哪个用例出错，如下代码所示：

```groovy
@Unroll
def "test add method #name"() {
    expect: "1 add 1 should equals 2"
        calculator.add(num1, num2) == result
    where: "some possible situation"
        name              | num1 | num2 || result
        "positive number" | 1    | 1    || 2
        "negative number" | -1   | -1   || -2
}
```

这样每个测试用例都会独自成为一行，如下图所示：

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16801025077256.jpg)

一般来说 where 代码块可以放在 expect 后，也可以跟在 then 后，其执行效果都一样。

#### stub

在单测中会有很多外部依赖，我们需要把外部依赖排除掉，其中有一个很常见的场景是：需要让外部接口返回特定的值。而单测中的 stub 就是用来解决这个问题的，通过 stub 可以让外部接口返回特定的值。

说起 stub 这个单词，一开始很不理解。但后面查了查它的英文单词，再联想一下其使用场景，就很容易理解了。stub 英文是树桩的意思，啥是树桩，就是像下面的玩意。单测的 stub 就是在外部依赖接口那里立一个树桩，当你跑到那个位置遇到了桩子（单测执行），就自动弹回来（返回特定值）。

![](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16801004607396.jpg)

在 Spock 中使用 stub 非常简单，只需要两步即可：
1. 确定需要 stub 的对象
2. 指定 stub 对象被调用方法的行为
举个例子，现在我们有一个更加复杂的计算器，里面有一个加法函数。该加法函数调用了开关服务的 isOpen 接口用于判断开关是否打开。当开关打开时，我们需要将最终的结果再乘以 2。当开关服务关闭时，直接返回原来的值。这个复杂计算器类的代码如下所示：

```groovy
public class ComplexCalculator {
    SwitchService switchService;

    public int add(int num1, int num2) {
        return switchService.isOpen()
                ? (num1 + num2) * 2
                : num1 + num2;
    }
    public void setSwitchService(SwitchService switchService) {
        this.switchService = switchService;
    }
}
```

我们并不知道 SwitchService 的具体逻辑是什么，但我们只知道开关打开时结果乘以 2，开关关闭时返回原来的结果。那么我们如何测试我们的加法函数是否编写正确呢？这时候就需要用到 stub 功能去让 SwitchService 接口返回特定的值，以此来测试我们的 add 函数是否正确了。此时的测试类代码如下所示：

```groovy
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
}
```

如上代码所示，我们在 and 代码块中 stub 了一个 SwitchService 对象，并将其复制给了 ComplexCalculator 对象，对象返回的值取决于 isOpen 属性的值。最后，在 where 代码块里，我们分别测试了开关打开和关闭时的场景。

#### mock 

mock 又是单测中一个非常重要的功能，甚至很多人会把 mock 与 stub 搞混，以为 stub 就是 mock，实际上它们很相似，但又有所区别。应该说：mock 包括了 stub 的所有功能，但是 mock 有 stub 没有的功能，那就是校验 mock 对象的行为。

我们先来说第一个点：mock 包括了 stub 的所有功能，即 mock 也可以插桩返回特定数据。在这个功能上，mock 其用法与 stub 一模一样，你只需要把 Stub 关键词换成 Mock 关键词即可，例如下面的代码与上文 stub 例子中代码的功能是一样的。

```groovy
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
```

接着，我们讲第二个点，即：Mock 可以校验对象的行为，而 stub 不行。举个例子，在上面的例子中，我们知道 add() 方法需要去调用 1 次 switchService.isOpen() 方法。但实际上有没有调用，我们其实不知道。

虽然我们可以去看代码，但是如果调用层级和链路很复杂呢？我们还是要一行行、一层层去调用链路吗？这时候 Mock 的校验对象行为功能就发挥出价值了！

```groovy
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
```

如上代码所示，第 12 行就用于校验 isOpen() 方法是否被调用了 1 次。除了判断是否被调用过之外，Mock 还能判断参数是否是特定类型、是否是特定的值等等。

如果必须要掌握一个功能，那么只掌握 mock 就好。但为了让代码可读性更高，如果只需要返回值，不需要校验对象行为，那还是用 Stub 即可。如果既需要返回值，又需要校验对象行为，那么才用 Mock。

#### thrown

有时候我们在代码里会抛出异常，那么我们怎么校验抛出异常这种情况呢？Spock 框架提供了 thrown 关键词来对异常抛出做校验。以计算器的例子为例，当我们的分母是 0 的时候会抛出 ArithmeticException 异常，此时我们便可以用 thrown 关键词捕获，如下代码所示。

```groovy
// 除法函数
public int div(int num1, int num2) {  
    return num1 / num2;  
}  

// 测试用例
def "test div"() {
    when:
        calculator.div(1, 0)
    then:
        def ex = thrown(ArithmeticException)
        ex.message == "/ by zero"
}
```

在 then 代码块中，我们用 thrown(ArithmeticException) 表明调用 calculator.div(1, 0) 时会抛出异常，并且用一个 ex 变量接收该异常，随后还对其返回的信息做了校验。

## 总结

关于 Spock 的简单使用就介绍到这里，相信通过本文的学习，你已经掌握了 Spock 的基本使用。

如果你想学习更多关于单测的知识，想了解更多单测的实践信息，欢迎加入「树哥的单测交流群」。只需扫描下方二维码加我微信好友，备注「加入单测交流群」即可（必须备注，不备注不通过）。

![陈树义的微信二维码](https://shuyi-tech-blog.oss-cn-shenzhen.aliyuncs.com/halo_blog_system_file/16636802444420.jpg)

## 参考资料

* [《Java Testing with Spock》中文版](https://www.yuque.com/lugew/spock)
* [Spock vs JUnit 5 - the ultimate feature comparison](https://blog.solidsoft.pl/2020/04/15/spock-vs-junit-5-the-ultimate-feature-comparison/)
* [Spock单元测试框架介绍以及在美团优选的实践](https://tech.meituan.com/2021/08/06/spock-practice-in-meituan.html)
