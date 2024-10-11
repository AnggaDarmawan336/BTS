package com.code.BTS.service.impl;

import com.code.BTS.entity.CheckList;
import com.code.BTS.repository.CheckListRepository;
import com.code.BTS.service.CheckListService;
import com.code.BTS.utils.GeneralSpecification;
import com.code.BTS.utils.dto.request.CheckListRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Service
@AllArgsConstructor
public class CheckListServiceImpl implements CheckListService {
    private CheckListRepository checklistRepository;

    @Override
    public CheckList create(CheckListRequest request) {
        return checklistRepository.saveAndFlush(request.convert());
    }

    @Override
    public Page<CheckList> getAll(Pageable pageable, CheckListRequest request) {
        Specification<CheckList> specification = GeneralSpecification.getSpecification(request);
        return checklistRepository.findAll(specification, pageable);
    }

    @Override
    public CheckList getById(Long id) {
        return checklistRepository.findById(id)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND, "Checklist with id " + id + " is not found"));
    }

    @Override
    public CheckList update(CheckList request) {
        return checklistRepository.save(request);
    }

    @Override
    public void delete(Long id) {
        this.getById(id);
        checklistRepository.deleteById(id);
    }
}
