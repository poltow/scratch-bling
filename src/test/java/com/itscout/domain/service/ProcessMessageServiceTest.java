package com.itscout.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.itscout.Application;
import com.itscout.domain.model.Message;
import com.itscout.domain.service.impl.ProcessMessageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProcessMessageServiceTest {

	@InjectMocks
	private ProcessMessageServiceImpl messageService;

	@Test
	public void processMessage() {
		String messageTest = "TEST";
		Message message = messageService.processMessage(messageTest);

		assertNotNull(message);
		assertEquals(message.getMessage(), "ECHO: " + messageTest);
	}
}
