package abstractionInterfaze;

// Concrete class implementing the Shape interface for a circle
class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    // Implementing the interface method to calculate area
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}