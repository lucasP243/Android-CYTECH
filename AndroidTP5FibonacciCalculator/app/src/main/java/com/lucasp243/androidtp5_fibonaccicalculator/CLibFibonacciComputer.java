package com.lucasp243.androidtp5_fibonaccicalculator;

public class CLibFibonacciComputer implements FibonacciComputer {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public long computeNthFibonacci(int n) {
        return fibonacci(n);
    }

    private native long fibonacci(int n);
}
