package com.lucasp243.androidtp5_fibonaccicalculator;

import java.math.BigDecimal;

public class FibonacciComputerProxy implements FibonacciComputer {

    private final FibonacciComputer delegated;

    private long computationTime = 0;

    public FibonacciComputerProxy(FibonacciComputer delegated) {
        this.delegated = delegated;
    }

    @Override
    public long computeNthFibonacci(int n) {
        computationTime = System.currentTimeMillis();
        long result = delegated.computeNthFibonacci(n);
        computationTime = System.currentTimeMillis() - computationTime;
        return result;
    }

    public long getLastComputationTime() {
        return computationTime;
    }

}
