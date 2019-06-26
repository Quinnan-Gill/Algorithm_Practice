public class Car {
    public String vin;
    public String make;
    public String model;
    public int price;
    public int mileage;
    public String color;

    public Car() {
        vin = null;
        make = null;
        model = null;
        price = 0;
        mileage = 0;
        color = null;
    }

    public Car(String vin, int price) {
        this.vin = vin;
        this.price = price;
    }

    public Car(String unParsed) {
        String[] inputs = unParsed.split(":");
        this.vin = inputs[0];
        this.make = inputs[1];
        this.model = inputs[2];
        this.price = Integer.parseInt(inputs[3]);
        this.mileage = Integer.parseInt(inputs[4]);
        this.color = inputs[5];
    }

    public String toString() {
        return vin;
    }

}
