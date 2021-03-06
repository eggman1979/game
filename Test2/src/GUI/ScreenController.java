package GUI;

import java.io.IOException;
import java.util.HashMap;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import klient.ClientController;
import shared.game.Table;

public class ScreenController extends StackPane {

	public static final String MAIN_MENU = "main";
	public static final String MAIN_MENU_FXML = "main.fxml";

	public static final String TABLE = "table";
	public static final String TABLE_FXML = "table.fxml";

	private HashMap<String, Node> screens = new HashMap<>(); 

	FXMLLoader myLoader;
	Parent loadScreen;
	ClientController cc;
	Table table;

	public ScreenController() throws IOException{
		this.cc = new ClientController();
	}

	public void addScreen(String name, Node screen) {
		screens.put(name, screen);
	} 

	public boolean loadScreen(String name, String resource) {
		try {

			myLoader = new FXMLLoader(getClass().getResource(resource));
			loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenControler =	((ControlledScreen) myLoader.getController());
			myScreenControler.setScreenParent(this);
			myScreenControler.init(table);



			addScreen(name, loadScreen);
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	} 
	public boolean setScreen(final String name) {     

		if (screens.get(name) != null) {   //screen loaded
			final DoubleProperty opacity = opacityProperty();
			if (!getChildren().isEmpty()) {    //if there is more than one screen
				Timeline fade = new Timeline(
						new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
						new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent t) {
								getChildren().remove(0);                    //remove the displayed screen
								getChildren().add(0, screens.get(name));     //add the screen

								Timeline fadeIn = new Timeline(
										new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
										new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
								fadeIn.play();
							}
						}, new KeyValue(opacity, 0.0)));
				fade.play();
			} else {
				//no one else been displayed, then just show
				setOpacity(0.0);
				getChildren().add(screens.get(name));
				Timeline fadeIn = new Timeline(
						new KeyFrame(Duration.ZERO,
								new KeyValue(opacity, 0.0)),
						new KeyFrame(new Duration(2500),
								new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}
			return true;
		} else {
			System.out.println("screen hasn't been loaded!\n");
			return false;
		}
	} 



	public void loadMainScreen(){
		loadScreen(MAIN_MENU, MAIN_MENU_FXML );
		setScreen(MAIN_MENU);
	}

	public void loadTableScreen(Table table){
		this.table = table;
		loadScreen(TABLE, TABLE_FXML);
		setScreen(TABLE);
	}


}
