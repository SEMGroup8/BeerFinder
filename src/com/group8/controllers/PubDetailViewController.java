package com.group8.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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


	/**
	 *	Place out all information about selectedPub in respective fields
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	  	pubImage.setImage(PubData.selectedPub.getImage());
		pubNameLabel.setText(PubData.selectedPub.get_name());
		pubAddressLabel.setText(PubData.selectedPub.get_address());
		pubOffersLabel.setText(PubData.selectedPub.get_offer());
		pubEntranceFeeLabel.setText("" +PubData.selectedPub.get_entranceFee());
		phoneNumberLabel.setText(PubData.selectedPub.get_phoneNumber());
		descriptionArea.setText(PubData.selectedPub.get_description());
	  
	}

	

}
