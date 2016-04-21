package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import shared.game.Table;

public class TableController implements ControlledScreen, Runnable, Initializable{
	Table table;
	
	ScreenController myController;

	@FXML
	private VBox chatBox;
	

	@Override
	public void setScreenParent(ScreenController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void init(Object data) {
		if(data instanceof Table){
			this.table = (Table)data;
		    chatBox.getChildren().add(new ChatWindow(myController, table));
		  
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    

	}

	@Override
	public void run() {


	}

}
