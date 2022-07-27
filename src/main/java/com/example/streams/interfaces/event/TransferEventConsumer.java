package com.example.streams.interfaces.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransferEventConsumer {


	@KafkaListener(topics = "${sample.transaction_topic}", groupId = "springboot")
	public void listenGroupFoo(@Payload String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
		log.info("Consume message from topic: {}, partition key: {},message key:{} , message:{} ", "txn", partition,
				key, message);
	}
	
	
}
