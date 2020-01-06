package sample;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.io.File;
import java.util.Collections;
import java.util.Optional;


public class Controller {

    //A count variable to track the number of moves in the game
    static int count=0;

    //Creating the object of the defined class Tic Tac Toe
    TicTacToe ticTacToe = new TicTacToe();

    //Creating file objects for different file used in GUI
    File oFile = new File("src/raw/O.png");
    File xFile = new File("src/raw/X.png");
    File backgroundFile1 = new File("src/raw/Background-1.png");
    File backgroundFile2 = new File("src/raw/Background-2.png");

    //Creating image objects for different images used in GUI
    Image oImage = new Image(oFile.toURI().toString());
    Image xImage = new Image(xFile.toURI().toString());
    Image backgroundImage1 = new Image(backgroundFile1.toURI().toString());
    Image backgroundImage2 = new Image(backgroundFile2.toURI().toString());

    //Declaring the Global variable for GridPane
    @FXML
    GridPane boardGrid;

    //Declaring the Global variable for image view of collinear line
    @FXML
    ImageView lineImageView;

    //Declaring the Global variable for image view of Background
    @FXML
    ImageView backgroundImageView;

    //Declaring the Global variable for image view of human button
    @FXML
    ImageView humanButtonImageView;

    //Declaring the Global variable for image view of human move
    @FXML
    ImageView humanListenerImageView;

    //Declaring the Global variable for image view of pc move
    @FXML
    ImageView pcListenerImageView;

    //Declaring the Global variable for list of imageviews present in gridpane
    ObservableList<Node> imageViewList;

    //Method to initialize the variable
    public void initialize(){

        imageViewList= boardGrid.getChildren();

    }


    //Method for mouse click listener
    public void onclick(MouseEvent mouseEvent) {

        //setting the imageview on which the user clicked
        humanListenerImageView =(ImageView) mouseEvent.getSource();
        System.out.println(mouseEvent.getSource());

        //cheking whether the chosen element is occupied or not
        if(ticTacToe.humanPicks(Integer.valueOf(humanListenerImageView.getUserData().toString()))){
            setImage(humanListenerImageView,oImage);

            count=count+1;

            //Checking whether the user has won the game
            if(ticTacToe.humHasWon()){
                //Sorting the winning move list
                Collections.sort(ticTacToe.winningMoves);

                //Calling the drawLine function to strike through the moves
                drawLine();

                //Generating dialog box after the game is over
                generateDialog("YOU WON!!");
                return;
            }

        } else return;

        //Declaring variable for the element which pc chooses
        int n=0;

        //Checking whether its first move or not
        if(count==1){

            //Checking whether 5 is available or not
            if(!ticTacToe.goFive()){

                //if not available then choose random
                n=ticTacToe.goRandom();

            }else {

                //else choose 5
                n=5;
            }

            //Setting image in the board
            pcListenerImageView=(ImageView) imageViewList.get(ticTacToe.tagList.get(n-1));
            setImage(pcListenerImageView,xImage);


        }//Checking whether vector list is empty or not
        else if(!ticTacToe.vectors.isEmpty()){

            //Checking whether computer or human winning in the next move
            if(ticTacToe.isComputerWinning() || ticTacToe.isHumanWinning()){

                //Choosing that particular element which can win the game for computer or user
                n=ticTacToe.computerPicks(ticTacToe.target);

            }else{
                //if no one is winning in the next move then choose random element
                n = ticTacToe.goRandom();
            }

            //Setting the image in the board
            pcListenerImageView=(ImageView) imageViewList.get(ticTacToe.tagList.get(n-1));
            setImage(pcListenerImageView,xImage);

            //Checking whether pc has won
            if(ticTacToe.comHasWon()){
                Collections.sort(ticTacToe.winningMoves);
                //if pc has won then calling the drawline function and generating dialog
                drawLine();
                generateDialog("YOU LOSE!!");
            }

        }


        //if vector list is empty then its a draw
        if(ticTacToe.vectors.isEmpty()){
            generateDialog("Its a Draw!!");
        }

    }

