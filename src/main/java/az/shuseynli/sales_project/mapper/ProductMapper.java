package az.shuseynli.sales_project
.mapper;

import az.shuseynli.sales_project
.model.dto.product.ProductCreateRequest;
import az.shuseynli.sales_project
.model.dto.product.ProductResponse;
import az.shuseynli.sales_project
.model.dto.product.ProductUpdateRequest;
import az.shuseynli.sales_project
.model.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

   @Mapping(target = "category", ignore = true)
   Product toEntity(ProductCreateRequest request);

   @Mapping(source = "category.id", target = "categoryId")
   ProductResponse toResponse(Product product);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   @Mapping(target = "category", ignore = true)
   void updateEntityFromRequest(ProductUpdateRequest request, @MappingTarget Product product);
}