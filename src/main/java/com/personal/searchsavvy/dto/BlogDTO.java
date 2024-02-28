package com.personal.searchsavvy.dto;
import com.personal.searchsavvy.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private Integer blogId;
    private String blogTitle;
    private CategoryEnum blogCategory;
    private String tagLine;
    private String blogContent;
    private String thumbnail;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
