package com.lucasp243.androidtp5_fibonaccicalculator;

public class JavaFibonacciComputer implements FibonacciComputer {

    @Override
    public long computeNthFibonacci(int n) {
        return fibonacci(n);
    }

    private long fibonacci(int n) {
        return n < 2 ? n : fibonacci(n - 1) + fibonacci(n - 2);
    }
}
