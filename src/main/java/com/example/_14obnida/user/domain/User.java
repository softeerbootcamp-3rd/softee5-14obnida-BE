package com.example._14obnida.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Getter
@Table(name="users")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String nickname;

    @Column(nullable = false, length = 60)
    private String password;

    private int total;

    public void addTotal(int saving){
        this.total+=saving;
    }

    //누적금액 비우기
    public void clear(){
        this.total = 0;
    }
}
