package com.lyng.study.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/14.
 * 书单实体类
 * @Component 注解可以将当前类纳入spring管理,
 * 便于其他类调用时自动注入
 * @ConfigurationProperties 注解可以绑定属性文件中的属性值到实体,注意属性文件中的属性值要与实体类中的属性值一致
 * 这样可以省去用多个@Value 注入每一个属性
 * @Entity 标注的实体类会在项目启动时通过jpa接口策略去生成对应的表
 */
//@Component
//@ConfigurationProperties(prefix = "book")
@Entity
public class Book implements Serializable{
    /**
     * 书单编号
     * @Id 注解标注哪个字段是表中主键id
     * @GeneratedValue 注解标注主键id生成策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * 书名
     */
    private String name;
    /**
     * 作者
     */
    private String author;
    /**
     * 状态
     */
    private int status;
    /**
     * 描述
     */
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
