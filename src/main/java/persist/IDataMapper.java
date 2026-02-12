package persist;

import java.util.Optional;

import domain.ApplicationException;

public interface IDataMapper<E,K> {
	
	Optional<E> find (K id) throws ApplicationException; 
	
	long insert(E e) throws ApplicationException;
	
	void update (E e) throws ApplicationException;
	
	void remove (E e) throws ApplicationException;
	
	long getId (E e) throws ApplicationException;
	
}
	