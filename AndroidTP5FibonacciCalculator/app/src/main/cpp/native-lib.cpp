#include <jni.h>

long fibonacci(int n) {
    return n < 2 ? n : fibonacci(n - 1) + fibonacci(n - 2);
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_lucasp243_androidtp5_1fibonaccicalculator_CLibFibonacciComputer_fibonacci(JNIEnv *env, jobject thiz, jint n) {
    return fibonacci(n);
}