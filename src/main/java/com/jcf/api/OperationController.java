package com.jcf.api;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.service.OperationServiceImpl;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;
import com.jcf.vo.RequestOpFilterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/operations")

public class OperationController {


    private final OperationServiceImpl operationService;

    @Autowired
    public OperationController(OperationServiceImpl operationService) {
        this.operationService = operationService;
    }


   @PostMapping()
   public ResponseEntity<Operation> createOperation(@RequestBody OperationDTO operationDTO) {
       final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/operations/save").toUriString());
       return ResponseEntity.created(uri).body(operationService.saveOperation(operationDTO));
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

//    @GetMapping()
//    public ResponseEntity<List<OperationVO>> getOperations() {
//        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        return ResponseEntity.ok(operationService.findOperations(userEmail));
//    }


//    @GetMapping("/{startDate}/{endDate}")
//    public ResponseEntity<List<OperationVO>> getOperationsByDate(@PathVariable("startDate") String startDate,
//                                                            @PathVariable("endDate") String endDate) {
//
//        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
//
//        FilteredOperationDto filter = new FilteredOperationDto();
//        filter.setFirstDate(Instant.now());
//        filter.setSecondDate(Instant.now());
//
//        return ResponseEntity.ok(operationService.findOperationsByFilter(userEmail, filter));
//    }
//
//    @GetMapping("/{opType}")
//    public ResponseEntity<List<OperationVO>> getOperationsByType(@PathVariable("opType") String type) {
//
//        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        FilteredOperationDto filter = new FilteredOperationDto();
//        filter.setType(type);
//
//        return ResponseEntity.ok(operationService.findOperationsByFilter(userEmail, filter));
//    }
//
//    @GetMapping("/{opCategory}")
//    public ResponseEntity<List<OperationVO>> getOperationsByCategory(@PathVariable("opCategory") String category) {
//
//        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        FilteredOperationDto filter = new FilteredOperationDto();
//        filter.setCategory(category);
//
//        return ResponseEntity.ok(operationService.findOperationsByFilter(userEmail, filter));
//    }

}
