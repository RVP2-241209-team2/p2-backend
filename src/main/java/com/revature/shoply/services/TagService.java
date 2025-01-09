package com.revature.shoply.services;

import com.revature.shoply.repositories.TagDAO;
import com.revature.shoply.models.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {

    private static final Logger log = LoggerFactory.getLogger(TagService.class);

    private final TagDAO tagDAO;

    public TagService(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    public List<Tag> getAllTags() {
        log.info("Getting all tags");
        return tagDAO.findAll();
    }

    public Tag getTagById(UUID id) {
        log.info("Getting tag by id: {}", id);
        return tagDAO.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public Tag getTagByName(String tagName) {
        log.info("Getting tag by name: {}", tagName);
        return tagDAO.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public Tag addTag(Tag tag) {
        log.info("Adding tag: {}", tag);
        if (tagDAO.findByName(tag.getName()).isPresent()) {
            throw new RuntimeException("Tag already exists");
        }
        return tagDAO.save(tag);
    }

    public Tag updateTag(Tag tag) {
        log.info("Updating tag with id: {}", tag.getId());
        Tag existingTag = tagDAO.findById(tag.getId()).orElseThrow(() -> new RuntimeException("Tag not found"));
        existingTag.setName(tag.getName());
        return tagDAO.save(existingTag);
    }

    public Boolean deleteTag(UUID id) {
        log.info("Deleting tag with id: {}", id);
        Tag existingTag = tagDAO.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        tagDAO.delete(existingTag);
        return true;
    }
}
