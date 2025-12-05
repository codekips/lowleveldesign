package com.abworks.structures.database.core.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class Query {
    private final Filter filter;
    private final int limit;
}
