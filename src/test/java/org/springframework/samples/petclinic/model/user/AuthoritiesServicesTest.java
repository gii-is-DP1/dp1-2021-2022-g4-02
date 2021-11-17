package org.springframework.samples.petclinic.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import sevenisles.user.Authorities;
import sevenisles.user.AuthoritiesService;
import sevenisles.user.User;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AuthoritiesServicesTest {
	
	@Autowired
	private AuthoritiesService AuthoritiesServices;
	
	
	
	@Test
	public void testCountWithInitialData() {
        int count = AuthoritiesServices.userCount();
        assertEquals(9,count);
    }
	
	@Test
	public void testFindAll() {
		Integer count = AuthoritiesServices.userCount();
		Iterator<User> users = AuthoritiesServices.userFindAll().iterator();
		List<User> userslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(users, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,userslist.size());
	}
	
	
	
	
	
}
