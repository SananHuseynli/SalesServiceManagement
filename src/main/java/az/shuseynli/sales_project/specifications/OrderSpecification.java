package az.shuseynli.sales_project.specifications;

import az.shuseynli.sales_project
.model.dto.order.SearchOrder;
import az.shuseynli.sales_project
.model.entity.*;
import az.shuseynli.sales_project
.model.entity.Order;
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

public class OrderSpecification implements Specification<Order> {

   private final List<SearchCriteria> criteriaList = new ArrayList<>();

   public OrderSpecification(SearchOrder filter) {
      // --- ORDER ---
      if (!ObjectUtils.isEmpty(filter.getChannel())) {
         criteriaList.add(SearchCriteria.builder().key1("channel").value1(filter.getChannel()).operation1(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getTotalAmountFrom()) && !ObjectUtils.isEmpty(filter.getTotalAmountTo())) {
         criteriaList.add(SearchCriteria.builder().key1("totalAmount").value1(filter.getTotalAmountFrom()).value2(filter.getTotalAmountTo()).operation1(SearchOperations.BETWEEN).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCurrentStatus())) {
         criteriaList.add(SearchCriteria.builder().key1("currentStatus").value1(filter.getCurrentStatus()).operation1(SearchOperations.EQUAL).build());
      }

      if (!ObjectUtils.isEmpty(filter.getIdempotencyKey())) {
         criteriaList.add(SearchCriteria.builder().key1("idempotencyKey").value1(filter.getIdempotencyKey()).operation1(SearchOperations.EQUAL).build());
      }

      // --- CUSTOMER JOIN ---
      if (!ObjectUtils.isEmpty(filter.getCustomerId())) {
         criteriaList.add(SearchCriteria.builder().key1("id").value1(filter.getCustomerId()).operation1(SearchOperations.JOIN_CUSTOMER).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCustomerName())) {
         criteriaList.add(SearchCriteria.builder().key1("name").value1(filter.getCustomerName()).operation1(SearchOperations.JOIN_CUSTOMER).operation2(SearchOperations.MATCH_START).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCustomerSurname())) {
         criteriaList.add(SearchCriteria.builder().key1("surname").value1(filter.getCustomerSurname()).operation1(SearchOperations.JOIN_CUSTOMER).operation2(SearchOperations.MATCH).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCustomerEmail())) {
         criteriaList.add(SearchCriteria.builder().key1("email").value1(filter.getCustomerEmail()).operation1(SearchOperations.JOIN_CUSTOMER).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCustomerPhoneNumber())) {
         criteriaList.add(SearchCriteria.builder().key1("phoneNumber").value1(filter.getCustomerPhoneNumber()).operation1(SearchOperations.JOIN_CUSTOMER).operation2(SearchOperations.MATCH_END).build());
      }

      // --- PRODUCT JOIN ---
      if (!ObjectUtils.isEmpty(filter.getProductId())) {
         criteriaList.add(SearchCriteria.builder().key1("id").value1(filter.getProductId()).operation1(SearchOperations.JOIN_PRODUCT).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getProductName())) {
         criteriaList.add(SearchCriteria.builder().key1("name").value1(filter.getProductName()).operation1(SearchOperations.JOIN_PRODUCT).operation2(SearchOperations.MATCH).build());
      }
      if (!ObjectUtils.isEmpty(filter.getProductBarcode())) {
         criteriaList.add(SearchCriteria.builder().key1("barcode").value1(filter.getProductBarcode()).operation1(SearchOperations.JOIN_PRODUCT).operation2(SearchOperations.EQUAL).build());
      }

      // --- PAYMENT JOIN ---
      if (!ObjectUtils.isEmpty(filter.getPaymentMethod())) {
         criteriaList.add(SearchCriteria.builder().key1("method").value1(filter.getPaymentMethod()).operation1(SearchOperations.JOIN_PAYMENT).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getPaymentStatus())) {
         criteriaList.add(SearchCriteria.builder().key1("status").value1(filter.getPaymentStatus()).operation1(SearchOperations.JOIN_PAYMENT).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getTransactionId())) {
         criteriaList.add(SearchCriteria.builder().key1("transactionId").value1(filter.getTransactionId()).operation1(SearchOperations.JOIN_PAYMENT).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getBranchId())) {
         criteriaList.add(SearchCriteria.builder().key1("branchId").value1(filter.getBranchId()).operation1(SearchOperations.JOIN_PAYMENT).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCashRegisterId())) {
         criteriaList.add(SearchCriteria.builder().key1("cashRegisterId").value1(filter.getCashRegisterId()).operation1(SearchOperations.JOIN_PAYMENT).operation2(SearchOperations.EQUAL).build());
      }

      // --- HISTORY JOIN ---
      if (!ObjectUtils.isEmpty(filter.getHistoryStatus())) {
         criteriaList.add(SearchCriteria.builder().key1("status").value1(filter.getHistoryStatus()).operation1(SearchOperations.JOIN_HISTORY).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getHistoryNotes())) {
         criteriaList.add(SearchCriteria.builder().key1("notes").value1(filter.getHistoryNotes()).operation1(SearchOperations.JOIN_HISTORY).operation2(SearchOperations.MATCH).build());
      }
   }

   @Override
   public @Nullable Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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

         // CUSTOMER JOIN LOGIC
         else if (criteria.getOperation1().equals(SearchOperations.JOIN_CUSTOMER)) {
            Join<Order, Customer> orderCustomerJoin = root.join("customer", JoinType.INNER);
            appendJoinPredicates(predicates, orderCustomerJoin, criteria, builder);
         }

         // PRODUCT JOIN LOGIC
         else if (criteria.getOperation1().equals(SearchOperations.JOIN_PRODUCT)) {
            Join<Order, Product> orderProductJoin = root.join("products", JoinType.INNER);
            appendJoinPredicates(predicates, orderProductJoin, criteria, builder);
         }

         // PAYMENT JOIN LOGIC
         else if (criteria.getOperation1().equals(SearchOperations.JOIN_PAYMENT)) {
            Join<Order, Payment> orderPaymentJoin = root.join("payment", JoinType.INNER);
            appendJoinPredicates(predicates, orderPaymentJoin, criteria, builder);
         }

         // HISTORY JOIN LOGIC
         else if (criteria.getOperation1().equals(SearchOperations.JOIN_HISTORY)) {
            Join<Order, History> orderHistoryJoin = root.join("histories", JoinType.INNER);
            appendJoinPredicates(predicates, orderHistoryJoin, criteria, builder);
         }
      }

