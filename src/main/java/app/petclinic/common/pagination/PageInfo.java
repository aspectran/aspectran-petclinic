package app.petclinic.common.pagination;

import com.aspectran.core.activity.Translet;
import com.aspectran.utils.StringUtils;
import com.aspectran.utils.annotation.jsr305.NonNull;

/**
 * <p>Created: 2025-04-21</p>
 */
public class PageInfo {

    private static final int DEFAULT_PAGE_SIZE = 3;

    private final int number;

    private final int size;

    private final int offset;

    private int totalRecords;

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

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
        this.totalPages = totalRecords / size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @NonNull
    public static PageInfo of(@NonNull Translet translet) {
        String pageNumber = translet.getParameter("page");
        String pageSize = translet.getParameter("size");
        int number = StringUtils.isEmpty(pageNumber) ? 1 : Integer.parseInt(pageNumber);
        int size = StringUtils.isEmpty(pageSize) ? DEFAULT_PAGE_SIZE : Integer.parseInt(pageSize);
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
