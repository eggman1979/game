package GUI;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import klient.ClientController;
import klient.ClientStates;
import shared.AccountDTO;
import shared.GamePacket;


public class LoginController implements Runnable, Initializable, ControlledScreen{
	GuiMain gm = new GuiMain();


	ScreenController myController;
	Alert alert;

	ClientController cc;
	boolean isConnected = false;


	@FXML
	private Label counter;

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

	// Action n�r login bliver klikket
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
				System.out.println(acc);

				if(!(null == acc)){
					myController.setScreen("main");
					System.out.println("main");
					System.out.println("login Controller if");
				}else {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							AlertBox.show("Error", "Error in user name or password");
							progress.setVisible(false);

						}
					});
					System.out.println("login Controller else");



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

		new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 20; i >= 0; i--){
					try{
						String status = i + " seconds";

						Platform.runLater(() -> counter.setText("" + status));
						if(isConnected){
							Platform.runLater(() -> counter.setText("Connected"));
							break;
						}
						Thread.sleep(1000);

					}catch(InterruptedException e){

					}
				}

			}
		}).start();


		new Thread(this).start();
	}

	public void setScreenParent(ScreenController screenParent){
		myController = screenParent;
	}



	@Override
	public void run() {
		myController.cc.sendPackets(new GamePacket("init",null,null));
		GamePacket init = myController.cc.initServerConnection();
		if(null != init){
			isConnected = true;
		}else{
			Platform.runLater(() ->	counter.setText("No connection to server"));
			initialize(null, null);
		}

	} 

}
