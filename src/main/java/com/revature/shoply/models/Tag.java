package com.revature.shoply.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "tag_name", unique = true, nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<Product> products;

    @OneToMany(mappedBy = "tag")
    private List<Tag> tags;

    public Tag() {
        super();
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
