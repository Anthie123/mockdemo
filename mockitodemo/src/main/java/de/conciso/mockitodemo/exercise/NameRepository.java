package de.conciso.mockitodemo.exercise;

import java.util.List;

public interface NameRepository {
	/**
	 * Create a name.
	 * @param firstName first name
	 * @param lastName last name
	 * @return name
	 * @throws RepositoryException if the name could not be created
	 */
	Name createName(String firstName, String lastName);
	/**
	 * Request all names
	 * @return list with all names or empty list
	 */
	List<Name> getAllNames();
	/**
	 * Get a name by id
	 * @param id id of the name
	 * @return name or null
	 */
	Name getName(long id);
	/**
	 * update a name
	 * @param id id of the name
	 * @param firstName new firstname or null
	 * @param lastName new last name or null
	 * @return changed name object
	 * @throws RepositoryException if the name could not be found
	 */
	Name updateName(long id, String firstName, String lastName);
	/**
	 * Delete a name by id
	 * @param id id of the name
	 * @throws RepositoryException if the name could not be deleted
	 */
	void deleteName(long id);
}
