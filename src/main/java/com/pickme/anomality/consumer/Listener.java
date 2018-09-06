package com.pickme.anomality.consumer;

import com.pickme.anomality.BankresponseApplication;
import com.pickme.anomality.dao.ResponseRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
    @Autowired private ResponseRepository responseRepository;


   @Value("${topic}")
   private String topic;


    @KafkaListener(topics = "${topic}")
    public void listen(ConsumerRecord<?,?> record) {

        try {
           // BankresponseApplication.getLogger().info("Kafka consumer is working fine! ");
            JSONObject jsonObject = new JSONObject(record.value().toString());

            JSONObject bodyObject = jsonObject.getJSONObject("body");

            //System.out.println(bodyObject.getInt("driver_id"));

            // responseRepository.insertResponse(bodyObject.getInt("driver_id"),jsonObject.getLong("created_at"),"A");

            //responseRepository.insertResponse();

        }
        catch (Exception e){
            BankresponseApplication.getLogger().error("Kafka consuming error!");
        }

    }
}
