package model;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Iterator;

/**
 * Classe de gestion de la fourmiliere
 * @author abergey
 * @author abrunet 
 *          correction largeur-hauteur
 *          correction boucle infinie si fourmi bloquée
 * @version 1.2
 *          
 */

 
public class Fourmiliere {
 
  private int largeur, hauteur ;

// Le nombre maximal de graines par cases  
  private int qMax ;  	

  // la liste des fourmis de la fourmiliere. 
  // Attention : la position X,Y d'une fourmi doit correspondre à un booleen true 
  // dans le tableau fourmis
  private List<Fourmi> lesFourmis ;  
	
  /************************************AJOUTE**************************************************/
 
  /* On d�finie comme final chaque Property afin de garantir qu'une fois initialis�e dans le constructeur, elle ne peut 
   * plus �tre remplac�e par une autre instance de DoubleProperty. Cela permet d'assurer l'int�grit� 
   * des donn�es de la propri�t� et d'�viter toute modification accidentelle ou malveillante de la r�f�rence de la propri�t�.
   */
			  /*****************/
  			  // POUR LE NOMBRE DE FOURMI
  			  /*****************/
  			  private final DoubleProperty NbFourmiProperty;
			  public DoubleProperty NbFourmiProperty() {
			      return NbFourmiProperty;
			  }	
			  public double getNbFourmi() {
			      return NbFourmiProperty.get();
			  }		
			  public void setNbFourmi(double value) {
				  NbFourmiProperty.set(value);
			  }
			
			  /*****************/
  			  // POUR LE NOMBRE DE GRAINE
  			  /*****************/
			  private final DoubleProperty NbGraineProperty;  
			  public DoubleProperty NbGraineProperty() {
			      return NbGraineProperty;
			  }
			  public double getNbGraine() {
			      return NbGraineProperty.get();
			  }		
			  public void setNbGraine(double qte) {
				  NbGraineProperty.set(qte);
			  }		  
			  /**
			   * Met � jour le nombre total de graines pr�sentes dans le mod�le de fourmili�re.
			   * Parcourt le tableau de quantit�s de graines et ajoute chaque quantit� � la variable nbGrainesTotal.
			   * Met ensuite � jour l'attribut nbGraine de la fourmili�re avec la valeur calcul�e.
			  */
			  public void MAJNbGrainesTotal() {
				    int nbGrainesTotal = 0;
				    for (int i = 1; i <= hauteur; i++) {
				        for (int j = 1; j <= largeur; j++) {
				            nbGrainesTotal += qteGraines[i][j];
				        }
				    }
				    setNbGraine(nbGrainesTotal);
			  }
			  
			  /*****************/
  			  // POUR LE NOMBRE D ITERATION
  			  /*****************/
			  private final DoubleProperty IterationProperty;  
			  public DoubleProperty IterationProperty() {
			      return IterationProperty;
			  }
			  public double getIteration() {
			      return IterationProperty.get();
			  }		
			  public void setIteration(double val) {
				  IterationProperty.set(val);
			  }	
			  
			  /*****************/
  			  // POUR L AFFICHAGE DES FOURMIS DANS LE PLATEAU
  			  /*****************/
			  public List<Fourmi> getLesFourmis() {
					return lesFourmis;
			  }
			  
			  /*****************/
  			  // POUR LA REINISIALISATION 
  			  /*****************/
			  
			  /**
			   * R�initialise le mod�le de la fourmili�re.
			   *  Remet � z�ro le nombre de fourmis, le nombre de graines, le nombre d'it�rations et la liste des fourmis.
			   *   Remet � false toutes les cases des tableaux fourmis et murs, et � z�ro toutes les cases du tableau qteGraines.
			   *   Met � jour le nombre total de graines avec la m�thode MAJNbGrainesTotal().
			  */
			  public void resetModel() {
			    setNbFourmi(0);
			    setNbGraine(0);
			    setIteration(0);
			    this.lesFourmis = new LinkedList<Fourmi>(); 
			    	
			    fourmis = new boolean[hauteur+2][largeur+2];
			    for (int i =0 ; i < hauteur+2 ; i++)
			      for (int j =0 ; j < largeur+2 ; j++)
				fourmis[i][j] = false ; 
				
			    murs = new boolean[hauteur+2][largeur+2];
			    for (int i =0 ; i < hauteur+2 ; i++)
			      for (int j =0 ; j < largeur+2 ; j++)
				murs[i][j] = (i==0)||(i==hauteur+1)||(j==0)||(j==largeur+1) ;
				
			    qteGraines = new int[hauteur+2][largeur+2];
			    for (int i =0 ; i < hauteur+2 ; i++)
			      for (int j =0 ; j < largeur+2; j++)
				qteGraines [i][j]=0 ; 
			    
			    MAJNbGrainesTotal();
			  }

