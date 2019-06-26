import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.*;

public class CarDatabase {
    String filePath;
    SuperPQ<Integer> pricePQ;
    SuperPQ<Integer> milePQ;

    public CarDatabase() {
        filePath = new File("").getAbsolutePath() + "/";
        pricePQ = new SuperPQ<Integer>(new PriceComparator());
        milePQ = new SuperPQ<Integer>(new MileComparator());
    }

    public void load() {
        String word;
        File init;
        BufferedReader reader = null;

        try {
            init = new File(filePath + "cars.txt");
            reader = new BufferedReader(new FileReader(init));

            while ((word = reader.readLine()) != null) {
                parseAndLoad(word);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseAndLoad(String line) {
        if (line.matches("^(#).*$")) return;
        Car in = new Car(line);

        pricePQ.insert(in);
        milePQ.insert(in);
    }

    public void run() {
        String input = "";
        BufferedReader reader = null;
        load();
        boolean cont = true;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            while(cont) {
                printMenu();
                System.out.print("> ");
                input = reader.readLine();
                if (!input.matches("[1-9]{1}")) {
                    System.out.println("INVALID INPUT");
                    continue;
                }
                switch(Integer.parseInt(input))  {
                    case 1:
                        insertCar(reader);
                        break;
                    case 2:
                        updateCar(reader);
                        break;
                    case 3:
                        removeCar(reader);
                        break;
                    case 4:
                        getMinPrice();
                        break;
                    case 5:
                        getMinMile();
                        break;
                    case 6:
                        minPriceMM(reader);
                        break;
                    case 7:
                        minMileMM(reader);
                        break;
                    case 8:
                        cont = false;
                        break;
                    case 9:
                        test();
                        break;
                    default:
                        System.out.println("INVALID INPUT");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMenu() {
        System.out.println("1) Add a car");
        System.out.println("2) Update a car");
        System.out.println("3) Remove a specific car from consideration");
        System.out.println("4) Retrieve the lowest price car");
        System.out.println("5) Retrieve the lowest mileage car");
        System.out.println("6) Retrieve the lowest price car by make and model");
        System.out.println("7) Retrieve the lowest mileage car by make and model");
        System.out.println("8) Quit");
    }

    private void insertCar(BufferedReader reader) {
        System.out.println("Insert a Car:");
        String input = "";
        Car car = new Car();

        try {
            System.out.print("\tVIN: ");
            input = reader.readLine();
            car.vin = input;

            System.out.print("\tMake: ");
            input = reader.readLine();
            car.make = input;

            System.out.print("\tModel: ");
            input = reader.readLine();
            car.model = input;

            do {
                System.out.print("\tPrice: ");
                input = reader.readLine();
                if (!input.matches("^[1-9]([0-9]{1,45}$)")) {
                    System.out.println("\tINVALID Price: must be an integer");
                }
            } while(!input.matches("^[1-9]([0-9]{1,45}$)"));
            car.price = Integer.parseInt(input);

            do {
                System.out.print("\tMileage: ");
                input = reader.readLine();
                if (!input.matches("^[1-9]([0-9]{1,45}$)")) {
                    System.out.println("\tINVALID Mileage: must be an integer");
                }
            } while(!input.matches("^[1-9]([0-9]{1,45}$)"));
            car.mileage = Integer.parseInt(input);

            System.out.print("\tColor: ");
            input = reader.readLine();
            car.color = input;

        } catch (IOException e) {
            e.printStackTrace();
        }

        pricePQ.insert(car);
        milePQ.insert(car);
    }

    private void updateCar(BufferedReader reader) {
        System.out.println("Update a Car:");
        String vin = "";
        String selector = "";
        String input = "";

        Car car;

        try {
            do {
                System.out.print("VIN: ");
                vin = reader.readLine();
                if (pricePQ.getCar(vin) == null) {
                    System.out.println("INVALID: VIN does not exist in pricing index");
                }
                if (milePQ.getCar(vin) == null) {
                    System.out.println("INVALID: VIN does not exist in mileage index");
                }
                if (pricePQ.getCar(vin) == null || milePQ.getCar(vin) == null) {
                    continue;
                }

                do {
                    System.out.println("\n1) Update price");
                    System.out.println("2) Update mileage");
                    System.out.println("3) Update color");
                    System.out.print("> ");
                    selector = reader.readLine();
                    if (!selector.matches("([1-3]){1}")) {
                        System.out.println("INVALID: Not an option");
                    }
                } while(!selector.matches("([1-3]){1}"));

                switch(Integer.parseInt(selector)) {
                    case 1:
                        do {
                            System.out.print("New Price: ");
                            input = reader.readLine();
                            if (!input.matches("^[1-9]([0-9]{1,45}$)")) {
                                System.out.println("INVALID: Price needs to be an integer");
                            }
                        } while (!input.matches("^[1-9]([0-9]{1,45}$)"));
                        pricePQ.update(vin, new Integer(Integer.parseInt(input)));
                        break;
                    case 2:
                        do {
                            System.out.print("New Mileage: ");
                            input = reader.readLine();
                            if (!input.matches("^[1-9]([0-9]{1,45}$)")) {
                                System.out.println("INVALID: Mileage needs to be an integer");
                            }
                        } while (!input.matches("^[1-9]([0-9]{1,45}$)"));
                        milePQ.update(vin, new Integer(Integer.parseInt(input)));
                        break;
                    case 3:
                        System.out.print("New Color: ");
                        input = reader.readLine();
                        car = pricePQ.getCar(vin);
                        car.color = input;
                        break;
                    default:
                        System.out.println("INVALID INPUT");
                }
            } while(pricePQ.getCar(vin) == null || milePQ.getCar(vin) == null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeCar(BufferedReader reader) {
        System.out.println("Remove a Car:");
        String input = "";
        String vin = "";

        assert pricePQ.size() == milePQ.size();
        if (pricePQ.isEmpty()) {
            System.out.println("Car Index is empty please insert more cars");
            return;
        }

        try {
            do {
                System.out.print("VIN: ");
                vin = reader.readLine();
                if (pricePQ.getCar(vin) == null) {
                    System.out.println("INVALID: VIN does not exist in pricing index");
                }
                if (milePQ.getCar(vin) == null) {
                    System.out.println("INVALID: VIN does not exist in mileage index");
                }
                if (pricePQ.getCar(vin) == null || milePQ.getCar(vin) == null) {
                    continue;
                }
            } while (pricePQ.getCar(vin) == null || milePQ.getCar(vin) == null);
            pricePQ.remove(vin);
            milePQ.remove(vin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMinPrice() {
        Car min = pricePQ.min();

        if (min == null) {
            System.out.println("No Cars in Index");
            return;
        }
        dispCar(min);
    }

    private void getMinMile() {
        Car min = milePQ.min();

        if (min == null) {
            System.out.println("No Cars in Index");
            return;
        }
        System.out.println("Lowest Mileage Car:");
        dispCar(min);

    }

    private void minPriceMM(BufferedReader reader) {
        Car min;
        String make = "";
        String model = "";

        if (pricePQ.isEmpty()) {
            System.out.println("No Cars in Index");
            return;
        }

        try {
            System.out.print("\tMake: ");
            make = reader.readLine();
            System.out.print("\tModel: ");
            model = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        min = pricePQ.min(make, model);

        if (min == null) {
            System.out.println(make + " " + model + " do not exist in index");
            return;
        }

        System.out.println("Cheapest " + make + " " + model);
        dispCar(min);
    }

    private void minMileMM(BufferedReader reader) {
        Car min;
        String make = "";
        String model = "";

        if (milePQ.isEmpty()) {
            System.out.println("No Cars in Index");
            return;
        }

        try {
            System.out.print("\tMake: ");
            make = reader.readLine();
            System.out.print("\tModel: ");
            model = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        min = milePQ.min(make, model);

        if (min == null) {
            System.out.println(make + " " + model + " do not exist in index");
            return;
        }

        System.out.println("Lowest Mileage " + make + " " + model);
        dispCar(min);
    }

    private void dispCar(Car x) {
        System.out.println("VIN: " +  x.vin);
        System.out.println("Make: " +  x.make);
        System.out.println("Model: " +  x.model);
        System.out.println("Price: " +  x.price);
        System.out.println("Mileage: " +  x.mileage);
        System.out.println("Color: " +  x.color);
    }

    private void dispPQ() {
        pricePQ.printPQ();
    }

    public void test() {
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
        pricePQ.update("RAQM7ZJBSFZ0HRTTN", 6000);
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
        pricePQ.update("RAQM7ZJBSFZ0HRTTN", 3000);
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
        pricePQ.update("SMOG8H2WXK466CRCA", 1000);
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
        System.out.println("=======================");
        System.out.println("DELETES");
        System.out.println("=======================");
        // Delete 1
        pricePQ.remove("16Z2DPEHSUK5KCMEH");
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
        pricePQ.remove("GNX5TS04SM5V5EXP8");
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
        pricePQ.remove("SY7I9WJQMMYVN0XNG");
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");

        // Delete Min
        System.out.println("DELETE MIN");
        pricePQ.remove("SMOG8H2WXK466CRCA");
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
        pricePQ.remove("RAQM7ZJBSFZ0HRTTN");
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
        pricePQ.remove("M75OUYC6G01AN759O");
        System.out.println("-----------------------");
        pricePQ.printPQ();
        System.out.println("-----------------------");
    }
}
