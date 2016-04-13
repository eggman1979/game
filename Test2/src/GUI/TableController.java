package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import game.Table;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

public class TableController implements ControlledScreen, Runnable, Initializable{
	Table table;
	
	ScreenController myController;
	@Override
	public void setScreenParent(ScreenController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void init(Object data) {
		System.out.println(data);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
