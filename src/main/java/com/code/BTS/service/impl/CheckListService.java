package com.code.BTS.service.impl;

import com.code.BTS.entity.CheckList;
import com.code.BTS.repository.CheckListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CheckListService {
    private CheckListRepository checklistRepository;

    public List<CheckList> getAllChecklists() {
        return checklistRepository.findAll();
    }

    public CheckList createChecklist(CheckList checklist) {
        return checklistRepository.save(checklist);
    }

    public void deleteChecklist(Long id) {
        checklistRepository.deleteById(id);
    }
}
