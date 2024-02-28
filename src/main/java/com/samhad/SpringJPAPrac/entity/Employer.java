package com.samhad.SpringJPAPrac.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "employer")
@Entity
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int eId;
    @Column
    private String name;
    @Column
    private String industry;
    @Column
    private String jobTitle;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_e_pId", referencedColumnName = "pId")
    private Person person;

    public Employer(String name, String industry, String jobTitle, Person person) {
        this.name = name;
        this.industry = industry;
        this.jobTitle = jobTitle;
        this.person = person;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "eId=" + eId +
                ", name='" + name + '\'' +
                ", industry='" + industry + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
