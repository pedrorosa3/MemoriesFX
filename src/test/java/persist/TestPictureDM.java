package persist;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import domain.Album;
import domain.ApplicationException;
import domain.Picture;
import domain.Tag;
import handlers.PictureHandler;
import main.ApplicationSettings;
import main.Main;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestPictureDM {
	
	private static PictureDM pictureDM;
	private static TagDM tagDM;
	
	@Test
	@BeforeAll
	public static void setUpClass() {
		Main.initEMF("TestingPU");
		pictureDM= PictureDM.getInstance();
		tagDM = TagDM.getInstance();
	}
	
	@Test
	@AfterAll
	public static void tearDownClass() {
		Main.emf.close();
	}
	
	@Test
	@Order(1)
	public void testFindAllPicturesVazio() throws ApplicationException{
		Set<Picture> setPicturesemp = pictureDM.findAllPictures();
		assertEquals(setPicturesemp.size(),0, "SHOULD BE ZERO");
	}
	
	@Test
    @Order(2)
    public void testAddTagToPicture() throws ApplicationException, IOException {
		
        Picture pic5 = PictureHandler.importPicture(ApplicationSettings.importFolder + "AnaLibano/P_20201226_145438.jpg");
        
        pictureDM.insert(pic5);
        
        Tag tag4 = tagDM.makeTag("teste101112");
        
        Tag tag7 = tagDM.makeTag("teste131415");
       
        pictureDM.addTag(pic5, tag4);
        
        pictureDM.addTag(pic5, tag7);

        Picture retrievedPic = pictureDM.find(pic5.getId()).orElse(null);
       
        assertTrue(retrievedPic.getTags().contains(tag7));
	}
	
	@Test
    @Order(3)
    public void testRemoveTagFromPicture() throws ApplicationException {
		Picture pic = new Picture();
        pictureDM.insert(pic);
        Tag tag = tagDM.makeTag("teste");

     
        pictureDM.addTag(pic, tag);
        pictureDM.removeTag(pic, tag);

        Picture retrievedPic = pictureDM.find(pic.getId()).orElseThrow(() -> new  ApplicationException("not found"));
        assertNotNull(retrievedPic);
        assertFalse(retrievedPic.getTags().contains(tag));
    }
	
	@Test
	@Order(4)
	void testFindByTag() throws ApplicationException, IOException{
		Picture pic1 = PictureHandler.importPicture(ApplicationSettings.importFolder + "DanielSantos/IMG_20190626_091207.jpg");
        Tag tag1 = tagDM.makeTag("teste123");
        Tag tag6 = tagDM.makeTag("teste000");
		pictureDM.addTag(pic1, tag1);
		pictureDM.addTag(pic1, tag6);
		Set<Picture> lista = pictureDM.findByTag(tag1);
		assertEquals(1,lista.size());
	}
	
	@Test
	@Order(5)
	void testFindByTags() throws ApplicationException, IOException{
		
		Picture pic2 = PictureHandler.importPicture(ApplicationSettings.importFolder + "AnaLibano/IMG_20160301_160428113_HDR.jpg");
		
		Picture pic3 = PictureHandler.importPicture(ApplicationSettings.importFolder + "AnaLibano/IMG_20160711_163640895.jpg");
		
		Picture pic4 = PictureHandler.importPicture(ApplicationSettings.importFolder + "AnaLibano/P_20200618_194511.jpg");
		
        Tag tag2 = tagDM.makeTag("teste456");
        
        Tag tag3 = tagDM.makeTag("teste789");
        
		pictureDM.addTag(pic2, tag2);
		
		pictureDM.addTag(pic2, tag3);
		
		pictureDM.addTag(pic3, tag2);
		
		pictureDM.addTag(pic3, tag3);
		
		pictureDM.addTag(pic4, tag2);
		
		Set<Tag> listatags = new HashSet<>();
		
		listatags.add(tag2);
		
		listatags.add(tag3);
		
		Set<Picture> lista = pictureDM.findByTags(listatags);
		
		assertEquals(2,lista.size());
	}
	
	@Test
	@Order(6)
	void testFindByAnyTags() throws ApplicationException{
		
	
        Tag tag2 = tagDM.findByName("teste456").orElse(null);
        Tag tag3 = tagDM.findByName("teste789").orElse(null);
        
		
		Set<Tag> listatags = new HashSet<>();
		listatags.add(tag2);
		listatags.add(tag3);
		
		Set<Picture> lista = pictureDM.findByAnyTags(listatags);
		
		assertEquals(3,lista.size());
	}
	

	@Test
	@Order(7)
	public void testGetRecentPictures() throws ApplicationException {
		
		Set<Picture> lista = pictureDM.getRecentPictures();
		assertEquals(2, lista.size());
		
	}

}
