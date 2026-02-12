package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persist.TagDM;
import domain.ApplicationException;

/**
 * This class displays a form to allow the creation of new tags.
 */
public class NewTagPane extends VBox {

    private RootPane root;
    private TextField tagNameField;
    private Button createButton;
    
    public NewTagPane(RootPane root) {
        super();
        this.root = root;

        // Definir o layout
        
        this.setAlignment(Pos.BOTTOM_RIGHT);
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        
        
        
        // Adicionar o campo de texto para o nome da tag
        tagNameField = new TextField();
        tagNameField.setPromptText("Enter tag name");
        tagNameField.setPrefWidth(200);  // Ajuste de largura para tornar a TextField menor

       
        // Adicionar o botão para criar a tag
        createButton = new Button("Create Tag");
        createButton.setOnAction(e -> {
			try {
				doCreateNewTag();
			} catch (ApplicationException e1) {
				
				e1.printStackTrace();
			}
		});  // Ao clicar, cria a tag

 
        // Criar o HBox para alinhar os elementos no canto inferior direito
        HBox inputLayout = new HBox(10);// Espaço de 10 entre os elementos
       
        inputLayout.setAlignment(Pos.BOTTOM_RIGHT);
        
        inputLayout.setPadding(new Insets(10));
        
        
        inputLayout.getChildren().addAll(tagNameField, createButton);
      
        // Definir o crescimento do VBox para empurrar os elementos para baixo
  
        

        // Adicionar os elementos ao layout principal
        
        this.getChildren().addAll(inputLayout);
    }

    private void doCreateNewTag() throws ApplicationException {
    	
    	
        
        String tagName = tagNameField.getText().trim();
        
        
        if (tagName.isEmpty()) {
            
        } else {
                TagDM.getInstance().makeTag(tagName);
                root.getTagPane().refresh();
        }

        
        tagNameField.clear();
        
    }
 
}

