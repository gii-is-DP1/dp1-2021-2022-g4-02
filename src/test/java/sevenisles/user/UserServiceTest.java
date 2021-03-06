package sevenisles.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

import sevenisles.user.exceptions.DuplicatedUserNameException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {
	
	@Autowired
	private AuthoritiesService authoritiesServices;
	@Autowired
	private UserService userServices;
	
	User newuser = new User();
	
	
	@BeforeEach
	public void init() throws DataAccessException, DuplicatedUserNameException {
		
		newuser.setUsername("userprueba");
		newuser.setPassword("userprueba");
		newuser.setFirstName("user");
		newuser.setLastName("prueba");
		userServices.saveUser(newuser);
		
		Authorities auth = new Authorities();
		auth.setAuthority("player");
		auth.setUser(newuser);
		authoritiesServices.saveAuthorities(auth);
		
		newuser.setAuthorities(auth);
		userServices.saveUser(newuser);
		
		
	}
	
	@AfterEach
	public void finish() {
		userServices.deleteUser(newuser);
	}
	
	@Test
	public void testCountUserWithInitialData() {
        int count = userServices.userCount();
        assertEquals(12,count);
    }
	
	@Test
	public void testFindAllUser() {
		Integer count = userServices.userCount();
		Iterator<User> users = userServices.userFindAll().iterator();
		List<User> userslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(users, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,userslist.size());
	}
	
	@Test
	public void testFindAllOrderByUsername() {
		Iterator<User> users = userServices.userFindAll().iterator();
		List<User> userslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(users, Spliterator.ORDERED), false).collect(Collectors.toList());
		userslist.sort(Comparator.naturalOrder());
		List<User> list2 = userServices.findAllOrderByUsername();
		assertEquals(list2,userslist);
	}

	@Test
	public void testfindByUsernamePageable() {
		PageRequest request = PageRequest.of(0,5);
		Page<User> page = userServices.findByUsernamePageable(request);
		List<User> users = page.getContent();
		
		List<User> usersExpected = userServices.findAllOrderByUsername();
		
		assertEquals(usersExpected.subList(0, 5),users);
	}
	
	
	@Test
	public void testFindUserById() {
		/* Sabemos que el id= 1 es el del administrador, con username = admin1*/
		int id = 1;
		User user = userServices.findUserById(id).get();
		assertEquals("admin1",user.getUsername());
	}
	
	@Test
	public void TestSaveUser() throws DataAccessException, DuplicatedUserNameException {
		int countinicial= userServices.userCount();
		Authorities auth = new Authorities();
		User user = new User();
		user.setFirstName("Manuel");
		user.setLastName("Gallego");
		user.setPassword("manuelgal");
		user.setUsername("manU");
		userServices.saveUser(user);
		auth.setAuthority("player");
		auth.setUser(user);
		authoritiesServices.saveAuthorities(auth);
		
		
		user.setAuthorities(auth);
		userServices.saveUser(user);
		int countfinal= userServices.userCount();
		assertEquals(countfinal,countinicial+1);
		
		userServices.deleteUser(user);
	}
	
	@Test
	public void testDeleteUser() {
		int countinicial = userServices.userCount();
		userServices.deleteUser(newuser);
		int count = userServices.userCount();
		assertEquals(count,countinicial-1);
	}
	
	@Test
	public void testDeleteUserById() {
		int countinicial = userServices.userCount();
		userServices.deleteUser(newuser.getId());
		int count = userServices.userCount();
		assertEquals(count,countinicial-1);
	}
	
	@Test
	@WithMockUser(username="userprueba",authorities="player")
	public void testFindCurrentUser(){
		Optional<User> user = userServices.findCurrentUser();
		assertTrue(user.isPresent());
		assertEquals(user.get().getUsername(), "userprueba");
	}
	
	@Test
	public void testFindCurrentUserNoUser(){
		Optional<User> user = userServices.findCurrentUser();
		assertTrue(user.isEmpty());
	}
}
