package projects.car_parking;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class App {

    public static Park park = new Park();
    static boolean isLoggedIn;

    static {
        park.setName("Chorsu Car Parking");
        park.setRows(generateRows());
        isLoggedIn = false;
    }

    static List<Row> generateRows() {
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            rows.add(generateRow(i));
        return rows;
    }

    static Row generateRow(int i) {
        return new Row(i, generateCells());
    }

    static List<Cell> generateCells() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            cells.add(generateCell());
        return cells;
    }

    static Cell generateCell() {
        return new Cell("✅", null);
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        if (isLoggedIn){
            Utils.println("Show    -> 4");
            Utils.println("Logout  -> 5");
            Utils.println("Quit    -> q");
        } else {
            Utils.println("In    -> 1");
            Utils.println("Out   -> 2");
            Utils.println("Login -> 3");
            Utils.println("Quit  -> q");
        }
        String choice = Utils.input("?:");
        switch (choice) {
            case "1" -> in();
            case "2" -> out();
            case "3" -> login();
            case "4" -> showPark();
            case "5" -> logout();
            case "q" -> {
                Utils.println("come back again", Utils.GREEN);
                System.exit(0);
            }
            default -> Utils.println("Wrong choice", Utils.RED);
        }
        run();
    }

    static void in() {
        if (!isParkFull()) {
            String number = Utils.input("Car number :");
            int randRowIndex = Utils.randIndex(9);
            int randCellIndex = Utils.randIndex(19);
            Cell cell = park.getRows().get(randRowIndex).getCells().get(randCellIndex);
            cell.setSign("🚗");
            cell.setCar(new Car(number));
            String qr = randRowIndex + "_" + randCellIndex;
            Utils.println("Qr:" + qr);
        } else {
            Utils.println("Park is full", Utils.RED);
        }
    }

    static void out() {
        String qr = Utils.input("Please enter your qr : ");
        String[] arr = qr.split("_");
        int rowID = Integer.valueOf(arr[0]);
        int cellID = Integer.valueOf(arr[1]);
        Cell cell = park.getRows().get(rowID).getCells().get(cellID);
        cell.setSign("✅");
        cell.setCar(null);
    }

    static void login() {
        String userName = Utils.input("Username : ");
        String password = Utils.readPassword("Password : ");
        System.out.println(userName);
        System.out.println(password);
        System.out.println("Logged in as: "+userName);
        isLoggedIn = true;
    }

    static void logout() {
        System.out.println("Logged out successfully");
        isLoggedIn = false;
    }

    static void showPark() {
        for (Row row : park.getRows()) {
            for (Cell cell : row.getCells()) {
                Utils.print(cell.getSign(), Utils.YELLOW);
            }
            Utils.println("");
        }
    }

    static boolean isParkFull() {
        for (Row row : park.getRows())
            for (Cell cell : row.getCells()) {
                if (Objects.isNull(cell.getCar()))
                    return false;
            }
        return true;
    }

}

class Car {
    private String number;

    public Car() {
    }

    public Car(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

class Cell {
    private String sign;
    private Car car;

    public Cell() {
    }

    public Cell(String sign, Car car) {
        this.sign = sign;
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}

class Row {
    private int rowId;
    private List<Cell> cells = new ArrayList<>();

    public Row() {

    }

    public Row(int rowId, List<Cell> cells) {
        this.rowId = rowId;
        this.cells = cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getRowId() {
        return rowId;
    }
}

class Park {

    private String name;
    private List<Row> rows;

    public Park() {

    }

    public Park(String name, List<Row> rows) {
        this.name = name;
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return this.name;
    }

}

class Utils {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void println(Object data) {
        println(data, PURPLE);
    }

    public static void println(Object data, String color) {
        System.out.println(color + data + RESET);
    }

    public static void print(Object data) {
        print(data, PURPLE);
    }

    public static void print(Object data, String color) {
        System.out.print(color + data + RESET);
    }

    public static String input(String placeHolder) {
        Console console = System.console();
        return console.readLine(YELLOW + placeHolder + RESET);
    }

    public static String readPassword(String placeHolder) {
        Console console = System.console();
        return new String(console.readPassword(YELLOW + placeHolder + RESET));
    }

    public static int randIndex(int min, int max) {
        Random random = new Random();
        return random.nextInt(min, max + 1);
    }

    public static int randIndex(int max) {
        return randIndex(0, max);
    }

    // public static int randRowIndex() {
    // Random random = new Random();
    // return random.nextInt(0, 10);
    // }

    // public static int randCellIndex() {
    // Random random = new Random();
    // return random.nextInt(0, 20);
    // }
}