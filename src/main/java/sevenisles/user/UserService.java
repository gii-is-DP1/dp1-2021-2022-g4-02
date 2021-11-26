/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sevenisles.user;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
    public Iterable<User> userFindAll() {
        return userRepository.findAll();
    }
	
	
	@Transactional(readOnly = true)
	public Page<User> findByUsernamePageable(Pageable pageable){
		return userRepository.findByUsername(pageable);
	}
	@Transactional(readOnly = true)
	public List<User> findAllOrderByUsername(){
		return userRepository.findAllOrderByUsername();
	}
	
	@Transactional(readOnly = true)
    public Integer userCount() {
        return (int) userRepository.count();
    }
	
	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}
	
	@Transactional
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	
	@Transactional(readOnly = true)
	public Optional<User> findUserById(Integer id) {
		return userRepository.findById(id);
	}
	
	
	@Transactional(readOnly = true)
	public Optional<User> findCurrentUser() throws DataAccessException {
		return userRepository.findCurrentUser(User.getCurrentUser());
	}
	
}
