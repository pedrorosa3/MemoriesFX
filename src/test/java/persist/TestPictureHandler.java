package persist;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import domain.ApplicationException;
import domain.Picture;
import domain.Tag;
import handlers.PictureHandler;
import main.ApplicationSettings;
import main.Main;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestPictureHandler {

	@Test
	@BeforeAll
	public static void setUpClass() {
		Main.initEMF("TestingPU");
		
	}
	
	@Test
	@AfterAll
	public static void tearDownClass() {
		Main.emf.close();
	}
	
	@Test
	@Order(1)
	public void testImportPicture() throws ApplicationException, IOException{
		
		ApplicationSettings settings = new ApplicationSettings();
		List<Path> pathList = settings.getPaths();
		String importFileName = pathList.get(0).toString();
		Picture picture = PictureHandler.importPicture(importFileName);
		String finalpic = picture.getNome().substring(picture.getNome().lastIndexOf("/")+1);
		String finalpic2 = importFileName.substring(importFileName.lastIndexOf("/")+1);
		assertEquals(finalpic,finalpic2);
	}

}
