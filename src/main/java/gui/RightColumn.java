package gui;

import domain.ApplicationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class represent the right column of the GUI. It contains the tag pane
 * that shows the collection of tags.
 *
 * @author tl
 */
public class RightColumn extends VBox {

    private TagsPane tagsPane;
    private NewTagPane NewTagPane;

    public RightColumn(RootPane root) throws ApplicationException {
        super();
        // create a label
        Label label = new Label("Tags: ");
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        
        
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.TOP_LEFT);  // Align the label to the top-right
        labelBox.getChildren().add(label);
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");
        
        // create a TagsPane
        tagsPane = new TagsPane(root);
        // create a "NewTagsPane"
        NewTagPane newtagpane =  new NewTagPane(root);
        // add the graphical elements to the interface.
        this.getChildren().add(labelBox);
        this.getChildren().add(tagsPane);
        this.getChildren().add(newtagpane);
    }

    public TagsPane getTagPane() {
        return this.tagsPane;
    }
    
    public NewTagPane getNewTagPane() {
    	return this.NewTagPane;
    }

}
