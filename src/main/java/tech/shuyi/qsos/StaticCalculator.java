package tech.shuyi.qsos;

// 静态计算器
public class StaticCalculator {
    public int add(int num1, int num2) {
        return NumberUtil.isEven(num1 + num2)
                ? (num1 + num2) * 2
                : num1 + num2;
    }
}

