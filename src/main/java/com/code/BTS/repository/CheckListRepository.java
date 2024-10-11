package com.code.BTS.repository;

import com.code.BTS.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    List<CheckList> findByUser_Username(String username);
    Optional<CheckList> findByIdAndUser_Username(Long id, String username);
}
