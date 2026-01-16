package io.github.gyowoo1113.notifykit.core.support;

import java.util.List;
import java.util.function.Function;

public record CursorPage<T>(
        List<T> items,
        Long nextCursor,
        boolean hasNext
) {
    public <R> CursorPage<R> map(Function<T,R> mapper){
        return new CursorPage<>(
                items.stream().map(mapper).toList(),
                nextCursor,
                hasNext
        );
    }
}
