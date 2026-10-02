package az.shuseynli.sales_project
.model.entity;

import az.shuseynli.sales_project
.model.enums.PaymentMethod;
import az.shuseynli.sales_project
.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Payment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, precision = 12, scale = 2)
   private BigDecimal amount;

   @Enumerated(EnumType.STRING)
   @Column(length = 30)
   private PaymentMethod method;

   @Enumerated(EnumType.STRING)
   @Column(length = 30)
   private PaymentStatus status;

   @CreationTimestamp
   private LocalDateTime paymentDate;

/// Online sale fields
   @Column(length = 100, unique = true)
   private String transactionId;

/// Offline sale fields
   @Column(length = 50)
   private String branchId;

   @Column(length = 50)
   private String cashRegisterId;


/// Relations
   @OneToOne(mappedBy = "payment", orphanRemoval = true)
   private Order order;


   @Override
   public final boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Payment payment = (Payment) o;
      return getId() != null && Objects.equals(getId(), payment.getId());
   }

   @Override
   public final int hashCode() {
      return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
   }

}
