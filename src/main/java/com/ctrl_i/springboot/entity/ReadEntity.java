package com.ctrl_i.springboot.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Create by zekdot on 19-5-30.
 */
@Entity
@Table(name = "read", schema = "ctrl_i", catalog = "")
@IdClass(ReadEntityPK.class)
public class ReadEntity {
    private String uId;
    private int aId;
    private Timestamp time;
    private double rate;

    @Id
    @Column(name = "u_id")
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Id
    @Column(name = "a_id")
    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "rate")
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadEntity that = (ReadEntity) o;
        return aId == that.aId &&
                Double.compare(that.rate, rate) == 0 &&
                Objects.equals(uId, that.uId) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, aId, time, rate);
    }
}
