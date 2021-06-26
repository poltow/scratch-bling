package com.itscout.app.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.itscout.domain.model.BackScratcher;
import com.itscout.domain.model.Size;
import com.itscout.domain.service.BackScratcherService;
import com.itscout.exception.GenericException;

@RestController
@RequestMapping(path = "/backscratchers")
public class BackScratcherController {

	@Autowired
	private BackScratcherService backScratcherService;

	// GET
	@GetMapping(path = "/")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<BackScratcher>> findAll() throws GenericException {
		return new ResponseEntity<>(backScratcherService.getAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<BackScratcher> findById(@PathVariable("id") Long id) throws GenericException {
		return new ResponseEntity<>(backScratcherService.getById(id), HttpStatus.OK);
	}

	@GetMapping(path = "/name/{name}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<BackScratcher> findByName(@PathVariable("name") String name) throws GenericException {
		return new ResponseEntity<>(backScratcherService.getByName(name), HttpStatus.OK);
	}

	// POST
	@PostMapping("/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<BackScratcher> save(@Valid @NotNull @RequestBody BackScratcher backScratcher)
			throws GenericException {
		backScratcher.setSizes(validateSizeValue(backScratcher.getSizes()));
		return new ResponseEntity<>(backScratcherService.create(backScratcher), HttpStatus.CREATED);
	}

	// PUT
	@PutMapping(path = "{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<BackScratcher> update(@PathVariable("id") Long id,
			@Valid @NotNull @RequestBody BackScratcher backScratcher) throws GenericException {
		backScratcher.setSizes(validateSizeValue(backScratcher.getSizes()));
		backScratcher.setId(id);
		return new ResponseEntity<>(backScratcherService.update(backScratcher), HttpStatus.OK);
	}

	// DELETE
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<BackScratcher> remove(@PathVariable("id") Long id) throws GenericException {
		backScratcherService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private String[] validateSizeValue(String[] sizes) {
		if(sizes == null || sizes.length==0){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Sizes should not be empty");
		}
		Set<String> values = new HashSet<>();
		for (String size : sizes) {
			if (!Size.isValid(size)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Size value should be one of " + Size.values());
			} else {
				values.add(size);
			}
		}
		return  values.toArray(new String[values.size()]);
	}

}