package com.group8.controllers;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import com.group8.database.tables.Beer;
import com.group8.singletons.BeerData;
import com.group8.singletons.Navigation;
import com.group8.singletons.PubData;
import com.group8.singletons.UserData;
import com.lynden.gmapsfx.MainApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * Created by Collins
 * Pub information scene
 * --> Used to show the user information about his Pub
 * --> Gives the user the ability to change all information about his Pub
 *
 * TODO change local object Pub when doing an update instead of re-fetchig data from server?
 */
public class PubInfo extends BaseController implements Initializable{

	// Not needed?
	//@FXML
	//public TextField pubID;
	@FXML
	public TextField pubName;
	@FXML
	public TextField pubAddress;
	@FXML
	public TextField pubPhoneNumber;
	@FXML
	public TextField pubDescription;
	@FXML
	public TextField pubOffer;
	@FXML
	public TextField pubEntranceFee;
	@FXML
	public Button pubSaveNew;
	@FXML
	public Button addBeer;
	@FXML
	public Button getMap;
	@FXML
    public ImageView pubImage;
	ImageView img= new ImageView((this.getClass().getResource("/com/group8/resources/Images/Icon_2.png").toString()));

	//table for beers in Pub
    public TableView<Beer> beerTable;
    @FXML
    public TableColumn<Beer, String> beerName;
    @FXML
    public TableColumn<Beer, String> beerType;
    @FXML
    public TableColumn<Beer, String> beerOrigin;
    @FXML
    public TableColumn<Beer, String> beerProducer;
    @FXML
    public TableColumn<Beer, String> beerPackage;
    @FXML
    public TableColumn<Beer, String> beerPercentage;
    @FXML
    public TableColumn<Beer, String> avrageRank;
    @FXML
    public TableColumn<Beer,Image> beerImage;
    @FXML
    public TableColumn<Beer,String> beerInPubPrice;
    @FXML
    public PieChart showPie;
    @FXML
    public Label userName;

	// Latlong
	double latitude = PubData.loggedInPub.getGeoLat();
	double longitude = PubData.loggedInPub.getGeoLong();
	
	Image imgLoad;
	FileInputStream imageStream ;
	File file;
	boolean loadAnImage = false;
	 public ObservableList<Beer> masterData = FXCollections.observableArrayList(UserData.userInstance.beersInPub);

