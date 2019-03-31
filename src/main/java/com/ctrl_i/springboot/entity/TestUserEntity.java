package com.ctrl_i.springboot.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Create by zekdot on 19-3-31.
 */
@Entity
@Table(name = "test_user", schema = "ctrl_i", catalog = "")
public class TestUserEntity {
    private String id;
    private String password;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        TestUserEntity that = (TestUserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password);
    }
}
