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

	// Action når login bliver klikket
	// Henter brugerens indtastninger og sender til serveren
	// Herefter kommer der en spinner der viser at der sker noget.
	// En task bliver kørt -> henter en pakke ind, tjekker om den er
	// er valid. hvis ja sender brugeren videre til main screen
	// ellers kommer der en fejlmeddelse
	@FXML
	void login(ActionEvent event) throws IOException {
		// Opretter AccountDTO og sender til serveren
		AccountDTO userLogin = new AccountDTO(user.getText(), pass.getText());
		myController.cc.sendPackets(new GamePacket("login", null, userLogin));
		
		//Viser at der sker noget i baggrunden
		progress.setVisible(true);
		progress.setProgress(-1f);

		// Task der har til opgave at hente næste pakke fra InConnection
		Task<Void> task = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				// Starter med at vente, så man er sikker på at der er kommet noget.
				Thread.sleep(1000);
				GamePacket gp =	myController.cc.getLoginPacket();
				AccountDTO acc = (AccountDTO)gp.getPayload();
				System.out.println(acc);
				
				// Evaluerer om den sendte pakke er blevet godkendt af serveren
				if(!(null == acc)){
					myController.setScreen("main");
					System.out.println("main");
				}else {
					// Kalder et popup fejlmeddelelse
					Platform.runLater(() -> {
							AlertBox.show("Error", "Error in user name or password");
							progress.setVisible(false);
	
					});
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
		createBtn.setDisable(true);
		loginBtn.setDisable(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 20; i >= 0; i--){
					try{
						String status = i + " seconds";

						Platform.runLater(() -> counter.setText("" + status));
						if(isConnected){
							Platform.runLater(() -> counter.setText("Connected"));
							createBtn.setDisable(false);
							loginBtn.setDisable(false);
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

	// Run metoden der under init sender en initpacket til serveren
	// Sætter isConnected flaget til true, hvis der kommer noget tilbage,
	// ellers kører den init igen.
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
