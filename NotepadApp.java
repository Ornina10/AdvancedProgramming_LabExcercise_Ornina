import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

public class NotepadApp extends Application {
    private final TextArea textArea = new TextArea();
    private final Label statusLabel = new Label("Ready");
    private final Label statsLabel = new Label("Words: 0 | Chars: 0");
    private final Label zoomLabel = new Label("100%");
    private File currentFile;
    private boolean dirty = false;
    private double currentFontSize = 14;

    @Override
    public void start(Stage stage) {
        textArea.setFont(Font.font(currentFontSize));
        textArea.setWrapText(true);
        textArea.textProperty().addListener((obs, oldVal, newVal) -> {
            dirty = true;
            updateStatus();
            updateStats();
        });

        BorderPane root = new BorderPane();
        VBox topContainer = new VBox(createMenuBar(stage), createToolbar(stage));
        root.setTop(topContainer);
        root.setCenter(textArea);
        root.setBottom(createStatusBar());
        BorderPane.setMargin(textArea, new Insets(0, 10, 10, 10));
        root.setStyle("-fx-background-color: #f8fafc;");

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("JavaFX Notepad");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if (!confirmDiscardIfNeeded(stage)) {
                event.consume();
            }
        });
        stage.show();
        updateStatus();
        updateStats();
    }

    private ToolBar createToolbar(Stage stage) {
        Button newBtn = new Button("New");
        newBtn.setOnAction(e -> newFile(stage));
        Button openBtn = new Button("Open");
        openBtn.setOnAction(e -> openFile(stage));
        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> saveFile(stage));
        Button saveAsBtn = new Button("Save As");
        saveAsBtn.setOnAction(e -> saveFileAs(stage));
        Button wordCountBtn = new Button("Word Count");
        wordCountBtn.setOnAction(e -> showWordCount());
        Button zoomOutBtn = new Button("A-");
        zoomOutBtn.setOnAction(e -> adjustZoom(-1));
        Button zoomInBtn = new Button("A+");
        zoomInBtn.setOnAction(e -> adjustZoom(1));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ToolBar toolBar = new ToolBar(
                newBtn, openBtn, saveBtn, saveAsBtn,
                wordCountBtn,
                spacer,
                zoomOutBtn, zoomInBtn, zoomLabel
        );
        toolBar.setStyle("-fx-background-color: #e2e8f0;");
        return toolBar;
    }

    private MenuBar createMenuBar(Stage stage) {
        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        newItem.setOnAction(e -> newFile(stage));

        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(e -> openFile(stage));

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> saveFile(stage));

        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setOnAction(e -> saveFileAs(stage));

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> {
            if (confirmDiscardIfNeeded(stage)) {
                stage.close();
            }
        });

        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem, new SeparatorMenuItem(), exitItem);

        Menu editMenu = new Menu("Edit");
        MenuItem cutItem = new MenuItem("Cut");
        cutItem.setOnAction(e -> textArea.cut());
        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(e -> textArea.copy());
        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(e -> textArea.paste());
        MenuItem selectAllItem = new MenuItem("Select All");
        selectAllItem.setOnAction(e -> textArea.selectAll());
        editMenu.getItems().addAll(cutItem, copyItem, pasteItem, new SeparatorMenuItem(), selectAllItem);

        Menu toolsMenu = new Menu("Tools");
        MenuItem wordCountItem = new MenuItem("Word Count");
        wordCountItem.setOnAction(e -> showWordCount());
        toolsMenu.getItems().add(wordCountItem);

        return new MenuBar(fileMenu, editMenu, toolsMenu);
    }

    private HBox createStatusBar() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox bar = new HBox(10, statusLabel, spacer, statsLabel);
        bar.setPadding(new Insets(8, 10, 8, 10));
        bar.setStyle("-fx-background-color: #e2e8f0; -fx-border-color: #cbd5e1; -fx-border-width: 1 0 0 0;");
        return bar;
    }

    private void newFile(Stage stage) {
        if (!confirmDiscardIfNeeded(stage)) return;
        textArea.clear();
        currentFile = null;
        dirty = false;
        updateStatus();
        updateStats();
    }

    private void openFile(Stage stage) {
        if (!confirmDiscardIfNeeded(stage)) return;
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Text File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selected = chooser.showOpenDialog(stage);
        if (selected == null) return;

        try {
            String content = Files.readString(selected.toPath(), StandardCharsets.UTF_8);
            textArea.setText(content);
            currentFile = selected;
            dirty = false;
            updateStatus();
            updateStats();
        } catch (IOException ex) {
            showError("Could not open file:\n" + ex.getMessage());
        }
    }

    private void saveFile(Stage stage) {
        if (currentFile == null) {
            saveFileAs(stage);
            return;
        }
        writeToFile(currentFile);
    }

    private void saveFileAs(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Text File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selected = chooser.showSaveDialog(stage);
        if (selected == null) return;
        currentFile = selected;
        writeToFile(currentFile);
    }

    private void writeToFile(File file) {
        try {
            Files.writeString(file.toPath(), textArea.getText(), StandardCharsets.UTF_8);
            dirty = false;
            updateStatus();
            updateStats();
        } catch (IOException ex) {
            showError("Could not save file:\n" + ex.getMessage());
        }
    }

    private boolean confirmDiscardIfNeeded(Stage stage) {
        if (!dirty) return true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("You have unsaved changes.");
        alert.setContentText("Do you want to save before continuing?");

        ButtonType save = new ButtonType("Save");
        ButtonType dontSave = new ButtonType("Don't Save");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(save, dontSave, cancel);

        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.isEmpty() || choice.get() == cancel) {
            return false;
        }
        if (choice.get() == save) {
            saveFile(stage);
            return !dirty;
        }
        return true;
    }

    private void showWordCount() {
        String text = textArea.getText().trim();
        int words = text.isEmpty() ? 0 : text.split("\\s+").length;
        int chars = textArea.getText().length();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Word Count");
        alert.setHeaderText("Document Stats");
        alert.setContentText("Words: " + words + "\nCharacters: " + chars);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText("Operation Failed");
        alert.showAndWait();
    }

    private void updateStatus() {
        String fileName = currentFile == null ? "Untitled" : currentFile.getName();
        String mark = dirty ? " (modified)" : "";
        statusLabel.setText(fileName + mark);
    }

    private void updateStats() {
        String content = textArea.getText();
        String trimmed = content.trim();
        int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
        int chars = content.length();
        statsLabel.setText("Words: " + words + " | Chars: " + chars);
    }

    private void adjustZoom(int step) {
        currentFontSize = Math.max(10, Math.min(30, currentFontSize + step));
        textArea.setFont(Font.font(currentFontSize));
        int zoomPercent = (int) Math.round((currentFontSize / 14.0) * 100.0);
        zoomLabel.setText(zoomPercent + "%");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
