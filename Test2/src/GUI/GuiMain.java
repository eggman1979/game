package GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application{

     public static final String LOGIN_SCREEN = "login";
     public static final String LOGIN_SCREEN_FXML = "login.fxml";
     

     public static final String MAIN = "main";
     public static final String MAIN_FXML = "main.fxml";
    
     
      public static final String CREATE_ACCOUNT_SCREEN = "create";
     public static final String CREATE_ACCOUNT_SCREEN_FXML = "create.fxml";
                                         
     ScreenController mainContainer;
     @Override
     public void start(Stage primaryStage) throws IOException {

       mainContainer = new ScreenController();
       mainContainer.loadScreen(GuiMain.LOGIN_SCREEN, GuiMain.LOGIN_SCREEN_FXML);
//       mainContainer.loadScreen(GuiMain.MAIN,  GuiMain.MAIN_FXML,null);
       mainContainer.loadScreen(GuiMain.CREATE_ACCOUNT_SCREEN,GuiMain.CREATE_ACCOUNT_SCREEN_FXML);
               
       

//       mainContainer.setScreen(GuiMain.CREATE_ACCOUNT_SCREEN);
       mainContainer.setScreen(GuiMain.LOGIN_SCREEN);
       

       Group root = new Group();
       root.getChildren().addAll(mainContainer);
       Scene scene = new Scene(root);
       scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
       primaryStage.setScene(scene);
       primaryStage.show();
     }
     
     public ScreenController getScreenController(){
    	 return mainContainer;
     }

}