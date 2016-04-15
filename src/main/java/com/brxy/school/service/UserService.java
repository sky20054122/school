package com.brxy.school.service;

import java.util.List;

import com.brxy.school.model.User;

public interface UserService {

	/**
	 * Find all User entities.
	 * 
	 * @return A Collection of User objects.
	 */
	List<User> findAll();

	/**
	 * Find a single User entity by primary key identifier.
	 * 
	 * @param id
	 *            A BigInteger primary key identifier.
	 * @return A User or <code>null</code> if none found.
	 */
	User findOne(Long id);

	/**
	 * Persists a User entity in the data store.
	 * 
	 * @param User
	 *            A User object to be persisted.
	 * @return A persisted User object or <code>null</code> if a problem
	 *         occurred.
	 */
	User create(User user);

	/**
	 * Updates a previously persisted User entity in the data store.
	 * 
	 * @param User
	 *            A User object to be updated.
	 * @return An updated User object or <code>null</code> if a problem
	 *         occurred.
	 */
	User update(User user);

	/**
	 * Removes a previously persisted User entity from the data store.
	 * 
	 * @param id
	 *            A BigInteger primary key identifier.
	 */
	void delete(Long id);

	/**
	 * Evicts all members of the "users" cache.
	 */
	void evictCache();

}
