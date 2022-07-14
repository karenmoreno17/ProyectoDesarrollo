package Vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafx.stage.StageStyle.UNDECORATED;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        StageStyle StageStyle = UNDECORATED;
        stage = new Stage(StageStyle);

        Parent parent = FXMLLoader.load(getClass().getResource("/Vista/GUI.fxml"));
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.getIcons().add(new Image("/Recursos/logo-recortado.png"));
        stage.show();
    }
}
