package tech.shuyi.qsos;

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