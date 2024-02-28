package com.samhad.SpringJPAPrac.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

//@NamedEntityGraph(name = "Person.addresses", attributeNodes = @NamedAttributeNode("addresses"))
//@NamedEntityGraph(name = "Person.employer", attributeNodes = @NamedAttributeNode("employer"))
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int pId;
    @Column
    private String name;
    @Column
    private int age;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "person")
    private List<Address> addresses;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "person")
    private Employer employer;

    public Person(String name, int age, List<Address> addresses) {
        this.name = name;
        this.age = age;
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Person{" +
                "pId=" + pId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                '}';
    }
}