  /************************************FIN AJOUT**************************************************/
  
  
// Tableaux contenant les murs, les fourmis et les graines. 
  // Attention : pour un terrain [1..hauteur]x[1..largeur], ces tableaux 
  // sont indicés de [0..hauteur+1][0..largeur+1], cela permet de simplifier 
  // certains traitements en ne traitant pas le cas particulier des bordures. 
  private boolean murs[][];
  private boolean fourmis[][]; 
  private int qteGraines[][];
  
  
    
  /**
   * Crée une fourmiliere de largeur l et de hauteur h. 
   * @param l			largeur 
   * @param h			hauteur
   * @param qMax                la qte max de graines par case
   */
  public Fourmiliere(int l, int h, int qMax) {
	NbFourmiProperty = new SimpleDoubleProperty();
	NbGraineProperty = new SimpleDoubleProperty();
	IterationProperty = new SimpleDoubleProperty();
    this.largeur = l;
    this.hauteur = h;
    this.qMax = qMax ; 
	
    this.lesFourmis = new LinkedList<Fourmi>(); 
    	
    fourmis = new boolean[hauteur+2][largeur+2];
    for (int i =0 ; i < hauteur+2 ; i++)
      for (int j =0 ; j < largeur+2 ; j++)
	fourmis[i][j] = false ; 
	
    murs = new boolean[hauteur+2][largeur+2];
    for (int i =0 ; i < hauteur+2 ; i++)
      for (int j =0 ; j < largeur+2 ; j++)
	murs[i][j] = (i==0)||(i==hauteur+1)||(j==0)||(j==largeur+1) ;
	
    qteGraines = new int[hauteur+2][largeur+2];
    for (int i =0 ; i < hauteur+2 ; i++)
      for (int j =0 ; j < largeur+2; j++)
	qteGraines [i][j]=0 ; 
    
    MAJNbGrainesTotal();
  }
    
  /**
   * Retourne la largeur de la fourmiliere
   * @return 			la largeur
   */
  public int getLargeur() {
    return largeur; 
  }
     
  /**
   * Retourne la largeur de la fourmiliere
   * @return			la hauteur
   */
  public int getHauteur() {
    return hauteur; 
  }
  
  /** 
   * Retourne le nombre max de graines par case
   * @return               le qMax     
   */
  public int getQMax(){
      return qMax ; 
  }
  
  /**
   * Positionne la quantité max de graines par case
   * @param qMax                la nouvelle quantité max 
   */
  public void setQMax(int qMax){
      this.qMax = qMax ; 
  }
  
  /**
   * Presence d'un mur au point  (x,y) du terrain 
   * @param x			coordonnée
   * @param y			abcisse
   * @return			vrai si il y a un mur
   */
  public boolean getMur(int x, int y) {
    return murs[y][x]; 
  }

  /**
   * Positionne un mur en au point (x,y) du terrain
   * @param x			coordonnée	
   * @param y			abcisse
   * @param mur			vrai si l'on veut poser un mur, faux sinon
   */
  public void setMur(int x, int y, boolean mur) {
    assert (x>0 && x <hauteur+1 && y > 0 && y <largeur+1);
    murs[y][x]=mur;
  }
    
  /**
   * Presence  d'une fourmi au point (x,y) du terrain
   * @param x		coordonnee
   * @param y		abcisse
   * @return		vrai si il y a une fourmi
   */
  public boolean contientFourmi(int x, int y){
    return fourmis[y][x];
  }
	