    //Method to generate dialog after the game is over
    void generateDialog(String text){

        //Using timer class to create a delay of 0.5 sec
        new java.util.Timer().schedule( new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                                alert.setHeaderText(text);
                                alert.setContentText("Choose your option.");
                                ButtonType buttonTypeOne = new ButtonType("Play Again");
                                ButtonType buttonTypeTwo = new ButtonType("Quit");
                                alert.getButtonTypes().setAll(buttonTypeOne,buttonTypeTwo);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == buttonTypeOne){
                                            playAgain();
                                        } else {
                                            Platform.exit();
                                        }
                                    }
                                });

                            }
                        });
                    }
                },
                500);
        }

    //Method which will be called when user chooses to play again
    void playAgain(){

        //Initializing all the variables again

        for(int i=0;i<imageViewList.size();i++){
            ImageView im = (ImageView) imageViewList.get(i);
            im.setImage(null);
        }

        ticTacToe=new TicTacToe();
        lineImageView.setX(0);
        lineImageView.setY(0);
        lineImageView.setRotate(0);
        lineImageView.setFitWidth(0);
        lineImageView.setImage(null);
        backgroundImageView.setImage(backgroundImage1);
        boardGrid.setVisible(false);
        count=0;

    }

    //Method to set image in the image view with transition effect
    public void setImage(ImageView imageView,Image image){

        //Creating the object for the FadeTransition class
        FadeTransition fadeTransitiont = new FadeTransition();
        imageView.setImage(image);

        //Setting the properties for transition
        fadeTransitiont.setDuration(Duration.millis(1000));
        fadeTransitiont.setNode(imageView);
        fadeTransitiont.setFromValue(0.0);
        fadeTransitiont.setToValue(1.0);
        fadeTransitiont.play();

    }

    //Method to draw the line after the game is over
    public void drawLine(){

            File lfile = new File("src/raw/Line.png");
            Image limage = new Image(lfile.toURI().toString());

            //Initializing the variables for the coordinates, width, and the rotation angle
            double x,y,rot,w;
            w=x=y=rot=0;

            //Checking several condition to decide where to draw the line
            if(ticTacToe.lineDetection(2,7)){
                x=55; y=155; rot=0; w=410;
            }else if(ticTacToe.lineDetection(1,5)){
                x=55; y=290; rot=0; w=410;
            }
            else if(ticTacToe.lineDetection(3,4)){
                x=55; y=430; rot=0; w=410;
            }
            else if(ticTacToe.lineDetection(2,4)){
                x=-105; y=310; rot=90; w=400;
            }
            else if(ticTacToe.lineDetection(3,5)){
                x=25; y=310; rot=90; w=400;
            }
            else if(ticTacToe.lineDetection(1,6)){
                x=175; y=310; rot=90; w=400;
            }
            else if(ticTacToe.lineDetection(2,5)){
                x=-10; y=315; rot=45; w=539;
            }
            else if(ticTacToe.lineDetection(4,5)){
                x=-40; y=315; rot=135; w=539;
            }

            //finally setting the coordinate, width, and the rotation angle to draw the line
            lineImageView.setLayoutX(x);
            lineImageView.setLayoutY(y);
            lineImageView.setRotate(rot);
            lineImageView.setFitWidth(w);
            lineImageView.setImage(limage);

    }

    //Mouse Listener to decide who goes first
    public void onButtonClick(MouseEvent mouseEvent) {

        //Checking who will go first?
        if(!(mouseEvent.getSource() ==humanButtonImageView)){
            ticTacToe.goFive();
            pcListenerImageView=(ImageView)imageViewList.get(4);
            setImage(pcListenerImageView,xImage);
        }

        //Setting the visibility of grid board
        boardGrid.setVisible(true);
        backgroundImageView.setImage(backgroundImage2);

    }

}
