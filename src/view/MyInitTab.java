package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;


public class MyInitTab extends Tab{
	
	//les conteneurs
	private VBox initBox;
	

	//elements d'interaction
	private Button confirmerInit;	


	private LabelTextField probaFourmi,probagraines,probamurs;
	private Label infoInit;
	

	public MyInitTab() {
		super("Initialisations");
		setClosable(false);
	
	
		initBox = new VBox(20);
		setContent(initBox);
		
		
		infoInit = new Label("Changez les Valeurs d'initialisations de la Simulation :");
		
		probaFourmi = new LabelTextField("Nombre Fourmi :");
		probaFourmi.setTextFieldInput("7");
		
		probagraines = new LabelTextField("Densité des graines :");
		probagraines.setTextFieldInput("25");
		
		probamurs = new LabelTextField("Densité des Murs :");
		probamurs.setTextFieldInput("90");
	
		confirmerInit= new Button("Confirmer");
		SetStyleBtn(confirmerInit);
		
		MySpring springintiTop = new MySpring("VBox");
		MySpring springintiBottom = new MySpring("VBox");
		initBox.getChildren().addAll(springintiTop,infoInit,probaFourmi,probagraines,probamurs,confirmerInit,springintiBottom);
		initBox.setAlignment(Pos.CENTER);
		
	}
	
	public Button getConfirmerInit() {
		return confirmerInit;
	}

	public LabelTextField getProbaFourmi() {
		return probaFourmi;
	}
	public LabelTextField getProbagraines() {
		return probagraines;
	}
	public LabelTextField getProbamurs() {
		return probamurs;
	}
	
	private void SetStyleBtn(Button btn) {
		btn.setStyle("-fx-background-color: transparent;-fx-border-color:black;");
	}

}
