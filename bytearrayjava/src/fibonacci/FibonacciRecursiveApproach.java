package fibonacci;

public class FibonacciRecursiveApproach {
    static int fib(int n) {
        if (n <= 1)
            return n;
        System.out.println("fib(n - 1) + fib(n - 2): " + fib(n-1) + fib(n-2));
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) {
        int N = 10;
        System.out.println("Fibonacci Series: ");
        for (int i = 0; i < N; i++) {
            System.out.println(fib(i) + " => Iter " + i);
        }
    }
}
