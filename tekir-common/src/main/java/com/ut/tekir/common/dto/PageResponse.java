package com.ut.tekir.common.dto;

import java.util.List;

/**
 * Generic page response — wraps Spring Data Page for REST API.
 */
public record PageResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
) {}
