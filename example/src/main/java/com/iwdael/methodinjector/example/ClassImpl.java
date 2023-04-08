package com.iwdael.methodinjector.example;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
public abstract class ClassImpl extends AbsClass {
    public ClassImpl(String name, String address) {
        super(name, address);
    }

    public void setOnSettingListener(OnSettingListener listener) {
        listener.set(true);
    }

    public void setOnClass(ClassImpl impl) {

    }
}
