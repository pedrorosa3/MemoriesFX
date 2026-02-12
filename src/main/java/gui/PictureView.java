package gui;

import domain.ApplicationException;
import domain.Picture;
import domain.Tag;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import persist.PictureDM;
import utils.Thumbnails;

/**
 * This class is responsible for displaying a pane that shows a picture and the
 * set of associated tags.
 *
 * @author tl
 */
public class PictureView extends HBox {

    private domain.Picture pic;
    // JavaFX for picture
    private ImageView iv;
    // A layout that for displaying a frame around the picture. It is used as a
    // visual feedback when the picture is selected. (color change).
    private HBox frame;
    // The image object (JavaFX API)
    private Image im;
    // private Label info;
    // A layout for the set of tags.
    private FlowPane tags;
    private RootPane root;

    // The background for PictureView
    private static BackgroundFill bf = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null);
    private static BackgroundFill framebf = new BackgroundFill(Color.RED, new CornerRadii(5), null);
    private static BackgroundFill pvback = new BackgroundFill(Color.GRAY, new CornerRadii(5), null);
    
   
    private static Background PVBackground = new Background(pvback);      
    private static Background frameBackground = new Background(bf);   
    private static Background selectedBackground = new Background(framebf);  

    private PictureView(RootPane root) {
    	
        super();
        this.root = root;
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setBackground(PVBackground);
        this.setMaxWidth(700);
        this.setSpacing(10);
        this.im = null;
        this.frame = new HBox();
        this.iv = new ImageView();
        this.iv.setFitWidth(220);
        this.iv.setPreserveRatio(true);
        
        iv.setOnMouseClicked(event -> {
            System.out.println("Image " + iv + " clicked!");
            try {
				toggleSelection();
			} catch (ApplicationException e) {
				
				e.printStackTrace();
			}
        });
        
        this.frame.getChildren().add(this.iv);
        // add ImageView to frame
        
        frame.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px;");
        
        this.tags = new FlowPane();
        tags.setPadding(new Insets(10));
        tags.setHgap(20);
        tags.setVgap(20);
        tags.setPrefWrapLength(200); // Permite quebra de linha automática
        tags.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px;");
        // create layout with TagViews
        
        this.getChildren().add(frame);
        this.getChildren().add(tags);
        // add frame and tags to 'this'.
  
        HBox.setHgrow(frame, Priority.ALWAYS);
        // for layout that contains TagViews:
        HBox.setHgrow(tags, Priority.NEVER);
        
    }

    /**
     * This constructor is used in the pagination process.
     *
     * @param root
     * @param m
     */
    
    public PictureView(RootPane root, domain.Picture m) {
        this(root);
        setImage(m);
    }

    
    
    private void toggleSelection() throws ApplicationException {
        if (pic.isSelected()) {
        	
            unselect();
            
            root.getPicturesPane().removeSelectedPic(this);
            
        } else {
        	
            select();
            
            root.getPicturesPane().addtoSelectedPic(this);
        }
    }

    public void select() throws ApplicationException {
    	this.setBackground(selectedBackground);
        // change the background of the image.
        // do whatever is needed.
    	
        pic.select();
        PictureDM.getInstance().update(pic);
    }

    public void unselect() throws ApplicationException {
    	
    	this.setBackground(frameBackground);
        // change the background of the image.
        // do whatever is needed.
        // in domain.Picture define the method unselect
    	
        pic.unselect();
        PictureDM.getInstance().update(pic);
    }

    @Override
    public String toString() {
        return "PictureView" + this.hashCode() + " [pic=" + pic + ", iv="
               + iv + ", im=" + im + "]";
    }

    private void setImage(domain.Picture im) {
        this.pic = im;
        
        if (pic.isSelected()) {
        	
        	this.setBackground(selectedBackground);
            // change background
        } else {
        	
        	this.setBackground(frameBackground);
            // change background
        }
        
        
        
        
        this.im = new Image("file:" + Thumbnails.thumbnailFilename(pic));
        
        
        
        this.iv.setImage(this.im);
        frame.getChildren().clear();
        frame.getChildren().add(this.iv);
        tags.getChildren().clear();
        for (Tag t : im.getTags()) {
        	TagView newTagView = new TagView(root,t,GuiElement.PICTURE_VIEW);
        	
        	tags.getChildren().add(newTagView);
            // create a new TagView for tag t
            // add to the interface
        }
    }

    // Note: the signature of following methods may be modified.
    public void addTag(TagView tagView) throws ApplicationException {
    	Tag tag  = tagView.getTag();
    	if(!(pic.hasTag(tag))) {
    		PictureDM.getInstance().addTag(pic, tag);
    		TagView tv = new TagView(root,tag,GuiElement.PICTURE_VIEW);
    		tags.getChildren().add(tv);
    		
    	}
        // Add corresponding tag to picture
        // create a new TagView
        // add to interface
    }

    public void removeTag(TagView tagView) throws ApplicationException {
    	Tag tag  = tagView.getTag();
    	if(pic.hasTag(tag)) {
    		PictureDM.getInstance().removeTag(pic, tag);
    		tags.getChildren().remove(tagView);
    		
    	}
        // remove tag from picture
        // remove Tagview from interface.
    }
    
    
    
    public Picture getPic () {
    	return pic;
    }

}
