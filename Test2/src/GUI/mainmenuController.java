package GUI;

public class mainmenuController implements ControlledScreen, Runnable {

	ScreenController myController;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;

	}

}
