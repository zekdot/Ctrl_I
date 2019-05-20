package com.ctrl_i.springboot.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Create by zekdot on 19-4-13.
 */
@Entity
@Table(name = "user", schema = "ctrl_i", catalog = "")
public class UserEntity {
    private String uId;
    private String nickname;
    private String password;
    private String email;
    private Byte state;
    @Id
    @Column(name = "u_id")
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(uId, that.uId) &&
                Objects.equals(nickname,that.nickname) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email,that.email) &&
                Objects.equals(state,that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, nickname, password, email, state);
    }
    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Basic
    @Column(name = "state")
    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
