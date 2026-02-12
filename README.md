# MemoriesFX - Desktop Photo Manager 🖼️💻

## About the Project
**MemoriesFX** is a desktop application developed in **Java** using the **JavaFX** framework. It serves as the graphical interface (GUI) implementation for the backend logic developed in the *MemoriesDB* project.

While the core logic remains focused on the relationships between **Pictures**, **Albums**, and **Tags**, this project introduces a visual layer that allows users to interact with their photo library through an intuitive dashboard rather than a command-line interface.

## Key Features

### 🖥️ Graphical User Interface (GUI)
* **Gallery View:** Displays pictures in a responsive grid or tile pane layout.
* **Image Viewer:** Click on a thumbnail to view the full-size image with its details.
* **Interactive Lists:** Sidebars to navigate between different Albums and select Tags for filtering.

### 🗂️ Management Tools
* **Album Creation:** Create new albums via dialog boxes and drag-and-drop pictures into them.
* **Tagging System:** Visually add or remove tags from selected photos.
* **Smart Search:** Filter the main gallery instantly by selecting specific tags or searching by description.

## Architecture
The project follows the **MVC (Model-View-Controller)** architectural pattern:

1.  **Model:** The business logic (reused from MemoriesDB). Handles the `Picture`, `Album`, and `Tag` classes and their data associations.
2.  **View:** Defined in **FXML** files. Describes the layout of the windows, buttons, image views, and lists.
3.  **Controller:** Java classes that handle user events (clicks, input) and update the View based on the Model's data.

## Technologies Used
* **Language:** Java
* **GUI Framework:** JavaFX
* **Layout:** FXML (Scene Builder)
* **Styling:** CSS (for UI customization)

## How to Run

### Prerequisites
* JDK 11 or higher (JDK 17+ recommended).
* JavaFX SDK (if not bundled with your JDK).
* Maven or Gradle (recommended for dependency management).

### Execution Steps
1.  **Clone the repository.**
2.  **Import** the project into your IDE (IntelliJ IDEA or Eclipse).
3.  **Configure JavaFX:**
    * If using Maven, run `mvn javafx:run`.
    * If running manually, ensure VM options are set:
        `--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml`
4.  **Launch:** Run the `Main.java` (or `App.java`) class to open the application window.

Important Notes:

To select an image, you must click on the photo.

To remove a tag from one or multiple photos, the photo must be selected (by clicking on the ImageView), just as is required when adding tags.

To perform actions related to Albums, follow these steps:

1. Create Album:

1.1 - Fill in the Album Name field.

1.2 - Fill in the Album Tag field with the name you want for the tag.

1.3 - Fill in the Album Description field.

1.4 - Click the Create button.

2. Add Photos to Album:

2.1 - Select the desired Album (by clicking on the AlbumView that appears in the FlowPane).

2.2 - A VBox displaying the album data will appear.

2.3 - Select the photographs you want to add to the album (by clicking on the ImageView).

2.4 - Click the SetPictures button.

3. Remove Photos from Album:

3.1 - You must have an Album selected, as explained previously.

3.2 - Select the photographs you wish to remove from the album (using the same method explained previously).

3.3 - Click the RemovePictures button.

4. Delete Album:

4.1 - Select an album (as explained previously).

4.2 - Click the Delete Album button located in the VBox containing the album information.


---
*This project was developed for educational purposes.*
