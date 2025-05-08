package com.retailer.rewardpoints.service;

import java.time.LocalDate;

import com.retailer.rewardpoints.dto.RewardPointsDto;

public interface RewardPointService {
	RewardPointsDto getRewardPoints(Long customerId, LocalDate startDate, LocalDate endDate);
}
