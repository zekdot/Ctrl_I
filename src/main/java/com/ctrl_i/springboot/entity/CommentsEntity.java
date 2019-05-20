package com.ctrl_i.springboot.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Create by zekdot on 19-5-20.
 */
@Entity
@Table(name = "comments", schema = "ctrl_i", catalog = "")
public class CommentsEntity {
    private int jId;
    private String uId;
    private Integer aId;
    private String content;
    private Timestamp time;

    @Id
    @Column(name = "j_id")
    public int getjId() {
        return jId;
    }

    public void setjId(int jId) {
        this.jId = jId;
    }

    @Basic
    @Column(name = "u_id")
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Basic
    @Column(name = "a_id")
    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentsEntity that = (CommentsEntity) o;
        return jId == that.jId &&
                Objects.equals(uId, that.uId) &&
                Objects.equals(aId, that.aId) &&
                Objects.equals(content, that.content) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jId, uId, aId, content, time);
    }
}
