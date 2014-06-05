package yingke.ita.response;

/**
 * @author Yingke
 *
 */
public class Price {

    private double price;
    private String currency;

    public Price(String price) {
        this.currency = price.substring(0, 3);
        this.price = Double.parseDouble(price.substring(3));
    }

    public String toString() {
        return currency + price;
    }

}
