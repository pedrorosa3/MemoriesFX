package gui;

import domain.Tag;
import javafx.scene.control.Label;
import java.util.Objects;

import domain.ApplicationException;

/**
 * This class represents a tag in the GUI
 *
 * @author tl
 */
public class TagView extends Label implements Comparable<TagView> {

    private Tag tag;
    private RootPane root;
    private GuiElement belongsTo;
    
    private SearchPane search;

    /**
     * Main constructor
     *
     * @param root
     * @param t
     * @param belongsTo
     */
    
    public TagView(RootPane root, Tag t, GuiElement belongsTo) {
        super(t.getNome());
        this.tag=t;
        this.belongsTo = belongsTo;
        this.root = root;
        this.search = this.root.getSearchPane();
        
        this.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-padding: 5px;");
        // Define the padding
        
        this.setOnMouseClicked(event -> {
            System.out.println("TagView " + this.tag.getNome() + " clicked!");
            try {
                executeAction();
            } catch (ApplicationException ex) {
                // it may not be necessary to catch an exception here
                // anyways you know how it should be done.
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * This constructor is used to obtain a copy of a TagView. This constructor
     * is useful when we want an existing TagView to appear somewhere else in
     * the interface.
     *
     * @param t
     * @param belongsTo
     */
    public TagView(TagView t, GuiElement belongsTo) {
        this(t.root, t.getTag(), belongsTo);
    }

    @Override
    public int compareTo(TagView t) {
    	return this.tag.getNome().compareTo(t.tag.getNome());
        // change the following line such that the ordering is alphabetical
        // (using tag's name)
    }

    @Override
    public String toString() {
        return "TagView[tag=" + tag + "]";
    }

    /**
     * This method is executed when a TagView is clicked. The belongsTo
     * attribute correspond the place of the TagView. It may belong to: 1) the
     * SearchPane 2) a PictureView 3) the TagPane. TODO: define a value in enum
     * GuiElement for each case.
     *
     * @throws ApplicationException
     */
    
    private void executeAction() throws ApplicationException {
    	
        switch (belongsTo) {
        
        	case SEARCH_PANE:
        		search.removeTagFromSearch(this);
            		
        		break;
        	
        	case PICTURE_VIEW:  
        		doRemoveTagFromPictures();
        		
            	break;
        		
        	case TAG_PANE:
        		if(root.getPicturesPane().arepicturesselected()) {
        			doAddTagToPictures();
        		}
        			
        		else {
        			TagView copiatagview = new TagView (this, GuiElement.SEARCH_PANE);
        			if(search.getTagSearch().contains(copiatagview.tag)){	
        			}
        			
        			else{
        				search.addTagToSearch(copiatagview);
        			}
        		}
            // case 1) the action must remove the tag from the search query.
            //      break;
            // case 2) the action must remove the tag from the picture.
            //
            // break;
            // case 3) 2 possibilities:
            //         - 1 or more pictures are selected -> add tag to pictures
            //         - else add tag to search query
        }
    }

    private void doRemoveTagFromPictures() throws ApplicationException {
    	
        root.getPicturesPane().removeTagFromPictures(this);
    }

    private void doAddTagToPictures() throws ApplicationException {
    	
    	TagView newTagView = new TagView(this,GuiElement.PICTURE_VIEW);
    	
    	root.getPicturesPane().addTagToSelectedPictures(newTagView);
        // create a new TagView that represent the same tag as 'this'.
        // add the TagView to selected pictures in TagPane.
    }
    
    public Tag getTag() {
        return this.tag;
    }

	@Override
	public int hashCode() {
		return Objects.hash(belongsTo, tag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagView other = (TagView) obj;
		return belongsTo == other.belongsTo && Objects.equals(tag, other.tag);
	}
}
