package persist;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import domain.Album;
import domain.ApplicationException;
import domain.Picture;
import domain.Tag;
import main.Main;



public class AlbumDM extends ADataMapper<Album,Long> {
	
	
	private static AlbumDM instance;
	
	private AlbumDM() {
		super(Album.class);
	}
	
	
	public static AlbumDM getInstance() {
		if(instance==null) {
			instance = new AlbumDM();
		}
		return instance;
	}
	
	public long getId(Album album) throws ApplicationException {
		
		Optional<Album> pictureop = find(album.getId());
		
		return pictureop.get().getId();
	}
	
	
	public Album makeAlbum(Tag testTag) throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		try {
			Album album= new Album(testTag);
			
			
			insert(album);
			
			return album;
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro ao cria album : " + er.getMessage());
		}finally {

		}
		
	}
	
	public Album setName(Album a , String name) throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		try {
			a.setNome(name);
			update(a);
			return a;
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro ao alterar o nome do album: "+er.getMessage());
			
		}finally{
			em.close();
		}
		
	}
	
	
	
	public Album setDescription(Album a , String desc) throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		try {
			a.setDescricao(desc);
			update(a);
			return a;
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro a alterar a descrição do album: "+er.getMessage());
			
		}finally{
			em.close();
		}
		
	}
	
	public Optional<Album> findByName(String albumname)throws ApplicationException{
		EntityManager em = Main.emf.createEntityManager();
		try {
			TypedQuery<Album> query = em.createQuery("SELECT a FROM Album a WHERE a.nome = :albumname",Album.class);
			query.setParameter("albumname",albumname);
			
			List<Album> albuns = query.getResultList();
			
			return albuns.isEmpty() ? Optional.empty() : Optional.of(albuns.get(0));
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro ao encontrar o álbum a partir do nome: "+ er.getMessage());
			
		}
		finally {
			em.close();
		}
	}
	
	
	public Optional<Album> findByTag(Tag tag)throws ApplicationException{
		EntityManager em = Main.emf.createEntityManager();
		try {
			
			Tag managedTag = em.find(Tag.class, tag.getId());
			
			TypedQuery<Album> query = em.createQuery("SELECT a FROM Album a WHERE a.tag = :albumname",Album.class);
			query.setParameter("albumname",managedTag);
			
			
			List<Album> albuns = query.getResultList();
			
			
			return albuns.isEmpty() ? Optional.empty() : Optional.of(albuns.get(0));
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro ao encontrar o álbum a partir da tag: "+ er.getMessage());
			
		}
		finally {
			em.close();
		}
	}
	
	public void setPictures(Album a , List<Picture> list) throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		try {
			a.setPictures(list);
			update(a);
			
		}catch(Exception er) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Erro a alterar a lista de fotografias do album: "+er.getMessage());
			
		}finally{
			em.close();
		}
	}


	public Set<Album> findAllAlbuns() throws ApplicationException {
		EntityManager em = Main.emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			TypedQuery<Album> query = em.createQuery("SELECT t FROM Album t", Album.class);
			
			
			List<Album> PicturebyTag = query.getResultList();
			
			em.getTransaction().commit();
			
			Set<Album> picfinal= new HashSet<Album>(PicturebyTag);
			
			
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

	

	
	

}
