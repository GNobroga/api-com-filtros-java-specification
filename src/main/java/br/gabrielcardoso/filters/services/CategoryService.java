package br.gabrielcardoso.filters.services;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.gabrielcardoso.filters.domain.entities.Category;
import br.gabrielcardoso.filters.interfaces.ICategoryService;
import br.gabrielcardoso.filters.models.FilterModel;
import br.gabrielcardoso.filters.models.PageModel;
import br.gabrielcardoso.filters.repositories.CategoryRepository;
import br.gabrielcardoso.filters.specifications.EntitySpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository repository;

    @Override
    public List<Category> list() {
        return repository.findAll();
    }

   
    @Override
    public PageModel<Category> list(FilterModel filter) {
        var page = repository.findAll(
            buildSpecification(filter), 
            filter.toPageable()
        );
        return new PageModel<>(page);
    }

    @Override
    public Specification<Category> buildSpecification(FilterModel filterModel) {
        Specification<Category> spec = Specification.where(null);

        for (var equalFilter: filterModel.getEqualFilters()) 
            spec = spec.and(EntitySpecification.equal(equalFilter, Category.class));
        
        for (var inFilter: filterModel.getInFilters()) 
            spec = spec.and(EntitySpecification.in(inFilter, Category.class));

        for (var dateFilter: filterModel.getDateFilters()) 
            spec = spec.and(EntitySpecification.rangeDate(dateFilter, Category.class));

        return spec;
    }
    
}
