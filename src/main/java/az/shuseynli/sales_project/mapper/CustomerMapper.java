package az.shuseynli.sales_project
.mapper;

import az.shuseynli.sales_project
.model.dto.customer.CustomerCreateRequest;
import az.shuseynli.sales_project
.model.dto.customer.CustomerRequest;
import az.shuseynli.sales_project
.model.dto.customer.CustomerResponse;
import az.shuseynli.sales_project
.model.entity.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = ProfileMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

   Customer toEntity(CustomerCreateRequest request);

   CustomerResponse toResponse(Customer customer);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void updateEntityFromRequest(CustomerRequest request, @MappingTarget Customer customer);

}