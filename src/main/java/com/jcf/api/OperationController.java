package com.jcf.api;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.service.OperationServiceImpl;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;
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

    @PostMapping("/{accountId}")
    public ResponseEntity<List<OperationVO>> getOperationsByFilter(@PathVariable("accountId") String accountId,
                                                                   @RequestBody FilteredOperationDto filter){

//        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
//        FilteredOperationDto filter = new FilteredOperationDto();
//        filter.setCategory(reqFilter.getCategory());
//        filter.setType(reqFilter.getType());
//        filter.setFirstDate(LocalDateTime.parse(reqFilter.getFirstDate(), dateTimeFormatter));
//        filter.setSecondDate(LocalDateTime.parse(reqFilter.getSecondDate(), dateTimeFormatter));

        return ResponseEntity.ok(operationService.findOperationsByFilter(Long.parseLong(accountId), filter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        operationService.delete(id);
        return ResponseEntity.ok("Entity was deleted");
    }
}
