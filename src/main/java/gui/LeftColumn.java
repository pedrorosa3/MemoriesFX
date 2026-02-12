package gui;

import domain.ApplicationException;
import javafx.scene.layout.VBox;

/**
 * This class displays the left column of the GUI. It contains the search pane
 * for searching the picture database using tags and the album pane that shows
 * the list of albums and allows the creation, edition and display of albums.
 *
 * @author tl
 */
public class LeftColumn extends VBox {
	
	private SearchPane searchpane;
	private RootPane root;
	private AlbumPane albumpane;
	

    public LeftColumn(RootPane root) throws ApplicationException {
    	this.root = root;
    	SearchPane searchpane = new SearchPane(this.root);
    	this.searchpane = searchpane;
    	
    	
    	
    	
    	AlbumPane albumpane = new AlbumPane(this.root);
    	this.albumpane = albumpane;
    	
    	
        // create a SearchPane
    	
    	this.getChildren().add(searchpane);
        // add to interface
    	this.getChildren().add(albumpane);
    }

    public SearchPane getSearchPane() {
        return  searchpane;
    }
    
    public AlbumPane getAlbumPane() {
    	return albumpane;
    }
}
