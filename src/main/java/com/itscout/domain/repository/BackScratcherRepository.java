package com.itscout.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.itscout.domain.model.BackScratcher;

public interface BackScratcherRepository extends JpaRepository<BackScratcher, Long> {

	Optional<BackScratcher> getByName(String name);

}
