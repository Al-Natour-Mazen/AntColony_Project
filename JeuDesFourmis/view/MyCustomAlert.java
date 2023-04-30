package view;

import javafx.scene.control.Alert;

/**
 * La classe MyCustomAlert représente une alerte personnalisée, qui étend la classe Alert.
 * Elle permet de créer une alerte avec un titre, un en-tête et un contenu personnalisés.
 */
public class MyCustomAlert extends Alert {

	/**
	 * Constructeur de la classe MyCustomAlert.
	 * 
	 * @param alerttype     Le type de l'alerte
	 * @param title         Le titre de l'alerte
	 * @param HeaderText    L'en-tête de l'alerte
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
