package az.shuseynli.sales_project
.model.entity;

import az.shuseynli.sales_project
.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Profile {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private LocalDate dateOfBirth;

   @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private Gender gender;

   private String country;

   private String city;

   @Column(columnDefinition = "TEXT")
   private String fullAddress;

   @Column(length = 20)
   private String zipCode;

   @Column(length = 10, unique = true)
   private String pin;

   @Column(length = 20)
   private String passport;

   @CreationTimestamp
   private LocalDateTime createdAt;

   @UpdateTimestamp
   private LocalDateTime updatedAt;


/// Relations
   @OneToOne(mappedBy = "profile", orphanRemoval = true)
   private Customer customer;


   @Override
   public final boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Profile profile = (Profile) o;
      return getId() != null && Objects.equals(getId(), profile.getId());
   }

   @Override
   public final int hashCode() {
      return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
   }

}