  /**
   * Ajoute (ou remplace) une fourmi non chargée au point (x,y) du terrain
   * @param x	    coordonnee
   * @param y		abcisse
   */
  public void ajouteFourmi(int x, int y){
    if (!fourmis[y][x] && !murs[y][x]){
      Fourmi f = new Fourmi(x,y,false);
      fourmis[y][x]=true ; 			
      lesFourmis.add(f);
      setNbFourmi(getNbFourmi()+1);
    };
  }
		
  /**
   * Retourne la quantité de graine au point (x,y) du terrain
   * @param x		coordonnnee
   * @param y		abcisse
   * @return		la quantité de graine
   */
  public int getQteGraines(int x,  int y) {
    return this.qteGraines[y][x];
  }
    
  /**
   * Positionne des graines au point (x,y) du terrain 
   * @param x		coordonnee
   * @param y		abcisse
   * @param qte	le nombre de graines que l'on souhaite poser. Si qte !E [0..QMAX] rien n'est effectué
   */
  public void setQteGraines(int x, int y, int qte) {
    //assert (qte >=0 && qte <=QMAX); 
    if(qte < 0 || qte > qMax) {
      return;
    }
    this.qteGraines[y][x]=qte;
    MAJNbGrainesTotal();
  }

  /**
   * Compte les graines du point (x,y) et des cellules voisines 
   * Les voisines s'entendent au sens de 8-connexité. 
   * On ne compte pas les graines sur les murs) 
   * @param x		coordonnee
   * @param y		abcisse
   * @return		le nombre de graines
   */
  private int compteGrainesVoisines(int x, int y) {
    assert (x>0 && x <hauteur+1 && y > 0 && y <largeur+1);
    int nb = 0 ; 
    for (int vx = -1 ; vx < 2 ; vx++)
      for (int vy = -1 ; vy < 2 ; vy++)
	if (!murs[y+vy][x+vx])
	  nb = nb + qteGraines[y+vy][x+vx];
    return nb ; 
  }
	
  /**
   * Evolution d'une étape de la fourmilière
   * Pour chaque fourmi f de la foumilière. 
   *     1) si il y a une(ou des) graines sur la case, et que 
   *       la fourmi ne porte rien : 
   *     		on choisit aléatoirement de charger ou non une graine, 
   *                  en fonction du nombre de graines autour. 
   *     2) f se deplace aléatoirement d'une case (en évitant les murs)
   *     3) si f est chargée et qu'il reste de la place pour une graine, 
   *          on choisit aléatoirement de poser ou non  la graine, 
   *          en fonction du nombre de graines autour.  
   *        
   */
  public void evolue() {
    Iterator<Fourmi> ItFourmi = lesFourmis.iterator();
    while (ItFourmi.hasNext()) {
    	setIteration(getIteration()+1);
      Fourmi f = ItFourmi.next();
      int posX = f.getX(); 
      int posY = f.getY(); 
      // la fourmi f prend ?  
      if (!f.porte() &&  qteGraines[f.getY()][f.getX()]>0){
	if (Math.random()<Fourmi.probaPrend(compteGrainesVoisines(posX,posY))){
	  f.prend();
	  qteGraines[posY][posX]--;					
	}
      }
      // la fourmi f se déplace. 
      int deltaX ; 
      int deltaY ; 
      // cptEssai compte les essais de déplacements pour eviter les blocages
      int cptEssai = 0 ; 
      do {
	cptEssai++ ; 
	deltaX = posX ; 
	deltaY = posY ; 
	int tirage  = (int) (Math.random() *7.99999999); 			     
	switch(tirage)
	  {
	  case 0 :
	    deltaX-- ; 
	    deltaY-- ; 
	    break ; 
	  case 1 : 
	    deltaY-- ; 
	    break ; 
	  case 2 :
	    deltaX++; 
	    deltaY--; 
	    break ; 
	  case 3 :
	    deltaX-- ; 
	    break ; 
	  case 4 :
	    deltaX++ ; 
	    break ; 
	  case 5 :
	    deltaX-- ; 
	    deltaY++ ; 
	    break ; 
	  case 6 :
	    deltaY++ ; 
	    break ; 
	  case 7 :
	    deltaX++ ; 
	    deltaY++ ; 
	    break ; 								     
	  }
	// On tire au sort jusqu'a ce qu'on soit tombe sur une case vide
	// ou bien qu'on ait essayé 99 fois. 
      } while((murs[deltaY][deltaX] || fourmis[deltaY][deltaX]) && cptEssai < 100);
      /************AJOUTEE CONDITION POUR PAS CRASH*******/
      if(cptEssai < 99) {
    	  fourmis[posY][posX]=false; 
          fourmis[deltaY][deltaX]=true; 
          f.setX(deltaX);
          f.setY(deltaY);
          // la fourmi pose ? 
          if (f.porte() && qteGraines[deltaY][deltaX]<qMax){
	    	if (Math.random()<Fourmi.probaPose(compteGrainesVoisines(deltaX,deltaY))){
		    	  f.pose();
		    	  qteGraines[deltaY][deltaX]++;				
	    		}
          }
      };
    }
  }


