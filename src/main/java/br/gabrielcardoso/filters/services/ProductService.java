package br.gabrielcardoso.filters.services;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.gabrielcardoso.filters.domain.entities.Product;
import br.gabrielcardoso.filters.interfaces.IProductService;
import br.gabrielcardoso.filters.models.FilterModel;
import br.gabrielcardoso.filters.models.PageModel;
import br.gabrielcardoso.filters.repositories.ProductRepository;
import br.gabrielcardoso.filters.specifications.EntitySpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository repository;

    @Override
    public List<Product> list() {
        return repository.findAll();
    }

    @Override
    public PageModel<Product> list(FilterModel filter) {
        var page = repository.findAll(
            buildSpecification(filter), 
            filter.toPageable()
        );
        return new PageModel<>(page);
    }

    @Override
    public Specification<Product> buildSpecification(FilterModel filterModel) {
        Specification<Product> spec = Specification.where(null);

        for (var equalFilter: filterModel.getEqualFilters()) 
            spec = spec.and(EntitySpecification.equal(equalFilter, Product.class));
        
        for (var inFilter: filterModel.getInFilters()) 
            spec = spec.and(EntitySpecification.in(inFilter, Product.class));

        for (var dateFilter: filterModel.getDateFilters()) 
            spec = spec.and(EntitySpecification.rangeDate(dateFilter, Product.class));

        return spec;
    }
    
}
