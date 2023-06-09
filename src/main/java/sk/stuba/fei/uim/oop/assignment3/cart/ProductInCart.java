package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInCart {

    @Id
    private Long productId;

    private int amount;

    public ProductInCart(CartAddRequest r) {
        productId = r.getProductId();
        amount = r.getAmount();
    }
}
