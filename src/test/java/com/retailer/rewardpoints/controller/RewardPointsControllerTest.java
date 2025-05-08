package com.retailer.rewardpoints.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.retailer.rewardpoints.controller.RewardPointsController;
import com.retailer.rewardpoints.dto.RewardPointsDto;
import com.retailer.rewardpoints.service.RewardPointService;

class RewardPointsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RewardPointService rewardService;

    @InjectMocks
    private RewardPointsController rewardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();
    }

    @Test
    void testGetRewardPoints() throws Exception {
        Long customerId = 1L;
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 31);

        RewardPointsDto rewardPointsDto = new RewardPointsDto();
        rewardPointsDto.setMonthlyPoints(Map.of("JANUARY", 90, "FEBRUARY", 30));
        rewardPointsDto.setTotalPoints(120);

        when(rewardService.getRewardPoints(customerId, startDate, endDate)).thenReturn(rewardPointsDto);

        mockMvc.perform(get("/rewards/points/{customerId}", customerId)
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyPoints.JANUARY").value(90))
                .andExpect(jsonPath("$.monthlyPoints.FEBRUARY").value(30))
                .andExpect(jsonPath("$.totalPoints").value(120));
    }

    @Test
    void testGetRewardPoints_InvalidDateRange() throws Exception {
        Long customerId = 1L;
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);

        when(rewardService.getRewardPoints(customerId, startDate, endDate))
                .thenThrow(new IllegalArgumentException("start date must be earlier than the end date."));

        mockMvc.perform(get("/rewards/points/{customerId}", customerId)
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("start date must be earlier than the end date."));
    }
}
