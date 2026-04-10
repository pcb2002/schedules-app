package com.schedulesapp.entity;

import com.schedulesapp.exception.CustomException;
import com.schedulesapp.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String author;
    private String password;

    public Schedule(String title, String content, String author, String password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }

    public void update(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void checkPassword(String inputPassword) {
        if (!this.password.equals(inputPassword)) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
