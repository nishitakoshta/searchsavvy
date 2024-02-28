package com.personal.searchsavvy.dto;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedBlogResultDTO {
    private List<BlogDTO> searchResult;
    private boolean pagination;
    private int pageIndex;
}
