package com.code.camping.controller;

import com.code.camping.entity.Admin;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.AdminService;
import com.code.camping.utils.dto.request.LoginAdminRequest;
import com.code.camping.utils.dto.request.RegisterAdminRequest;
import com.code.camping.utils.dto.response.AdminResponse;
import com.code.camping.utils.dto.response.LoginAdminResponse;
import com.code.camping.utils.dto.webResponse.PageResponse;
import com.code.camping.utils.dto.webResponse.Res;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admins")
@AllArgsConstructor
public class AdminController {

	private final AdminService admin_service;
	private final JwtUtils jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterAdminRequest request) {
		AdminResponse adminResponse = AdminResponse.fromAdmin(admin_service.create(request));
		return Res.renderJson(adminResponse,"Register Admin created successfully",HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public LoginAdminResponse login(@RequestBody LoginAdminRequest request){
		return admin_service.login(request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get_by_id(
			@PathVariable String id,
			@RequestHeader(name = "Authorization") String access_token
	){
		Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
		Date currentDate = new Date();
		boolean isAdminIdJWTEqualsAdminIdReqParams = jwtPayload.getSubject().equals(id);
		boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
		if (isAdminIdJWTEqualsAdminIdReqParams && isTokenNotYetExpired) {
			return Res.renderJson(AdminResponse.fromAdmin(admin_service.get_by_id(id)),
					"Admin ID Retrieved Successfully",HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
		}
	}

	@GetMapping
	public ResponseEntity<?> get_all(
			@RequestHeader(name = "Authorization") String access_token,
			@PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.ASC) Pageable page,
			@ModelAttribute RegisterAdminRequest registerAdminRequest
	){
		Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
		Date currentDate = new Date();
		String AdminToken = jwtPayload.getSubject();
		String AdminService = admin_service.get_by_id(AdminToken).getId();
		boolean EqualsAdminToken = jwtPayload.getSubject().equals(AdminService);
		boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
		if (EqualsAdminToken && isTokenNotYetExpired){
			PageResponse<Admin> res = new PageResponse<>(admin_service.get_all(page, registerAdminRequest));
			return Res.renderJson(res, "ok", HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(
			@RequestHeader(name = "Authorization") String access_token,
			@RequestBody RegisterAdminRequest request
	){
		Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
		Date currentDate = new Date();
		boolean isAdminIdJWTEqualsAdminIdReqParams = jwtPayload.getSubject().equals(request.getId());
		boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
		if (isAdminIdJWTEqualsAdminIdReqParams && isTokenNotYetExpired) {
			Admin admin = admin_service.update(request);
			return ResponseEntity.ok(AdminResponse.fromAdmin(admin));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@PathVariable String id,
			@RequestHeader(name = "Authorization") String access_token
	){
		Claims jwtPayLoad = jwtUtils.decodeAccessToken(access_token);
		Date currentDate = new Date();
		boolean isAdminIdJWTEqualsAdminIdReqParams = jwtPayLoad.getSubject().equals(id);
		boolean isTokenNotYetExpired = currentDate.before(jwtPayLoad.getExpiration());

		if (isAdminIdJWTEqualsAdminIdReqParams && isTokenNotYetExpired){
			try {
				admin_service.delete(id);
				return Res.renderJson(null,"Admin deleted successfully",HttpStatus.OK);
			} catch (Exception error){
				return Res.renderJson(null,"Failed to delete admin",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
		}
	}
}
