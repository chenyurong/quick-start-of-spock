package tech.shuyi.qsos;

public class MockPrivateClass {
    public String mockPrivateFunc() {
        return  privateFunc();
    }
    private String privateFunc() {
        return "private func";
    }
}