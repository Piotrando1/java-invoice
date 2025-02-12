package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    private Map<Product, Integer> productQuantityOf = new HashMap<>();

    public void addProduct(Product product) {
        if (product == null){
            throw new IllegalArgumentException("Product needs to have own name");
        };
        this.productQuantityOf.put(product,1);
    }


    public void addProduct(Product product, Integer quantity) {
       if (quantity <= 0 || product == null) {
           throw new IllegalArgumentException("Incorrect product quantity, it cant be zero, also product needs to have its own name");
       }
        this.productQuantityOf.put(product,quantity);
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
        for(Product product : productQuantityOf.keySet()) {
            totalPrice = totalPrice.add(product.getPriceWithTax().multiply(new BigDecimal(productQuantityOf.get(product))));
        }
        return totalPrice;
    }
}
