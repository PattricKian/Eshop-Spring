package sk.stuba.fei.uim.oop.assignment3.cart;

import sk.stuba.fei.uim.oop.assignment3.exceptions.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;

public interface ICartService {
    Cart create();
    Cart getById(Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    Cart addProduct(Long id, CartAddRequest request) throws NotFoundException, IllegalOperationException;
    String pay(Long id) throws NotFoundException, IllegalOperationException;
}
