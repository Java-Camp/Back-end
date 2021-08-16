package com.jcf.api;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.service.OperationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/operations")

public class OperationController {

    private final OperationServiceImpl operationService;

    @Autowired
    public OperationController(OperationServiceImpl operationService) {
        this.operationService = operationService;
    }

    @PutMapping("")
    public ResponseEntity<Operation> updateOperation(@RequestBody OperationDTO operationDTO) {
        return ResponseEntity.ok(operationService.updateOperation(operationDTO));
    }

    @PostMapping("")
    public ResponseEntity<Operation> createOperation(@RequestBody OperationDTO operationDTO) {
        return ResponseEntity.ok(operationService.saveOperation(operationDTO));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Operation>> findAll() {
        return ResponseEntity.ok(operationService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        operationService.delete(id);
        return ResponseEntity.ok("Entity was deleted");
    }
}
