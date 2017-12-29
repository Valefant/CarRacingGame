import java.net.URI;
import java.util.*;

import com.sun.glass.events.WindowEvent;

import javafx.application.Application;
import javafx.scene.control.Alert; 
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;




public class App extends Application 
{
	// Diese Eigenschaften, blocken die Eingabe, wenn die andere dazugehörige Taste nicht gedrückt wird
	boolean player1KeyState = false;
	boolean player2KeyState = false;
	final int WIDTH = 1250;
	final int HEIGHT = 590;
	Player p1;
	Player p2;
	Stage primaryStage;
	static MediaPlayer player;
	static Timer timer;
	static Timer startTimer;
	static int elapsedTime = 0;
	static boolean wait = true;
	static ImageView counter = new ImageView(new Image("Images/Countdown3.png"));
	
	public static void main(String[] args) 
	{
		launch(args);
		
	}
  
	private static void timer()
	{		
		TimerTask task = new TimerTask()
		{
			int maxTime = 999;

		    @Override
		    public void run()
		    {
		    	elapsedTime++;

		       if(elapsedTime == 999)
		           System.out.println("Timeout!");
		       else
		           System.out.println("Time elapsed: " + elapsedTime );
		    }
		};
		
		timer = new Timer();
		timer.schedule(task, 0, 1000);
	}

	private static void startTimer()
	{
		wait = true;
		TimerTask task2 = new TimerTask()
		{
			
			int countdown = 3;

		    @Override
		    public void run()
		    {
		       if(countdown == 0)
		       {
		           wait = false;
		           timer();
		           startTimer.cancel();
		           counter.setImage(null);
		       }
		       else
		       {
		    	   switch (countdown) {
		    	   	case 2:
		    	   		counter.setImage(new Image("Images/Countdown2.png"));
		    	   		break;
		    	   	case 1:
		    	   		counter.setImage(new Image("Images/Countdown1.png"));
		    	   		break;
		    	   			
		    	   }
		           System.out.println(countdown);
		       }
		       
		       countdown--;
		    }
		};
		
		startTimer = new Timer();
		startTimer.schedule(task2, 0, 1000);
	}
	
