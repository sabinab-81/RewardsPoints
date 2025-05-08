package com.retailer.rewardpoints.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.retailer.rewardpoints.dto.RewardPointsDto;
import com.retailer.rewardpoints.entity.Transaction;
import com.retailer.rewardpoints.repo.TransactionRepository;
import com.retailer.rewardpoints.service.RewardPointServiceImpl;

class RewardPointServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private RewardPointServiceImpl rewardServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
    
    @Test
    public void testCalculatePoints_AmountLessThan50() {
        int points = rewardServiceImpl.calculatePoints(30.0);
        assertEquals(0, points);
    }

    @Test
    public void testCalculatePoints_AmountBetween50And100() {
        int points = rewardServiceImpl.calculatePoints(75.0);
        assertEquals(25, points);
    }

    @Test
    public void testCalculatePoints_AmountGreaterThan100() {
        int points = rewardServiceImpl.calculatePoints(150.0);
        assertEquals(150, points);
    }

    @Test
    public void testCalculateMonthlyPoints() {
        rewardServiceImpl.transactionRepository = transactionRepository;

        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 31);
        Long customerId = 1L;

        Transaction transaction1 = new Transaction(1L, customerId, 120.0, LocalDate.of(2025, 1, 15));
        Transaction transaction2 = new Transaction(2L, customerId, 80.0, LocalDate.of(2025, 2, 20));
        Transaction transaction3 = new Transaction(3L, customerId, 50.0, LocalDate.of(2025, 3, 10));

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        Mockito.when(transactionRepository.findByCustomerIdAndDateBetween(customerId, startDate, endDate)).thenReturn(transactions);

        RewardPointsDto result = rewardServiceImpl.calculateMonthlyPoints(customerId, startDate, endDate);

        assertEquals(3, result.getMonthlyPoints().size());
        assertEquals(90, result.getMonthlyPoints().get("JANUARY"));
        assertEquals(30, result.getMonthlyPoints().get("FEBRUARY"));
        assertEquals(0, result.getMonthlyPoints().get("MARCH"));
        assertEquals(120, result.getTotalPoints());
    }

    @Test
    public void testValidateAndAdjustDates_BothDatesNull() {
        Map<String, Object> result = rewardServiceImpl.validateAndAdjustDates(null, null);
        LocalDate expectedEndDate = LocalDate.now();
        LocalDate expectedStartDate = expectedEndDate.minusMonths(3);
        assertEquals(expectedStartDate, result.get("startDate"));
        assertEquals(expectedEndDate, result.get("endDate"));
    }

    @Test
    public void testValidateAndAdjustDates_StartDateProvided_EndDateNull() {
        LocalDate startDate = LocalDate.of(2025, 4, 29);
        Map<String, Object> result = rewardServiceImpl.validateAndAdjustDates(startDate, null);
        LocalDate expectedEndDate = startDate.plusMonths(3);
        assertEquals(startDate, result.get("startDate"));
        assertEquals(expectedEndDate, result.get("endDate"));
    }

    @Test
    public void testValidateAndAdjustDates_StartDateNull_EndDateProvided() {
        LocalDate endDate = LocalDate.of(2025, 4, 29);
        Map<String, Object> result = rewardServiceImpl.validateAndAdjustDates(null, endDate);
        LocalDate expectedStartDate = endDate.minusMonths(3);
        assertEquals(expectedStartDate, result.get("startDate"));
        assertEquals(endDate, result.get("endDate"));
    }

    @Test
    public void testValidateAndAdjustDates_StartDateAfterEndDate() {
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 29);
        Map<String, Object> result = rewardServiceImpl.validateAndAdjustDates(startDate, endDate);
        assertEquals("start date must be earlier than the end date.", result.get("error"));
    }

    @Test
    public void testValidateAndAdjustDates_DateRangeExceedsThreeMonths() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 29);
        Map<String, Object> result = rewardServiceImpl.validateAndAdjustDates(startDate, endDate);
        assertEquals("Date range should not exceed three months.", result.get("error"));
    }

    @Test
    public void testGetRewardPoints_InvalidDateRange() {
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 29);
        Long customerId = 1L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardServiceImpl.getRewardPoints(customerId, startDate, endDate);
        });

        assertEquals("start date must be earlier than the end date.", exception.getMessage());
    }

    }