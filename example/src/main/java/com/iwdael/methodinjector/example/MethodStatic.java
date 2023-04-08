package com.iwdael.methodinjector.example;

import android.os.Bundle;
import android.util.Log;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
public class MethodStatic {


    public static void call(String apple, int banner) {
    }

    public static void call(String apple) {
    }

    public static void call(Object obj) {
    }

    public void call(Bundle obj) {
        Log.i("method-chain", "thread[" + Thread.currentThread().getName() + "] com.iwdael.methodinjector.Utils.cal2(Utils.java:17) arg1: " + obj + "on hashCode(" + this.hashCode() + ")");
    }

    public static void call() {
    }

    public static void call2(String apple, int banner) {
        Log.i("method-chain", "thread[" + Thread.currentThread().getName() + "] com.iwdael.methodinjector.Utils.cal2(Utils.java:17)" + " arg1: " + apple + "arg2: " + banner);
    }
}

