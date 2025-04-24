package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.sql.SQLOutput;

public abstract class Product {

    private final String name;
    private final BigDecimal price;
    private final BigDecimal taxPercent;

    protected Product(String name, BigDecimal price, BigDecimal tax) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }


        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative or null");
        }

        if (tax == null) {
            throw new IllegalArgumentException("tax cant equal to 0");
        }

        this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getTaxPercent() {
        return this.taxPercent;
    }

    public BigDecimal getPriceWithTax() {
        return this.price.multiply(this.getTaxPercent()).add(this.price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product another = (Product) o;
        return name.equals(another.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
