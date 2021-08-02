package com.jcf.api;

import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.model.dto.OperationDTO;
import com.jcf.service.OperationService;
import com.jcf.service.OperationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/operations")

public class OperationController {


    private final OperationServiceImpl operationService;

    @Autowired
    public OperationController(OperationServiceImpl operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> createOperation(@RequestBody OperationDTO operationDTO) {
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/operations/save").toUriString());
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.created(uri).body(operationService.saveOperation(userEmail, operationDTO));
    }
}
