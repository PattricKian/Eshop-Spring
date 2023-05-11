package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Product create(ProductRequest request) {
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setDescription(request.getDescription());
        newProduct.setAmount(request.getAmount());
        newProduct.setUnit(request.getUnit());
        newProduct.setPrice(request.getPrice());
        return this.repository.save(newProduct);
    }

    @Override
    public Product getById(Long id) throws NotFoundException {
        if(this.repository.findById(id).isPresent()){
            return this.repository.findById(id).get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Product update(Long id, ProductRequest request) throws NotFoundException {
        Product existingProduct = getById(id);
        if(request.getName() != null) {
            existingProduct.setName(request.getName());
        }
        if(request.getDescription() != null) {
            existingProduct.setDescription(request.getDescription());
        }
        return this.repository.save(existingProduct);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if(this.repository.findById(id).isPresent()){
            this.repository.deleteById(id);
        } else {
        throw new NotFoundException();
        }
    }


    @Override
    public IntResponse getAmount(Long id) throws NotFoundException {
        if(this.repository.findById(id).isPresent()){
            return new IntResponse(this.repository.findById(id).get().getAmount());
        }else {
            throw new NotFoundException();
        }
    }

    @Override
    public IntResponse getIncreasedAmount(Long id, int amount) throws NotFoundException {
        Optional<Product> optionalProduct = this.repository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new NotFoundException();
        }
        Product product = optionalProduct.get();
        product.setAmount(product.getAmount() + amount);
        Product savedProduct = this.repository.save(product);
        return new IntResponse(savedProduct.getAmount());
    }



}