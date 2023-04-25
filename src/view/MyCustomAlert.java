package view;

import javafx.scene.control.Alert;

/**
 * La classe MyCustomAlert repr�sente une alerte personnalis�e, qui �tend la classe Alert.
 * Elle permet de cr�er une alerte avec un titre, un en-t�te et un contenu personnalis�s.
 */
public class MyCustomAlert extends Alert {

	/**
	 * Constructeur de la classe MyCustomAlert.
	 * 
	 * @param alerttype     Le type de l'alerte
	 * @param title         Le titre de l'alerte
	 * @param HeaderText    L'en-t�te de l'alerte
	 * @param ContentText   Le contenu de l'alerte
	 */
	public MyCustomAlert(AlertType alerttype,String title,String HeaderText, String ContentText ) {
		super(alerttype);
		setTitle(title);
		setHeaderText(HeaderText);
		setContentText(ContentText);
		showAndWait();
	}

}
