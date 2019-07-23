package com.sunlight.collection;

import java.util.Collections;
import java.util.List;

public class PageList<T> {

    private List<T> list;
    private int offset;
    private int pageSize;

    public PageList(List<T> list, int pageSize) {
        this.list = list;
        this.offset = 0;
        this.pageSize = pageSize;
    }

    public boolean hasNextPage() {
        return list != null && offset < list.size();
    }

    public List<T> nextPage() {
        if (list == null) {
            return Collections.emptyList();
        }
        if (offset >= list.size()) {
            return Collections.emptyList();
        }
        int endOffset = offset + pageSize;
        if (endOffset > list.size()) {
            endOffset = list.size();
        }
        List<T> page = list.subList(offset, endOffset);
        offset = endOffset;
        return page;
    }

}
