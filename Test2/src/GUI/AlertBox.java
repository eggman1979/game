package GUI;



import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
private static VBox box;
private static Label label;
private static Button closeBtn;
private static Stage stage;
private static Scene scene;

	public static void show(String title, String message){
	stage = new Stage();
	
	stage.initModality(Modality.NONE);
	stage.setTitle(title);
	stage.setWidth(200);
	stage.setHeight(130);
	
    label = new Label(message);
	closeBtn = new Button("Close");
	closeBtn.setOnAction(e -> stage.close());
	box = new VBox(10);
    box.getChildren().addAll(label, closeBtn);
    box.setAlignment(Pos.CENTER);
    
    scene = new Scene(box,200,130);
    stage.setScene(scene);
    stage.show();
   
 
	}
	
	
}
