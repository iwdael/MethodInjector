package com.iwdael.methodinjector.example;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
public abstract class AbsClass {
    String name;
    String address;

    public AbsClass(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int count(int a, int b) {
        return a + b;
    }
}
