package app.petclinic.common.pagination;

import com.aspectran.core.activity.Translet;
import com.aspectran.utils.StringUtils;
import com.aspectran.utils.annotation.jsr305.NonNull;
import org.springframework.util.Assert;

import java.util.function.LongSupplier;

/**
 * <p>Created: 2025-04-21</p>
 */
public class PageInfo {

    private static final int DEFAULT_PAGE_SIZE = 2;

    private final int number;

    private final int size;

    private final int offset;

    private long totalElements;

    private int totalPages;

    public PageInfo(int number, int size) {
        this.number = number;
        this.size = size;
        this.offset = (number - 1) * size;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public int getOffset() {
        return offset;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        this.totalPages = (int)(totalElements / size);
    }

    public void setTotalElements(int actualPageSize, LongSupplier totalSupplier) {
        Assert.notNull(totalSupplier, "TotalSupplier must not be null");
        if (isPartialPage(actualPageSize)) {
            if (isFirstPage()) {
                setTotalElements(actualPageSize);
            } if (actualPageSize > 0) {
                setTotalElements(offset + actualPageSize);
            }
        } else {
            setTotalElements(totalSupplier.getAsLong());
        }
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isPartialPage(int actualPageSize) {
        return (actualPageSize < size);
    }

    public boolean isFirstPage() {
        return (number == 1);
    }

    public boolean isLastPage() {
        return (number == totalPages);
    }

    public boolean hasPreviousPage() {
        return (number > 1);
    }

    @NonNull
    public static PageInfo of(@NonNull Translet translet) {
        return of(translet, DEFAULT_PAGE_SIZE);
    }

    @NonNull
    public static PageInfo of(@NonNull Translet translet, int defaultPageSize) {
        String pageNumber = translet.getParameter("page");
        String pageSize = translet.getParameter("size");
        int number = StringUtils.isEmpty(pageNumber) ? 1 : Integer.parseInt(pageNumber);
        int size = StringUtils.isEmpty(pageSize) ? defaultPageSize : Integer.parseInt(pageSize);
        return of(number, size);
    }

    @NonNull
    public static PageInfo of(int number, int size) {
        if (number < 1) {
            number = 1;
        }
        if (size < 1) {
            size = DEFAULT_PAGE_SIZE;
        }
        return new PageInfo(number, size);
    }

}
