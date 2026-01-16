package me.market.controller;

import me.market.model.Product;
import me.market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public String save(@RequestBody Product product) {
        productRepository.save(product);

        return String.format("O produto %s foi salvo com sucesso!", product.getName());
    }

    @GetMapping
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        productRepository.deleteById(Long.parseLong(id));

        return String.format("O produto %s foi deletado com sucesso!", id);
    }
}
