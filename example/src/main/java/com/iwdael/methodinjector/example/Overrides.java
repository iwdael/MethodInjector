package com.iwdael.methodinjector.example;

import android.content.res.Resources;
import android.graphics.Bitmap;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
public class Overrides {
    public static void mains() {
        MethodImpl method = new MethodImpl() {
            @Override
            void method(Bitmap bitmap, Resources resources) {
                super.method(bitmap, resources);
                new MethodImpl(){
                    @Override
                    void method(Bitmap bitmap, Resources resources) {
                        super.method(bitmap, resources);
                    }
                };
            }
        };

    }
}
