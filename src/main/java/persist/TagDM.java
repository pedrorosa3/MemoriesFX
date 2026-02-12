package persist;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import domain.ApplicationException;
import domain.Picture;
import domain.Tag;
import main.Main;

public class TagDM extends ADataMapper<Tag, Long> {
	
	private static TagDM instance;
	
	
	private TagDM() {
		super(Tag.class);
	}
	
	public static TagDM getInstance() {
		if(instance==null) {
			instance=new TagDM();
		}
		return instance;
	}
	
	
	
	
	public long getId(Tag pic) throws ApplicationException {
		
		Optional<Tag> pictureop = find(pic.getId());
		
		return pictureop.get().getId();
	}
	
	
	public Optional<Tag> findByName(String name) throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.nome = :name", Tag.class);
			query.setParameter("name", name);
			Tag tag=query.getSingleResult();
			
			em.getTransaction().commit();
			
			return Optional.ofNullable(tag);
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro encontrar a Tag: " + er.getMessage());
			
		}
		finally {
			em.close();
		}
	}

	public Tag makeTag(String string) throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		try {
			
			
			em.getTransaction().begin();
			
			TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.nome = :nome", Tag.class);
			query.setParameter("nome", string);
			Tag tagBd;
			try {
				tagBd = query.getSingleResult();
				
			}catch(NoResultException e) {
				tagBd=null;
			}catch(NonUniqueResultException er) {
				tagBd=null;
			}
			
			
			
			if(tagBd!=null) {
				return tagBd;
			}
			else {
				
				em.getTransaction().commit();
				Tag tag= new Tag();
				tag.setNome(string);
		
				
				
				insert(tag);
				
				return tag;
			}	
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro ao criar a tag : " + er.getMessage());
		}finally {
			em.close();
		}
		
	}

	public Set<Tag> findAllTags() throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t", Tag.class);
			
			
			List<Tag> PicturebyTag = query.getResultList();
			
			em.getTransaction().commit();
			
			Set<Tag> picfinal= new HashSet<Tag>(PicturebyTag);
			
			return picfinal;
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro a encontrar as tags existentes:"+er.getMessage());
			
		}finally {
			em.close();
		}
	}
	
	
	public void addTagtoPicture(Picture picture, String tagName) throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		try {
			em.getTransaction().begin();
			
			TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.nome = :tagName", Tag.class);
			query.setParameter("tagName", tagName);
			
			Tag tag;
			if(query.getResultList().isEmpty()) {
				tag= new Tag();
				tag.setNome(tagName);
				em.persist(tag);
				
			}else {
				tag=query.getSingleResult();
				
			}
			
			if(!tag.getPictures().contains(picture)) {
				tag.getPictures().add(picture);
				picture.getTags().add(tag);
				
			}
			
			em.merge(tag);
			em.merge(picture);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro ao adicionar tag à fotografia: " + e.getMessage());
			
		}finally {
			em.close();
		}
	}
	
}
