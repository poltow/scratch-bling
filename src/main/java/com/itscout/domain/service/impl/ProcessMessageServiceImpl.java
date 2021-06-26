package com.itscout.domain.service.impl;

import org.springframework.stereotype.Service;

import com.itscout.domain.model.Message;
import com.itscout.domain.service.ProcessMessageService;

@Service
public class ProcessMessageServiceImpl implements ProcessMessageService {

    @Override
    public Message processMessage(String message) {
        return new Message("ECHO: " + message);
    }
}
