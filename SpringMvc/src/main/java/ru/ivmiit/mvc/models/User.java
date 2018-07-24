package ru.ivmiit.mvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.mvc.forms.UserForm;

import javax.persistence.*;
import java.util.List;

@Data           //auto generation setter getter and constructor, its annotation from jdbc
@NoArgsConstructor
@AllArgsConstructor
@Builder        //pattern builder
@Entity
@Table(name = "fix_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "owner")
    private List<Car> cars;

    public static User from(UserForm form){
        return User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .build();
    }
}
