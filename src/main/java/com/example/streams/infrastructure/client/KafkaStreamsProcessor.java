package com.example.streams.infrastructure.client;

import java.math.BigDecimal;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.streams.infrastructure.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaStreamsProcessor {

	/**
	 * STRING_SERDE
	 */
	private static final Serde<String> STRING_SERDE = Serdes.String();

	/**
	 * DOUBLE_SERDE
	 */
	private static final Serde<Double> DOUBLE_SERDE = Serdes.Double();

	@Autowired
	public void process(StreamsBuilder builder) {
		// KStream
		KStream<String, String> streams = builder.stream("txn", Consumed.with(STRING_SERDE, STRING_SERDE));

		// KGroupStream
		KGroupedStream<String, Double> groupStreams = streams.map((key, value) -> {
			JsonNode jsonNode = JsonUtil.stringToJsonNode(value);
			String action = jsonNode.get("action").asText();
			BigDecimal instructedAmount = BigDecimal.valueOf(jsonNode.get("instructedAmount").asDouble());
			BigDecimal amount = action.equals("DEPOSIT") ? instructedAmount : instructedAmount.negate();
			return KeyValue.pair(key, amount.doubleValue());
		}).groupBy((k, v) -> k, Grouped.with(STRING_SERDE, DOUBLE_SERDE));

		// KTable
		KTable<String, Double> streamsTable = groupStreams.reduce(Double::sum, Materialized.as("balance"));
		streamsTable.toStream().foreach((k, v) -> {
			log.info("KTable [Balance] key:{},value:{}", k, v);
		});
	}

}
