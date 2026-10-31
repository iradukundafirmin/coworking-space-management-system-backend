package com.coworking.coworkingspacemanagementsystem.controller;

import com.coworking.coworkingspacemanagementsystem.entity.UsageCharge;
import com.coworking.coworkingspacemanagementsystem.service.UsageChargeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usage-charges")
public class UsageChargeController {

    private final UsageChargeService usageChargeService;

    public UsageChargeController(UsageChargeService usageChargeService) {
        this.usageChargeService = usageChargeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUsageCharge(
            @RequestParam UUID deskBookingId,
            @RequestBody UsageCharge usageCharge) {

        String result = usageChargeService.saveUsageCharge(usageCharge, deskBookingId);

        if (result.equals("The desk booking with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if (result.equals("Usage charges are only for ENDED desk bookings")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }

    @GetMapping("/member/{memberCode}")
    public ResponseEntity<List<UsageCharge>> getChargesByMemberCode(
            @PathVariable String memberCode) {

        return new ResponseEntity<>(
                usageChargeService.getChargesByMemberCode(memberCode),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUsageCharge(@PathVariable UUID id) {

        String result = usageChargeService.deleteUsageCharge(id);

        if (result.equals("The usage charge with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}