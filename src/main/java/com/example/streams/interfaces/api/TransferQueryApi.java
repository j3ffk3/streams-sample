package com.example.streams.interfaces.api;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.streams.interfaces.dto.GenericResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TransferQueryApi {

	/**
	 * StreamsBuilderFactoryBean
	 */
	private final StreamsBuilderFactoryBean factoryBean;

	/**
	 * 查詢餘額
	 * @param accountId
	 * @return
	 */
	@GetMapping(value = "/balance/{accountId}", produces = "application/json")
	public ResponseEntity<GenericResponseDTO> getBalance(@PathVariable("accountId") String accountId) {
		// TODO
		final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
		final ReadOnlyKeyValueStore<String, Long> balance = kafkaStreams
				.store(StoreQueryParameters.fromNameAndType("balance", QueryableStoreTypes.keyValueStore()));
		return new ResponseEntity<>(GenericResponseDTO.builder().code(String.valueOf(HttpStatus.OK.value()))
				.message(HttpStatus.OK.name()).data(balance.get(accountId)).build(), HttpStatus.OK);
	}

}
