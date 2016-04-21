
package GUI;




import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import shared.GamePacket;
import shared.game.Chat;
import shared.game.ChatMessage;
import shared.game.Table;

public class ChatWindow extends VBox implements Runnable{
	TextArea ta;
	TextField tf;
	ScreenController sc;
	Table table;

	
	
	public ChatWindow(ScreenController sc, Table table){
		ta = new TextArea();
		tf = new TextField();
		this.sc = sc;

		this.table = sc.table;
		getChildren().addAll(ta, tf);
		tf.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					ChatMessage msg = new ChatMessage((String)tf.getText(), table.getGameID());
					sc.cc.sendPackets(new GamePacket("chatEntry", sc.cc.getToken(), msg));
                    tf.setText("");
				}
			}
		})	;
		new Thread(this).start();
	}

	public void updateChat(){


		GamePacket gp =	sc.cc.getChatPacket();
		if(gp.getPayload() instanceof Chat){
			ta.setText("");
			final ArrayList<String> 	chat	= ((Chat)gp.getPayload()).getChat();
			System.out.println("chat size fra klient" + chat.size());
			for (int i = 0; i < chat.size(); i++) {
				String entry = chat.get(i);
				Platform.runLater(() ->	ta.appendText(entry + "\n"));
					
			}

		}

	}
	@Override
	public void run() {

		while(true){
			updateChat();
		}

	}

}
