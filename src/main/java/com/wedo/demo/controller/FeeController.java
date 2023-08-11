package com.wedo.demo.controller;

import com.wedo.demo.domain.fee.Fee;
import com.wedo.demo.domain.fee.FeeContext;
import com.wedo.demo.domain.fee.entity.FeeEntity;
import com.wedo.demo.domain.fee.service.FeeServiceImpl;
import com.wedo.demo.dto.common.R;
import com.wedo.demo.dto.fee.FeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/fee", "FEE"})
public class FeeController {
    @Autowired
    private FeeServiceImpl feeService;

    @GetMapping("/{id}")
    public R<FeeDto> get(@PathVariable("id") Long id) {
        FeeDto dto = feeService.get(id, FeeDto::new);

        return R.success(dto);
    }

    @PostMapping("/save")
    public R<Long> save(@RequestBody FeeDto dto) {
        Long id = feeService.factory(FeeContext.current(), dto.getId(), builder -> {
            builder.setAmount(dto.getAmount());
            builder.setDeparture(dto.getDeparture());
            builder.setDestination(dto.getDestination());
            builder.setLocation(dto.getLocation());
            builder.setReason(dto.getReason());
            builder.setType(dto.getType());
            builder.setOccurredAt(dto.getOccurredAt());
        }, Fee::getId);
        return R.success(id);
    }

}
