package abstractionInterfaze;

// Concrete class implementing the Shape interface for a rectangle
class Rectangle implements Shape{
    private double length;
    private double width;
    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    // Implementing the interface method to calculate area
    @Override
    public double calculateArea() {
        return length * width;
    }
}