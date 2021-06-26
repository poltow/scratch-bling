package com.itscout.domain.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itscout.domain.model.BackScratcher;
import com.itscout.domain.repository.BackScratcherRepository;
import com.itscout.domain.service.BackScratcherService;
import com.itscout.exception.EntityNotDeletedException;
import com.itscout.exception.EntityNotFoundException;
import com.itscout.exception.EntityNotSavedException;
import com.itscout.exception.GenericException;

@Service
@Transactional
public class BackScratcherServiceImpl implements BackScratcherService {

    private static final Logger logger = LogManager.getLogger(BackScratcherServiceImpl.class);

    @Autowired
    private BackScratcherRepository backScratcherRepository;

    @Override
    public BackScratcher create(BackScratcher backScratcher) throws GenericException {
        logger.info("Creating the backScratcher {}", backScratcher.toString());

        BackScratcher newBackScratcher = backScratcherRepository.save(backScratcher);
        validateSaveBackScratcherResult(newBackScratcher, String.format("Error during creating the backScratcher %s", backScratcher));

        logger.info("BackScratcher {} successfully created", newBackScratcher.getId());
        return newBackScratcher;
    }

    @Override
    public BackScratcher update(BackScratcher backScratcher) throws GenericException {
        logger.info("Updating the backScratcher {}", backScratcher.toString());

        validateBackScratcherExists(backScratcher.getId());
        BackScratcher updatedBackScratcher = backScratcherRepository.save(backScratcher);
        validateSaveBackScratcherResult(updatedBackScratcher, String.format("Error during updating the backScratcher %s", backScratcher));

        logger.info("BackScratcher {} successfully updated", backScratcher.getId());
        return updatedBackScratcher;
    }

    private void validateSaveBackScratcherResult(BackScratcher backScratcher, String msg) throws GenericException {
        if (backScratcher == null) {
            logger.error(msg);
            throw new EntityNotSavedException(msg);
        }
    }

    @Override
    public void delete(Long id) throws GenericException {
        logger.info("Deleging the backScratcher {}", id);

        validateBackScratcherExists(id);

        try {
            backScratcherRepository.deleteById(id);
            logger.info("BackScratcher {} successfully deleted", id);
        } catch (Exception e) {
            String msg = String.format("Error during deleting the backScratcher %s \n %s", id, e.getMessage());
            logger.error(msg);
            throw new EntityNotDeletedException(msg);
        }
    }

    private void validateBackScratcherExists(Long id) throws GenericException {
        if (!backScratcherRepository.existsById(id)) {
            String msg = String.format("Can't find the backScratcher %s", id);
            logger.error(msg);
            throw new EntityNotFoundException(msg);
        }
    }

    @Override
    public List<BackScratcher> getAll() {
        logger.info("Retrieving all backScratchers");

        List<BackScratcher> result = (List<BackScratcher>) backScratcherRepository.findAll();

        logger.info("All backScratchers result: " + result.toString());
        return result;
    }

    @Override
    public BackScratcher getById(Long id) throws GenericException {
        logger.info("Retrieving backScratcher {}", id);

        Optional<BackScratcher> backScratcher = backScratcherRepository.findById(id);
        validateGetBackScratcherResult(String.valueOf(id), backScratcher);

        logger.info("Returning backScratcher: " + backScratcher.orElseThrow().toString());
        return backScratcher.orElseThrow();
    }

    @Override
    public BackScratcher getByName(String name) throws GenericException {
        logger.info("Retrieving backScratcher {}", name);

        Optional<BackScratcher> backScratcher = backScratcherRepository.getByName(name);
        validateGetBackScratcherResult(name, backScratcher);

        logger.info("Returning backScratcher: " + backScratcher.orElseThrow().toString());
        return backScratcher.orElseThrow();
    }

    private void validateGetBackScratcherResult(String backScratcherValue, Optional<BackScratcher> backScratcher) throws EntityNotFoundException {
        if (!backScratcher.isPresent()) {
            String msg = String.format("Error retrieving the backScratcher %s", backScratcherValue);
            logger.error(msg);
            throw new EntityNotFoundException(msg);
        }
    }

}