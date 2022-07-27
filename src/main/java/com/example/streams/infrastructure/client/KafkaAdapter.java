package com.example.streams.infrastructure.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaAdapter {

	/**
	 * kafkaTemplate
	 */
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * 發送 Message
	 * 
	 * @param topic
	 * @param msg
	 */
	public void sendMessage(String topic, String key, String msg) {
		// prepare message
		Message<String> message = MessageBuilder.withPayload(msg).setHeader(KafkaHeaders.MESSAGE_KEY, key)
				.setHeader(KafkaHeaders.TOPIC, topic).build();

		// callback
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("Publish message to topic:{} ,message key:{} ,message:{},offset:{}", topic, key, msg,
						result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				log.info("Unable to send message to topic:{},message key:{},message:{} ,due to:{}", topic, key, msg,
						ex.getMessage());
			}
		});

	}

	/**
	 * 發送 Message （指定 Partion ID)
	 * 
	 * @param topic
	 * @param msg
	 * @param partionId
	 */
	public void sendMessage(String topic, String key, String msg, int partionId) {
		// prepare message
		Message<String> message = MessageBuilder.withPayload(msg).setHeader(KafkaHeaders.TOPIC, topic)
				.setHeader(KafkaHeaders.MESSAGE_KEY, key).setHeader(KafkaHeaders.PARTITION_ID, partionId).build();

		// callback
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("Publish message to topic:{},partionId:{},message key:{},message:{},offset:{}", topic,
						partionId, key, msg, result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				log.info("Unable to send message to topic:{},partionId:{},message key:{},message:{} ,due to:{}", topic,
						partionId, key, msg, ex.getMessage());
			}
		});

	}
}
