package view;

import javafx.scene.control.Alert;

/**
 * La classe MyCustomAlert represente une alerte personnalisee, qui etend la classe Alert.
 * Elle permet de creer une alerte avec un titre, un en-tete et un contenu personnalises.
 */
public class MyCustomAlert extends Alert {

	/**
	 * Constructeur de la classe MyCustomAlert.
	 * 
	 * @param alerttype     Le type de l'alerte
	 * @param title         Le titre de l'alerte
	 * @param HeaderText    L'en-tete de l'alerte
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
