package az.shuseynli.sales_project
.model.entity;

import az.shuseynli.sales_project
.model.enums.Channel;
import az.shuseynli.sales_project
.model.enums.OrderStatus;
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
public class Order {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, length = 20)
   private Channel channel;

   @Column(nullable = false, precision = 12, scale = 2)
   private BigDecimal totalAmount;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, length = 30)
   private OrderStatus currentStatus;

   @CreationTimestamp
   private LocalDateTime createdAt;

   @UpdateTimestamp
   private LocalDateTime updatedAt;

   @Column(unique = true)
   private String idempotencyKey;

/// Relations
   @ToString.Exclude
   @ManyToOne
   @JoinColumn(name = "customer_id")
   private Customer customer;

   @ToString.Exclude
//   @JsonIgnore
   @OneToMany(mappedBy = "order", orphanRemoval = true)
   private Set<History> histories = new LinkedHashSet<>();



   @ToString.Exclude
//   @JsonIgnore
   @ManyToMany
   @JoinTable(name = "Order_product",
           joinColumns = @JoinColumn(name = "order_id"),
           inverseJoinColumns = @JoinColumn(name = "product_id"))
   private Set<Product> products = new LinkedHashSet<>();

   @OneToOne
   @JoinColumn(name = "payment_id")
   private Payment payment;

   @Override
   public final boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Order order = (Order) o;
      return getId() != null && Objects.equals(getId(), order.getId());
   }

   @Override
   public final int hashCode() {
      return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
   }

}
