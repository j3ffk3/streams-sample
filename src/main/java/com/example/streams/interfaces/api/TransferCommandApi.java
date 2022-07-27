package com.example.streams.interfaces.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.streams.core.account.entity.AccountEntity;
import com.example.streams.interfaces.converter.TransferConverter;
import com.example.streams.interfaces.dto.GenericResponseDTO;
import com.example.streams.interfaces.dto.TransferRequestDTO;
import com.example.streams.usecase.TransferApplicationService;


@RestController
public class TransferCommandApi {
	
	/**
	 * transferConverter
	 */
	@Autowired
	TransferConverter transferConverter;
	
	/**
	 * transferApplicationService
	 */
	@Autowired
	TransferApplicationService transferApplicationService;

	/**
	 * 轉帳
	 * @param transferRequestDTO
	 * @return
	 */
	@PostMapping(value = "/transfer",consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponseDTO> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
		
		List<AccountEntity> accountEntityList= transferConverter.dtoToDo(transferRequestDTO);
		transferApplicationService.transfer(accountEntityList.get(0),accountEntityList.get(1));
		return new ResponseEntity<>(
				GenericResponseDTO.builder()
					.code(String.valueOf(HttpStatus.OK.value()))
					.message(HttpStatus.OK.name())
					.build(), HttpStatus.OK);
	}
}
