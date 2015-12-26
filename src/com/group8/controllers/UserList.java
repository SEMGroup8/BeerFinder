package com.group8.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.group8.database.tables.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class UserList extends BaseController implements Initializable {
	
    @FXML
    public TableView<User> userTable;
    @FXML
    public TableColumn<User, String> userName;
    @FXML
    public TableColumn<User, String> userAge;
    @FXML
    public TableColumn<User,String> userEmail;
    @FXML
    public TableColumn<User, Image> userImage;


    public ObservableList<User> masterData = FXCollections.observableArrayList(UserData.userInstance.allUsers);


    /**
     * Select a beer row and proceed to the beerDetail scene
     */
    public void getRow(){
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
                    System.out.println("clicked on " + userTable.getSelectionModel().getSelectedItem().getId());
                    int id = userTable.getSelectionModel().getSelectedItem().getId();
                    // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
                    
                    System.out.println(id+"  usssseriiiid");

                    User selected = new User("select * from users where userId =" +id);
                    UserData.selected = selected;
                    UserData.selected.getFollowers();
                    try {
                        mainScene.changeCenter("/com/group8/resources/views/otherUsersProfile.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
    }

    /**
     * initialize result controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


          Navigation.current_CenterFXML = "/com/group8/resources/views/pubList.fxml";

        // You have to have a get function that is named get +" type" for it to work sets values.
            userName.setCellValueFactory(new PropertyValueFactory<User, String>("_fullName"));
	        userEmail.setCellValueFactory(new PropertyValueFactory<User, String>("_email"));
	        userAge.setCellValueFactory(new PropertyValueFactory<User, String>("age"));
	      


        // Try loading the image, if there is none will use placeholder
        userImage.setCellValueFactory(new PropertyValueFactory<User, Image>("image"));
        /**
         * Set the Cellfactory
         */
        userImage.setCellFactory(new Callback<TableColumn<User, Image>, TableCell<User, Image>>() {
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
        userTable.setItems(masterData);

    }
}
