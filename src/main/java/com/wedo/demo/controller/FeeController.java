package com.wedo.demo.controller;

import com.wedo.demo.domain.fee.Fee;
import com.wedo.demo.domain.fee.FeeContext;
import com.wedo.demo.domain.fee.service.FeeServiceImpl;
import com.wedo.demo.dto.common.R;
import com.wedo.demo.dto.fee.FeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/fee", "FEE"})
public class FeeController {
    private static final Logger logger = LoggerFactory.getLogger(FeeController.class);
    @Autowired
    private FeeServiceImpl feeService;

    @GetMapping("/{id}")
    public R<FeeDto> get(@PathVariable("id") Long id) {
        FeeDto dto = feeService.get(id, FeeDto::new);

        return R.success(dto);
    }

    @PostMapping("/save")
    public R<FeeDto> save(@RequestBody FeeDto dto) {
        try {
            logger.debug("save {}", dto);
            FeeDto res = feeService.factory(FeeContext.current(), dto.getId(), builder -> {
                updateValues(dto, builder);
            }, FeeDto::new);
            return R.success(res);
        } catch (RuntimeException ex) {
            logger.warn("failed to save fee {}", dto, ex);
            throw ex;
        }
    }

    private static void updateValues(FeeDto dto, Fee.Builder builder) {
        builder.setAmount(dto.getAmount());
        builder.setDeparture(dto.getDeparture());
        builder.setDestination(dto.getDestination());
        builder.setLocation(dto.getLocation());
        builder.setReason(dto.getReason());
        builder.setType(dto.getType());
        builder.setOccurredAt(dto.getOccurredAt());
        builder.setTypeName(dto.getTypeName());
    }

    @PostMapping("/submit")
    public R<FeeDto> submit(@RequestBody FeeDto dto) {
        FeeDto res = feeService.submit(FeeContext.current(), dto.getId(), builder -> {
            updateValues(dto, builder);
        }, FeeDto::new);
        return R.success(res);
    }

}
