package com.example.streams;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaStreamsTest {

	/**
	 * Input Topic
	 */
	private static final String INPUT_TOPIC = "txn";

	/**
	 * Output Topic
	 */
	private static final String OUTPUT_TOPIC = "txn-copy";

	/**
	 * Bootstrap Servers
	 */
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";

	/**
	 * Deserializer for message keys.
	 */
	private final Serde<String> keySerde = Serdes.String();

	/**
	 * Serializer for message values
	 */
	private final Serde<String> valueSerde = Serdes.String();

	@Test
	public void streamsTest() throws InterruptedException {
		// TODO: Config Streams
		Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "strems-interation");
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
		streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		// TODO: Create the stream from the "txn" topic
		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, String> stream = builder.stream(INPUT_TOPIC, Consumed.with(keySerde, valueSerde));

		// TODO: use foreach to print each message
		stream.foreach((key, value) -> log.info("key:{},value:{}",key,value));

		// TODO: process the stream and send the result to the "txn-copy" topic
		stream.filter((key, value) -> !value.equals("")).to(OUTPUT_TOPIC, Produced.with(keySerde, valueSerde));

		// TODO: create streams
		final Topology topology = builder.build();
		KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

		// Run KStreams for 30sec
		streams.start();
		Thread.sleep(30000);
		streams.close();
	}
}
