package main;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import domain.ApplicationException;
import handlers.PictureHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	
	public static EntityManagerFactory emf;
	
	public App() {
		
		
	}
	
    @Override
    public void start(Stage stage) throws ApplicationException, IOException {
        stage.setTitle("MemoriesFX");
        gui.RootPane main = new gui.RootPane();
        Scene scene = new Scene(main);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        try {
        	if(args.length>0 && args[0].equals("initdb")) {
        		Main.initEMF("TestingPU");
        		PictureHandler.initializeDB();
        	}
        	else {
        		Main.initEMF("ApplicationPU");
        	}
            
            launch();
        } finally {
        	if(Main.emf!=null) {
        		Main.emf.close();
        	}
            
        }
    }

}
