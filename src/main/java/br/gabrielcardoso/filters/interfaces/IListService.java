package br.gabrielcardoso.filters.interfaces;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.gabrielcardoso.filters.models.FilterModel;
import br.gabrielcardoso.filters.models.PageModel;

public interface IListService<T> {
    
    List<T> list();

    PageModel<T> list(FilterModel filter);

    Specification<T> buildSpecification(FilterModel filterModel);
}
