package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.BeerRank;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.text.NavigationFilter;

/**
 * Created by AnkanX on 15-10-27.
 * TODO fix nice detail layout and images
 */
public class BeerDetailController implements Initializable{

    @FXML
    public Button back, favourite;
    @FXML
    public Button newSearch;
    @FXML
    public Label showBeerName;
    @FXML
    public Label showOrigin;
    @FXML
    public Label showBeerType;
    @FXML
    public Label showPercentage;
    @FXML
    public TextArea showDescription;
    @FXML
    public Label showVolume;
    @FXML
    public Label showTap;
    @FXML
    public Label showPackage;
    @FXML
    public Label showProducer;
    @FXML
    public Label showPrice;
    @FXML
    public ImageView showImage;
    @FXML
    public ImageView oneStar, twoStar, threeStar, fourStar, fiveStar;
    @FXML
    public Label cantRank;
    boolean justRanked=false; //I'm using this to keep the rank as the user just ranked the beer so he can know he ranked the beer
//    @FXML
//    public ImageView theoneStar;


    /**
     * Back button pressed takes you back to "result screen"
     * @param event
     * @throws IOException
     */
    @FXML
    public void backAction(ActionEvent event) throws IOException {

        if (!NavigationFilter.backFXML.equals("/com/group8/resources/views/favourites.fxml")) {
            // Update the beer list for changes
            BeerData.beer = new ArrayList<Beer>();
            ArrayList<ArrayList<Object>> sqlData;
            System.out.println(BeerData.searchInput);
            sqlData = MysqlDriver.selectMany(BeerData.searchInput);

            for (int i = 0; i < sqlData.size(); i++) {
                // Add a new Beer to the beer arraylist
                Beer beer = new Beer(sqlData.get(i));
                // Testoutput
                //System.out.print(beer.getName()+"\n");
                BeerData.beer.add(beer);
            }
        }
        Parent homescreen = FXMLLoader.load(getClass().getResource(Navigation.resultviewFXML));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }
    public void rankStar(int number){
    	if(UserData.userInstance!=null) {
            BeerRank beer = new BeerRank(UserData.userInstance.get_id(), BeerData.selectedBeer.getId(), number);

            beer.insertRank();

        }
        }
    @FXML
    public void onRankOneStar(MouseEvent event) throws IOException {
    	if (logState() == true){
    		if (notRankedYet() == true){
    			rankStar(1);
    			justRanked=true;
    			oneState();
    		}
    	}
    }
    @FXML
    public void onRankTwoStar(MouseEvent event) throws IOException {
    	if (logState() == true){
    		if (notRankedYet() == true){
    			rankStar(2);
    			justRanked=true;
    			twoState();
    		}
    	}
    }
    @FXML
    public void onRankThreeStar(MouseEvent event) throws IOException {
    	if (logState() == true){
    		if (notRankedYet() == true){
    			rankStar(3);
    			justRanked=true;
    			threeState();
    		}
    	}
    }
    @FXML
    public void onRankFourStar(MouseEvent event) throws IOException {
    	if (logState() == true){
    		if (notRankedYet() == true){
    			rankStar(4);
    			justRanked=true;
    			fourState();
    		}
    	}
    }
    @FXML
    public void onRankFiveStar(MouseEvent event) throws IOException {
    	if (logState() == true){
    		if (notRankedYet() == true){
    			rankStar(5);
    			justRanked=true;
    			fiveState();
    		}
    	}
    }
    @FXML
    public void hoverOne(MouseEvent event) throws IOException {
    	if (logState() == true){ if (justRanked==false){
    		oneState();
    	}}
    }
    @FXML
    public void hoverTwo(MouseEvent event) throws IOException {
    	if (logState() == true){ if (justRanked==false){
    		twoState();
    	}}
    }
    @FXML
    public void hoverThree(MouseEvent event) throws IOException {
    	if (logState() == true){ if (justRanked==false){
    		threeState();
    	}}
    }
    @FXML
    public void hoverFour(MouseEvent event) throws IOException {
    	if (logState() == true){ if (justRanked==false){
    		fourState();
    	}}
    }
    @FXML
    public void hoverFive(MouseEvent event) throws IOException {
    	if (logState() == true){ if (justRanked==false){
    		fiveState();
    	}}
    }
    @FXML
    public void backToRank(MouseEvent event) throws IOException {
    	 if (justRanked==false){
    		 defaultState();
    	 }
    }
//    	oneStar.setOpacity(0.4);
//        twoStar.setOpacity(0.4);
//        threeStar.setOpacity(0.4);
//        fourStar.setOpacity(0.4);
//        fiveStar.setOpacity(0.4);

//    oneStar.setImage(@../group8/resources/Images/staricon.png);
//    twoStar.setImage(@../group8/resources/Images/stariconNN.png);
    
//    @FXML
//    public void mouseTheOnRankOneStar(ActionEvent event) throws IOException {
//        rankStar(1);
//    }

