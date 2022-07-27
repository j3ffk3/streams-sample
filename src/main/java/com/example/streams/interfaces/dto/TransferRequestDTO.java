package com.example.streams.interfaces.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransferRequestDTO {

	/**
	 * 付款帳戶 ID
	 */
	private String accountId;
	
	/**
	 * 收款帳戶 ID
	 */
	private String toAccountId;

	/**
	 * 指示金額
	 */
	private BigDecimal instructedAmount;
	

}
