package com.example.streams.usecase;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.streams.core.account.entity.AccountEntity;
import com.example.streams.infrastructure.client.KafkaAdapter;

@Service
public class TransferApplicationService {

	/**
	 * kafkaAdapter
	 */
	@Autowired
	private KafkaAdapter kafkaAdapter;
	
	@Value(value = "${sample.transaction_topic}")
	private String transactionTopic;

	/**
	 * 轉帳
	 * @param fromAccount
	 * @param toAccount
	 */
	@Transactional
	public void transfer(AccountEntity fromAccount, AccountEntity toAccount) {
		// 扣款
		fromAccount.withdraw();
		kafkaAdapter.sendMessage(transactionTopic, fromAccount.getAccountId(), fromAccount.toString());
		// 存款
		toAccount.deposit();
		kafkaAdapter.sendMessage(transactionTopic, toAccount.getAccountId(), toAccount.toString());

	}
}
