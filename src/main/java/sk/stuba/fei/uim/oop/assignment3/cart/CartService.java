package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.exceptions.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.IProductService;
import sk.stuba.fei.uim.oop.assignment3.product.Product;

import java.util.Optional;

@Service
public class CartService implements ICartService {

    private CartRepository repository;

    private IProductService productService;

    @Autowired
    public CartService(CartRepository repository, IProductService service) {
        this.repository = repository;
        this.productService = service;
    }

    @Override
    public Cart create() {
        return this.repository.save(new Cart());
    }

    @Override
    public Cart getById(Long id) throws NotFoundException {
        if (this.repository.findById(id).isPresent()) {
            return this.repository.findById(id).get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (this.repository.findById(id).isPresent()) {
            this.repository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Cart addProduct(Long id, CartAddRequest request) throws NotFoundException, IllegalOperationException {
        Optional<Cart> optionalCart = this.repository.findById(id);
        if (!optionalCart.isPresent()) {
            throw new NotFoundException();
        }
        Cart currCart = optionalCart.get();
        if (currCart.isPayed()) {
            throw new IllegalOperationException();
        }
        Product product = this.productService.getById(request.getProductId());
        if (product.getAmount() < request.getAmount()) {
            throw new IllegalOperationException();
        }
        Optional<ProductInCart> optionalProductInCart = currCart.getShoppingList().stream()
                .filter(p -> p.getProductId().equals(request.getProductId()))
                .findFirst();
        if (optionalProductInCart.isPresent()) {
            ProductInCart productInCart = optionalProductInCart.get();
            productInCart.setAmount(productInCart.getAmount() + request.getAmount());
        } else {
            currCart.getShoppingList().add(new ProductInCart(request));
        }
        product.setAmount(product.getAmount() - request.getAmount());
        return currCart;
    }

    public String pay(Long id) throws NotFoundException, IllegalOperationException {
        Optional<Cart> optionalCart = this.repository.findById(id);
        if (!optionalCart.isPresent()) {
            throw new NotFoundException();
        }
        Cart cart = optionalCart.get();
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        }
        int total = 0;
        for (ProductInCart product : cart.getShoppingList()) {
            Product p = this.productService.getById(product.getProductId());
            total += p.getPrice() * product.getAmount();
        }
        return String.format("%d", total);
    }

}
