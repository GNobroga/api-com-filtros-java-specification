package br.gabrielcardoso.filters.services;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.gabrielcardoso.filters.domain.entities.Supplier;
import br.gabrielcardoso.filters.interfaces.ISupplierService;
import br.gabrielcardoso.filters.models.FilterModel;
import br.gabrielcardoso.filters.models.PageModel;
import br.gabrielcardoso.filters.repositories.SupplierRepository;
import br.gabrielcardoso.filters.specifications.EntitySpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {

    private final SupplierRepository repository;

    @Override
    public List<Supplier> list() {
        return repository.findAll();
    }

    @Override
    public PageModel<Supplier> list(FilterModel filter) {
        var page = repository.findAll(
            buildSpecification(filter), 
            filter.toPageable()
        );
        return new PageModel<>(page);
    }

    @Override
    public Specification<Supplier> buildSpecification(FilterModel filterModel) {
        Specification<Supplier> spec = Specification.where(null);

        for (var equalFilter: filterModel.getEqualFilters()) 
            spec = spec.and(EntitySpecification.equal(equalFilter, Supplier.class));
        
        for (var inFilter: filterModel.getInFilters()) 
            spec = spec.and(EntitySpecification.in(inFilter, Supplier.class));

        for (var dateFilter: filterModel.getDateFilters()) 
            spec = spec.and(EntitySpecification.rangeDate(dateFilter, Supplier.class));

        return spec;
    }
}
