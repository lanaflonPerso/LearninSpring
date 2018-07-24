package ru.ivmiit.mvc.models;

import lombok.*;

import javax.persistence.*;

@Data           //auto generation setter getter and constructor, its annotation from jdbc
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "owner")
@Entity
@Table(name = "fix_car")
public class Car {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
