package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.exceptions.IllegalOperationException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService service;

    @PostMapping()
    public CartResponse createCart(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new CartResponse(this.service.create());
    }

    @GetMapping("/{id}")
    public CartResponse getCartById(@PathVariable("id") Long id) throws NotFoundException {
        return new CartResponse(this.service.getById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable("id") Long id) throws NotFoundException {
        this.service.delete(id);
    }

    @PostMapping("/{id}/add")
    public CartResponse addProductToCart(@PathVariable("id") Long id,@RequestBody CartAddRequest request) throws NotFoundException, IllegalOperationException {
        return new CartResponse(this.service.addProduct(id,request));
    }

    @GetMapping(value="/{id}/pay", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String payForCart(@PathVariable("id") Long id) throws NotFoundException, IllegalOperationException {
        return this.service.pay(id);
    }
}
