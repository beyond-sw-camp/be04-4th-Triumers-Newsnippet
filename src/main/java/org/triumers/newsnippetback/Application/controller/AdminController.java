package org.triumers.newsnippetback.Application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.newsnippetback.common.exception.InsufficientAuthorityException;
import org.triumers.newsnippetback.domain.aggregate.enums.UserRole;
import org.triumers.newsnippetback.domain.aggregate.vo.ResponseMessageVO;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/health-check")
    public ResponseEntity<ResponseMessageVO> healthCheck() {
        try {
            isAdmin();
        } catch (InsufficientAuthorityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessageVO(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("Server is online..."));
    }

    private void isAdmin() throws InsufficientAuthorityException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

//        System.out.println("사용자 아이디: " + SecurityContextHolder.getContext().getAuthentication().getName());
//        System.out.println("사용자 ROLE: " + role);

        if (!role.equals(UserRole.ADMIN.name())) {
            throw new InsufficientAuthorityException();
        }
    }
}
