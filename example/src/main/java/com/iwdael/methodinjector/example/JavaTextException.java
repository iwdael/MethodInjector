package com.iwdael.methodinjector.example;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/MethodInjector
 */
public class JavaTextException {

    public static void test() {
        JavaTextException java = new JavaTextException();
        java.tryBoolean();
        java.tryBooleanNull();
        java.tryChar();
        java.tryCharNull();
        java.tryByte();
        java.tryByteNull();
        java.tryShort();
        java.tryShortNull();
        java.tryInt();
        java.tryIntNull();
        java.tryLong();
        java.tryLongNull();
        java.tryFloat();
        java.tryFloatNull();
        java.tryDouble();
        java.tryDoubleNull();
    }

    public boolean tryBoolean() {
        try {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean tryBooleanNull() {
        try {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public char tryChar() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 12;
        }
    }

    public Character tryCharNull() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte tryByte() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 12;
        }
    }

    public Byte tryByteNull() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public short tryShort() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 12;
        }
    }

    public Short tryShortNull() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int tryInt() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
    }

    public Integer tryIntNull() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int tryLong() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
    }

    public Long tryLongNull() {
        try {
            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public float tryFloat() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
    }

    public Float tryFloatNull() {
        try {
            return 0f;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public float tryDouble() {
        try {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
    }

    public Float tryDoubleNull() {
        try {
            return 0f;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
