package za.nmu.wrav301.a1;

public class Item {
    private String name;
    private int year;
    private double value;
    private String type;

    public Item(String name, int year, double value, String type) {
        this.name = name;
        this.year = year;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public double getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(String.format("\nName: %s\n", name))
                .append(String.format("Year: %d\n", year))
                .append(String.format("Price: %f\n", value))
                .append(String.format("Type: %s\n\n", type))
                .toString();
    }
}
