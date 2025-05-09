import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
interface Vehicle {
    void rent();
    void returnVehicle();
    double calculateRentalCost(int days);
    String getModel();
    double getDailyRate();
    boolean isRented();
}
 
class Car implements Vehicle {
    private String model;
    private boolean isRented;
    private double dailyRate;

    public Car(String model, double dailyRate) {
        this.model = model;
        this.dailyRate = dailyRate;
        this.isRented = false;
    }

    @Override
    public void rent() {
        if (!isRented) {
            isRented = true;
            JOptionPane.showMessageDialog(null, "You have successfully rented the car: " + model);
        } else {
            JOptionPane.showMessageDialog(null, "Sorry, the car " + model + " is already rented.");
        }
    }

    @Override
    public void returnVehicle() {
        if (isRented) {
            isRented = false;
            JOptionPane.showMessageDialog(null, "You have successfully returned the car: " + model);
        } else {
            JOptionPane.showMessageDialog(null, "This car was not rented.");
        }
    }

    @Override
    public double calculateRentalCost(int days) {
        return days * dailyRate;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public double getDailyRate() {
        return dailyRate;
    }

    @Override
    public boolean isRented() {
        return isRented;
    }
}
 
class RentalSystem {
    private List<Vehicle> vehicles;

    public RentalSystem() {
        vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.isRented()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    public Vehicle getVehicleByModel(String model) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getModel().equalsIgnoreCase(model)) {
                return vehicle;
            }
        }
        return null;
    }
}
 
public class VehicleRentalSystema {
    private static RentalSystem rentalSystem;

    public static void main(String[] args) {
        rentalSystem = new RentalSystem();
 
        rentalSystem.addVehicle(new Car("Santro", 500));
        rentalSystem.addVehicle(new Car("Honda WRV", 450));
        rentalSystem.addVehicle(new Car("Ciaz", 300));
        rentalSystem.addVehicle(new Car("Alto 800", 200));
 
        JFrame mainFrame = new JFrame("Vehicle Rental System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new GridLayout(3, 1));
 
        JButton customerButton = createButton("Customer", Color.CYAN);
        customerButton.addActionListener(e -> openCustomerInterface());
 
        JButton developerButton = createButton("Developer", Color.CYAN);
        developerButton.addActionListener(e -> openDeveloperInterface());

        JButton exitbutton = createButton("Exit", Color.CYAN);
        exitbutton.addActionListener(e -> mainFrame.dispose());
 
        mainFrame.add(customerButton);
        mainFrame.add(developerButton);
        mainFrame.add(exitbutton);
        mainFrame.setVisible(true);
    }

   
private static void openCustomerInterface() {
    JFrame customerFrame = new JFrame("Customer Interface");
    customerFrame.setSize(400, 300);
    customerFrame.setLayout(new GridLayout(4, 1));

    JButton browseButton = createButton("View Available Rides", Color.CYAN);
    browseButton.addActionListener(e -> {
        List<Vehicle> availableVehicles = rentalSystem.getAvailableVehicles();
        StringBuilder vehiclesList = new StringBuilder("Available Vehicles:\n");
        for (Vehicle vehicle : availableVehicles) {
            
            vehiclesList.append(vehicle.getModel())
                        .append(" - Daily Rate: ₹")
                        .append(vehicle.calculateRentalCost(1))  
                        .append("\n");
        }
        if (availableVehicles.isEmpty()) {
            vehiclesList.append("No vehicles available.");
        }
        JOptionPane.showMessageDialog(customerFrame, vehiclesList.toString());
    });

    JButton rentButton = createButton("Book a Vehicle", Color.CYAN);
    rentButton.addActionListener(e -> {
        String modelToRent = JOptionPane.showInputDialog("Enter the model of the vehicle you want to rent:");
        Vehicle vehicleToRent = rentalSystem.getVehicleByModel(modelToRent);
        if (vehicleToRent != null && !vehicleToRent.isRented()) {
            String daysStr = JOptionPane.showInputDialog("How many days do you want to rent it for?");
            int days = Integer.parseInt(daysStr);
            vehicleToRent.rent();
            JOptionPane.showMessageDialog(customerFrame, "Rental cost: ₹" + vehicleToRent.calculateRentalCost(days));
        } else {
            JOptionPane.showMessageDialog(customerFrame, "Sorry, that vehicle is either unavailable or already rented.");
        }
    });

    JButton returnButton = createButton("Return a Vehicle", Color.CYAN);
    returnButton.addActionListener(e -> {
        String modelToReturn = JOptionPane.showInputDialog("Enter the model of the vehicle you want to return:");
        Vehicle vehicleToReturn = rentalSystem.getVehicleByModel(modelToReturn);
        if (vehicleToReturn != null && vehicleToReturn.isRented()) {
            vehicleToReturn.returnVehicle();
        } else {
            JOptionPane.showMessageDialog(customerFrame, "That vehicle is not currently rented.");
        }
    });

    JButton exitButton = createButton("Exit", Color.CYAN);
    exitButton.addActionListener(e -> customerFrame.dispose());

    customerFrame.add(browseButton);
    customerFrame.add(rentButton);
    customerFrame.add(returnButton);
    customerFrame.add(exitButton);

    customerFrame.setVisible(true);
}

    
    private static void openDeveloperInterface() {
        JFrame developerFrame = new JFrame("Developer Interface");
        developerFrame.setSize(400, 200);
        developerFrame.setLayout(new GridLayout(3, 2));

        JLabel carLabel = new JLabel("Car Model:");
        JTextField carField = new JTextField();
        JLabel rateLabel = new JLabel("Daily Rate:");
        JTextField rateField = new JTextField();

        JButton addButton = createButton("Add Car", Color.CYAN);
        addButton.addActionListener(e -> {
            String carModel = carField.getText();
            String rateStr = rateField.getText();
            double rate = Double.parseDouble(rateStr);

          
            rentalSystem.addVehicle(new Car(carModel, rate));

            JOptionPane.showMessageDialog(developerFrame, "Car added successfully: " + carModel + " with a rate of ₹" + rate + " per day.");
        });

        JButton exitButton = createButton("Exit", Color.CYAN);
        exitButton.addActionListener(e -> developerFrame.dispose());

        developerFrame.add(carLabel);
        developerFrame.add(carField);
        developerFrame.add(rateLabel);
        developerFrame.add(rateField);
        developerFrame.add(addButton);
        developerFrame.add(exitButton);

        developerFrame.setVisible(true);
    }
 
    private static JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }
} 