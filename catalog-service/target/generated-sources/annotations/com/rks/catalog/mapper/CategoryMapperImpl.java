package com.rks.catalog.mapper;

import com.rks.catalog.dto.category.CategoryResponse;
import com.rks.catalog.entity.category.Category;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-22T16:22:46+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_282 (AdoptOpenJDK)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponse categoryToCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId( category.getId() );
        categoryResponse.setName( category.getName() );
        categoryResponse.setDescription( category.getDescription() );

        return categoryResponse;
    }
}
