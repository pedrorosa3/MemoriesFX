package gui;

import domain.ApplicationException;
import domain.Tag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import persist.TagDM;

/**
 * This class contains the tag collection. It appears on the right side of the
 * interface.
 *
 * @author tl
 */
public class TagsPane extends VBox {

    // a layout to contain TagViews. You may choose any kind of layout.
    private  FlowPane tagCollection;
    private RootPane root;
    private List<TagView> tagViewList;
    
    private Set<Tag> tags;

    public TagsPane(RootPane root) throws ApplicationException {
        super();
        this.root = root;
        // You may choose a different laypout.
        tagCollection = new FlowPane();
        tagCollection.setPadding(new Insets(10));
        tagCollection.setHgap(20);
        tagCollection.setVgap(5);
        tagCollection.setPrefWrapLength(200); // Permite quebra de linha automática
        tagCollection.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px;");
        
        tagViewList = new ArrayList<>();
        
        // Get tags from DB
        tags = TagDM.getInstance().findAllTags();
        for (Tag tagnova : tags) {
        	TagView tagviewnova = new TagView(root,tagnova,GuiElement.TAG_PANE);
        	tagViewList.add(tagviewnova);
        	
        }
        
        // create TagViews for each tag.
        // ---
        // The following line will sort the TagViews. In order to be easier to
        // find tags it better o sort then after some criteria. A good option
        // is to sort alphabetically. It will work if the TagView class
        // implements the Comparable interface (see TagView class). When the
        // code in the TagView class is completed, you can uncomment the
        // following line. The tags should appear sorted.
        Collections.sort(tagViewList);
        // ---
        // add the list of TagViews to the interface.
        for(TagView view : tagViewList) {
        	tagCollection.getChildren().add(view);
        }
        
        this.getChildren().add(tagCollection);
    }
    public void refresh() throws ApplicationException {
    	Set<Tag> tagsrefresh = TagDM.getInstance().findAllTags();
    	for (Tag tagnova : tagsrefresh) {
    		if(tags.contains(tagnova)) {
			   
    		}
    		else {
    			TagView tagviewnova = new TagView(root,tagnova,GuiElement.TAG_PANE);
    			tags.add(tagnova);
    			tagViewList.add(tagviewnova);
    			
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
    	Collections.sort(tagViewList);
       // ---
       // add the list of TagViews to the interface.
    	tagCollection.getChildren().clear();
    	for(TagView view : tagViewList) {
    	   
    		tagCollection.getChildren().add(view);
    	}
       
    	this.getChildren().clear();
    	this.getChildren().add(tagCollection);
	   
    }

}
