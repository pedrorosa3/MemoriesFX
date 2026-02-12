package persist;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import domain.Album;
import domain.ApplicationException;
import domain.Picture;
import domain.Tag;
import main.Main;

class TestAlbumDM {
	
	private static TagDM tagDM;
	private static AlbumDM albumDM;
	
	
	
	@Test
	@BeforeAll
	public static void setUpClass() {
		Main.initEMF("TestingPU");
		
		albumDM = AlbumDM.getInstance();
		tagDM = TagDM.getInstance();
		PictureDM.getInstance();
	}
	
	@Test
	@AfterAll
	public static void tearDownClass() {
		Main.emf.close();
	}
	
	@Test
	@Order(1)
	void testgetID() throws ApplicationException {
		boolean condition=true;
		assumeTrue(condition);
		
		Tag testTag= tagDM.makeTag("nome_tag");
		
		Album novo = albumDM.makeAlbum(testTag);
		
		long id = novo.getId();
		long id1 = 1;
		assertNotNull(id);
		assertEquals(id,id1);
	}
	
	@Test
	@Order(2)
	void testMakeAlbum() throws ApplicationException {
		boolean condition= true;
		assumeTrue(condition);
		
		Tag testTag= tagDM.makeTag("nome_tag");
		
		Album novo = albumDM.makeAlbum(testTag);
		
		assertNotNull(novo);
		assertNotNull(novo.getId());
		assertEquals(testTag,novo.getTag());
	}
	
	@Test
	@Order(3)
	void testSetName() throws ApplicationException{
		
		Tag testTag= tagDM.makeTag("nome_tag");
		Album novo = albumDM.makeAlbum(testTag);
		String nome = "nomeAlbum";
		
		albumDM.setName(novo, nome);
		assertEquals(nome,novo.getNome());
	}
	
	@Test
	@Order(4)
	void testSetDescription() throws ApplicationException{
		
		Tag testTag= tagDM.makeTag("nome_tag");
		Album novo =albumDM.makeAlbum(testTag);
		String descricao = "descricaoAlbum";
		
		albumDM.setDescription(novo, descricao);
		assertEquals(descricao,novo.getDescricao());
	}
	
	@Test
	@Order(5)
	void testFindByName() throws ApplicationException{
		
		Tag testTag= tagDM.makeTag("nome_tag");
		Album novo = albumDM.makeAlbum(testTag);
		String nome = "nomeAlbum";
		
		albumDM.setName(novo, nome);
		Album album2 = albumDM.findByName(nome).orElse(null);
		assertEquals(album2.getNome(),novo.getNome());
		
	}
	
	@Test
	@Order(6)
	void testFindByTag() throws ApplicationException{
		
		Tag testTag= tagDM.makeTag("nome_tag");
		Album novo = albumDM.makeAlbum(testTag);
		
		Album album2 = albumDM.findByTag(testTag).orElse(null);
		assertEquals(album2.getTag(),novo.getTag());
	}	
	
	@Test
	@Order(7)
	void testSetPictures() throws ApplicationException{
		
		Tag tag = tagDM.makeTag("nome_album");
		Album album = albumDM.makeAlbum(tag);
		
		ArrayList<Picture> lista = new ArrayList<>();
		
		Picture picture1 = new Picture();
		Picture picture2 = new Picture();
		Picture picture3 = new Picture();
		Picture picture4 = new Picture();
		
		lista.add(picture1);
		lista.add(picture2);
		lista.add(picture3);
		lista.add(picture4);
		
		albumDM.setPictures(album, lista);
		assertEquals(album.getPictures().size(), 4);
	
	}
	
}
