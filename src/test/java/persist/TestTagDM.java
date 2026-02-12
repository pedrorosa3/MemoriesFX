package persist;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import domain.ApplicationException;
import domain.Tag;
import main.Main;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestTagDM {

	private static TagDM tagDM;
	

	@Test
	@BeforeAll
	public static void setUpClass() {
		Main.initEMF("TestingPU");
		PictureDM.getInstance();
		tagDM = TagDM.getInstance();
	}
	
	@Test
	@AfterAll
	public static void tearDownClass() {
		Main.emf.close();
	}
	
	@Test
	@Order(1)
	public void testFindAllTagsVazio() throws ApplicationException{
		Set<Tag> setTagsemp = tagDM.findAllTags();
		assertEquals(setTagsemp.size(),0, "SHOULD BE ZERO");
	}
	
	
	@Test
	@Order(2)
	void testgetID() throws ApplicationException {
		
		
		Tag pic= tagDM.makeTag("nome_tag");
		
		long id = pic.getId();
		long id1 = 1;
		assertNotNull(id);
		assertEquals(id,id1);
		
	}
	
	@Test
    @Order(3)
    void testMakeTag() throws ApplicationException {
		
		
        Tag tag = tagDM.makeTag("tag_name_test");

        assertEquals("tag_name_test", tag.getNome());
    }
	
	
	@Test
    @Order(4)
    void testFindByName() throws ApplicationException {
		
		
        Tag foundTag = tagDM.findByName("tag_name_test").orElse(null);


        assertEquals("tag_name_test", foundTag.getNome());
    }
	
	
	@Test
    @Order(5)
    void testAllTags() throws ApplicationException {
		
		Tag tag1 = tagDM.makeTag("tag_name_test1");
		Tag tag2 = tagDM.makeTag("tag_name_test2");
		Tag tag3 = tagDM.makeTag("tag_name_test3");
		Tag tag4 = tagDM.makeTag("tag_name_test4");
		Tag tag5 = tagDM.makeTag("tag_name_test4");
		
		
		
        Set<Tag> tags = tagDM.findAllTags();


        assertEquals(6, tags.size());
    }
	
	
	
	
	
	
	

}
