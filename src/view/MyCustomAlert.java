package view;

import javafx.scene.control.Alert;


public class MyCustomAlert extends Alert {

	public MyCustomAlert(AlertType alerttype,String title,String HeaderText, String ContentText ) {
		super(alerttype);
		// TODO Auto-generated constructor stub
		  setTitle(title);
		  setHeaderText(HeaderText);
		  setContentText(ContentText);
		  showAndWait();
	}

}
