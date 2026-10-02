package az.shuseynli.sales_project
.model.entity;

import az.shuseynli.sales_project
.model.enums.CustomerStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Customer {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, length = 50)
   private String name;

   @Column(length = 50)
   private String surname;

   @Column(unique = true, length = 100)
   private String email;

   @Column(unique = true, length = 20)
   private String phoneNumber;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private CustomerStatus status;

   @Column(name = "created_at", updatable = false)
   @CreationTimestamp
   private LocalDateTime createdAt;


/// Relations
   @OneToOne(orphanRemoval = true)
   @JoinColumn(name = "profile_id")
   private Profile profile;

   @ToString.Exclude
   @JsonIgnore
   @OneToMany(mappedBy = "customer", orphanRemoval = true)
   private Set<Order> orders = new LinkedHashSet<>();


   @Override
   public final boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Customer customer = (Customer) o;
      return getId() != null && Objects.equals(getId(), customer.getId());
   }

   @Override
   public final int hashCode() {
      return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
   }

}