	/**
	 *
	 */
	 public void getRow(){
	        beerTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            // Select item will only be displayed when dubbleclicked

	            /**
	             * Dubleclick event
	             * @param event
	             */
	            @Override
	            public void handle(MouseEvent event) {
	                if (event.getClickCount() == 2) {
	                        // Show that we can select items and print it
	                        //System.out.println("clicked on " + beerTable.getSelectionModel().getSelectedItem());
	                        // Set the selectedBeer instance of beer we have to selected item
	                        BeerData.selectedBeer = beerTable.getSelectionModel().getSelectedItem();
	                        // load the details scene
	                        // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
	                        try {
	  							mainScene.changeCenter("/com/group8/resources/views/beerDetails_center.fxml");
	                        } catch (IOException e) {
	                            // Print error msg
	                            e.printStackTrace();
	                        }
	                }
	            }

	        });


	}


	public void initialize(URL location, ResourceBundle resources) {
				
		pubName.setText(PubData.loggedInPub.getName());
		pubPhoneNumber.setText(PubData.loggedInPub.getPhoneNumber());
		pubAddress.setText(PubData.loggedInPub.get_address());
		pubDescription.setText(PubData.loggedInPub.getDescription());
		pubOffer.setText(PubData.loggedInPub.getOffer());
		pubEntranceFee.setText(""+PubData.loggedInPub.get_entranceFee());
		pubImage.setImage(PubData.loggedInPub.getImage());




		System.out.println(PubData.loggedInPub.getImage()+"    why IMAGE");

		//testtLabel.setText(BeerData.beersInPubDetails.get_price());
		Navigation.current_CenterFXML = "/com/group8/resources/views/pubInfo.fxml";

        // You have to have a get function that is named get +" type" for it to work sets values.
        beerName.setCellValueFactory(new PropertyValueFactory<Beer, String>("Name"));
        beerType.setCellValueFactory(new PropertyValueFactory<Beer, String>("Type"));
        beerOrigin.setCellValueFactory(new PropertyValueFactory<Beer, String>("Origin"));
        beerProducer.setCellValueFactory(new PropertyValueFactory<Beer, String>("Producer"));
        beerPackage.setCellValueFactory(new PropertyValueFactory<Beer, String>("BeerPackage"));
        avrageRank.setCellValueFactory(new PropertyValueFactory<Beer, String>("AvRank"));
        beerPercentage.setCellValueFactory(new PropertyValueFactory<Beer, String>("Percentage"));
        beerInPubPrice.setCellValueFactory(new PropertyValueFactory<Beer, String>("Price"));


        // Try loading the image, if there is none will use placeholder
        beerImage.setCellValueFactory(new PropertyValueFactory<Beer, Image>("Image"));
        /**
         * Set the Cellfactory
         */
        beerImage.setCellFactory(new Callback<TableColumn<Beer, Image>, TableCell<Beer, Image>>() {
            @Override
            public TableCell<Beer, Image> call(TableColumn<Beer, Image> param) {
                TableCell<Beer, Image> cell = new TableCell<Beer, Image>() {

                    /**
                     * Override the updateItem method to set a imageView
                     * @param item
                     * @param empty
                     */
                    @Override
                    public void updateItem(Image item, boolean empty) {

                        if(!empty) {
                            if (item != null) {
                                VBox vb = new VBox();
                                vb.setAlignment(Pos.CENTER);
                                ImageView imgVw = new ImageView();
                                imgVw.setImage(item);
                                imgVw.setFitWidth(20);
                                imgVw.setFitHeight(40);
                                vb.getChildren().addAll(imgVw);
                                setGraphic(vb);



                            } else {
                                VBox vb = new VBox();
                                vb.setAlignment(Pos.CENTER);
                                ImageView imgVw = new ImageView();
                                imgVw.setImage(new Image(new File("src/com/group8/resources/Images/beerHasNoImage.png").toURI().toString()));
                                imgVw.setFitWidth(20);
                                imgVw.setFitHeight(40);
                                // Test Output
                                //System.out.println(imgVw.getImage().toString());
                                vb.getChildren().addAll(imgVw);
                                setGraphic(vb);

                            }
                        }
                    }
                };
                return cell;
            }

        });

        //Populate the Tableview
        beerTable.setItems(masterData);

    }


	/**
	 * Created by
	 * @param event
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void updatePub(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
		
		float entrance = Float.parseFloat(pubEntranceFee.getText());
		
		Byte[] emptyImage = new Byte[0];
		String pubInfo = "";
		int pubID = UserData.userInstance.getPubId();

		// Setup the mysel connection
		String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";
        
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection con = DriverManager.getConnection(url, user, password);
		
		// Make the sql statement as a string
		String statmnt = "UPDATE `pubs` SET `name`='"+
				pubName.getText()+
				"',`phoneNumber`='"+ pubPhoneNumber.getText()+"'";

				// If you loaded an image, update the image field allso.
				if(loadAnImage) {
					statmnt += ",`image`=?";
				}

		// rest of the fields
		statmnt +=",`description`='"+ pubDescription.getText()+"',`offers`='"+
				pubOffer.getText()+"',`entrenceFee`="+entrance+" WHERE pubID='"+
				UserData.userInstance.getPubId()+"';";

		// Set the statement to the prepared statement
		PreparedStatement statement = con.prepareStatement(statmnt);
		// Debug output
		System.out.println("loaded an image? ->"+loadAnImage);
		// if you loaded a new image set tthe binary stream
		if(loadAnImage) {
			statement.setBinaryStream(1, imageStream, (int) file.length());
		}

		statement.executeUpdate();
		statement = con.prepareStatement("UPDATE `pubAddress` SET `address`='"+pubAddress.getText()+"',`longitude`='" + longitude + "',`latitude`='" + latitude +"' where addressID=" + PubData.loggedInPub.getAdressId());
		statement.executeUpdate();

		// Show confirmation
		img.setFitWidth(60);
		img.setFitHeight(60);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Update Complete");
		alert.setHeaderText("Alert!");
		alert.setContentText("Your pub has been updated!");
		alert.setGraphic(img);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
		alert.showAndWait();

		// Test border set red
		//pubDescription.setStyle("-fx-border-color: red");

	}


	public void loadPubImage(ActionEvent event)throws IOException {

		// Set to true if you wanted to load an image
		loadAnImage = true;
		// Try catch to handle exeptions if user cancels imageload
		try{
			// Start a filechooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("open image file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

		Stage primaryStage=new Stage();

		file= fileChooser.showOpenDialog(primaryStage);


		imageStream = new FileInputStream(file);

		// Supported formats include JPEG , PNG, JPG
		if (file.isFile() && (file.getName().contains(".jpg")
				||file.getName().contains(".png")
				||file.getName().contains(".jpeg")
		)) {


			String thumbURL = file.toURI().toURL().toString();
			//	System.out.println(thumbURL);
			Image imgLoad = new Image(thumbURL);
			pubImage.setImage(imgLoad);

		}
		}catch(NullPointerException ex){
			// If the user cancels the imageload the loadAnImage is set to false
			// curently allso prints debug msg to console in form of boolean and file ( will be null )
			//ex.printStackTrace();
			System.out.println(file);
			loadAnImage = false;
			System.out.println(loadAnImage);
		}
	} // end of method

	/**
	 * load the addBeer scene
	 * --> Used to change the center FXML to the addBeer FXML alt "scene"
	 * @param event
	 * @throws IOException
	 */
	public void onAddBeer(ActionEvent event) throws IOException{
		mainScene.changeCenter("/com/group8/resources/views/addBeer.fxml");
         
	}

	/**
	 * Created by Andreas Fransson.
	 * @param event
	 * @throws IOException
	 */
	public void getMap(javafx.event.ActionEvent event) throws IOException{

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/com/group8/resources/views/AddressMap.fxml"));
		BorderPane page = loader.load();
		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Add Your Location");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(Navigation.primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		dialogStage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
		// Show the dialog and wait until the user closes it
		dialogStage.show();

		// When exiting the window update the address and fetch the latlong
		dialogStage.setOnHidden(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if(BeerData.Address != null) {

					// Store the address as lat and long doubles
					latitude = BeerData.Address.getLatitude();
					longitude = BeerData.Address.getLongitude();
				}
			}
		});

	}
}

	


		
	
	
	





	

