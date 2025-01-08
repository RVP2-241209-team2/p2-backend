package com.revature.shoply.services;

import com.revature.shoply.repositories.TagDAO;
import com.revature.shoply.models.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {

    private final TagDAO tagDAO;

    public TagService(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    public List<Tag> getAllTags() {
        return tagDAO.findAll();
    }

    public Tag getTagById(UUID id) {
        return tagDAO.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public Tag getTagByName(String tagName) {
        return tagDAO.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public Tag addTag(Tag tag) {
        return tagDAO.save(tag);
    }

    public Tag updateTag(Tag tag) {
        Tag existingTag = tagDAO.findById(tag.getId()).orElseThrow(() -> new RuntimeException("Tag not found"));
        existingTag.setName(tag.getName());
        return tagDAO.save(existingTag);
    }

    public Boolean deleteTag(UUID id) {
        Tag existingTag = tagDAO.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        tagDAO.delete(existingTag);
        return true;
    }
}