      return builder.and(predicates.toArray(new Predicate[0]));
   }


   private void appendJoinPredicates(List<Predicate> predicates, Join<?, ?> join, SearchCriteria criteria, CriteriaBuilder builder) {
      if (criteria.getOperation2().equals(SearchOperations.EQUAL)) {
         predicates.add(builder.equal(join.get(criteria.getKey1()), criteria.getValue1()));
      }
      else if (criteria.getOperation2().equals(SearchOperations.MATCH)) {
         predicates.add(builder.like(builder.lower(join.get(criteria.getKey1())),
                 "%" + criteria.getValue1().toString().toLowerCase() + "%"));
      }
      else if (criteria.getOperation2().equals(SearchOperations.MATCH_START)) {
         predicates.add(builder.like(builder.lower(join.get(criteria.getKey1())),
                 criteria.getValue1().toString().toLowerCase() + "%"));
      }
      else if (criteria.getOperation2().equals(SearchOperations.MATCH_END)) {
         predicates.add(builder.like(builder.lower(join.get(criteria.getKey1())),
                 "%" + criteria.getValue1().toString().toLowerCase()));
      }
      else if (criteria.getOperation2().equals(SearchOperations.BETWEEN)) {
         predicates.add(builder.between(join.get(criteria.getKey1()),
                 (Comparable) criteria.getValue1(), (Comparable) criteria.getValue2()));
      }
   }

}