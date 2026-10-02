package az.shuseynli.sales_project
.model.entity;

import az.shuseynli.sales_project
.model.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(length = 50)
   private String name;

   @Column(columnDefinition = "TEXT")
   private String description;

   @Column(unique = true, length = 30)
   private String barcode;

   @Column(nullable = false, precision = 12, scale = 2)
   private BigDecimal price;

   @Column(nullable = false)
   private Integer stockQuantity;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, length = 20)
   private ProductStatus status;

   @CreationTimestamp
   private LocalDateTime createdAt;

   @UpdateTimestamp
   private LocalDateTime updatedAt;


/// Relations
   @ManyToOne
   @JoinColumn(name = "category_id")
   private Category category;

   @ToString.Exclude
   @JsonIgnore
   @ManyToMany(mappedBy = "products")
   private Set<Order> order = new LinkedHashSet<>();


   @Override
   public final boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Product product = (Product) o;
      return getId() != null && Objects.equals(getId(), product.getId());
   }

   @Override
   public final int hashCode() {
      return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
   }

}
