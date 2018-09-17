package com.pickme.anomality;

import com.pickme.anomality.services.SendSMS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSTest {

    @Autowired
    SendSMS sendSMS;

    @Test
    public void sendSMS() {
        for (int i =0 ; i<3 ; i++) {
            sendSMS.handleMessage(i+1, 1537178323016L, 1537177323016L);
        }
    }
}
