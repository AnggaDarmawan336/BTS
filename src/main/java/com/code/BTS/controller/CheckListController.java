package com.code.BTS.controller;

import com.code.BTS.entity.CheckList;
import com.code.BTS.security.JwtUtils;
import com.code.BTS.service.CheckListService;
import com.code.BTS.service.UserService;
import com.code.BTS.service.impl.CheckListServiceImpl;
import com.code.BTS.utils.dto.request.CheckListRequest;
import com.code.BTS.utils.dto.response.CheckListResponse;
import com.code.BTS.utils.dto.webResponse.PageResponse;
import com.code.BTS.utils.dto.webResponse.Res;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/api/checklist")
public class CheckListController {

    @Autowired
    private CheckListServiceImpl checkListServiceImpl;
    private CheckListService checkListService;
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader(name = "Authorization") String access_token,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            @ModelAttribute CheckListRequest request) {

        Claims jwtPayload = JwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isTokenNotYetExpired) {
            PageResponse<CheckList> res = new PageResponse<>(checkListService.getAll(page, request));
            return Res.renderJson(res, "ok", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token expired");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CheckListRequest request, @RequestHeader(name = "Authorization") String access_token) {
        Claims jwtPayload = JwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(userService.getById(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            CheckList checkList = checkListService.create(request);
            CheckListResponse response = CheckListResponse.fromCheckList(checkList);
            return Res.renderJson(response, "Product ID Retrieved Successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization") String access_token, @PathVariable String idCheckList) {
        Claims jwtPayload = JwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(userService.getById(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            try {
                checkListService.delete(Long.valueOf(idCheckList));
                return Res.renderJson(null, "Product Deleted Successfully", HttpStatus.OK);
            } catch (Exception e) {
                return Res.renderJson(null, "Failed to Delete Product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }

    }
}
