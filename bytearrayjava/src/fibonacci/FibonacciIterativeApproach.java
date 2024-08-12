package fibonacci;

public class FibonacciIterativeApproach {
    public static void main(String[] args) {
        int N = 10;
        int num1 = 0, num2 = 1;
        System.out.println("Fibonacci Series: ");
        for (int i = 0; i < N; i++) {
            System.out.println(" ---- Iterate " + i + " ---- ");
            System.out.println(num1 + " ");
            int num3 = num1 + num2;
            num1 = num2;
            num2 = num3;
        }
    }
}
