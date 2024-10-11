package com.code.BTS.repository;

import com.code.BTS.entity.CheckListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListItemRepository extends JpaRepository<CheckListItem, Long> {
}
