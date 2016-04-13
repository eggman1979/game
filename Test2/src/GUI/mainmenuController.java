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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import shared.GamePacket;

public class mainmenuController implements ControlledScreen, Runnable, Initializable {

	ScreenController myController;
	ObservableList<Table> tables = null;
	Table table;

	@FXML
	private TableColumn<Table, String> column1;

	@FXML
	private Label userLabel;

	@FXML
	private Label pointsLabel;

	@FXML
	private Button joinBtn;

	@FXML
	private Button createTableBtn;

	@FXML
	private Button logOutBtn;

	@FXML
	private ListView<Table> lobbyTable;

	@FXML
	void logOut(ActionEvent event) {
		myController.setScreen("login");
		myController.cc.sendPackets(new GamePacket("logout", myController.cc.getToken(), null));
	}

	@FXML
	void joinTable(ActionEvent event) {
		GamePacket gp = new GamePacket("joinTable", myController.cc.getToken(), table);
		myController.cc.sendPackets(gp);
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				myController.cc.getTables();
				return null;
			}
		};
		myController.loadTableScreen(table);

	}


	@FXML
	void tableClick(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY){
			table = lobbyTable.getSelectionModel().getSelectedItem();
			System.out.println(table);
		}
		event.consume();
	}

	@FXML
	void createTable(ActionEvent event) {
		CreateTableBox gtb = new CreateTableBox(myController);
		gtb.show();
		Task<Void> task = new Task<Void>(){
        
			@Override
			protected Void call() throws Exception {
				while(gtb.isOn()){
					// Løkke der kører indtil vinduet er lukket
					Thread.sleep(100);
					System.out.println(gtb.isOn());
					
				}

				initialize(null, null);
				return null;
			}
		};
		new Thread(task).start();





	}
	/*
	 * Below is various non FXML RELATED METHODS
	 * 
	 */


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new Thread(this).start();
	}

	@Override
	public void run() {
		myController.cc.sendPackets(new GamePacket("getTables", null,null));
		System.out.println("sender jeg?");

		GamePacket getTables = myController.cc.getTables();
		if(getTables.getPayload() instanceof ArrayList<?>){
			@SuppressWarnings("unchecked")
			ArrayList<Table> tempTable = (ArrayList<Table>) getTables.getPayload();
			tables = FXCollections.observableArrayList(tempTable);
			Platform.runLater(() -> lobbyTable.setItems(tables));

			lobbyTable.setCellFactory(new Callback<ListView<Table>, ListCell<Table>>(){

				@Override
				public ListCell<Table> call(ListView<Table> p) {

					ListCell<Table> cell = new ListCell<Table>(){

						@Override
						protected void updateItem(Table t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								setText(t.getTableName() + ": " + t.getNoOfPlayers());

							}
						}

					};
					return cell;
				}
			});
		}

	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;

	}
	@Override
	public void init(Object data) {
		// TODO Auto-generated method stub

	}
}
