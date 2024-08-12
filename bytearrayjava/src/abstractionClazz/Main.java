package abstractionClazz;

public class Main {
    public static void main(String[] args) {
        // Creating objects of Rectangle and Circle
        Rectangle rectangle = new Rectangle(5,3);
        Circle circle = new Circle(4);
        
        // Calling methods defined in the abstract class
        rectangle.printInfo();
        System.out.println("Area of rectangle: " + rectangle.calculateArea());
        
        circle.printInfo();
        System.out.println("Area of circle: " + circle.calculateArea());
    }
}
