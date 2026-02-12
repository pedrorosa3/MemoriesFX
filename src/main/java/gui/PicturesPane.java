package gui;

import domain.ApplicationException;
import domain.Picture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * This layout class contains the instances of PictureViews shown as a list of
 * pages. The number of PictureView per page is fixed and set in the constructor.
 *
 * @author tl
 */
public class PicturesPane extends VBox {

    private List<domain.Picture> pictures;
    private RootPane root;
    private Pagination pagination;
    private int nbImagesPerPage;
    private List<PictureView> selected;
    private List<PictureView> picviews;

    public PicturesPane(RootPane root, Collection<domain.Picture> images,
                        int nbImagesPerPage) {
        super();
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");
        this.root = root;
        this.nbImagesPerPage = nbImagesPerPage;
        this.selected=new ArrayList<>();
        this.picviews=new ArrayList<>();
        for(PictureView picviewss : picviews) {
        	if(picviewss.getPic().isSelected()) {
        		selected.add(picviewss);
        	}
        }
        setPictures(images);
    }

    /**
     * This method is used to set or substitute the set of pictures visible in
     * the PicturesPane.
     *
     * @param searchResult
     */
    public void setPictures(Collection<Picture> searchResult) {
        // If search result is not empty
        if (searchResult != null && searchResult.size() > 0) {
            
            this.pictures = new ArrayList<>(searchResult);
        }
        pagination = new Pagination(nbPages());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
        this.getChildren().clear();
        this.getChildren().add(pagination);
        
        VBox.setVgrow(pagination, Priority.ALWAYS);
    }

    /**
     * This method is used in the pagination process.
     *
     * @param pageIndex
     * @return
     */
    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        box.setSpacing(5);
        if (pageIndex * this.nbImagesPerPage > pictures.size()) {
            pageIndex = 0;
        }
        int page = pageIndex * this.nbImagesPerPage;
        List<domain.Picture> pageItems
                = pictures.subList(page,
                                   (page + this.nbImagesPerPage < pictures.
                                           size()) ? page + this.nbImagesPerPage : pictures.
                                                   size());

        for (domain.Picture m : pageItems) {
            PictureView mv = new PictureView(root, m);
            if(m.isSelected()) {
            	selected.add(mv);
            }
            mv.setAlignment(Pos.CENTER);
            mv.setMaxWidth(Double.MAX_VALUE);
            VBox.setVgrow(mv,Priority.ALWAYS);
            box.getChildren().add(mv);
        }
        
        return box;
    }

    private int nbPages() {
        return (int) Math.ceil(1.0 * pictures.size() / this.nbImagesPerPage);
    }

    public void addTagToSelectedPictures(TagView tagView) throws ApplicationException {
    	
    	if(selected.isEmpty()) {
    		return ;
    	}
		for(PictureView pic : selected) {
    		pic.addTag(tagView);
    	}
        // for all selected images, add the corresponding tag.
    }

    public void removeTagFromPictures(TagView tv) throws ApplicationException {
    	for(PictureView pic : selected) {
    		pic.removeTag(tv);
    	}
        // for all selected pictures remove the corresponding tag
    }
    
    public boolean arepicturesselected() {
    	return !(selected.isEmpty());
    }
    
    public List<PictureView> getSelectedPictures(){
    	return this.selected;
    }
    
    public void addtoSelectedPic(PictureView v) {
    	this.selected.add(v);
    }
    
    public void removeSelectedPic(PictureView v) {
    	this.selected.remove(v);
    }

}
