package com.group8.controllers;

import com.group8.database.MysqlDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Pub Detail View
 * --> Showing details about a specific chosen pub
 */

public class PubDetailViewController extends BaseController implements Initializable{

	/**
	 * Make a connection to FXML elements existing inside pubDetailView.fxml
	 */
	@FXML
	public CheckBox follow;
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
	public Button addToFavourites;
	@FXML
	public Label added;

	/**
	 *	Place out all information about selectedPub in respective fields
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Navigation.current_CenterFXML = "/com/group8/resources/views/pubDetailView.fxml";

	  	pubImage.setImage(PubData.selectedPub.getImage());
		pubNameLabel.setText(PubData.selectedPub.getName());
		pubAddressLabel.setText(PubData.selectedPub.get_address());
		pubOffersLabel.setText(PubData.selectedPub.getOffer());
		pubEntranceFeeLabel.setText("" +PubData.selectedPub.get_entranceFee());
		phoneNumberLabel.setText(PubData.selectedPub.getPhoneNumber());
		descriptionArea.setText(PubData.selectedPub.getDescription());

		if(UserData.userInstance!=null) {
			if (!UserData.userInstance.getIsPub()) {
				addToFavourites.setVisible(true);
			}
		}
	}

	public void onFavourites(ActionEvent event) throws Exception
	{
		if(UserData.userInstance!=null)
		{
			String sqlQuery = "insert into favouritePub values(" + PubData.selectedPub.getPubId() + ", " + UserData.userInstance.getId() + ", 1);";
			
			MysqlDriver.insert(sqlQuery);

			added.setVisible(true);
		}
	}
}
