package com.lucasp243.androidtp5_fibonaccicalculator

class KotlinFibonacciComputer: FibonacciComputer {

    override fun computeNthFibonacci(n: Int): Long {
        return fibonacci(n)
    }

    private fun fibonacci(n: Int): Long =
            if (n < 2) n.toLong()
            else fibonacci(n - 1) + fibonacci(n - 2)
}