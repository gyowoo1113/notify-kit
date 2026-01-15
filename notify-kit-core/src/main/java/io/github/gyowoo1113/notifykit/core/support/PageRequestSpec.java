package io.github.gyowoo1113.notifykit.core.support;

import io.github.gyowoo1113.notifykit.core.exception.ValidationException;

public record PageRequestSpec(int page, int size) {
    public static PageRequestSpec of(Integer rawPage, Integer rawSize, int defaultSize, int maxSize){
        int normalizedPage = (rawPage == null) ? 0 : rawPage;
        int normalizedSize = (rawSize == null) ? defaultSize : rawSize;

        if (normalizedPage < 0) throw new ValidationException("notification","page","min_0");
        if (normalizedSize < 1) throw new ValidationException("notification","size","min_1");
        if (normalizedSize > maxSize) throw new ValidationException("notification","size","max_" + maxSize);
        return new PageRequestSpec(normalizedPage, normalizedSize);
    }
}
