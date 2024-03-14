package br.gabrielcardoso.filters.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gabrielcardoso.filters.domain.entities.Supplier;
import br.gabrielcardoso.filters.interfaces.ISupplierService;

@RestController
@RequestMapping("/suppliers")
public non-sealed class SupplierController extends BaseController<Supplier, ISupplierService> {

}
