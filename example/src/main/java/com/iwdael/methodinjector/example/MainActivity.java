package com.iwdael.methodinjector.example;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JavaTest.test();
        JavaTextException.test();
        Extends.mains();
        Overrides.mains();
        Observable.just(this)
                .map(new Function<MainActivity, Integer>() {
                    @Override
                    public Integer apply(MainActivity mainActivity) throws Exception {
                        return new Integer("12");
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.err.println(s);
                    }
                });
    }
}