	@Override
	public void start(Stage aPrimaryStage) throws Exception 
	{
		primaryStage = aPrimaryStage;
		// TODO:: Irgendwo müssen noch Hinweise angezeigt werden, die erklären, welche Tasten zum Spielen benutzt werden
		primaryStage.setTitle("Car Racing Game");
		primaryStage.setResizable(false);
		primaryStage.setHeight(HEIGHT);
		primaryStage.setWidth(WIDTH);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		
		Scene primaryScene = new Scene(grid);
		primaryStage.setScene(primaryScene);
		
		TextField player1TextField = new TextField();
		Label player1Label = new Label("Player 1");
		
		TextField player2TextField = new TextField();
		Label player2Label = new Label("Player 2");
		
		Button button = new Button("Play");
		button.setPrefWidth(80);
		
		TextField courseLength = new TextField("100");
		courseLength.setAlignment(Pos.CENTER_RIGHT);
		Label courseLabel = new Label("Course length");
		
		grid.add(player1Label, 0, 1);
		grid.add(player1TextField, 1, 1);
		
		grid.add(player2Label, 0, 2);
		grid.add(player2TextField, 1, 2);
		
		grid.add(courseLabel, 0, 3);
		grid.add(courseLength, 1, 3);
		
		grid.add(button, 1, 4);
		
		GamePlay gamePlay = new GamePlay();
		
		button.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent arg0) 
			{
				play();
				String player1Name = "Player1";
				String player2Name = "Player2";
				
				if(!player1TextField.getText().isEmpty()) {
					player1Name = player1TextField.getText();
				}
				
				if(!player2TextField.getText().isEmpty()) {
					player2Name = player2TextField.getText();
				}
				
				p1 = new Player(player1Name, new Car(new Position(0, 260), "Images/Car_Blue.png"), new PlayerStats(0));
				p2 = new Player(player2Name, new Car(new Position(0, 330), "Images/Car_Red.png"), new PlayerStats(0));
				
				gamePlay.setPlayers(p1, p2);
				
				Pane pane = new Pane();
				ImageView background = new ImageView(new Image("Images/Background.png"));
				
				Label labelp1 = new Label(p1.getName());
				Label labelp2 = new Label(p2.getName());
				labelp1.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
				labelp2.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
				
				labelp1.setLayoutX(23);
				labelp1.setLayoutY(20);
				
				labelp2.setLayoutX(1005);
				labelp2.setLayoutY(20);
				
				counter.setLayoutX(WIDTH / 2);
				counter.setLayoutY(25);
				
				pane.getChildren().add(background);
				pane.getChildren().add(p1.getCar().getImageView());
				pane.getChildren().add(p2.getCar().getImageView());
				pane.getChildren().add(labelp1);
				pane.getChildren().add(labelp2);
				
				pane.getChildren().add(counter);
				// TODO:: Hier muss dann noch der Header eingefügt werden. Jane weiß was damit gemeint ist :)
				
				gamePlay.setCourseLength(Integer.parseInt(courseLength.getText()));
				Scene gameScene = new Scene(pane);


				// Eingabe auf das ImageView gelegt, da die Szene nur eine Eingabe registrieren kann
				// Wenn ihr Ideen habt, wie ihr das besser machen könnt, dann macht das ruhig, weil diese Lösung ist ziemlich hässlich :/
				p1.getCar().getImageView().setFocusTraversable(true);
				p1.getCar().getImageView().requestFocus();
				p1.getCar().getImageView().setOnKeyPressed(new EventHandler<KeyEvent>() 
				{
					@Override
					public void handle(KeyEvent event) 
					{
						if(!wait)
						{
							if (event.getCode() == KeyCode.LEFT) 
							{
								player1KeyState = true;
							}
							
							if (player1KeyState == true && event.getCode() == KeyCode.RIGHT) 
							{
								// Breite des Fensters durch die Streckenlänge teilen, damit je nach länge der Strecke, die Autos langsamer oder schneller werden
								p1.getCar().move(WIDTH / gamePlay.getCourseLength());
								p1.getPlayerStats().increaseClick();
								player1KeyState = false;
							}
							
							if(event.getCode() == KeyCode.M)
							{
								p1.getCar().move(WIDTH-300);
								p1.getPlayerStats().increaseClick();
								player1KeyState = false;
							}
						}
					}
					
				});

				// Beim Drücken einer Taste wird die Methode hier ausgeführt
				gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() 
				{

					@Override
					public void handle(KeyEvent event) 
					{
					
						if(!wait)
						{
							if (event.getCode() == KeyCode.A) 
							{
								player2KeyState = true;
							}
							
							if (player2KeyState == true && event.getCode() == KeyCode.D) 
							{
								p2.getCar().move(WIDTH / gamePlay.getCourseLength());
								p2.getPlayerStats().increaseClick();
								player2KeyState = false;
							}
							
							if (gamePlay.isEndOfRace()) 
							{
								System.out.println("Winner: " + gamePlay.getWinner().getName());
								showWinner(gamePlay.getWinner());
								
								if(p1.getCar().getPosition().getX() > p2.getCar().getPosition().getX())
									p1.getPlayerStats().setTime(elapsedTime);
								else p2.getPlayerStats().setTime(elapsedTime);
								
								elapsedTime = 0;
								timer.cancel();
							}
						}
						
					}
					
				});
				
				
				primaryStage.setScene(gameScene);

				startTimer();
				
				primaryStage.setOnCloseRequest(new EventHandler<javafx.stage.WindowEvent>() {

					@Override
					public void handle(javafx.stage.WindowEvent arg0) {
						// TODO Auto-generated method stub
						timer.cancel();
					}
				});
			}
		});
		
		primaryStage.show();
	}
	
	private void showWinner(Player aWinner)
	{
		Stage dialogStage = new Stage();
		dialogStage.setMinHeight(HEIGHT/3);
		dialogStage.setMaxHeight(HEIGHT/3);
		dialogStage.setMinWidth(WIDTH/3);
		dialogStage.setMaxWidth(WIDTH/3);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(15);
		
		Scene primaryScene = new Scene(grid);
		dialogStage.setScene(primaryScene);
		dialogStage.setTitle("Congratulation");
		Label lblWinner = new Label("The winner is " + aWinner.getName());
		
		Button btnPlay = new Button("  Play again  ");
		btnPlay.setPrefWidth(100);
		Button btnExit = new Button("  Exit  ");
		btnExit.setPrefWidth(100);
		
		grid.add(btnPlay, 1, 2);
		grid.add(btnExit, 3, 2);
		grid.add(lblWinner, 2, 0);

		btnExit.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent arg0) 
			{
				timer.cancel();
				System.exit(0);
			}
		});
		
		btnPlay.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent arg0) 
			{
				p1.getCar().getPosition().setX(0);
				p2.getCar().getPosition().setX(0);
				p1.getCar().move(0);
				p2.getCar().move(0);
				dialogStage.close();
				startTimer();
			}
		});

		dialogStage.show();
	}
	
	private static void play()
	{
	    Media audioFile = new Media("file:///c:/Users/Onit/Desktop/Workspace/CarRacingGame/src/Sounds/test.wav");     
	    try
	    {   
	    	player = new MediaPlayer(audioFile);
	        player.play();
	    }
	    catch (Exception e)
	    {
	        System.out.println( e.getMessage() );
	    }        
	}
}