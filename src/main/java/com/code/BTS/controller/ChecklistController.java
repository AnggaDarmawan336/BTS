package com.code.BTS.controller;

import com.code.BTS.entity.CheckList;
import com.code.BTS.service.impl.CheckListServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/checklist")
public class ChecklistController {

    @Autowired
    private CheckListServiceImpl checkListServiceImpl;

    @GetMapping
    public ResponseEntity<List<CheckList>> getAllChecklists() {
        List<CheckList> checklists = checkListServiceImpl.getAllChecklists();
        return ResponseEntity.ok(checklists);
    }

    @PostMapping
    public ResponseEntity<CheckList> createChecklist(@RequestBody CheckList checklist) {
        CheckList createdChecklist = checkListServiceImpl.createChecklist(checklist);
        return ResponseEntity.status(201).body(createdChecklist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChecklist(@PathVariable Long id) {
        checkListServiceImpl.deleteChecklist(id);
        return ResponseEntity.noContent().build();
    }
}
