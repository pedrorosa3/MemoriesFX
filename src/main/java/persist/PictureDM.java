package persist;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import domain.ApplicationException;
import domain.Picture;
import domain.Tag;
import main.Main;

public class PictureDM extends ADataMapper<Picture,Long> {
	
	private static PictureDM instance; 
	
	PictureDM() {
		super(Picture.class);
	}
	
	public static PictureDM getInstance() {
		if(instance==null) {
			instance=new PictureDM();
		}
		return instance;
	}
	
	
	
	
	public long getId(Picture pic) throws ApplicationException{
		
		Optional<Picture> pictureop = find(pic.getId());
		
		return pictureop.get().getId();
	}
	
	
	
	public Set<Picture> getRecentPictures() throws ApplicationException{
		
		EntityManager em = Main.emf.createEntityManager();
		try {
			em.getTransaction().begin();
			
			TypedQuery<Integer> query = em.createQuery("SELECT MAX(CAST(SUBSTRING(p.data, 7, 4) AS INTEGER)) FROM Picture p",Integer.class);
			Integer latesteYear = query.getSingleResult();
			
			
			TypedQuery<Picture> picquery = em.createQuery("SELECT p FROM Picture p WHERE CAST(SUBSTRING(p.data, 7, 4) AS INTEGER) = :ano", Picture.class);
			picquery.setParameter("ano", latesteYear);
			
			List<Picture> recentpic = picquery.getResultList();
			
			em.getTransaction().commit();
			
			return new HashSet<>(recentpic);
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error ao encontrar as fotografias recentes:"+ er.getMessage());
			
		}finally {
			em.close();
		}
	}
	
	public Set<Picture> findByTag(Tag tag) throws ApplicationException{
		
		EntityManager em = Main.emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			
			TypedQuery<Picture> query = em.createQuery("SELECT p FROM Picture p WHERE :tag MEMBER OF p.tags ",Picture.class); 
			query.setParameter("tag", tag);
			
			
			List<Picture> PicturebyTag = query.getResultList();
			
			em.getTransaction().commit();
			Set<Picture> picfinal= new HashSet<Picture>(PicturebyTag);
			return picfinal;
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error a encontrar a fotografia pela tag:"+er.getMessage());
			
		}
		finally {
			em.close();
		}
		
	}

	
	
	public Set<Picture> findByTags(Set<Tag> querytags) throws ApplicationException{
		
		EntityManager em = Main.emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			
			
			TypedQuery<Picture> query = em.createQuery("SELECT p FROM Picture p JOIN p.tags t WHERE t IN :queryTags GROUP BY p HAVING COUNT(t) = :tagcount", Picture.class);
			
			query.setParameter("queryTags", querytags);
			
			
			
			query.setParameter("tagcount", (long) querytags.size());
			
			
			List<Picture> PicturebyTag = query.getResultList();
			
			em.getTransaction().commit();
			Set<Picture> picfinal= new HashSet<Picture>(PicturebyTag);
			
			
			return picfinal;
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro a encontrar as fotografias pelas tags:"+er.getMessage());
			
		}
		finally {
			em.close();
		}
		
	}
	
	public Set<Picture> findByAnyTags(Set<Tag> querytags) throws ApplicationException{
		
		EntityManager em = Main.emf.createEntityManager();
		
		
		try {
			em.getTransaction().begin();
			
			TypedQuery<Picture> query = em.createQuery("SELECT p FROM Picture p JOIN p.tags t WHERE t IN :queryTags", Picture.class);
			
			query.setParameter("queryTags", querytags);
			
			
			List<Picture> PicturebyTag = query.getResultList();
			em.getTransaction().commit();
			Set<Picture> picfinal= new HashSet<Picture>(PicturebyTag);
			return picfinal;
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error a encontrar as fotografias com pelo menos uma tag:"+er.getMessage());
			
		}
		finally {
			em.close();
		}
		
	}
	
	public void addTag(Picture pic, Tag tag) throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();

	    try {
	       
	        Picture managedPic = em.find(Picture.class, pic.getId());
	        Tag managedTag = em.find(Tag.class, tag.getId());

	        if (managedPic == null || managedTag == null) {
	            throw new ApplicationException("Picture ou Tag não encontrada.");
	        }

	        
	        pic.addTag(managedTag);
	        
	        
	        
	        update(pic);
	        
	        
	    } catch (Exception e) {
	    	if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
	        throw new ApplicationException("Erro ao adicionar a tag."+ e.getMessage());
	    } finally {
	        em.close();
	    }
	}

	public void removeTag(Picture pic, Tag tag) throws ApplicationException {
		
		EntityManager em = Main.emf.createEntityManager();

	    try {
	        

	        
	        Picture managedPic = em.find(Picture.class, pic.getId());
	        Tag managedTag = em.find(Tag.class, tag.getId());

	        if (managedPic == null || managedTag == null) {
	            throw new ApplicationException("Picture ou Tag não encontrada.");
	        }

	        
	        pic.removeTag(managedTag);
	        
	        update(pic);
	        
	        

	    } catch (Exception e) {
	    	if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
	        throw new ApplicationException("Erro ao remover a tag."+ e.getMessage());
	    } finally {
	        em.close();
	    }
	}
	
	
	public Set<Picture> findAllPictures() throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			TypedQuery<Picture> query = em.createQuery("SELECT p FROM Picture p", Picture.class);
			
			
			List<Picture> PicturebyTag = query.getResultList();
			
			em.getTransaction().commit();
			Set<Picture> picfinal= new HashSet<Picture>(PicturebyTag);
		
			return picfinal;
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro a encontrar todas as fotografias:"+er.getMessage());
			
		}finally {
			em.close();
		}
		
	}
	
}
