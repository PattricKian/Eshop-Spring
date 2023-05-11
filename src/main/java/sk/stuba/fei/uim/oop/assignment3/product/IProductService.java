package sk.stuba.fei.uim.oop.assignment3.product;

import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.IntResponse;
import java.util.List;

public interface IProductService {
    List<Product> getAll();
    Product create(ProductRequest request);
    Product getById(Long id) throws NotFoundException;
    Product update(Long id, ProductRequest response) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    IntResponse getAmount(Long id) throws NotFoundException;
    IntResponse getIncreasedAmount(Long id, int amount) throws NotFoundException;
}