  // méthodes d'affichage
  /** 
   * Retourne une chaine affichant les graines sur le terrain 
   *    (X pour un mur ou 1 entier pour le nbgraines)
   * @return 	la chaine représentant le terrain
   */
  public String stringGraines() {
    String res = "" ; 
    for (int i =0 ; i < hauteur+2; i++) {
      for (int j =0 ; j < largeur+2; j++)
	if (murs[i][j])
	  res = res + "X"; 
	else	{
	  if (qteGraines[i][j]>0){
	    String qS = String.format("%d", qteGraines[i][j])	;		
	    res = res + qS ;
	  }
	  else 
	    res = res +".";
	}
      res = res + "\n";
    }
    return res ; 
  };

  /** 
   * Retourne une chaine affichant les fourmis sur le terrain 
   *      (X pour un mur ou 0 pour la fourmi)
   * @return 	la chaine représentant le terrain. 
   */
  public String stringFourmis() {
    String res = "" ; 
    for (int i =0 ; i < hauteur+2; i++) {
      for (int j =0 ; j < largeur+2; j++)
	if (murs[i][j])
	  res = res + "X"; 
	else if (fourmis[i][j])	{
	  res = res + "O";
	}
	else 
	  res = res + ".";
      res = res + "\n";
    }
    return res ; 
  }

  /** 
   * Retourne une chaine affichant le le terrain (X pour un mur)
   * @return 	la chaine représentant le terrain. 
   */
  public String stringMurs() {
    String res = "" ; 
    for (int i =0 ; i < hauteur+2 ; i++) {
      for (int j =0 ; j < largeur+2 ; j++)
	res = res + (murs[i][j]?"X":".");
      res = res + "\n";
    }
    return res ; 
  }	



  /**
   * Exemple de main manipulant une fourmiliere 
   *  de largeur 20 et de hauteur 10. 
   * @param args
   */
  public static void main(String[] args) {

    // TODO Auto-generated method stub
			
    // La fourmilere
    Fourmiliere f = new Fourmiliere(20,10,4);
			
    // On crée quelques murs
    for (int i =1; i <4; i++)
      f.setMur(i, 2*i, true);
    // On ajoute 3 fourmis dans la fourmilière
    f.ajouteFourmi(1, 1);
    f.ajouteFourmi(2, 2);
    f.ajouteFourmi(3, 3);
    // On pose des graines
    for (int i =0; i <10; i++){
      f.setQteGraines(2*i, i, 1);
      f.setQteGraines(2*i , 11-i , 1);
    }
			
    // On affiche les probabilités de prise et dépot.  
    System.out.println("proba      Prise      Pause ");
    for (int i = 0 ; i <24 ; i++){
      System.out.println("   "+i+"   "+String.format("%f2", Fourmi.probaPrend(i))+"  "+
			 String.format("%f2",Fourmi.probaPose(i)));
    }
			
    // On affiche le terrain initial. 
    System.out.println(f.stringMurs());
    System.out.println(f.stringGraines());	
    System.out.println(f.stringFourmis());
			
    // On fait evoluer la fourmiliere 
    for (int i =1 ; i <10 ; i++) {
      f.evolue();
      System.out.println("-------------------- "+i+" ----------------------------------");
      System.out.println("---------------------      --------------------------------");
      System.out.println(f.stringMurs());
      System.out.println(f.stringGraines());	
      System.out.println(f.stringFourmis());
    }
  }

}
