package br.gabrielcardoso.filters.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.gabrielcardoso.filters.interfaces.IListService;
import br.gabrielcardoso.filters.models.FilterModel;
import br.gabrielcardoso.filters.models.PageModel;

import static org.springframework.http.ResponseEntity.ok;

public sealed class BaseController<TEntity, TService extends IListService<TEntity>>
        permits CategoryController, ProductController, SupplierController {

    @Autowired
    protected TService service;

    @GetMapping
    public ResponseEntity<PageModel<TEntity>> get(@RequestParam Map<String, String> params) {
        FilterModel filter = new FilterModel(params);
        return ok(service.list(filter));
    }
}
