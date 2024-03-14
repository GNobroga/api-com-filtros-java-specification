package br.gabrielcardoso.filters.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import lombok.Setter;
import static br.gabrielcardoso.filters.constants.FilterConstants.*;


@Setter
public class FilterModel {
    private Integer limit;
    private Integer page;
    private String sort;
    private String equalFilters;
    private String inFilters;
    private String dateFilters;

    public FilterModel(Map<String, String> params) {
        limit = params.containsKey(LIMIT_KEY) ? Integer.valueOf(params.get(LIMIT_KEY)) : DEFAULT_LIMIT;
        page = params.containsKey(PAGE_KEY) ? Integer.valueOf(params.get(PAGE_KEY)) : DEFAULT_PAGE;
        sort = params.containsKey(SORT_KEY) ? params.get(SORT_KEY) : DEFAULT_SORT;
        equalFilters = params.containsKey(EQUAL_FILTERS_KEY) ? params.get(EQUAL_FILTERS_KEY) : DEFAULT_EQUAL_FILTERS;
        inFilters = params.containsKey(IN_FILTERS_KEY) ? params.get(IN_FILTERS_KEY) : DEFAULT_IN_FILTERS;
        dateFilters = params.containsKey(DATE_FILTERS_KEY) ? params.get(DATE_FILTERS_KEY) : DEFAULT_DATE_FILTERS;
    }

    public Integer getPage() {
        return Math.max(page, DEFAULT_PAGE);
    }

    public Integer getLimit() {
        if (limit < 1) {
            return 1;
        }
        else if (limit > 50) {
            return MAX_LIMIT;
        }
        else {
            return limit;
        }
    }

    public List<InFilterModel> getInFilters() {
        
        // id:1,2,3,4

        // ~id:1,2,3,4,5;^supplier.id

        List<InFilterModel> filters = new ArrayList<>();

        if (Objects.isNull(inFilters) || inFilters.trim().isEmpty()) 
            return filters;

        var tuples = inFilters.split(";");

        for (var tuple: tuples) {
            var columnAndInValues = tuple.split(":");

            if (columnAndInValues.length != 2)
                continue;
            
            var column = columnAndInValues[0];
            var isIn = !column.startsWith("~");
            var values = Arrays.asList(columnAndInValues[1].split(","));
            var inFilterModel = new InFilterModel(column.replace("~", ""), values, isIn);
            filters.add(inFilterModel);
        }


        return filters;
    }

    public List<DateFilterModel> getDateFilters() {
        //TODO creationDate: 2019-07-10to2019-07-10
        List<DateFilterModel> filters = new ArrayList<>();

        if (Objects.isNull(dateFilters) || dateFilters.trim().isEmpty())
            return filters;
 
        var criterias = dateFilters.split(";");

        for (var criteria: criterias) {

            var dateRange = criteria.split(":");
            
            if (dateRange.length != 2) 
                continue;

            var column = dateRange[0];
            var dates = dateRange[1].split("to");


            if (dates.length != 2) {
                continue;
            }

            var initialDate = getDate(dates[0].trim());
            var finalDate = getDate(dates[1].trim());


            if (initialDate == null || finalDate == null) {
                continue;
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(finalDate);
            cal.add(Calendar.DAY_OF_MONTH, 1);

            filters.add(new DateFilterModel(column, initialDate, cal.getTime()));
        }


        return filters;
    }

    private Date getDate(String date) {
        try {
            var sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<EqualityFilterModel> getEqualFilters() {
        //TODO: - Separador / = - igualdade  != - diferença Ex: name:!=Gabriel
        List<EqualityFilterModel> filters = new ArrayList<>();

        if (Objects.isNull(equalFilters) || equalFilters.trim().isEmpty())   
            return filters;

        var tuples = equalFilters.split(";");

        for (var equalFilter: tuples) {
            var columnCriteria = equalFilter.split(":");

            if (columnCriteria.length != 2)
                continue;
            
            var column = columnCriteria[0];
            var criteria = columnCriteria[1];

            if (criteria.startsWith("=") || criteria.startsWith("!=")) {
                var filter = new EqualityFilterModel(column, criteria.replaceAll("=|!=", ""), criteria.startsWith("="));
                filters.add(filter);
            }
        }

        return filters;
    }

    public Pageable toPageable() {
        //TODO Ordenando pelos campos -> field,-field,field -> (-) - DESC
        List<Order> orders = Arrays
                .stream(sort.split(","))
                .filter(field -> !field.isEmpty())
                .map(FilterModel::extractOrder)
                .collect(Collectors.toList());
        return PageRequest.of(getPage(), getLimit(),  Sort.by(orders));
    }
    
    private static Order extractOrder(String field) {
        var startsWithMinus = field.startsWith("-");
        field = field.replaceAll("-", "").trim();
        return startsWithMinus ? new Order(Direction.DESC, field) : new Order(Direction.ASC, field);
    }

}
