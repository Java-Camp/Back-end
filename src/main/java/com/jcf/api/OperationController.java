package com.jcf.api;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.service.OperationServiceImpl;
import com.jcf.vo.*;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

   @GetMapping("/{accountId}")
   public ResponseEntity<List<SpecialOperationVo>> getOperationsByCurrentDate(@PathVariable("accountId") String accountId){
        return ResponseEntity.ok(operationService.getOperationsByCurrentDate(Long.parseLong(accountId)));
   }

    @PostMapping("/{accountId}")
    public ResponseEntity<List<OperationVO>> getOperationsByFilter(@PathVariable("accountId") String accountId,
                                                                   @RequestBody FilteredOperationDto filter){



        return ResponseEntity.ok(operationService.findOperationsByFilter(Long.parseLong(accountId), filter));
    }

    @PostMapping("/{accountId}/getByRange")
    public ResponseEntity<List<ChartOperationVO>> getOperationsTypeSumByDate(@PathVariable("accountId") String accountId,
                                                                              @RequestBody DateFilteredOperationVO filter){



        return ResponseEntity.ok(operationService.getOperationsTypeSumByDate(Long.parseLong(accountId), filter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        operationService.delete(id);
        return ResponseEntity.ok("Entity was deleted");
    }
}
