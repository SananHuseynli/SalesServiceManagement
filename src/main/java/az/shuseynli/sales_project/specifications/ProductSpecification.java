package az.shuseynli.sales_project.specifications;

import az.shuseynli.sales_project.model.dto.product.SearchProduct;
import az.shuseynli.sales_project
.model.entity.Category;
import az.shuseynli.sales_project
.model.entity.Product;
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

public class ProductSpecification implements Specification<Product> {

   private final List<SearchCriteria> criteriaList = new ArrayList<>();

   public ProductSpecification(SearchProduct filter) {
      // --- PRODUCT ---
      if (!ObjectUtils.isEmpty(filter.getName())) {
         criteriaList.add(SearchCriteria.builder().key1("name").value1(filter.getName()).operation1(SearchOperations.MATCH_START).build());
      }
      if (!ObjectUtils.isEmpty(filter.getDescription())) {
         criteriaList.add(SearchCriteria.builder().key1("description").value1(filter.getDescription()).operation1(SearchOperations.MATCH).build());
      }
      if (!ObjectUtils.isEmpty(filter.getBarcode())) {
         criteriaList.add(SearchCriteria.builder().key1("barcode").value1(filter.getBarcode()).operation1(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getPriceFrom()) && !ObjectUtils.isEmpty(filter.getPriceTo())) {
         criteriaList.add(SearchCriteria.builder().key1("price").value1(filter.getPriceFrom()).value2(filter.getPriceTo()).operation1(SearchOperations.BETWEEN).build());
      }
      if (!ObjectUtils.isEmpty(filter.getStockFrom()) && !ObjectUtils.isEmpty(filter.getStockTo())) {
         criteriaList.add(SearchCriteria.builder().key1("stockQuantity").value1(filter.getStockFrom()).value2(filter.getStockTo()).operation1(SearchOperations.BETWEEN).build());
      }
      if (!ObjectUtils.isEmpty(filter.getStatus())) {
         criteriaList.add(SearchCriteria.builder().key1("status").value1(filter.getStatus()).operation1(SearchOperations.EQUAL).build());
      }

      // --- CATEGORY ---
      if (!ObjectUtils.isEmpty(filter.getCategoryId())) {
         criteriaList.add(SearchCriteria.builder().key1("id").value1(filter.getCategoryId()).operation1(SearchOperations.JOIN_CATEGORY).operation2(SearchOperations.EQUAL).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCategoryName())) {
         criteriaList.add(SearchCriteria.builder().key1("name").value1(filter.getCategoryName()).operation1(SearchOperations.JOIN_CATEGORY).operation2(SearchOperations.MATCH).build());
      }
      if (!ObjectUtils.isEmpty(filter.getCategoryActive())) {
         criteriaList.add(SearchCriteria.builder().key1("isActive").value1(filter.getCategoryActive()).operation1(SearchOperations.JOIN_CATEGORY).operation2(SearchOperations.EQUAL).build());
      }
   }

   @Override
   public @Nullable Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
         else if (criteria.getOperation1().equals(SearchOperations.JOIN_CATEGORY)) {

            Join<Product, Category> productCategoryJoin = root.join("category", JoinType.INNER);

            if (criteria.getOperation2().equals(SearchOperations.EQUAL)) {
               predicates.add(builder.equal(productCategoryJoin.get(criteria.getKey1()), criteria.getValue1()));
            }
            else if (criteria.getOperation2().equals(SearchOperations.MATCH)) {
               predicates.add(builder.like(builder.lower(productCategoryJoin.get(criteria.getKey1())),
                       "%" + criteria.getValue1().toString().toLowerCase() + "%"));
            }
            else if (criteria.getOperation2().equals(SearchOperations.MATCH_START)) {
               predicates.add(builder.like(builder.lower(productCategoryJoin.get(criteria.getKey1())),
                       criteria.getValue1().toString().toLowerCase() + "%"));
            }
            else if (criteria.getOperation2().equals(SearchOperations.MATCH_END)) {
               predicates.add(builder.like(builder.lower(productCategoryJoin.get(criteria.getKey1())),
                       "%" + criteria.getValue1().toString().toLowerCase()));
            }
            else if (criteria.getOperation2().equals(SearchOperations.BETWEEN)) {
               predicates.add(builder.between(productCategoryJoin.get(criteria.getKey1()),
                       (Comparable) criteria.getValue1(), (Comparable) criteria.getValue2()));
            }
         }
      }

      return builder.and(predicates.toArray(new Predicate[0]));
   }

}