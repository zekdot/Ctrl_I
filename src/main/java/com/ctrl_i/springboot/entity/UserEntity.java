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
    private String password;

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
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, password);
    }
}
