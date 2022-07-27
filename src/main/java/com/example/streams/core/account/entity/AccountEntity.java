package com.example.streams.core.account.entity;

import java.math.BigDecimal;

import com.example.streams.core.account.entity.vo.ActionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccountEntity {

	/**
	 * 帳戶 ID
	 */
	private String accountId;

	/**
	 * 執行動作 提：WITHDRAW; 存：DEPOSIT;
	 */
	private ActionType action;

	/**
	 * 指示金額
	 */
	private BigDecimal instructedAmount;

	/**
	 * 提款
	 * 
	 * @return
	 */
	public AccountEntity withdraw() {
		this.setAction(ActionType.WITHDRAW);
		return this;
	}

	/**
	 * 存款
	 * 
	 * @return
	 */
	public AccountEntity deposit() {
		this.setAction(ActionType.DEPOSIT);
		return this;
	}

	/**
	 * 轉 JSON String
	 */
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
