package com.group8.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.Pub;
import com.group8.database.tables.User;

import com.group8.singletons.BeerData;
import com.group8.singletons.Navigation;
import com.group8.singletons.PubData;
import com.group8.singletons.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 * Created by Collins
 * Users information scene
 * --> Used to show a list of users in the database
 * --> Gives the logged in user the opportunity to see other users and follow them
 *
 */

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
	
	/**
	 * table to display all followed users
	 */
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

	/**
	 * table to display all favorite beers
	 */
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
	
	/**
	 * converts arrays into observable arrayList
	 */
	public ObservableList<Beer> masterData = FXCollections.observableArrayList(UserData.selected.favourites);
	public ObservableList<Pub> masterData1 = FXCollections.observableArrayList(UserData.selected.pubFavouritesDetails);
	public ObservableList<User> masterData2 = FXCollections.observableArrayList(UserData.selected.followedUsers);
    ImageView img= new ImageView((this.getClass().getResource("/com/group8/resources/Images/Icon_2.png").toString()));

		
		
	public Label showGreetings;
	public Label emailLabel;
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
	private Service<Void> backgroundThread;
	/**
	 * automatically loads the information of the logged in user
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {


		Navigation.current_CenterFXML =  "/com/group8/resources/views/otherUsersProfile.fxml";
		/**
		 * Created by Andreas Fransson
		 */
		if(UserData.selected.getImage()==null){
			userImage.setImage(new Image(new File("src/com/group8/resources/Images/defaultIcon.png").toURI().toString()));
		}else {
			userImage.setImage(UserData.selected.getImage());
		}

		if(UserData.userInstance==null) {

			followButton.setVisible(false);

		}

		age.setText(""+UserData.selected.getAge());
		fullname.setText(UserData.selected.getFullName());
		password.setText(UserData.selected.getPassword());
		email.setText(UserData.selected.getEmail());


		// You have to have a get function that is named get +" type" for it to work sets values.
		beerName.setCellValueFactory(new PropertyValueFactory<Beer, String>("name"));
		beerType.setCellValueFactory(new PropertyValueFactory<Beer, String>("type"));
		beerOrigin.setCellValueFactory(new PropertyValueFactory<Beer, String>("origin"));
		beerProducer.setCellValueFactory(new PropertyValueFactory<Beer, String>("producer"));
		beerPackage.setCellValueFactory(new PropertyValueFactory<Beer, String>("beerPackage"));
		avrageRank.setCellValueFactory(new PropertyValueFactory<Beer, String>("avRank"));
		beerPercentage.setCellValueFactory(new PropertyValueFactory<Beer, String>("percentage"));


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

		

		//You have to have a get function that is named get +" type" for it to work sets values.
		pubName.setCellValueFactory(new PropertyValueFactory<Pub, String>("name"));
		pubAddress.setCellValueFactory(new PropertyValueFactory<Pub, String>("adressId"));
		pubPhoneNumber.setCellValueFactory(new PropertyValueFactory<Pub, String>("phoneNumber"));
		pubOffer.setCellValueFactory(new PropertyValueFactory<Pub, String>("offer"));
		pubDescription.setCellValueFactory(new PropertyValueFactory<Pub, String>("description"));
		pubEntranceFee.setCellValueFactory(new PropertyValueFactory<Pub, String>("entranceFee"));


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
								imgVw.setImage(new Image(new File("src/com/group8/resources/Images/defaultIcon.png").toURI().toString()));
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


		// You have to have a get function that is named get +" type" for it to work sets values.
		userName1.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
		userEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
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

					// load the details scene
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
					// load the details scene
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


					backgroundThread = new Service<Void>() {
						@Override
						protected Task<Void> createTask() {
							return new Task<Void>() {
								@Override
								protected Void call() throws Exception {

									// Show that we can select items and print it
									int id = userTable.getSelectionModel().getSelectedItem().getId();
									// Has to be in a tr / catch because of the event miss match, ouseevent cant throw IOexceptions

									User selected = new User("select * from users where userId =" +id);
									UserData.selected = selected;
									UserData.selected.getFavouriteBeers();
									UserData.selected.getPubFavourites();
									UserData.selected.getFollowers();

									return null;
								}
							};
						}
					};

					backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						@Override
						public void handle(WorkerStateEvent event) {

							try {
								mainScene.changeCenter("/com/group8/resources/views/otherUsersProfile.fxml");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});

					// Start thread
					backgroundThread.start();
				}
			}

		});
	}

	public void followUser(ActionEvent event) throws ClassNotFoundException, SQLException{

		int myId=UserData.userInstance.getId();
		int followerID=UserData.selected.getId();
		/**
		 * checking to see whether user is already following selected user
		 * if not, it does insertion into the database base else sends an error message
		 */
		String checkStatus="select `userId`, `id` from `followUser` where userId"+myId+ "AND id="+followerID;
		ArrayList<Object> isIn = MysqlDriver.select(checkStatus);
		if(!isIn.isEmpty()){
		String followUser="INSERT INTO `followUser`(`userId`, `id`) VALUES ("+myId+", "+followerID+")";
		MysqlDriver.insert(followUser);
		}else{
			img.setFitWidth(60);
            img.setFitHeight(60);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Follow Error");
            alert.setContentText("You already following this person!");
            alert.setGraphic(img);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
            alert.showAndWait();
		}
	}
}
		
		
	