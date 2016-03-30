package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ClientConnection.InClientConnection;
import ClientConnection.OutConnectionClient;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import klient.ClientController;
import klient.ClientStates;
import server.InConnectionServer;
import shared.AccountDTO;
import shared.GamePacket;
import shared.Token;

public class LoginController implements Runnable, Initializable, ControlledScreen{
	GuiMain gm = new GuiMain();

	ScreenController myController;

	ClientController cc;
	boolean isStartedFlag = false;

	@FXML
	private ProgressIndicator progress;

	@FXML
	private Button loginBtn;

	@FXML
	private PasswordField pass;

	@FXML
	private TextField user;

	@FXML
	private Button createBtn;
	@FXML
	private Label info;

	// Action når login bliver klikket
	@FXML
	void login(ActionEvent event) throws IOException {
		String login = user.getText()+"," +pass.getText();
		myController.cc.setState(ClientStates.LOGIN);
		myController.cc.sendPackets(new GamePacket("login", null, login));
	
		progress.setVisible(true);
		progress.setProgress(-1f);
		


		Task<Void> task = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				Thread.sleep(1000);
			GamePacket gp =	myController.cc.getLoginPacket();
			AccountDTO acc = (AccountDTO)gp.getPayload();
			System.out.println(gp.getToken().getTokenID());
				
				if(gp.getToken().getTokenID() != null){
					myController.setScreen("main");
					System.out.println("main");
				}else {
					info.setText("Wrong username or password");
					System.out.println("jeg kommer i if else");
					progress.setVisible(false);
				}
			
				return null;
			}




		};
		new Thread(task).start();
		

	}



	@FXML
	void loadCreateAccount(ActionEvent event) {
		myController.setScreen(GuiMain.CREATE_ACCOUNT_SCREEN);

	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void setScreenParent(ScreenController screenParent){
		myController = screenParent;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub

	} 

}
