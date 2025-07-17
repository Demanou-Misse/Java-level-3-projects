package expense;

public class Expense {

    private String category;
    private double amount;
    private String description;

    public Expense (String category, double amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description =  description;
    }

    public String getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return " Category: " + category + "    | Amount: " + amount + " £      | Description: " + description;
    }

}
