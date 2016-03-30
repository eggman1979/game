



package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import com.mysql.jdbc.Field;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
	void createAccount(ActionEvent event) {
		//TODO knappen skal ikke aktiveres f�r at alle felter er udfyldt korrekt
		AccountDTO acc = new AccountDTO( user.getText(), email.getText(), pass.getText());
		GamePacket gp = new GamePacket("create", null, acc);
		myController.cc.sendPackets(gp);

		// h�ndterer svar fra serveren
		Task task = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				Thread.sleep(500);
				GamePacket gp = myController.cc.getCreateAccountPacket();
				if(((AccountDTO)gp.getPayload()) instanceof AccountDTO){
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
		if(!FieldVerifier.userNameVerifier(user.getText())){
			isUser = false;
			user.getStyleClass().add("error");
			enableCreateButton();
		}else{
			isUser = true;
			user.getStyleClass().add("normal");
			enableCreateButton();
		}
	}

	@FXML
	void emailFieldHandler(KeyEvent event) {
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
