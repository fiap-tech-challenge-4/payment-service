package br.com.payment.utils;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Pagination {

    public static Sort.Direction getSort(String value) {
        return Sort.Direction.ASC.name().equals(value.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public static PageRequest getPageRequest(Integer size, Integer page, String sort, String... properties) {
        if (page > 0)
            page -= 1;

        return PageRequest.of(page, size, getSort(sort), properties);
    }

}
