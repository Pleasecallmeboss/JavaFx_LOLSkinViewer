package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Fxml.TableViewController;

public class Main extends Application
{
    private ObservableList<Heros> HerosList;
    private FXMLLoader loader;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Parent root = FXMLLoader.load(getClass().getResource("Fxml/sample.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("Fxml/TableView.fxml"));
         loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Fxml/TableView.fxml"));
        //System.out.println(loader.load().toString());
        Parent root = loader.load();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        //initList();
        translateList();
        primaryStage.show();


    }


    public static void main(String[] args)
    {
        launch(args);
    }

    private void initList()
    {
        HerosList = FXCollections.observableArrayList(
                new Heros(), new Heros("osnd", "djfs","sdfsdf")
        );

    }

    public ObservableList<Heros> getHerosList()
    {
        return HerosList;
    }

    public void translateList()
    {
        //FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("Fxml/TableView.fxml"));


        TableViewController controller = loader.getController();
        System.out.println(controller.toString());
        System.out.println(this.toString());
        controller.setMain(this);

    }

    public Stage getStage()
    {
        return stage;
    }
}
