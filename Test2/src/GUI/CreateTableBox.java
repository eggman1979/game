package GUI;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.GamePacket;
public class CreateTableBox {

		
	private static VBox box;
	private static Label label;
	private static Button closeBtn;
	private static Stage stage;
	private static Scene scene;
	private static TextField tableName;
	private ScreenController sc;
	String table;

	public CreateTableBox(ScreenController sc){
		this.sc = sc;
	}
		public void show(){
		stage = new Stage();
		
		stage.initModality(Modality.NONE);
		stage.setTitle("Create new Table");
		stage.setWidth(200);
		stage.setHeight(130);
		
	    label = new Label("Enter the Name of the Table");
	    tableName = new TextField();
		closeBtn = new Button("create");
		closeBtn.setOnAction(e -> createTable());
		box = new VBox(10);
	    box.getChildren().addAll(label,tableName, closeBtn);
	    box.setAlignment(Pos.CENTER);
	    
	    scene = new Scene(box,200,130);
	    stage.setScene(scene);
	    stage.show();
		}
		
		private void createTable(){
			table = tableName.getText();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					sc.cc.sendPackets(new GamePacket("createTable", sc.cc.getToken(), table));
					
				}
			}).start();		
			stage.hide();
		}
}
