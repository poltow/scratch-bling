package com.itscout.domain.service;

import java.util.List;

import com.itscout.domain.model.BackScratcher;
import com.itscout.exception.GenericException;

public interface BackScratcherService {

    public List<BackScratcher> getAll() throws GenericException;

    public BackScratcher getById(Long id) throws GenericException;

    public BackScratcher getByName(String name) throws GenericException;

    public BackScratcher create(BackScratcher BackScratcher) throws GenericException;

    public BackScratcher update(BackScratcher BackScratcher) throws GenericException;

    public void delete(Long id) throws GenericException;


}