package me.mryakar.tp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = PersonEntity.TABLE_NAME)
@SequenceGenerator(
        name = EntityConstant.SEQUENCE_GENERATOR_NAME,
        sequenceName = PersonEntity.SEQUENCE_NAME,
        allocationSize = 1
)
@Getter
@Setter
@RequiredArgsConstructor
public class PersonEntity extends BaseEntity {

    public static final String TABLE_NAME = "person";
    public static final String SEQUENCE_NAME = TABLE_NAME + "_seq";

    @Column(name = "name", nullable = false, length = 50)
    @NotBlank(message = "Person must have a name.")
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    @NotBlank(message = "Person must have a surname.")
    private String surname;

    @Column(name = "age", nullable = false)
    @Min(value = 0, message = "Person's age must be bigger than zero.")
    @Max(value = 130, message = "Person's age must be smaller than 130.")
    private int age;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "Person must have a gender.")
    private Gender gender;

    @Column(name = "nation", length = 50)
    @Pattern(regexp = "(\\w+){1,50}")
    private String nation;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hp ? hp.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hp ? hp.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PersonEntity that = (PersonEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hp ? hp.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}