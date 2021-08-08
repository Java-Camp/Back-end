package com.jcf.api;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.service.OperationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/operations")

public class OperationController {


    private final OperationServiceImpl operationService;

    @Autowired
    public OperationController(OperationServiceImpl operationService) {
        this.operationService = operationService;
    }

   /*
   @PostMapping("/save")
    public ResponseEntity<Operation> createOperation(@RequestBody OperationDTO operationDTO) {
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/operations/save").toUriString());
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
       return ResponseEntity.created(uri).body(operationService.saveOperation(operationDTO));
    }
    */
   @PostMapping("/save")
   public ResponseEntity<Operation> createOperation(@RequestBody OperationDTO operationDTO) {
       final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/operations/save").toUriString());
       return ResponseEntity.created(uri).body(operationService.saveOperation(operationDTO));
   }

    @GetMapping("/findAll")
    public ResponseEntity<List<Operation>> findAll() {
        return ResponseEntity.ok(operationService.findAll());
    }
}
