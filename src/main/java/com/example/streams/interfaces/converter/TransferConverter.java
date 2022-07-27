package com.example.streams.interfaces.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.streams.core.account.entity.AccountEntity;
import com.example.streams.interfaces.dto.TransferRequestDTO;

@Service
public class TransferConverter {

	/**
	 * DTO to DO
	 * @param transferRequestDTO
	 * @return
	 */
	public List<AccountEntity> dtoToDo(TransferRequestDTO transferRequestDTO) {
		List<AccountEntity> accountEntityList= new ArrayList<>();
		accountEntityList.add(AccountEntity.builder()
				  .accountId(transferRequestDTO.getAccountId())
				  .instructedAmount(transferRequestDTO.getInstructedAmount())
				  .build());
		accountEntityList.add(AccountEntity.builder()
				  .accountId(transferRequestDTO.getToAccountId())
				  .instructedAmount(transferRequestDTO.getInstructedAmount())
				  .build());
		
		return  accountEntityList;
	}
}
