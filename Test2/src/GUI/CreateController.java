package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import shared.AccountDTO;
import shared.FieldVerifier;
import shared.GamePacket;

public class CreateController implements Initializable, ControlledScreen {

	private boolean isUser = false;
	private boolean  isEmail = false;
	private boolean isPassword = false;
	private boolean  isConfirmPassword = false;

	@FXML
	private PasswordField pass;

	@FXML
	private TextField confPass;

	@FXML
	private Button createBtn;

	@FXML
	private TextField user;

	@FXML
	private TextField email;

	@FXML
	private Button backToMainBtn;

	@FXML
	void createAccount(ActionEvent event) {
		//TODO knappen skal ikke aktiveres f�r at alle felter er udfyldt korrekt
		AccountDTO acc = new AccountDTO( user.getText(), email.getText(), pass.getText());
		GamePacket gp = new GamePacket("create", null, acc);
		myController.cc.sendPackets(gp);

		// h�ndterer svar fra serveren
		Task<Void> task = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				Thread.sleep(500);
				GamePacket gp = myController.cc.getCreateAccountPacket();
				if(((AccountDTO)gp.getPayload()) instanceof AccountDTO){
					myController.setScreen("login");
				}else{
					Platform.runLater(() ->	AlertBox.show("Error!","User already exists"));
				}
				return null;
			}

		};
		(new Thread(task)).start();
	}
	ScreenController myController;
	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
	}




	@FXML
	void userFieldHandler(KeyEvent event) {
		user.getStyleClass().remove("error");
		user.getStyleClass().remove("normal");
		if(FieldVerifier.userNameVerifier(user.getText())){
			isUser = true;

			user.getStyleClass().add("normal");
			enableCreateButton();
		}else{
			isUser = false;
			user.getStyleClass().add("error");
			enableCreateButton();
		}
	}

	@FXML
	void emailFieldHandler(KeyEvent event) {
		email.getStyleClass().remove("normal");
		email.getStyleClass().remove("error");
		if(!FieldVerifier.emailVerifier(email.getText())){
			email.getStyleClass().add("error");
			isEmail = false;
			enableCreateButton();
		}else{
			isEmail = true;

			email.getStyleClass().add("normal");

			enableCreateButton();
		}
	}

	@FXML
	void passFieldHandler(KeyEvent event) {
		pass.getStyleClass().remove("normal");
		pass.getStyleClass().remove("error");
		if(!FieldVerifier.passwordVerifier(pass.getText())){
			pass.getStyleClass().add("error");
			isPassword = false;
			enableCreateButton();
		}else{
			isPassword = true;
			pass.getStyleClass().add("normal");
			enableCreateButton();
		}
	}

	@FXML
	void confirmFieldHandler(KeyEvent event) {
		confPass.getStyleClass().remove("normal");
		confPass.getStyleClass().remove("error");
		if(!FieldVerifier.confirmedPasswordVerifier(pass.getText(), confPass.getText())){
			confPass.getStyleClass().add("error");
			isConfirmPassword = false;
			enableCreateButton();
		}else{
			isConfirmPassword = true;
			confPass.getStyleClass().add("normal");
			enableCreateButton();
		}
	}

	@FXML
	void backToMain(ActionEvent event) {
		myController.setScreen("login");
	}
	private void enableCreateButton(){
		if(isEmail && isPassword && isUser && isConfirmPassword){
			createBtn.setDisable(false);
		}else{
			createBtn.setDisable(true);
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createBtn.setDisable(true);

	}
}
