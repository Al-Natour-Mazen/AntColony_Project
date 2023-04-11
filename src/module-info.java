module Project_POO_IHM2 {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens model to javafx.graphics, javafx.fxml;
	opens view to javafx.graphics, javafx.fxml;
	opens main to javafx.graphics, javafx.fxml;
	//opens controller to javafx.graphics, javafx.fxml;
}
