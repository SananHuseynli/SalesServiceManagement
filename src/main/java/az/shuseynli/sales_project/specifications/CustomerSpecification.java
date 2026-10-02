package az.shuseynli.sales_project.specifications;

import az.shuseynli.sales_project
.model.dto.customer.SearchCustomer;
import az.shuseynli.sales_project
.model.entity.Customer;
import az.shuseynli.sales_project
.model.entity.Profile;
import az.shuseynli.sales_project
.specifications.helper.SearchCriteria;
import az.shuseynli.sales_project
.specifications.helper.SearchOperations;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification implements Specification<Customer> {

   private final List<SearchCriteria> criteriaList = new ArrayList<>();

   public CustomerSpecification(SearchCustomer filter) {
      // --- CUSTOMER ---
      if (!ObjectUtils.isEmpty(filter.getName())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("name").
                 value1(filter.getName()).
                 operation1(SearchOperations.MATCH_START).build());
      }
      if (!ObjectUtils.isEmpty(filter.getSurname())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("surname").
                 value1(filter.getSurname()).
                 operation1(SearchOperations.MATCH).build());
      }
      if (!ObjectUtils.isEmpty(filter.getEmail())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("email").
                 value1(filter.getEmail()).
                 operation1(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getPhoneNumber())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("phoneNumber").
                 value1(filter.getPhoneNumber()).
                 operation1(SearchOperations.MATCH_END).build());
      }
      if (!ObjectUtils.isEmpty(filter.getStatus())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("status").
                 value1(filter.getStatus()).
                 operation1(SearchOperations.EQUAL).build());
      }

      // --- PROFILE ---
      if (!ObjectUtils.isEmpty(filter.getPin())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("pin").
                 value1(filter.getPin()).
                 operation1(SearchOperations.CUSTOMER_JOIN_PROFILE).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getPassport())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("passport").
                 value1(filter.getPassport()).
                 operation1(SearchOperations.CUSTOMER_JOIN_PROFILE).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCountry())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("country").
                 value1(filter.getCountry()).
                 operation1(SearchOperations.CUSTOMER_JOIN_PROFILE).operation2(SearchOperations.MATCH).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCity())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("city").
                 value1(filter.getCity()).
                 operation1(SearchOperations.CUSTOMER_JOIN_PROFILE).operation2(SearchOperations.MATCH).build());
      }
      if (!ObjectUtils.isEmpty(filter.getZipCode())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("zipCode").
                 value1(filter.getZipCode()).
                 operation1(SearchOperations.CUSTOMER_JOIN_PROFILE).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getGender())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("gender").
                 value1(filter.getGender()).
                 operation1(SearchOperations.CUSTOMER_JOIN_PROFILE).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getDobFrom()) && !ObjectUtils.isEmpty(filter.getDobTo())) {
         criteriaList.add(SearchCriteria.builder().
                 key1("dateOfBirth").
                 value1(filter.getDobFrom()).value2(filter.getDobTo()).
                 operation1(SearchOperations.CUSTOMER_JOIN_PROFILE).operation2(SearchOperations.BETWEEN).build());
      }
   }

   @Override
   public @Nullable Predicate toPredicate(Root<Customer> root,
                                          CriteriaQuery<?> query,
                                          CriteriaBuilder builder) {
      List<Predicate> predicates = new ArrayList<>();

      for (SearchCriteria criteria : criteriaList) {

         if (criteria.getOperation1().equals(SearchOperations.EQUAL)) {
            predicates.add(builder.equal(root.get(criteria.getKey1()), criteria.getValue1()));
         }
         else if (criteria.getOperation1().equals(SearchOperations.MATCH)) {
            predicates.add(builder.like(builder.lower(root.get(criteria.getKey1())),
                    "%" + criteria.getValue1().toString().toLowerCase() + "%"));
         }
         else if (criteria.getOperation1().equals(SearchOperations.MATCH_START)) {
            predicates.add(builder.like(builder.lower(root.get(criteria.getKey1())),
                    criteria.getValue1().toString().toLowerCase() + "%"));
         }
         else if (criteria.getOperation1().equals(SearchOperations.MATCH_END)) {
            predicates.add(builder.like(builder.lower(root.get(criteria.getKey1())),
                    "%" + criteria.getValue1().toString().toLowerCase()));
         }
         else if (criteria.getOperation1().equals(SearchOperations.BETWEEN)) {
            predicates.add(builder.between(root.get(criteria.getKey1()),
                    (Comparable) criteria.getValue1(), (Comparable) criteria.getValue2()));
         }
         else if (criteria.getOperation1().equals(SearchOperations.CUSTOMER_JOIN_PROFILE)) {

            Join<Customer, Profile> customerProfileJoin = root.join("profile", JoinType.INNER);

            if (criteria.getOperation2().equals(SearchOperations.EQUAL)) {
               predicates.add(builder.equal(customerProfileJoin.get(criteria.getKey1()), criteria.getValue1()));
            }
            else if (criteria.getOperation2().equals(SearchOperations.MATCH)) {
               predicates.add(builder.like(builder.lower(customerProfileJoin.get(criteria.getKey1())),
                       "%" + criteria.getValue1().toString().toLowerCase() + "%"));
            }
            else if (criteria.getOperation2().equals(SearchOperations.MATCH_START)) {
               predicates.add(builder.like(builder.lower(customerProfileJoin.get(criteria.getKey1())),
                       criteria.getValue1().toString().toLowerCase() + "%"));
            }
            else if (criteria.getOperation2().equals(SearchOperations.MATCH_END)) {
               predicates.add(builder.like(builder.lower(customerProfileJoin.get(criteria.getKey1())),
                       "%" + criteria.getValue1().toString().toLowerCase()));
            }
            else if (criteria.getOperation2().equals(SearchOperations.BETWEEN)) {
               predicates.add(builder.between(customerProfileJoin.get(criteria.getKey1()),
                       (Comparable) criteria.getValue1(), (Comparable) criteria.getValue2()));
            }
         }
      }

      return builder.and(predicates.toArray(new Predicate[0]));
   }

}