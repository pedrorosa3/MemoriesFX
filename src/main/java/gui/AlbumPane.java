package gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.Album;
import domain.ApplicationException;
import domain.Picture;
import domain.Tag;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persist.AlbumDM;
import persist.TagDM;

public class AlbumPane extends VBox {
	
	private FlowPane selectedAlbum;
    private RootPane root;
    private List<AlbumView> ListaAlbuns; 
    private Set<Album> albuns;
    private TextField albumNameField;
    private TextField tagNameField;
    private TextField descNameField;
    private VBox  albuminfo;
    private Album albumselected;
    

    
	public AlbumPane(RootPane root) throws ApplicationException {
		super();
        this.root = root;
        Label label = new Label("Albuns:");
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        
        selectedAlbum = new FlowPane();
        selectedAlbum.setPadding(new Insets(10));
        selectedAlbum.setHgap(20);
        selectedAlbum.setVgap(5);
        selectedAlbum.setPrefWrapLength(200); 
        selectedAlbum.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px;");
        
        ListaAlbuns = new ArrayList<>();
        
        this.albuminfo=null;
        
        
        albuns = AlbumDM.getInstance().findAllAlbuns();
        for (Album albumnovo : albuns) {
        	AlbumView AlbumViewNovo = new AlbumView(root,albumnovo);
        	ListaAlbuns.add(AlbumViewNovo);
        	
        }
        
        Collections.sort(ListaAlbuns);
        // ---
        // add the list of TagViews to the interface.
        for(AlbumView view : ListaAlbuns) {
        	selectedAlbum.getChildren().add(view);
        }
        
        
        
        albumNameField = new TextField();
        albumNameField.setPromptText("Enter Album name");
        albumNameField.setPrefWidth(200);  // Ajuste de largura para tornar a TextField menor
        tagNameField = new TextField();
        tagNameField.setPromptText("Enter Tag name");
        tagNameField.setPrefWidth(200);  // Ajuste de largura para tornar a TextField menor
        descNameField = new TextField();
        descNameField.setPromptText("Enter Description");
        descNameField.setPrefWidth(200);  // Ajuste de largura para tornar a TextField menor
        
        

        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        
        Button createButton = new Button("Create");
        createButton.setOnAction(e -> {
            try {
                doCreate();
            } catch (ApplicationException e1) {
                e1.printStackTrace();
            }
        });

        Button addpicButton = new Button("Set pictures");
        addpicButton.setOnAction(e -> {
            try {
                doAdd();
            } catch (ApplicationException e1) {
                e1.printStackTrace();
            }
        });

        Button removepicButton = new Button("Remove pictures");
        removepicButton.setOnAction(e -> {
            try {
                doRemove();
            } catch (ApplicationException e1) {
                e1.printStackTrace();
            }
        });

        // Adiciona os botões ao HBox
        buttonBox.getChildren().addAll(createButton, addpicButton, removepicButton);
        
        
        

        
        
        
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(label,selectedAlbum,albumNameField,tagNameField,descNameField,buttonBox);
	}
	
	private void doCreate() throws ApplicationException {
		
		String tagName = tagNameField.getText().trim();
		String albumName = albumNameField.getText().trim();
		String descName = descNameField.getText().trim();
		
		
        
        
        if (tagName.isEmpty() || albumName.isEmpty() || descName.isEmpty()) {
            
        } else {
        	
                Tag tagalbum = TagDM.getInstance().makeTag(tagName);
                root.getTagPane().refresh();
                Album album = AlbumDM.getInstance().makeAlbum(tagalbum);
                AlbumDM.getInstance().setName(album, albumName);
                AlbumDM.getInstance().setDescription(album, descName);
                AlbumView abview = new AlbumView(root,album);
                
                selectedAlbum.getChildren().add(abview);
                
                
                
        }

        refresh();
        tagNameField.clear();
        albumNameField.clear();
        descNameField.clear();
		
	}
	
	private void doAdd() throws ApplicationException {
		
		List<PictureView> picviewlist= root.getPicturesPane().getSelectedPictures();
		List<Picture> domainlistpic = new ArrayList<>();
		
		for(PictureView piclist : picviewlist) {
			Picture pic2 = piclist.getPic();
			domainlistpic.add(pic2);
		}
		
		List<Picture> listpic = albumselected.getPictures();
		
		for(Picture pic3 : domainlistpic) {
			if(listpic.contains(pic3)) {
				
			}
			else {
				listpic.add(pic3);
			}
		}
		
		AlbumDM.getInstance().setPictures(albumselected, listpic);
		
		refreshFotos();
		
		
		
		
			
	}
	
	private void doRemove() throws ApplicationException {
		
		List<PictureView> picviewlist= root.getPicturesPane().getSelectedPictures();
		List<Picture> domainlistpic = new ArrayList<>();
		
		for(PictureView piclist : picviewlist) {
			Picture pic2 = piclist.getPic();
			domainlistpic.add(pic2);
		}
		
		List<Picture> listpic = albumselected.getPictures();
		
		for(Picture pic3 : domainlistpic) {
			if(listpic.contains(pic3)) {
				listpic.remove(pic3);
			}
			else {
				
			}
		}
		
		AlbumDM.getInstance().setPictures(albumselected, listpic);
		
		refreshFotos();
		
	}
	
