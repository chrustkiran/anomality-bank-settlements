package com.pickme.anomality.consumer;

import com.pickme.anomality.BankresponseApplication;
import com.pickme.anomality.dao.ResponseRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;


@PropertySources({
        @PropertySource(value = {"classpath:application.properties"}),
        @PropertySource(value = "file:/opt/bank-settlements/config/application.properties", ignoreResourceNotFound = true)
})
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

           responseRepository.insertResponse(bodyObject.getInt("id"),bodyObject.getInt("bank"),bodyObject.getString("transaction_reference_id"),bodyObject.getInt("payment_type"),bodyObject.getLong("payment_type_reference_id"),bodyObject.getInt("status"),bodyObject.getLong("updated_datetime"),bodyObject.getLong("created_datetime"));


        }
        catch (Exception e){
            BankresponseApplication.getLogger().error("Kafka consuming error!");
        }

    }


}
