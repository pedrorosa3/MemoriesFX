package gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import persist.PictureDM;
import java.io.IOException;
import java.util.Collection;
import domain.ApplicationException;
import domain.Picture;

/**
 * This class corresponds to the main layout i.e. the root of graphical
 * components hierarchy.
 *
 * @author tl
 */

public class RootPane extends VBox {

    private LeftColumn leftColumn;
    private RightColumn rightColumn;
    private PicturesPane centerColumn;
    private HBox middleRow;
    private TagsPane tagsPane;
    private SearchPane searchPane;
    private AlbumPane albumPane;

    public RootPane() throws ApplicationException, IOException {
        super();
        // create a label (topLabel)
        Node topLabel = topLabel();
        this.getChildren().add(topLabel);
        this.middleRow = middleRow();
        this.getChildren().add(middleRow);
        // The 2 following lines control how the height of the VBox layout grow
        // (or shrink) when the size of the window changes.
        
        VBox.setVgrow(topLabel, Priority.NEVER);
        VBox.setVgrow(middleRow, Priority.ALWAYS);
    }

    private HBox middleRow() throws ApplicationException, IOException {
        HBox box = new HBox();
        
        Collection <Picture> recentpiclist = PictureDM.getInstance().getRecentPictures(); 
        
       
        leftColumn = new LeftColumn(this);
        this.searchPane = leftColumn.getSearchPane();
        this.albumPane = leftColumn.getAlbumPane();
        centerColumn = new PicturesPane(this, recentpiclist, 3);
        rightColumn = new RightColumn(this);
        this.tagsPane=rightColumn.getTagPane();
        // create left column object
        // create right column object
        // You may change the following lines:
        HBox.setHgrow(leftColumn, Priority.NEVER);
        
        HBox.setHgrow(centerColumn,Priority.ALWAYS);
        
        HBox.setHgrow(rightColumn, Priority.NEVER);
        
        leftColumn.maxWidth(300);
        centerColumn.maxWidth(Double.MAX_VALUE);
        rightColumn.maxWidth(350);
        
        box.getChildren().add(leftColumn);
        box.getChildren().add(centerColumn);
        box.getChildren().add(rightColumn);
        // add graphical elements to box.
        
        return box;
    }

    private Node topLabel() {
        // Cria um Label com o texto desejado
        Label label = new Label("MemoriesFX");
        
        // Define o estilo do label, se necessário
        label.setStyle("-fx-font-size: 75px; -fx-font-weight: bold;");

        // Alinha o label no centro horizontalmente
        label.setAlignment(Pos.CENTER);
        
        this.setAlignment(Pos.CENTER);
        
        return label;
    }

    public TagsPane getTagPane() {
        return tagsPane;
    }

    public PicturesPane getPicturesPane() {
        return centerColumn;
    }
    
    public LeftColumn getLeftColumn() {
    	return leftColumn;
    }
    
    public SearchPane getSearchPane() {
    	return searchPane;
    }
    
    public AlbumPane getAlbumPane() {
    	return albumPane;
    }
    
}