	public void refresh() throws ApplicationException {
		
    	Set<Album> tagsrefresh = AlbumDM.getInstance().findAllAlbuns();
    	
    	for (Album tagnova : tagsrefresh) {
    		
    		if(albuns.contains(tagnova)) {
			   
    		}
    		else {
    			AlbumView tagviewnova = new AlbumView(root,tagnova);
    			albuns.add(tagnova);
    			ListaAlbuns.add(tagviewnova);
    			
    		}
    		
    		
    		  
       	
       	
    	}
       
       // create TagViews for each tag.
       // ---
       // The following line will sort the TagViews. In order to be easier to
       // find tags it better o sort then after some criteria. A good option
       // is to sort alphabetically. It will work if the TagView class
       // implements the Comparable interface (see TagView class). When the
       // code in the TagView class is completed, you can uncomment the
       // following line. The tags should appear sorted.
    	Collections.sort(ListaAlbuns);
       // ---
       // add the list of TagViews to the interface.
    	selectedAlbum.getChildren().clear();
    	for(AlbumView view : ListaAlbuns) {
    	   
    		selectedAlbum.getChildren().add(view);
    	}
       
    	
	   
    }
	
	
	public void refreshelim() throws ApplicationException {
		
    	Set<Album> tagsrefresh = AlbumDM.getInstance().findAllAlbuns();
    	
    	this.albuns= new HashSet<Album>();
		this.ListaAlbuns= new ArrayList<>();
    	for (Album tagnova : tagsrefresh) {
    		
    		
    		AlbumView albumviewnova = new AlbumView(root,tagnova);
    		albuns.add(tagnova);
    		ListaAlbuns.add(albumviewnova);
    	}
       
       // create TagViews for each tag.
       // ---
       // The following line will sort the TagViews. In order to be easier to
       // find tags it better o sort then after some criteria. A good option
       // is to sort alphabetically. It will work if the TagView class
       // implements the Comparable interface (see TagView class). When the
       // code in the TagView class is completed, you can uncomment the
       // following line. The tags should appear sorted.
    	Collections.sort(ListaAlbuns);
       // ---
       // add the list of TagViews to the interface.
    	selectedAlbum.getChildren().clear();
    	for(AlbumView view : ListaAlbuns) {
    		
    		selectedAlbum.getChildren().add(view);
    	}
       
    	
	   
    }
	
	public void SetAlbumInfo(VBox albuminfo,Album a) {
		this.albuminfo=albuminfo;
		this.albumselected = a;
	}
	
	
	public VBox refreshFotosinicial() {
		
    	
    	// Criar uma VBox para exibir o nome do álbum e a lista de fotos
        VBox albumInfoBox = new VBox();
        albumInfoBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 10px;");
        albumInfoBox.setSpacing(5); // Espaçamento entre os elementos da VBox
        
        // Adicionar o nome do álbum como o primeiro elemento
        Label albumNameLabel = new Label("Álbum: " + albumselected.getNome());
        
        albumInfoBox.getChildren().add(albumNameLabel);
        
        // Adicionar a lista de fotos
        Label fotosLabel = new Label("Fotos:");
        
        albumInfoBox.getChildren().add(fotosLabel);
        
        
        for (Picture foto : albumselected.getPictures()) {
        	String nome = foto.getAppPath();
            Label fotoLabel = new Label("- " + nome);
            albumInfoBox.getChildren().add(fotoLabel);
        }
        
        this.root.getAlbumPane().SetAlbumInfo(albumInfoBox, albumselected);
        
        this.root.getAlbumPane().getChildren().add(albumInfoBox);
	   	
        
        
        return albumInfoBox;
		
	}
	
	public VBox refreshFotos() {
		VBox vboxantiga = this.root.getAlbumPane().getAlbumInfo();
    	this.root.getAlbumPane().getChildren().remove(vboxantiga);
    	
    	// Criar uma VBox para exibir o nome do álbum e a lista de fotos
        VBox albumInfoBox = new VBox();
        albumInfoBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 10px;");
        albumInfoBox.setSpacing(5); // Espaçamento entre os elementos da VBox
        
        // Adicionar o nome do álbum como o primeiro elemento
        Label albumNameLabel = new Label("Álbum: " + albumselected.getNome());
        
        albumInfoBox.getChildren().add(albumNameLabel);
        
        // Adicionar a lista de fotos
        Label fotosLabel = new Label("Fotos:");
        
        albumInfoBox.getChildren().add(fotosLabel);
        
        
        for (Picture foto : albumselected.getPictures()) {
        	String nome = foto.getAppPath();
            Label fotoLabel = new Label("- " + nome);
            albumInfoBox.getChildren().add(fotoLabel);
        }
        
        Button createButton = new Button("Apagar Album");
        createButton.setOnAction(e -> {
            try {
				doDelete();
			} catch (ApplicationException e1) {
				
				e1.printStackTrace();
			}
        });
        
        albumInfoBox.getChildren().add(createButton);
        
        this.root.getAlbumPane().SetAlbumInfo(albumInfoBox, albumselected);
       
        this.root.getAlbumPane().getChildren().add(albumInfoBox);
	   	
        
        
        
        
        
        
        return albumInfoBox;
		
	}
	
	public VBox getAlbumInfo() {
		return this.albuminfo;
	}
	
	public void doDelete() throws ApplicationException {
    	
    	VBox vboxantiga = this.root.getAlbumPane().getAlbumInfo();
    	this.root.getAlbumPane().getChildren().remove(vboxantiga);
    	AlbumDM.getInstance().remove(albumselected);
    	this.root.getAlbumPane().refreshelim();
    	
    	
    	
		
	}


}
