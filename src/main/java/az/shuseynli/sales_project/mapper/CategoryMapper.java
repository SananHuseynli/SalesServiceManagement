package az.shuseynli.sales_project
.mapper;

import az.shuseynli.sales_project
.model.dto.category.CategoryCreateRequest;
import az.shuseynli.sales_project
.model.dto.category.CategoryResponse;
import az.shuseynli.sales_project
.model.dto.category.CategoryUpdateRequest;
import az.shuseynli.sales_project
.model.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = ProductMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

   Category toEntity(CategoryCreateRequest request);

   CategoryResponse toResponse(Category category);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void updateEntityFromRequest(CategoryUpdateRequest request, @MappingTarget Category category);
}