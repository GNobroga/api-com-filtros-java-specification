package br.gabrielcardoso.filters.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gabrielcardoso.filters.domain.entities.Product;
import br.gabrielcardoso.filters.interfaces.IProductService;

@RestController
@RequestMapping("/products")
public non-sealed class ProductController extends BaseController<Product, IProductService> {

}
