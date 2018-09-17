package com.pickme.anomality.services;

import com.pickme.anomality.BankresponseApplication;
import com.pickme.anomality.services.SendEmail;
import com.pickme.anomality.entity.Response;
import com.pickme.anomality.dao.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

@Service
@PropertySources({
        @PropertySource(value = {"classpath:application.properties"}),
        @PropertySource(value = "file:/opt/bank-settlements/config/application.properties", ignoreResourceNotFound = true)
})
public class Startup implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    SendEmail sendEmail;

    @Value("${time.interval}")
    int time_interval;

    private long specifiedTime;

    private long currentTime;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {



        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                specifiedTime();
                BankresponseApplication.getLogger().info("Timer is running!");

                 if (allRequestFailed(Startup.this.specifiedTime)){
                     BankresponseApplication.getLogger().info("ALL ARE FAILED BETWEEN "+Startup.this.specifiedTime +" AND "+Startup.this.currentTime);
                     sendEmail.sendingEmail(1,Startup.this.currentTime,Startup.this.specifiedTime);
                }
                if(allReceivedRequestFailed(Startup.this.specifiedTime)){
                     BankresponseApplication.getLogger().info("ALL RECIVED REQUESTS ARE FAILED BETWEEN "+Startup.this.specifiedTime + " AND "+ Startup.this.currentTime);
                    sendEmail.sendingEmail(2,Startup.this.currentTime,Startup.this.specifiedTime);
                }

                if(allUpdatedRequestFailed(Startup.this.specifiedTime)){
                    BankresponseApplication.getLogger().info("ALL UPDATED REQUESTS ARE FAILED BETWEEN "+Startup.this.specifiedTime + " AND "+ Startup.this.currentTime);
                    sendEmail.sendingEmail(3,Startup.this.currentTime,Startup.this.specifiedTime);
                }

            }
        }, 0, 10000);       //it will check for every 10 secs.


    }

    private  boolean allRequestFailed(long time_interval){
        boolean allfailed = false;

        int failedRequests = responseRepository.findCountReceivedFail(time_interval)+responseRepository.findCountUpdatedFail(time_interval);
        int allRequests = responseRepository.findCountinSpecifiedTime(time_interval);
        if((failedRequests == allRequests) && allRequests >0  ){
            allfailed = true;
        }
        return allfailed;
    }
    private  boolean allReceivedRequestFailed(long time_interval){
        boolean allReceivedfailed = false;

        int failedRequests = responseRepository.findCountReceivedFail(time_interval)+responseRepository.findCountUpdatedFail(time_interval);
        int allRequests = responseRepository.findCountinSpecifiedTime(time_interval);
        if((failedRequests == allRequests) && allRequests >0  ){
            allReceivedfailed = true;
        }
        return allReceivedfailed;
    }

    private  boolean allUpdatedRequestFailed(long time_interval){
        boolean allUpdatedfailed = false;

        int failedRequests = responseRepository.findCountUpdatedFail(time_interval);
        int allRequests = responseRepository.findCountinSpecifiedTime(time_interval);
        if((failedRequests == allRequests) && allRequests >0  ){
            allUpdatedfailed = true;
        }
        return allUpdatedfailed;
    }
    private void specifiedTime(){
        this.currentTime = Calendar.getInstance().getTimeInMillis();
        this.specifiedTime =   currentTime - time_interval*60*1000;

    }

}

