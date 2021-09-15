package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

public class KafkaClient {

	public static void main(String[] args) {

		createTopic("email");
		System.out.println(topicList());
      }
	public static List<String> topicList() {
		Map<String, List<PartitionInfo> > topics;
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(propertiesConfigs());
		topics = consumer.listTopics();
		consumer.close();
		return topics.keySet().stream().collect(Collectors.toList()); 
	}
    public static Properties propertiesConfigs() {
    	Properties props = new Properties();
//		props.put("bootstrap.servers", "prowatch.cats4u.ai:9092,prowatch1.cats4u.ai:9092,prowatch2.cats4u.ai:9092");
    	props.put("bootstrap.servers", "localhost:9092");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
	public static String createTopic(String topic) {
		AdminClient adminClient = AdminClient.create(propertiesConfigs());
		NewTopic newTopic = new NewTopic(topic, 1, (short)1);
		List<NewTopic> newTopics = new ArrayList<NewTopic>();
		newTopics.add(newTopic);
		adminClient.createTopics(newTopics);
		adminClient.close();
		return topic;
	}

}
