package com.ctrl_i.springboot.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Create by zekdot on 19-4-13.
 */
@Entity
@Table(name = "article", schema = "ctrl_i", catalog = "")
public class ArticleEntity {
    private int aId;
    private String title;
    private String author;
    private String cover;
    private String content;
    private String type;
    private Timestamp time;
    private Integer readNum;

    @Id
    @Column(name = "a_id")
    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "cover")
    public String getCover() { return cover; }

    public void setCover(String cover){ this.cover = cover; }
    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "read_num")
    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleEntity that = (ArticleEntity) o;
        return aId == that.aId &&
                Objects.equals(title, that.title) &&
                Objects.equals(author, that.author) &&
                Objects.equals(content, that.content) &&
                Objects.equals(type, that.type) &&
                Objects.equals(time, that.time) &&
                Objects.equals(readNum, that.readNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aId, title, author, content, type, time, readNum);
    }
}
