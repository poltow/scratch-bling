package com.itscout.domain.service;

import com.itscout.domain.model.BackScratcher;
import com.itscout.domain.repository.BackScratcherRepository;
import com.itscout.domain.service.impl.BackScratcherServiceImpl;
import com.itscout.exception.EntityNotDeletedException;
import com.itscout.exception.EntityNotFoundException;
import com.itscout.exception.EntityNotSavedException;
import com.itscout.exception.GenericException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BackScratcherServiceTest {

	@InjectMocks
	private BackScratcherServiceImpl backScratcherService;

	@Mock
	private BackScratcherRepository backScratcherRepository;

	private BackScratcher backScratcher = new BackScratcher(-1L, "NAME", "DESC", new String[]{"S", "L"}, 10.20d);

	@BeforeEach
	void setup() {
	}

	@Nested
	class CreateTest {

		@Test
		@DisplayName("Should save and return the persisted entity")
		void createAndReturnBackScratcher() throws GenericException {
			BackScratcher resultBackScratcher = new BackScratcher();

			when(backScratcherRepository.save(any(BackScratcher.class))).thenReturn(resultBackScratcher);

			BackScratcher result = backScratcherService.create(backScratcher);
			assertEquals(resultBackScratcher, result);
		}

		@Test
		@DisplayName("Should throw exception when not returning persisted entity")
		void createAndReturnNull() throws GenericException {

			when(backScratcherRepository.save(any(BackScratcher.class))).thenReturn(null);

			assertThrows(EntityNotSavedException.class, () -> backScratcherService.create(backScratcher));
		}
	}

	@Nested
	class UpdateTest {

		@Test
		@DisplayName("Should save and return the updated entity")
		void updateAndReturnBackScratcher() throws GenericException {

			BackScratcher resultBackScratcher = new BackScratcher();
			when(backScratcherRepository.existsById(anyLong())).thenReturn(true);
			when(backScratcherRepository.save(any(BackScratcher.class))).thenReturn(resultBackScratcher);

			Assertions.assertEquals(resultBackScratcher, backScratcherService.update(backScratcher));
		}

		@Test
		@DisplayName("Should throw exception when not reurning updated entity")
		void updateAndReturnNull() throws GenericException {

			when(backScratcherRepository.existsById(anyLong())).thenReturn(true);
			when(backScratcherRepository.save(backScratcher)).thenReturn(null);

			assertThrows(EntityNotSavedException.class, () -> backScratcherService.update(backScratcher));
		}

		@Test
		@DisplayName("Should throw exception when cant find entity to update")
		void updateCantFindBackScratcher() throws GenericException {

			when(backScratcherRepository.existsById(anyLong())).thenReturn(false);

			assertThrows(EntityNotFoundException.class, () -> backScratcherService.update(backScratcher));
		}
	}

	@Nested
	class DeleteTest {

		@Test
		@DisplayName("Should remove the entity from repository")
		void deleteBackScratcher() throws GenericException {

			when(backScratcherRepository.existsById(anyLong())).thenReturn(true);
			doNothing().when(backScratcherRepository).deleteById(anyLong());
			backScratcherService.delete(-1L);
			verify(backScratcherRepository, times(1)).deleteById(anyLong());
		}

		@Test
		@DisplayName("Should throw exception if error during delete")
		void errorDuringDelete() throws GenericException {

			when(backScratcherRepository.existsById(anyLong())).thenReturn(true);
			doThrow(new RuntimeException()).when(backScratcherRepository).deleteById(anyLong());

			assertThrows(EntityNotDeletedException.class, () -> backScratcherService.delete(-1L));
		}

		@Test
		@DisplayName("Should throw exception when cant find entity to delete")
		void deleteCantFindBackScratcher() throws GenericException {

			when(backScratcherRepository.existsById(anyLong())).thenReturn(false);

			assertThrows(EntityNotFoundException.class, () -> backScratcherService.delete(-1L));
		}
	}

	@Nested
	class GetTest {

		@Test
		@DisplayName("Should return all the entitites")
		void getAllBackScratchers() throws GenericException {

			List<BackScratcher> backScratchers = new ArrayList<>();

			when(backScratcherRepository.findAll()).thenReturn(backScratchers);

			assertEquals(backScratchers, backScratcherService.getAll());
		}

		@Test
		@DisplayName("Should return entity")
		void getBackScratcherById() throws GenericException {

			BackScratcher resultBackScratcher = new BackScratcher();
			when(backScratcherRepository.findById(anyLong())).thenReturn(Optional.of(resultBackScratcher));

			Assertions.assertEquals(resultBackScratcher, backScratcherService.getById(-1L));
		}

		@Test
		@DisplayName("Should throw exception when cant find entity")
		public void getByIdCantFindBackScratcher() throws GenericException {

			when(backScratcherRepository.findById(anyLong())).thenReturn(Optional.empty());

			assertThrows(EntityNotFoundException.class, () -> backScratcherService.getById(-1L));
		}

		@Test
		@DisplayName("Should return entity")
		void getBackScratcherByName() throws GenericException {

			BackScratcher resultBackScratcher = new BackScratcher();
			when(backScratcherRepository.getByName(anyString())).thenReturn(Optional.of(resultBackScratcher));

			Assertions.assertEquals(resultBackScratcher, backScratcherService.getByName("Mesa"));
		}

		@Test
		@DisplayName("Should throw exception when cant find entity")
		public void getByNameCantFindBackScratcher() throws GenericException {

			when(backScratcherRepository.getByName(anyString())).thenReturn(Optional.empty());

			assertThrows(EntityNotFoundException.class, () -> backScratcherService.getByName("Mesa"));
		}

	}
}
