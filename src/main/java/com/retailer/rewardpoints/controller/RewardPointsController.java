package com.retailer.rewardpoints.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardpoints.dto.ResponseDto;
import com.retailer.rewardpoints.dto.RewardPointsDto;
import com.retailer.rewardpoints.service.RewardPointService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for handling reward points related requests. Provides endpoints to
 * points for a specific customer or all customers within a date
 * range.
 */
@RestController
@RequestMapping("/rewards")
@Slf4j
public class RewardPointsController {

    @Autowired
    private RewardPointService rewardService;

    /**
     * Endpoint to get reward points for a specific customer within a date range.
     */
    
	@GetMapping("/points/{customerId}")
	public ResponseEntity<ResponseDto> getRewardPoints(@PathVariable Long customerId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		
			RewardPointsDto points = rewardService.getRewardPoints(customerId, startDate, endDate);
			
			 ResponseDto responseDto = new ResponseDto();
			    responseDto.setRewardPointsDto(points);
			    responseDto.setStatus("success");
		
		     return ResponseEntity.ok(responseDto);
	}
}
