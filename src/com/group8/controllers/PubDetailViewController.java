package com.group8.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.group8.database.MysqlDriver;

/**
 * Pub Detail View
 * --> Showing details about a specific chosen pub
 */

public class PubDetailViewController extends BaseController implements Initializable{

	/**
	 * Make a connection to FXML elements existing inside pubDetailView.fxml
	 */
	@FXML
    public ImageView pubImage;
	@FXML
	public TextArea descriptionArea;
	@FXML
	public Label pubNameLabel;
	@FXML
	public Label pubAddressLabel;
	@FXML
	public Label pubOffersLabel;
	@FXML
	public Label pubEntranceFeeLabel;
	@FXML
	public Label phoneNumberLabel;
	@FXML
	public Button addToFavouritesButton;


	/**
	 *	Place out all information about selectedPub in respective fields
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Navigation.current_CenterFXML = "/com/group8/resources/views/pubDetailView.fxml";

	  	pubImage.setImage(PubData.selectedPub.getImage());
		pubNameLabel.setText(PubData.selectedPub.get_name());
		pubAddressLabel.setText(PubData.selectedPub.get_address());
		pubOffersLabel.setText(PubData.selectedPub.get_offer());
		pubEntranceFeeLabel.setText("" +PubData.selectedPub.get_entranceFee());
		phoneNumberLabel.setText(PubData.selectedPub.get_phoneNumber());
		descriptionArea.setText(PubData.selectedPub.get_description());
	  
	}
   public void addToFavourites(ActionEvent event)throws IOException {
	   if (UserData.userInstance != null) {
			String sqlQuery = "insert into favouritePub (pubId, userId)  values(" + PubData.selectedPub.get_pubId()+ ", "
					+ UserData.userInstance.get_id() + ");";

			System.out.println(sqlQuery);

			MysqlDriver.insert(sqlQuery);

			UserData.userInstance.getPubFavourites();

			addToFavouritesButton.setVisible(true);
		}
	   
   }
	

}
