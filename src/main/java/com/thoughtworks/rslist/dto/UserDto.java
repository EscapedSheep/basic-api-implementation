package com.thoughtworks.rslist.dto;
import com.thoughtworks.rslist.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String userName;

    private String email;
    private int age;
    private String phone;
    private int votes;
    private String gender;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userDto")
    private List<RsEventDto> rsEventDto;

    public User toUser() {
        return User.builder().age(age).email(email).userName(userName).gender(gender).phone(phone).votes(votes).id(id).build();
    }
}
