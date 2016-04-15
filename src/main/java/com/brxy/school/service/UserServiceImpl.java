package com.brxy.school.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brxy.school.model.User;
import com.brxy.school.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	/**
	 * The <code>CounterService</code> captures metrics for Spring Actuator.
	 */
	@Autowired
	private CounterService counterService;

	@Override
	public List<User> findAll() {
		logger.info("> findAll");

		counterService.increment("method.invoked.userRepository.findAll");

		List<User> users = userRepository.findAll();

		logger.info("< findAll");
		return users;
	}

	@Cacheable(value = "users", key = "#id")
	@Override
	public User findOne(Long id) {
		logger.info("> findOne {}", id);

		counterService.increment("method.invoked.userServiceImpl.findOne");

		User user = userRepository.findOne(id);

		logger.info("< findOne {}", id);
		return user;
	}

	@CachePut(value = "users", key = "#result.id")
	@Override
	@Transactional
	public User create(User user) {
		logger.info("> create");

		counterService.increment("method.invoked.userServiceImpl.create");

		// Ensure the entity object to be created does NOT exist in the
		// repository. Prevent the default behavior of save() which will update
		// an existing entity if the entity matching the supplied id exists.
		if (user.getId() != null) {
			logger.error("Attempted to create a User, but id attribute was not null.");
			logger.info("< create");
			throw new EntityExistsException(
					"Cannot create new User with supplied id.  The id attribute must be null to create an entity.");
		}

		User savedUser = userRepository.save(user);

		logger.info("< create");
		return savedUser;
	}

	@CachePut(value = "users", key = "#user.id")
	@Override
	@Transactional
	public User update(User user) {
		logger.info("> update {}", user.getId());

		counterService.increment("method.invoked.userServiceImpl.update");

		// Ensure the entity object to be updated exists in the repository to
		// prevent the default behavior of save() which will persist a new
		// entity if the entity matching the id does not exist
		User userToUpdate = findOne(user.getId());
		if (userToUpdate == null) {
			logger.error("Attempted to update a User, but the entity does not exist.");
			logger.info("< update {}", user.getId());
			throw new NoResultException("Requested User not found.");
		}

		userToUpdate.setFullName("changed name");
		User updatedUser = userRepository.save(userToUpdate);

		logger.info("< update {}", user.getId());
		return updatedUser;
	}

	@CacheEvict(value = "users", key = "#id")
	@Override
	@Transactional
	public void delete(Long id) {
		logger.info("> delete {}", id);

		counterService.increment("method.invoked.userServiceImpl.delete");

		userRepository.delete(id);

		logger.info("< delete {}", id);

	}

	@CacheEvict(value = "greetings", allEntries = true)
	@Override
	public void evictCache() {
		logger.info("> evictCache");

		counterService.increment("method.invoked.greetingServiceBean.evictCache");

		logger.info("< evictCache");
	}

}
