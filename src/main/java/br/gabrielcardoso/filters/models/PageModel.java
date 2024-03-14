package br.gabrielcardoso.filters.models;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter @Getter
public class PageModel<T> {
    private Long totalElements;
    private Integer currentPage;
    private Integer totalPages;
    private Integer pageSize;
    private List<T> elements;

    public PageModel(Page<T> page) {
         elements = page.getContent();
         totalPages = page.getTotalPages();
         totalElements = page.getTotalElements();
         pageSize = page.getSize();
         currentPage = page.getNumber();
    }
}
