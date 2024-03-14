package br.gabrielcardoso.filters.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gabrielcardoso.filters.domain.entities.Category;
import br.gabrielcardoso.filters.interfaces.ICategoryService;;

@RestController
@RequestMapping("/categories")
public non-sealed class CategoryController extends BaseController<Category, ICategoryService> {

}
