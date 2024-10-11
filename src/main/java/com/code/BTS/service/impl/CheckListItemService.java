package com.code.BTS.service.impl;

import com.code.BTS.entity.CheckListItem;
import com.code.BTS.repository.CheckListItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckListItemService {
    @Autowired
    private CheckListItemRepository checklistItemRepository;

    public CheckListItem getItemById(Long checklistId, Long itemId) {
        return checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public CheckListItem createItem(Long checklistId, CheckListItem item) {
        return checklistItemRepository.save(item);
    }

    public void deleteItem(Long checklistId, Long itemId) {
        checklistItemRepository.deleteById(itemId);
    }

    public CheckListItem updateItem(Long checklistId, Long itemId, CheckListItem item) {
        return checklistItemRepository.save(item);
    }

    public CheckListItem renameItem(Long checklistId, Long itemId, String newName) {
        CheckListItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setItemName(newName);
        return checklistItemRepository.save(item);
    }
}
