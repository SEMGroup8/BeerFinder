package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.BeerRank;

import com.group8.singletons.BeerData;
import com.group8.singletons.Navigation;
import com.group8.singletons.UserData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Andreas Fransson
 *
 * Controller for the beer details scene.
 *
 * Has functionality for ranking the beer, adding it to your favourites, or adding it to your searchForPubsCheckbox inventory
 * if you are a pub user.
 */
public class BeerDetailController extends BaseController implements Initializable{

    @FXML
    public Button googleMaps;
    @FXML
    public Label gMapsError;
    @FXML
    public Button back, favourite, addToPub, removeFromFavouritesButton, removeFromPubButton;
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
    public ImageView showCountryFlag;
    @FXML
    public Label cantRank;

    boolean justRanked=false; //I'm using this to keep the rank as the user just ranked the beer so he can know he ranked the beer

    @FXML
    public Label added;
    @FXML
    public Button updateBeerButton;
    @FXML
    public ImageView oneStar, twoStar, threeStar, fourStar, fiveStar;
    @FXML
    public Label rankShow;
    @FXML
    public HBox imageViewHBox;


    /**
	 * Created by Joseph Roberto Delatolas.
	 *
     * The function for that each button calls in the end.
	 *
     * @param number
     */
    public void rankStar(int number){
    	if(UserData.userInstance!=null) {
            BeerRank beer = new BeerRank(UserData.userInstance.getId(), BeerData.selectedBeer.getId(), number);

            beer.insertRank();
        }
    }

	/**
	 * Created by Joseph Roberto Delatolas.
	 *
	 * Series of functions that gets called when the user presses the appropriate rank star button.
	 * Only works when logged in.
	 *
	 * @param event
	 * @throws IOException
	 */
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

	/**
	 * Created by Joseph Roberto Delatolas.
	 *
	 * Series of funcitons that gets called when the user hovers over each rank star button.
	 *
	 * Only works when logged in.
	 * @param event
	 * @throws IOException
	 */
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


