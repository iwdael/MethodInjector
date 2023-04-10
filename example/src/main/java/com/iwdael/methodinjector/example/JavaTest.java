package com.iwdael.methodinjector.example;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
public class JavaTest {

    public static void test() {
        JavaTest type = new JavaTest();
        type.func(true);
        type.func(Boolean.valueOf("true"));
        type.func(new boolean[]{true, false});
        type.func(new Boolean[]{true, false});

        type.func((char) 10);
        type.func(Character.valueOf((char) 10));
        type.func(new char[]{(char) 10});
        type.func(new Character[]{Character.valueOf((char) 10)});

        type.func((byte) 10);
        type.func(Byte.valueOf((byte) 10));
        type.func(new byte[]{(byte) 10});
        type.func(new Byte[]{Byte.valueOf((byte) 10)});


        type.func((short) 10);
        type.func(Short.valueOf((short) 10));
        type.func(new short[]{(short) 10});
        type.func(new Short[]{Short.valueOf((short) 10)});

        type.func((int) 10);
        type.func(Integer.valueOf((int) 10));
        type.func(new int[]{(int) 10});
        type.func(new Integer[]{Integer.valueOf("10")});


        type.func((long) 10);
        type.func(Long.valueOf((long) 10));
        type.func(new long[]{(long) 10});
        type.func(new Long[]{Long.valueOf("10")});

        type.func((double) 10);
        type.func(Double.valueOf((double) 10));
        type.func(new double[]{(double) 10});
        type.func(new Double[]{Double.valueOf("10")});

        type.func((float) 10);
        type.func(Float.valueOf((float) 10));
        type.func(new float[]{(float) 10});
        type.func(new Float[]{Float.valueOf("10")});

    }


    public boolean func(boolean b) {
        return b;
    }

    public Boolean func(Boolean b) {
        return b;
    }

    public boolean[] func(boolean[] b) {
        return b;
    }

    public Boolean[] func(Boolean[] b) {
        return b;
    }

    public char func(char b) {
        return b;
    }

    public Character func(Character b) {
        return b;
    }

    public char[] func(char[] b) {
        return b;
    }

    public Character[] func(Character[] b) {
        return b;
    }

    public byte func(byte b) {
        return b;
    }

    public Byte func(Byte b) {
        return b;
    }

    public byte[] func(byte[] b) {
        return b;
    }

    public Byte[] func(Byte[] b) {
        return b;
    }

    public short func(short b) {
        return b;
    }

    public Short func(Short b) {
        return b;
    }

    public short[] func(short[] b) {
        return b;
    }

    public Short[] func(Short[] b) {
        return b;
    }

    public int func(int b) {
        return b;
    }

    public Integer func(Integer b) {
        return b;
    }

    public int[] func(int[] b) {
        return b;
    }

    public Integer[] func(Integer[] b) {
        return b;
    }

    public long func(long b) {
        return b;
    }

    public Long func(Long b) {
        return b;
    }

    public long[] func(long[] b) {
        return b;
    }

    public Long[] func(Long[] b) {
        return b;
    }

    public double[] func(double[] b) {
        return b;
    }

    public Double[] func(Double[] b) {
        return b;
    }

    public double func(double b) {
        return b;
    }

    public Double func(Double b) {
        return b;
    }

    public float func(float b) {
        return b;
    }

    public Float func(Float b) {
        return b;
    }

    public float[] func(float[] b) {
        return b;
    }

    public Float[] func(Float[] b) {
        return b;
    }
}

