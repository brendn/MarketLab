import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Lab 3.2 - Guenther's Market
 *
 * Task: Make a shopping list application which uses collections to store your items.
 *
 * Build Specifications:
 *      Use a Map to keep track of the menu of items.
 *          It should have a String key (for item name) and Double value (for item price).
 *      Use parallel ArrayLists (one of strings, one of doubles) to store the items ordered and their prices.
 *      Write 3 methods to find 1) the average cost of the items ordered and the indexes
 *      of the 2) highest and 3) lowest cost items.
 */
public class MarketLab {

    //Messages
    private static final String MESSAGE_WELCOME = "Welcome to Guenther's Market!";
    private static final String MESSAGE_WHAT_ITEM = "What item would you like to order?";
    private static final String MESSAGE_ADD_TO_CART = "Adding %s to cart at $%.2f%n";
    private static final String MESSAGE_CONTINUE = "Would you like to order anything else (y/n)?";
    private static final String MESSAGE_THANK_YOU = "Thanks for your order!\nHere's what you got:";
    private static final String MESSAGE_AVERAGE_PRICE = "Average price per item in order was $%.2f%n";
    private static final String MESSAGE_MOST_EXPENSIVE = "Most expensive item in order was $%.2f%n";
    private static final String MESSAGE_LEAST_EXPENSIVE = "Least expensive item in order was $%.2f%n";
    private static final String MESSAGE_ERR_NOT_FOUND = "Sorry, we don't have those.  Please try again.";

    //Input
    private static final String INPUT_YES = "y";

    //Table
    private static final String TABLE_DIVIDER = "========================";
    private static final String[] TABLE_TITLES = { "Items", "Price" };

    //The items the store has for sale
    private static final Map<String, Double> ITEMS = Map.of(
            "apple", 0.99,
            "banana", 0.59,
            "cauliflower", 1.59,
            "dragonfruit", 2.19,
            "Elderberry", 1.79,
            "figs", 2.09,
            "grapefruit", 1.99,
            "honeydew", 3.49
    );

    //List containing the names of each of the items in the user's cart
    private static final List<String> CART_ITEMS = new ArrayList<>();

    //List containing the prices of each of the items in the user's cart
    private static final List<Double> CART_PRICES = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(MESSAGE_WELCOME);
        boolean orderingItems = true;
        do {
            printInventoryTable();
            System.out.println(MESSAGE_WHAT_ITEM);
            String item = scanner.nextLine();
            if (ITEMS.containsKey(item)) {
                CART_ITEMS.add(item);
                CART_PRICES.add(ITEMS.get(item));
                System.out.printf(MESSAGE_ADD_TO_CART, item, ITEMS.get(item));
                System.out.println(MESSAGE_CONTINUE);
                orderingItems = scanner.nextLine().toLowerCase().startsWith(INPUT_YES);
            } else {
                System.out.println(MESSAGE_ERR_NOT_FOUND);
            }
        } while (orderingItems);

        System.out.println(MESSAGE_THANK_YOU);
        printOrderTable();
        System.out.printf(MESSAGE_AVERAGE_PRICE, calculateAverageCost());
        System.out.print(getMostExpensiveItemOrdered());
        System.out.print(getLeastExpensiveItemOrdered());
        scanner.close();
    }

    /**
     * Prints a table containing the users' order.
     */
    private static void printOrderTable() {
        for (String s : CART_ITEMS) {
            System.out.printf("%-15s $%.2f%n", s, CART_PRICES.get(CART_ITEMS.indexOf(s)));
        }
    }

    /**
     * Prints a table containing the store's inventory
     */
    private static void printInventoryTable() {
        System.out.printf("%-15s %s %n", TABLE_TITLES[0], TABLE_TITLES[1]);
        System.out.println(TABLE_DIVIDER);
        for (Map.Entry<String, Double> item : ITEMS.entrySet()) {
            System.out.printf("%-15s $%.2f%n", item.getKey(), item.getValue());
        }
    }

    /**
     * Calculates the average cost of the items in the user's cart
     *
     * @return The average cost of the items in the cart
     */
    private static double calculateAverageCost() {
        return CART_PRICES.stream().mapToDouble(d -> d).sum() / CART_PRICES.size();
    }

    /**
     * Checks the cart for the most expensive item and returns a message for the console
     * to report it.
     *
     * @return "Most expensive item in order was $<most expensive>"
     */
    private static String getMostExpensiveItemOrdered() {
        double max = CART_PRICES.stream().max(Double::compare).get();
        return String.format(MESSAGE_MOST_EXPENSIVE, max);
    }

    /**
     * Checks the cart for the most expensive item and returns a message for the console
     * to report it.
     *
     * @return "Least expensive item in order was $<most expensive>"
     */
    private static String getLeastExpensiveItemOrdered() {
        double min = CART_PRICES.stream().min(Double::compare).get();
        return String.format(MESSAGE_LEAST_EXPENSIVE, min);
    }
}
