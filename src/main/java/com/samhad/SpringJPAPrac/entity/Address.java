package com.samhad.SpringJPAPrac.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int aId;
    @Column
    private String street;
    @Column
    private int pinCode;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_a_pId", referencedColumnName = "pId")
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_air_airId", referencedColumnName = "airId")
    private Airport airport;

    public Address(String street, int pinCode) {
        this.street = street;
        this.pinCode = pinCode;
    }

    public Address(String street, int pinCode, Person person) {
        this.street = street;
        this.pinCode = pinCode;
        this.person = person;
    }

    @Override
    public String toString() {
        return "Address{" +
                "aId=" + aId +
                ", street='" + street + '\'' +
                ", pinCode=" + pinCode +
                ", createdAt=" + createdAt +
                '}';
    }
}
