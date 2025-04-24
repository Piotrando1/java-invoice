package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {


    private static final AtomicInteger COUNTER = new AtomicInteger(100_000_000);
    private final int number = COUNTER.incrementAndGet();
    private final Map<Product, Integer> productQuantityOf = new HashMap<>();

    public int getNumber() {
        return number;
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Produkt null lub zła ilość");
        }

        if (productQuantityOf.containsKey(product)) {
            int oldQty = productQuantityOf.get(product);
            productQuantityOf.put(product, oldQty + quantity);
        } else {
            productQuantityOf.put(product, quantity);
        }
    }

    public BigDecimal getNetPrice() {
        BigDecimal netSum = BigDecimal.ZERO;
        for (Product product : productQuantityOf.keySet()) {
            netSum = netSum.add(product.getPrice().multiply(new BigDecimal(productQuantityOf.get(product))));
        }
        return netSum;
    }

    public BigDecimal getTax() {
        BigDecimal taxValue = BigDecimal.ZERO;
        for (Product product : productQuantityOf.keySet()) {
            taxValue = taxValue.add(product.getPrice().multiply(product.getTaxPercent()).multiply(new BigDecimal(productQuantityOf.get(product))));
        }
        return taxValue;
    }

    public BigDecimal getGrossPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Product product : productQuantityOf.keySet()) {
            totalPrice = totalPrice.add(product.getPriceWithTax().multiply(new BigDecimal(productQuantityOf.get(product))));
        }
        return totalPrice;
    }

    public String printFaktura() {
        String text = "";

        text += "Faktura nr " + getNumber() + "\n";

        for (Product p : productQuantityOf.keySet()) {
            int qty = productQuantityOf.get(p);
            text += p.getName() + " | " + qty + " szt. | " + p.getPrice() + " PLN\n";
        }

        text += "Liczba pozycji na fakturze: " + productQuantityOf.size()+ "\n";
        text += "Suma wszystkich produktow brutto: " + getGrossPrice()+ " PLN";
        return text;

    }

    int getPositionsCount() {
        return productQuantityOf.size();
    }
}
