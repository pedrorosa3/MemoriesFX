package persist;

import java.util.Optional;

import javax.persistence.EntityManager;

import domain.ApplicationException;
import main.Main;

public abstract class ADataMapper <E, K> implements IDataMapper<E,K>{
	
	
	
	protected Class <E> theClass;

	protected ADataMapper(Class <E> theClass) {
		this.theClass = theClass;
	}
	
	
	public Class <E> getEntityClass (){
		
		return theClass;
	}
	
	
	
	
	
	@Override
	public Optional<E> find (K id) throws ApplicationException{
		
		EntityManager em = Main.emf.createEntityManager();
		try {
			E obj = em.find(theClass,id);
			
			
			return Optional.ofNullable(obj);
			
		} catch(Exception er){
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error:"+ er.getMessage());
		
		}finally {
			em.close();
			
		}
	};
	
	
	@Override
	public long insert(E e) throws ApplicationException {
		
		EntityManager em = Main.emf.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			em.persist(e);
			em.getTransaction().commit();
			
			return (long) em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(e);
					
		} catch(Exception er){
			
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error:"+ er.getMessage());
			
		}finally {
			
			em.close();
		}
	
	}
	
	
	
	@Override
	public void update (E e) throws ApplicationException {
		
		EntityManager em = Main.emf.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			em.merge(e);
			em.getTransaction().commit();
			
			
		} catch(Exception er){
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error:"+ er.getMessage());
	
			
		}finally {
			
			em.close();
		}
	
	}
	
	
	@Override
	public void remove (E e) throws ApplicationException {
		
		EntityManager em = Main.emf.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			if (em.contains(e)) {
				em.remove(e);
				
			}
			else{
				E mergedEntity= em.merge(e);
				em.remove(mergedEntity);
			}
			em.getTransaction().commit();
			
			
					
		} catch(Exception er){
			
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error:"+ er.getMessage());
			
		}finally {
			
			em.close();
		}
	
	}
		
}
