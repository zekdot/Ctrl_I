package com.ctrl_i.springboot.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Create by zekdot on 19-5-30.
 */
@Entity
@Table(name = "recommend", schema = "ctrl_i", catalog = "")
public class RecommendEntity {
    private String uId;
    private String content;
    private Date date;

    @Id
    @Column(name = "u_id")
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendEntity that = (RecommendEntity) o;
        return Objects.equals(uId, that.uId) &&
                Objects.equals(content, that.content) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, content, date);
    }
}
