package com.code.BTS.service;

import com.code.BTS.entity.CheckList;
import com.code.BTS.utils.dto.request.CheckListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CheckListService {
    CheckList create(CheckListRequest request);
    Page<CheckList> getAll(Pageable pageable, CheckListRequest request);
    CheckList getById(String id);
    CheckList update(CheckList request);
    void delete(String id);
    CheckList fineByUserId(String userId);
}
