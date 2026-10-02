package az.shuseynli.sales_project
.mapper;

import az.shuseynli.sales_project
.model.dto.customer.ProfileCreateRequest;
import az.shuseynli.sales_project
.model.dto.customer.ProfileRequest;
import az.shuseynli.sales_project
.model.dto.customer.ProfileResponse;
import az.shuseynli.sales_project
.model.entity.Profile;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProfileMapper {

   Profile toEntity(ProfileCreateRequest request);

   ProfileResponse toResponse(Profile profile);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void updateEntityFromRequest(ProfileRequest request, @MappingTarget Profile profile);

}