    @FXML
    public void addToFavourite(ActionEvent event) throws IOException
    {
        if(UserData.userInstance!=null)
        {
            String sqlQuery = "insert into favourites values(" + BeerData.selectedBeer.getId() + ", " + UserData.userInstance.get_id() + ");";

            System.out.println(sqlQuery);

            MysqlDriver.insert(sqlQuery);

            UserData.userInstance.getFavourites();
        }
    }

    /**
     * Home Button
     * @param event
     * @throws IOException
     */
    @FXML
    public void returnHome(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource(Navigation.homescreenFXML));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    /**
     * Initialize beerDetail controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        defaultState();
        // test output
        System.out.println("beerDetails accsessed and initializeing!");
        // Display Name of beer
        showBeerName.setText(BeerData.selectedBeer.getName());
        // Display Origin
        showOrigin.setText(BeerData.selectedBeer.getOrigin());
        // Display beer Type
        showBeerType.setText(BeerData.selectedBeer.getType());
        // Display beer Description
        showDescription.setText(BeerData.selectedBeer.getDescription());
        // Display if beer is tap
        if (BeerData.selectedBeer.getIsTap().toString().equals("false")) {

            showTap.setText("This beer is not on Tap");
        } else {

            showTap.setText("This beer is on Tap");
        }
        // Try loading the image, if there is none will use placeholder
        if (BeerData.selectedBeer.getImage() == null) {
            System.out.println("No image! Will use Placeholder Image!");
        } else{
            showImage.setImage(BeerData.selectedBeer.getImage());
        }
        // Display beer volume
        showVolume.setText("" + BeerData.selectedBeer.getVolume() + " ml");
        // Display beer percentage
        showPercentage.setText(""+BeerData.selectedBeer.getPercentage()+"%");
        // Display beer package
        showPackage.setText(BeerData.selectedBeer.getBeerPackage());
        // Display the beer producer
        showProducer.setText(BeerData.selectedBeer.getProducer());
        // Display beer price
        showPrice.setText(BeerData.selectedBeer.getPrice()+":-");
        



        // Test the data in our beer instance
        System.out.println(BeerData.selectedBeer.toString());

        
        

    }
    public void noState(){
    	oneStar.setOpacity(0.2);
        twoStar.setOpacity(0.2);
        threeStar.setOpacity(0.2);
        fourStar.setOpacity(0.2);
        fiveStar.setOpacity(0.2);
    } 
    public void oneState(){
    	noState();
    	oneStar.setOpacity(1);
    	if(justRanked==true){
    	oneStar.setEffect(new Glow(1));}
    }public void twoState(){
    	oneState();
        twoStar.setOpacity(1);
    	if(justRanked==true){
    	twoStar.setEffect(new Glow(1));}
    }public void threeState(){
    	twoState();
        threeStar.setOpacity(1);
    	if(justRanked==true){
    	threeStar.setEffect(new Glow(1));}
    }public void fourState(){
    	threeState();
        fourStar.setOpacity(1);
    	if(justRanked==true){
    	fourStar.setEffect(new Glow(1));}
    }public void fiveState(){
    	fourState();
        fiveStar.setOpacity(1);
    	if(justRanked==true){
    	fiveStar.setEffect(new Glow(1));}
    }
    
    public void defaultState() {
    	if(BeerData.selectedBeer.getAvRank()>=1.0 && BeerData.selectedBeer.getAvRank()<2.0){
    		oneState();
    	}else if(BeerData.selectedBeer.getAvRank()>=2.0 && BeerData.selectedBeer.getAvRank()<3.0){
    		twoState();
    	}else if(BeerData.selectedBeer.getAvRank()>=3.0 && BeerData.selectedBeer.getAvRank()<4.0){
    		threeState();
    	}else if(BeerData.selectedBeer.getAvRank()>=4.0 && BeerData.selectedBeer.getAvRank()<5.0){
    		fourState();
    	}else if(BeerData.selectedBeer.getAvRank()>=5.0 && BeerData.selectedBeer.getAvRank()<6.0){
    		fiveState();
    	}else if(BeerData.selectedBeer.getAvRank()>=0.0 && BeerData.selectedBeer.getAvRank()<1.0){
    		noState();
    	}
    }
    public boolean logState() {
    	return UserData.userInstance!=null;
    }
    public boolean notRankedYet(){
    	String selectQuery = "Select * from beerRank where userID = '" +UserData.userInstance.get_id()+"' and beerID = '"+BeerData.selectedBeer.getId() + "';";
    	if(RegisterUserController.checkAvailability(selectQuery)){
    		return true;
    	}else{
    		cantRank.setVisible(true);
    		return false;
    	}
    }
}
