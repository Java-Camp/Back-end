package com.jcf.api;

import com.jcf.persistence.model.DateTest;
import com.jcf.persistence.repository.DateTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class DateTestController {
    private final DateTestRepository dateTestRepository;

    @PostMapping("/date/test")
    public void saveDate(@RequestBody DateTest date) {
        dateTestRepository.saveOrUpdate(date);
    }
}
