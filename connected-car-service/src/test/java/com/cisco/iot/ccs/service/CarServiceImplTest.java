package com.cisco.iot.ccs.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;

import com.cisco.iot.ccs.dao.CarDao;
import com.cisco.iot.ccs.model.Car;

public class CarServiceImplTest {

	// activate the strict subs rule
	@Rule
	public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

	@Mock
	private CarDao carDao;

	@Mock
	Page<Car> pageM;

	@InjectMocks
	private CarServiceImpl carService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreate() {
		Car carE = new Car();
		String make = "audi";
		carE.setMake(make);
		Mockito.when(carDao.save(ArgumentMatchers.argThat(new CarArgumentMatchers(carE))))
				.then(new Answer<Car>() {
					@Override
					public Car answer(InvocationOnMock invocation) throws Throwable {
						Car ent = (Car) invocation.getArgument(0);
						ent.setId(Long.valueOf(ent.getMake().hashCode()));
						return ent;
					}
				});

		Car car = new Car();
		car.setMake(make);
		car = carService.create(car);
		assertEquals(make.hashCode(), car.getId().longValue());
		verify(carDao, Mockito.times(1)).save(any(Car.class));
	}

	@Test
	public void testGetById() {
		Car carE = new Car();
		String make = "bmw";
		carE.setMake(make);
		long id = 123L;
		Mockito.when(carDao.findById(id)).thenReturn(Optional.of(carE));

		Car car = carService.get(id);
		assertEquals(make, car.getMake());
		verify(carDao, Mockito.times(1)).findById(id);
	}

	@Test
	public void testGetWithFieldsAndMakeNull() {
		String make = "bmw";
		String[] fields = { "make,model" };
		int pageNumber = 0;
		int pageSize = 10;
		boolean distinct = false;
		Car carE = new Car();
		carE.setMake(make);
		Mockito.when(pageM.getContent()).thenReturn(Arrays.asList(carE));
		//Mockito.when(carDao.findAll()).thenReturn(pageM);

		com.cisco.iot.ccs.model.Page<Car> page = carService.get(pageSize, pageNumber);
		assertEquals(1, page.getData().size());
		assertEquals(make, page.getData().get(0).getMake());
		//verify(carDao, Mockito.times(1)).findAll(pageSize, pageNumber);
	}

	private static class CarArgumentMatchers implements ArgumentMatcher<Car> {

		private Car left;

		public CarArgumentMatchers(Car left) {
			this.left = left;
		}

		@Override
		public boolean matches(Car right) {
			return EqualsBuilder.reflectionEquals(left, right);
		}
	}
}
