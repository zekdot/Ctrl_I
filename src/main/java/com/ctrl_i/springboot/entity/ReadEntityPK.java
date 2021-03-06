package com.ctrl_i.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Create by zekdot on 19-5-30.
 */
public class ReadEntityPK implements Serializable {
    private String uId;
    private int aId;

    @Column(name = "u_id")
    @Id
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Column(name = "a_id")
    @Id
    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadEntityPK that = (ReadEntityPK) o;
        return aId == that.aId &&
                Objects.equals(uId, that.uId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, aId);
    }
}
