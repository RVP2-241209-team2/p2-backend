package com.revature.shoply.controllers;

import com.revature.shoply.models.Tag;
import com.revature.shoply.models.enums.UserRole;
import com.revature.shoply.services.TagService;
import org.springframework.http.ResponseEntity;


//import org.springframework.security.access.annotation.Secured;


import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/v1/all")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/v1/tag/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable UUID id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @GetMapping("/v1/tag/{tagName}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String tagName) {
        return ResponseEntity.ok(tagService.getTagByName(tagName));
    }

//    @Secured("STORE_OWNER")
    @PostMapping("/v1/add")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.addTag(tag));
    }

//    @Secured("STORE_OWNER")
    @PutMapping("/v1/update")
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.updateTag(tag));
    }

//    @Secured("STORE_OWNER")
    @DeleteMapping("/v1/delete/{id}")
    public ResponseEntity<Boolean> deleteTag(@PathVariable UUID id) {
        return ResponseEntity.ok(tagService.deleteTag(id));
    }
}
