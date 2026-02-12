package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persist.PictureDM;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import domain.ApplicationException;
import domain.Picture;
import domain.Tag;

public class SearchPane extends VBox {

    private FlowPane selectedTags;
    private RootPane root;

    SearchPane(RootPane root) {
        super();
        this.root = root;

        // Rótulo "Search"
        Label label = new Label("Search:");
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");
        label.setAlignment(Pos.TOP_LEFT);

        // Área de seleção de tags
        selectedTags = new FlowPane();
        selectedTags.setPadding(new Insets(10));
        selectedTags.setHgap(5);
        selectedTags.setVgap(5);
        selectedTags.setPrefWrapLength(200); // Permite quebra de linha automática
        selectedTags.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px;");

        // Caixa para botões com espaçamento
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        // Botões "All", "Any" e "Reset"
        Button allButton = new Button("All");
        allButton.setOnAction(e -> {
            try {
                doSearchAll();
            } catch (ApplicationException e1) {
                e1.printStackTrace();
            }
        });

        Button anyButton = new Button("Any");
        anyButton.setOnAction(e -> {
            try {
                doSearchAny();
            } catch (ApplicationException e1) {
                e1.printStackTrace();
            }
        });

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            try {
                doReset();
            } catch (ApplicationException e1) {
                e1.printStackTrace();
            }
        });

        // Adiciona os botões ao HBox
        buttonBox.getChildren().addAll(allButton, anyButton, resetButton);

        // Adiciona os componentes ao VBox principal
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(label, selectedTags, buttonBox);
    }

    private void doReset() throws ApplicationException {
        selectedTags.getChildren().clear();
        Collection<Picture> recent = PictureDM.getInstance().getRecentPictures();
        root.getPicturesPane().setPictures(recent);
    }

    private void doSearchAll() throws ApplicationException {
        Set<Tag> tagSet = new HashSet<>();
        for (Node node : selectedTags.getChildren()) {
            if (node instanceof TagView) {
                TagView textNode = (TagView) node;
                tagSet.add(textNode.getTag());
            }
        }

        Collection<Picture> result = PictureDM.getInstance().findByTags(tagSet);
        root.getPicturesPane().setPictures(result);
    }

    private void doSearchAny() throws ApplicationException {
        Set<Tag> tagSet = new HashSet<>();
        for (Node node : selectedTags.getChildren()) {
        	if (node instanceof TagView) {
                TagView textNode = (TagView) node;
                tagSet.add(textNode.getTag());
            }
        }

        Collection<Picture> result = PictureDM.getInstance().findByAnyTags(tagSet);
        root.getPicturesPane().setPictures(result);
    }

    public void addTagToSearch(TagView t) {
        selectedTags.getChildren().add(t);
    }

    public void removeTagFromSearch(TagView t) {
        selectedTags.getChildren().remove(t);
    }
    public Set<Tag> getTagSearch() {
    	
    	Set<Tag> tagSet = new HashSet<>();
        for (Node node : selectedTags.getChildren()) {
        	if (node instanceof TagView) {
                TagView textNode = (TagView) node;
                tagSet.add(textNode.getTag());
            }
    	
        }
    	return tagSet;
    }
}
