package com.pickme.anomality;

import com.pickme.anomality.entity.Response;
import com.pickme.anomality.dao.ResponseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BankresponseApplicationTests {

	@Autowired
	ResponseRepository responseRepository;
	@Test
	public void contextLoads() {
	}


	@Test
	public void testinsertResponse(){
		int transection_id = 1;
		long created_time = 123456789;
		String status = "4";
		responseRepository.insertResponse(transection_id,created_time,status);
		Response newResponse =  responseRepository.findById(transection_id);
		//Assert.assertEquals(created_time,newResponse.getCreated_time());
	}

@Test
	public void testFindAll(){	//Now db doess not have any data, in order to find all the data.
		int transection_id = 1;
		long created_time = 123456789;
		String status = "4";

		responseRepository.findAll(); // as for now it can't be tested.
}

}
