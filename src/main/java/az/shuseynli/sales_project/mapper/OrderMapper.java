package az.shuseynli.sales_project
.mapper;

import az.shuseynli.sales_project
.model.dto.order.*;
import az.shuseynli.sales_project
.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

   OrderResponse toResponse(Order order);

   OrderFullResponse toFullResponse(Order order);

   OrderCustomerResponse toCustomerResponse(Customer customer);

   OrderProductResponse toProductResponse(Product product);

   OrderPaymentResponse toPaymentResponse(Payment payment);

   OrderHistoryResponse toHistoryResponse(History history);

}