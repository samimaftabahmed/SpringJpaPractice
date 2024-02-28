package com.samhad.SpringJPAPrac.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "airport")
@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int airId;
    @Column
    private String portName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "airport")
    private List<Address> addresses;

    public Airport(String portName) {
        this.portName = portName;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airId=" + airId +
                ", portName='" + portName + '\'' +
                '}';
    }
}
