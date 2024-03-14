package br.gabrielcardoso.filters.specifications;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import br.gabrielcardoso.filters.builders.PathBuilder;
import br.gabrielcardoso.filters.domain.entities.BaseEntity;
import br.gabrielcardoso.filters.models.DateFilterModel;
import br.gabrielcardoso.filters.models.EqualityFilterModel;
import br.gabrielcardoso.filters.models.InFilterModel;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class EntitySpecification {

    
    public static <T extends BaseEntity> Specification<T> rangeDate(DateFilterModel date, Class<T> clazz) {
        return (root, query, cb) -> {
            var pathBuilder = new PathBuilder<>(clazz);

            var path = pathBuilder.get(root, date.getColumn());
    
            if (Objects.isNull(path)) 
                return cb.and();

                Expression<Date> dateToCompare = path.as(Date.class);
                
                Predicate predicate = cb.between(dateToCompare, date.getInitialDate(), date.getFinalDate());
                
            return predicate;
        };
    } 
    

    public static <T extends BaseEntity> Specification<T> in(InFilterModel in, Class<T> clazz) {
        return (root, query, cb) -> {
            var pathBuilder = new PathBuilder<>(clazz);

            var path = pathBuilder.get(root, in.getColumn());
    
            if (Objects.isNull(path)) 
                return cb.and();
            
            Predicate predicate = in.isIn() ? path.as(String.class).in(in.getValues()) : path.as(String.class).in(in.getValues()).not();

            return predicate;
        };
    } 
    
    public static <T extends BaseEntity> Specification<T> equal(EqualityFilterModel eq, Class<T> clazz) {
        return (root, query, cb) -> {
            var pathBuilder = new PathBuilder<>(clazz);

            var path = pathBuilder.get(root, eq.getColumn());
    
            if (Objects.isNull(path)) 
                return cb.and();
            
            Predicate predicate = eq.isEqual() ? cb.equal(path, eq.getValue()) : cb.notEqual(path, eq.getValue());

            return predicate;
        };
    } 
}
