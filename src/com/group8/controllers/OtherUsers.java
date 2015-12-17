package com.group8.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.Pub;
import com.group8.database.tables.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class OtherUsers extends BaseController implements Initializable {


		// TODO Auto-generated method stub
		 @FXML
		    public Button logout, account;
		    @FXML
		    public Button Back;
		    
		    @FXML
			public TableView<Pub> pubTable;
		    @FXML
		    public TableColumn<Pub, String> pubName;
		    @FXML
		    public TableColumn<Pub, String> pubAddress;
		    @FXML
		    public TableColumn<Pub, String> pubPhoneNumber;
		    @FXML
		    public TableColumn<Pub, String> pubOffer;
		    @FXML
		    public TableColumn<Pub, String> pubDescription;
		    @FXML
		    public TableColumn<Pub, String> pubEntranceFee;
		    @FXML
		    public TableColumn<Pub, Image> image;
		    public Label emailError, passwordError, isPubError, fullNameError;
		    
		    @FXML
		    public TableView<User> userTable;
		    @FXML
		    public TableColumn<User, String> userName1;
		    @FXML
		    public TableColumn<User, String> userAge;
		    @FXML
		    public TableColumn<User,String> userEmail;
		    @FXML
		    public TableColumn<User, Image> userImage1;
		    
		    
		    @FXML
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
		    public PieChart showPie;
		    @FXML
		    public Label userName;

		    public ObservableList<Beer> masterData = FXCollections.observableArrayList(UserData.selected.favourites);
		    public ObservableList<Pub> masterData1 = FXCollections.observableArrayList(UserData.selected.pubFavouritesDetails);
		    public ObservableList<User> masterData2 = FXCollections.observableArrayList(UserData.selected.followedUsers);
		
		
		public Label showGreetings;
		public Label emailLabel;
		public Label followLabel;
		public Button userImageButton;
		public Button loadImage;
		public Button followButton;
		
		public Label age;
		 public Button update;
		    @FXML
		    public Label fullname;
		    @FXML
		    public Label email;
		    @FXML
		    public Label password;
		
		public ImageView userImage;
		PreparedStatement statement;
		FileInputStream imageStream ;
		File file;
		boolean loadAnImage = false;

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			userImage.setImage(UserData.selected.getImage());System.out.println(UserData.userInstance.getImage()+"   geniussss!!");
			followLabel.setText(UserData.selected.get_name());
			age.setText(""+UserData.selected.getAge());
			fullname.setText(UserData.selected.get_fullName());
		    password.setText(UserData.selected.get_password());
		    email.setText(UserData.selected.get_email());
			
			
	        Navigation.current_CenterFXML =  "/com/group8/resources/views/favourites.fxml";

	        // You have to have a get function that is named get +" type" for it to work sets values.
	        beerName.setCellValueFactory(new PropertyValueFactory<Beer, String>("Name"));
	        beerType.setCellValueFactory(new PropertyValueFactory<Beer, String>("Type"));
	        beerOrigin.setCellValueFactory(new PropertyValueFactory<Beer, String>("Origin"));
	        beerProducer.setCellValueFactory(new PropertyValueFactory<Beer, String>("Producer"));
	        beerPackage.setCellValueFactory(new PropertyValueFactory<Beer, String>("BeerPackage"));
	        avrageRank.setCellValueFactory(new PropertyValueFactory<Beer, String>("AvRank"));
	        beerPercentage.setCellValueFactory(new PropertyValueFactory<Beer, String>("Percentage"));


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
		
	        
	        Navigation.current_CenterFXML = "/com/group8/resources/views/pubList.fxml";

	        // You have to have a get function that is named get +" type" for it to work sets values.
	            pubName.setCellValueFactory(new PropertyValueFactory<Pub, String>("_name"));
		        pubAddress.setCellValueFactory(new PropertyValueFactory<Pub, String>("_adressId"));
		        pubPhoneNumber.setCellValueFactory(new PropertyValueFactory<Pub, String>("_phoneNumber"));
		        pubOffer.setCellValueFactory(new PropertyValueFactory<Pub, String>("_offer"));
		        pubDescription.setCellValueFactory(new PropertyValueFactory<Pub, String>("_description"));
		        pubEntranceFee.setCellValueFactory(new PropertyValueFactory<Pub, String>("_entranceFee"));
	          


	        // Try loading the image, if there is none will use placeholder
	        image.setCellValueFactory(new PropertyValueFactory<Pub, Image>("image"));
	        /**
	         * Set the Cellfactory
	         */
	        image.setCellFactory(new Callback<TableColumn<Pub, Image>, TableCell<Pub, Image>>() {
	            @Override
	            public TableCell<Pub, Image> call(TableColumn<Pub, Image> param) {
	                TableCell<Pub, Image> cell = new TableCell<Pub, Image>() {

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
	                                imgVw.setFitWidth(40);
	                                imgVw.setFitHeight(40);
	                                vb.getChildren().addAll(imgVw);
	                                setGraphic(vb);

	                            } else {
	                                VBox vb = new VBox();
	                                vb.setAlignment(Pos.CENTER);
	                                ImageView imgVw = new ImageView();
	                                imgVw.setImage(new Image(new File("src/com/group8/resources/Images/beerHasNoImage.png").toURI().toString()));
	                                imgVw.setFitWidth(60);
	                                imgVw.setFitHeight(40);
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
	        pubTable.setItems(masterData1);
	        
	        Navigation.current_CenterFXML = "/com/group8/resources/views/pubList.fxml";

	        // You have to have a get function that is named get +" type" for it to work sets values.
	            userName1.setCellValueFactory(new PropertyValueFactory<User, String>("_fullName"));
		        userEmail.setCellValueFactory(new PropertyValueFactory<User, String>("_email"));
		        userAge.setCellValueFactory(new PropertyValueFactory<User, String>("age"));
		      


	        // Try loading the image, if there is none will use placeholder
	        userImage1.setCellValueFactory(new PropertyValueFactory<User, Image>("image"));
	        /**
	         * Set the Cellfactory
	         */
	        userImage1.setCellFactory(new Callback<TableColumn<User, Image>, TableCell<User, Image>>() {
	            @Override
	            public TableCell<User, Image> call(TableColumn<User, Image> param) {
	                TableCell<User, Image> cell = new TableCell<User, Image>() {

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
	                                imgVw.setFitWidth(60);
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
	        userTable.setItems(masterData2);


	    }
		
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
	                    // Set the selectedBeer instance of beer we have to selected item
	                    BeerData.selectedBeer = beerTable.getSelectionModel().getSelectedItem();

	                    // Load the details scene
	                    // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
	                    try {
	                        // TODO have to fix nameing
	                        mainScene.changeCenter("/com/group8/resources/views/beerDetails_center.fxml");
	                        

	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }

	                }
	            }

	        });
	    
		        pubTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		            // Select item will only be displayed when dubbleclicked

		            /**
		             * Dubleclick event
		             * @param event
		             */
		            @Override
		            public void handle(MouseEvent event) {
		                if (event.getClickCount() == 2) {
		                        // Show that we can select items and print it
		                       
		                       PubData.selectedPub = pubTable.getSelectionModel().getSelectedItem();
		                        // Load the details scene
		                        // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
		                        try {
									mainScene.changeCenter("/com/group8/resources/views/pubDetailView.fxml");
		                        } catch (IOException e) {
		                            // Print error msg
		                            e.printStackTrace();
		                        }
		                        
		                }
		            }

		        });

		        userTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		            // Select item will only be displayed when dubbleclicked

		            private User selected;

					/**
		             * Dubleclick event
		             * @param event
		             */
		            @Override
		            public void handle(MouseEvent event) {
		                if (event.getClickCount() == 2) {
		                    // Show that we can select items and print it
		                    System.out.println("clicked on " + userTable.getSelectionModel().getSelectedItem().get_id());
		                    int id = userTable.getSelectionModel().getSelectedItem().get_id();
		                    // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
		                    
		                    System.out.println(id+"  usssseriiiid");

		                    User selected = new User("select * from users where userId =" +id);
		                    UserData.selected = selected;

		                    try {
		                        mainScene.changeCenter("/com/group8/resources/views/otherUsersProfile.fxml");
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }

		                }
		            }

		        });
		    }
		
		public void followUser(ActionEvent event) throws ClassNotFoundException, SQLException{
			// Setup the mysel connection
			String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
	        String user = "Gr8";
	        String password = "group8";
	        
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, user, password);
			System.out.println(UserData.userInstance.get_id()+"  uuu  "+ UserData.selected.get_id());
			int 	myId=UserData.userInstance.get_id();
			int followerID=UserData.selected.get_id();
			
			//if (myId=null){
			String followUser="INSERT INTO `followUser`(`userId`, `id`) VALUES ("+myId+", "+followerID+")";
			MysqlDriver.insert(followUser);
			
			//}
			//else{
				
			//}
			
		}
		
		
		}
		
		
	