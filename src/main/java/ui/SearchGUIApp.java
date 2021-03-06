package ui;

import controller.SearchGUIAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SearchGUIApp extends Application {

    private Stage primaryStage;

    public static void main(String args[]) throws IOException, SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        search();
    }

    public void search() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchGUIApp.fxml"));
        Parent root = loader.load();

        SearchGUIAppController controller = loader.getController();
        controller.setMain(this);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Search DB");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