    /**
	 * Created by Linus Eiderström Swahn.
	 *
     * Gets called when the user presses the "add to favourites" button.
	 *
	 * Adds the beer to the users favourite list.
     * @param event
     * @throws IOException
     */
    @FXML
    public void addToFavourite(ActionEvent event) throws IOException
    {
        if(UserData.userInstance!=null)
        {
			// Has the user all ready added the beer to the favourites?
			if(notAddedToFavourites())
            {
				String sqlQuery = "insert into favourites values(" + BeerData.selectedBeer.getId() + ", " + UserData.userInstance.getId() + ", 1);";

				MysqlDriver.insert(sqlQuery);

				UserData.userInstance.getFavouriteBeers();

                added.setText("Added to favourites!");
				added.setVisible(true);

                favourite.setVisible(false);
                removeFromFavouritesButton.setVisible(true);
			}
        }
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Gets called when the user presses the "remove form favourites" button.
     *
     * Removes the beer from the users favourite list.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void removeFromFavourites(ActionEvent event) throws IOException
    {
        if(UserData.userInstance!=null)
        {
            // Is the beer a favourite?
            if (!notAddedToFavourites())
            {
                String sqlQuery = "delete from favourites where beerID = " + BeerData.selectedBeer.getId() + " and userId = " + UserData.userInstance.getId() + ";";

                MysqlDriver.update(sqlQuery);

                added.setText("Removed from favourites!");
                added.setVisible(true);

                removeFromFavouritesButton.setVisible(false);

                favourite.setVisible(true);
            }
        }
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Gets called when the user presses the "remove form pub" button.
     *
     * Removes the beer from the searchForPubsCheckbox inventory.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void removeFromPub(ActionEvent event) throws IOException
    {
        if(UserData.userInstance!=null)
        {
            // Is the beer in the pub?
            if (!notInPub())
            {
                cantRank.setVisible(false);
                
                String sqlQuery = "delete from beerInPub where beerId = " + BeerData.selectedBeer.getId() + " and pubID = " + UserData.userInstance.getPubId() + ";";

                MysqlDriver.update(sqlQuery);

                added.setText("Removed from Pub!");
                added.setVisible(true);

                addToPub.setVisible(true);

                removeFromPubButton.setVisible(false);
            }
        }
    }

    /**
	 * Created by Linus Eiderström Swahn.
	 *
	 * Gets called when the pub user presses the "add to pub" button.
	 *
     * Lets a pub user add a beer to his current beer selection.
     * @param event
     * @throws IOException
     */
    @FXML
    public void addToPub(ActionEvent event) throws IOException {
        if (UserData.userInstance != null)
        {
            if (UserData.userInstance.getIsPub()) {

				// Is the beer all ready added to the searchForPubsCheckbox inventory?
				if(notInPub()) {
					// Open a dialog window.
					final Stage dialog = new Stage();
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.initOwner(Navigation.primaryStage);
					VBox dialogVbox = new VBox(20);
					dialogVbox.setAlignment(Pos.CENTER);
					dialogVbox.getChildren().add(new Text("Add a beer to your pub!"));
					TextField price = new TextField("Type in price:");
					Button addBeerToPub = new Button("Add to pub");

					addBeerToPub.setOnAction(
							new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {

									String query = "Insert into beerInPub values("
											+ UserData.userInstance.getPubId() + ", "
											+ BeerData.selectedBeer.getId() + ", "
											+ Float.parseFloat(price.getText()) + ", 1)";

									MysqlDriver.insert(query);

									dialog.close();

                                    added.setText("Added to Pub!");
                                    added.setVisible(true);

                                    addToPub.setVisible(false);

                                    removeFromPubButton.setVisible(true);
								}
							});

					dialogVbox.getChildren().add(price);
					dialogVbox.getChildren().add(addBeerToPub);

					Scene dialogScene = new Scene(dialogVbox, 300, 200);
					dialog.setScene(dialogScene);
					dialog.show();
				}
                else
                {
                    added.setText("All ready in pub!");

                    added.setVisible(true);
                }
            }
        }
    }

    /**
     * Initialize beerDetail controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Navigation.current_CenterFXML = "/com/group8/resources/views/beerDetails_center.fxml";

        imageViewHBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#2A1806"), BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(4))));

        if(UserData.userInstance!=null) {

            if (UserData.userInstance.getIsPub()) {

                if(notInPub())
                {
                    addToPub.setVisible(true);
                }
                else
                {
                    removeFromPubButton.setVisible(true);
                }
				updateBeerButton.setVisible(true);
            }
            else
            {
                if(notAddedToFavourites()) {
                    favourite.setVisible(true);
                }
                else
                {
                    removeFromFavouritesButton.setVisible(true);
                }
            }
        }

        // Display Name of beer
        showBeerName.setText(BeerData.selectedBeer.getName());
        // Display Origin
        showOrigin.setText(BeerData.selectedBeer.getOrigin());
        // Display country flag
        showCountryFlag.setImage(BeerData.selectedBeer.getCountryFlag());
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
    	if (justRanked==true){
    	fourStar.setEffect(new Glow(1));}
    }public void fiveState(){
    	fourState();
        fiveStar.setOpacity(1);
    	if(justRanked==true){
    	fiveStar.setEffect(new Glow(1));}
    }

    /**
     * Sets the default state of rank for the current beer.
     */
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

    /**
     * Checks if the current beer has been ranked.
     * @return
     */
    public boolean notRankedYet(){
    	String selectQuery = "Select * from beerRank where userID = '" +UserData.userInstance.getId()+"' and beerID = '"+BeerData.selectedBeer.getId() + "';";
    	if(RegisterUserController.checkAvailability(selectQuery)){
    		return true;
    	}else{
			cantRank.setText("All ready ranked this beer.");
    		cantRank.setVisible(true);
    		return false;
    	}
    }

	/**
	 * Created by Linus Eiderström Swahn.
	 *
	 * Checks if the beer is all ready in the users favourite list.
	 * @return
	 */
	public boolean notAddedToFavourites(){
		String selectQuery = "Select * from favourites where userID = '" +UserData.userInstance.getId()+"' and beerID = '"+BeerData.selectedBeer.getId() + "';";
		if(RegisterUserController.checkAvailability(selectQuery)){
			return true;
		}else{
			cantRank.setText("This beer is all ready a favourite.");
			cantRank.setVisible(true);
			return false;
		}
	}

	/**
	 * Created by Linus Eiderström Swahn.
	 *
	 * Checks if the current beer has allready been added to the searchForPubsCheckbox inventory.
	 * @return
	 */
	public boolean notInPub()
	{
		String selectQuery = "Select * from beerInPub where pubID = '" +UserData.userInstance.getPubId()+"' and beerID = '"+BeerData.selectedBeer.getId() + "';";
		if(RegisterUserController.checkAvailability(selectQuery)){
			return true;
		}else{
			cantRank.setText("All ready in pub.");
			cantRank.setVisible(true);
			return false;
		}
	}

    /**
     * Lets a Pub_User update a beer he has inserted into the system.
     * @param event
     * @throws IOException
     */
    public void updateBeer(ActionEvent event) throws IOException {

        mainScene.changeCenter("/com/group8/resources/views/updateBeer.fxml");
    }
}
