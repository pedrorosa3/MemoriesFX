package gui;

import domain.Album;
import domain.ApplicationException;
import domain.Picture;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import persist.AlbumDM;

public class AlbumView extends Label implements Comparable<AlbumView> {

    private Album album;
    private RootPane root;
    private AlbumPane albumpane; 
    
    
    public AlbumView(RootPane root, Album a) {
    	
        super(a.getNome());
        this.album=a;
        
        this.root = root;
        this.albumpane = this.root.getAlbumPane();
        
        this.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-padding: 5px;");
        // Define the padding
        
        this.setOnMouseClicked(event -> {
            System.out.println("AlbumView" + this.album.getNome() + " clicked!");
            try {
                executeAction();
            } catch (ApplicationException ex) {
                // it may not be necessary to catch an exception here
                // anyways you know how it should be done.
                System.err.println(ex.getMessage());
            }
        });
    }
    
    private void executeAction() throws ApplicationException {
    	
    	VBox vboxantiga = this.root.getAlbumPane().getAlbumInfo();
    	this.root.getAlbumPane().getChildren().remove(vboxantiga);
    	
    	// Criar uma VBox para exibir o nome do álbum e a lista de fotos
        VBox albumInfoBox = new VBox();
        albumInfoBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 10px;");
        albumInfoBox.setSpacing(5); // Espaçamento entre os elementos da VBox
        
        // Adicionar o nome do álbum como o primeiro elemento
        Label albumNameLabel = new Label("Álbum: " + album.getNome());
        
        albumInfoBox.getChildren().add(albumNameLabel);
        
        // Adicionar a lista de fotos
        Label fotosLabel = new Label("Fotos:");
        
        albumInfoBox.getChildren().add(fotosLabel);
        
       
        for (Picture foto : album.getPictures()) {
        	String nome = foto.getAppPath();
            Label fotoLabel = new Label("- " + nome);
            albumInfoBox.getChildren().add(fotoLabel);
        }
        
        Button createButton = new Button("Apagar Album");
        createButton.setOnAction(e -> {
            try {
				doDelete();
			} catch (ApplicationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        albumInfoBox.getChildren().add(createButton);
        
        this.root.getAlbumPane().SetAlbumInfo(albumInfoBox, album);
        
        this.root.getAlbumPane().getChildren().add(albumInfoBox);
	   	
	   
    }
    
    
    
    
    public void doDelete() throws ApplicationException {
    	
    	VBox vboxantiga = this.root.getAlbumPane().getAlbumInfo();
    	this.root.getAlbumPane().getChildren().remove(vboxantiga);
    	AlbumDM.getInstance().remove(album);
    	this.root.getAlbumPane().refreshelim();
    	
    	
    	
		
	}

	public int compareTo(AlbumView a) {
    	return this.album.getNome().compareTo(a.album.getNome());
        // change the following line such that the ordering is alphabetical
        
    }

}


