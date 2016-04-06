package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import game.Table;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.GamePacket;

public class mainmenuController implements ControlledScreen, Runnable, Initializable {

	ScreenController myController;
	ObservableList<Table> tables = null;

	@FXML
	private TableColumn<Table, String> column1;

	@FXML
	private Label userLabel;

	@FXML
	private Label pointsLabel;

	@FXML
	private TableColumn<Table, String> column2;

	@FXML
	private Button logOutBtn;

	@FXML
	private TableView<Table> lobbyTable;

	@FXML
	void logOut(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

     new Thread(this).start();
		

	}
	@Override
	public void run() {
		myController.cc.sendPackets(new GamePacket("getTables", null,null));
		System.out.println("sender jeg?");
		try {
			GamePacket getTables = myController.cc.getTables();
			if(getTables.getPayload() instanceof ArrayList<?>){
				ArrayList<Table> tempTable = (ArrayList<Table>) getTables.getPayload();
				tables = FXCollections.observableArrayList(tempTable);
				column1.setCellValueFactory(new PropertyValueFactory<Table,String>("tableName"));
				column2.setCellValueFactory(new PropertyValueFactory<Table,String>("noOfPlayers"));
				Platform.runLater(() -> lobbyTable.setItems(tables));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;

	}

}
