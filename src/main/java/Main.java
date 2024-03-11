/* ---------------------------------------------
File Name:      Main.java
Author:         Joshua Hontanosas
Description:    This is the class where the JavaFX program will run.
--------------------------------------------- */

import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	// --- BaccaratGame Object ---
	BaccaratGame bGame = new BaccaratGame();
	
	// --- JavaFX Elements ---
	// *Start Screen Elements*
	private TextField gameTitle = new TextField("Baccarat");
	private Button startBtn = new Button("Play");
	private HBox titleScreenButtons = new HBox();
	// *Game Screen Elements*
	// - Bottom View -
	private Button playerBtn = new Button("Bet Player");
	private Button bankerBtn = new Button("Bet Banker");
	private Button tieBtn = new Button("Bet Tie");
	private Button startRoundBtn = new Button("Start \nRound");
	private Text betFieldLabel = new Text("Bet Amount:");
	private TextField betTextField = new TextField();
	private HBox bottomView = new HBox(); // *Betting HBox - Player puts there bets here*
	// - Center View -
	private VBox pHand = new VBox(); //*Player Hand VBox - Contains player hand label and current score*
	private VBox bHand = new VBox(); //*Banker Hand VBox - Contains banker hand label and current score*
	private Text playerLabel = new Text("Player Hand"); //	Player Hand Label
	private Text playerScore = new Text("Score:"); //	Player Score
	private Text bankerLabel = new Text("Banker Hand");
	private Text bankerScore = new Text("Score:"); //	Banker Score
	private HBox playerCards = new HBox(); // *Player Hand HBox*
	private HBox dealerCards = new HBox(); // *Dealer Hand HBox*
	private ImageView[] playerCardsIV = new ImageView[3]; // Player Cards ImageViews
	private ImageView[] bankerCardsIV = new ImageView[3]; // Banker Cards ImageViews
	private GridPane pCardsGP = new GridPane();
	private GridPane bCardsGP = new GridPane();
	private VBox centerView = new VBox(); // *Put player and dealer hands in this*
	private FadeTransition[][] fadeInCard = new FadeTransition[2][3];
	private FadeTransition[][] fadeOutCard = new FadeTransition[2][3];
	// - Top View -
	private Text moneyStats = new Text();
	private VBox topView = new VBox();
	private MenuBar mBar = new MenuBar();
	private Menu optionsMenu = new Menu("Options");
	private MenuItem exitProgramMI = new MenuItem("Exit");
	private MenuItem freshStartMI = new MenuItem("Fresh Start");
	// - Right View -
	private StackPane rightView = new StackPane();
	private Text messageText = new Text("Messages will\ngo here.");
	private Rectangle messageTextBackground = new Rectangle();
	
	// --- Main ----
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Baccarat - Joshua Hontanosas");
		
		// -- Prepping Start Screen elements ---
		setupStartScreen();
		// --- Prepping Game Screen elements ---
		setupBottomView();	
		setupCenterView();
		setupTopView();
		setupRightView();
		
		// --- Set Transitions ---
		setFadeTransitions();
		
		 // *** Start Screen ***
	     BorderPane startBorderPane = new BorderPane();
	     BackgroundFill startBackgroundFill = new BackgroundFill(Color.FORESTGREEN, new CornerRadii(0), new javafx.geometry.Insets(0));
	     startBorderPane.setBackground(new Background (startBackgroundFill));
	     startBorderPane.setTop(gameTitle);
	     startBorderPane.setBottom(titleScreenButtons);
	     Scene startScreen = new Scene(startBorderPane, 960,540);
	     
	     // *** Game Screen ***
	     BorderPane gameBorderPane = new BorderPane();
	     BackgroundFill gameBackgroundFill = new BackgroundFill(Color.FORESTGREEN, new CornerRadii(0), new javafx.geometry.Insets(0));
	     gameBorderPane.setBackground(new Background (gameBackgroundFill));
	     gameBorderPane.setTop(topView);
	     gameBorderPane.setCenter(centerView);
	     gameBorderPane.setBottom(bottomView);
	     gameBorderPane.setRight(rightView);
	     BorderPane.setMargin(rightView, new Insets(50, 50, 50, 50));
	     Scene gameScreen = new Scene(gameBorderPane, 960,540);
	     
	     // --- Event Handlers ---
	     // startBtn - Switches to gameScreen.
	     startBtn.setOnAction(e -> {
	    	 primaryStage.setScene(gameScreen);
	     });
	     
	     // playerBtn - Switches betChoice to "Player".
	     playerBtn.setOnAction(e -> {
	    	 playerBtn.setDisable(true);
	    	 bankerBtn.setDisable(false);
	    	 tieBtn.setDisable(false);
		     bGame.setBetChoice("Player");
	     });
	     
	     // bankerBtn - Switches betChoice to "Banker".
	     bankerBtn.setOnAction(e -> {
	    	 playerBtn.setDisable(false);
	    	 bankerBtn.setDisable(true);
	    	 tieBtn.setDisable(false);
	    	 bGame.setBetChoice("Banker");
	     });
	     
	     // tieBtn - Switches betChoice to "Draw".
	     tieBtn.setOnAction(e -> {
	    	 playerBtn.setDisable(false);
	    	 bankerBtn.setDisable(false);
	    	 tieBtn.setDisable(true);
	    	 bGame.setBetChoice("Draw");
	     });
	     
	     // startRoundBtn - Runs the round
	     startRoundBtn.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent e) {
				 if(isValidBet()) {
					 startRoundBtn.setDisable(true);	// Disable button
					 resetGame();						// Reset game
					 startRound();						// Start this round of Baccarat
				 }
			 }
		 });
	     
	     //exitProgramMI - Exits the program.
	     exitProgramMI.setOnAction(e ->{
	    	 primaryStage.close();
	     });
	     
	     // freshStartMI - Resets currentWinnings to zero.
	     freshStartMI.setOnAction(e -> {
	    	 bGame.totalWinnings = 0.0;
	    	 updateWinnings();
	     });
	     
	     // --- For the Game Screen, set "Bet Player" button as default option. ---
	     playerBtn.setDisable(true);
	     bGame.setBetChoice("Player");
	   
	     // --- On start-up, set to start screen ---
	     primaryStage.setScene(startScreen);
	     primaryStage.show();
	}
	
//	// --- JavaFX Methods ---
	// *** For Game ***
	// isValidBet() - Determines if bet entered into betTextField is a valid amount.
	boolean isValidBet() {
		double value = 0.0;
		try {
			value = Double.parseDouble(betTextField.getText());
		}
		catch(Exception e){
			messageText.setText("Cannot read bet.\nTry entering a valid amount.");
			return false;
		}
		
		if(value <= 0) {
			messageText.setText("Cannot bet negative/zero values.\nTry entering a positive bet.");
			return false;
		}
		else {
			bGame.currentBet = value;
			updateWinnings();
			return true;
		}
	}
	
	// startRound - Prepares BaccaratDealer and UI for the round.
	void startRound() {
		messageText.setText("Round begun!");
		bGame.theDealer.generateDeck();			// Create a new deck
		bGame.theDealer.shuffleDeck();			// Shuffle deck before dealing
		clearCards();						// Clear cards (transitions to dealing hands)
	}
	
	// clearCards() - Fades out cards from the game screen.
	void clearCards() {
		ParallelTransition cardsToClear = new ParallelTransition();
		for(int i = 0; i < 3; i++) {
			if(playerCardsIV[i].getOpacity() == 1.0)
				cardsToClear.getChildren().add(fadeOutCard[0][i]);
			if(bankerCardsIV[i].getOpacity() == 1.0)
				cardsToClear.getChildren().add(fadeOutCard[1][i]);
		}
		cardsToClear.setOnFinished(e -> {
			playerDealHand();
		});
		cardsToClear.play();
	}
	
	// playerDealHand() - Deals two cards to the player hand.
	void playerDealHand() {
		// Draw two cards for player.
		for(int i = 1; i <= 2; i++)
			bGame.playerHand.add(bGame.theDealer.drawOne());
		// Calculate hand total.
		int s = bGame.gameLogic.handTotal(bGame.playerHand);
		// Update ImageViews for the first two player cards.
		playerCardsIV[0].setImage(new Image("/cardImages/" + bGame.playerHand.get(0).suite + bGame.playerHand.get(0).value + ".png"));
		playerCardsIV[1].setImage(new Image("/cardImages/" + bGame.playerHand.get(1).suite + bGame.playerHand.get(1).value + ".png"));
		// Fade in the first two cards in order.
		SequentialTransition seqFirstTwoCards = new SequentialTransition(fadeInCard[0][0], fadeInCard[0][1]);
		// Deal the banker's hand once animation is complete.
		seqFirstTwoCards.setOnFinished(e -> {
			playerScore.setText("Score: " + s);
			if(s >= 8)
				bGame.setGotNaturalWin(true);
	    	bankerDealHand();
	     });
		seqFirstTwoCards.play();
	}
	
	// bankerDealHand() - Deals two cards to the banker hand.
	void bankerDealHand() {
		// Draw two cards for player.
		for(int i = 1; i <= 2; i++)
			bGame.bankerHand.add(bGame.theDealer.drawOne());
		// Calculate hand total.
		int s = bGame.gameLogic.handTotal(bGame.bankerHand);
		// Update ImageViews for the first two player cards.
		bankerCardsIV[0].setImage(new Image("/cardImages/" + bGame.bankerHand.get(0).suite + bGame.bankerHand.get(0).value + ".png"));
		bankerCardsIV[1].setImage(new Image("/cardImages/" + bGame.bankerHand.get(1).suite + bGame.bankerHand.get(1).value + ".png"));
		// Fade in the first two cards in order.
		SequentialTransition seqFirstTwoCards = new SequentialTransition(fadeInCard[1][0], fadeInCard[1][1]);
		// Check if there is a natural win. If so, check who won. Otherwise, check if player and banker can draw one more. 
		seqFirstTwoCards.setOnFinished(e -> {
			bankerScore.setText("Score: " + s);
			if(s >= 8)
				bGame.setGotNaturalWin(true);
			if(bGame.getGotNaturalWin()) {
				checkWinner();
			}
			else {
				playerDrawOneMore();
			}
	     });
		seqFirstTwoCards.play();
	}
	
	// playerDrawOneMore() - Check if player should draw another card. If so, draw one.
	void playerDrawOneMore() {
		// Check if player should draw another card. Otherwise, move on to the banker.
		if(bGame.gameLogic.evaluatePlayerDraw(bGame.playerHand)) {
			// Draw another card for the player.
			bGame.playerHand.add(bGame.theDealer.drawOne());
			int s = bGame.gameLogic.handTotal(bGame.playerHand);
			// Update ImageView for the third player card.
			playerCardsIV[2].setImage(new Image("/cardImages/" + bGame.playerHand.get(2).suite + bGame.playerHand.get(2).value + ".png"));
			// Fade in the third card. 
			SequentialTransition seqThirdCard = new SequentialTransition(fadeInCard[0][2]);
			// Now draw the banker's third card (if applicable)
			seqThirdCard.setOnFinished(e -> {
				playerScore.setText("Score: " + s);
				bankerDrawOneMore();
			});
			seqThirdCard.play();
		}
		else {
			bankerDrawOneMore();
		}
	}
	
	// bankerDrawOneMore() - Check if banker should draw another card. If so, draw one.
	void bankerDrawOneMore() {
		// Check if player should draw another card. Otherwise, check winner of the round.
		if(bGame.gameLogic.evaluatePlayerDraw(bGame.bankerHand)) {
			// Draw another card for the banker.
			bGame.bankerHand.add(bGame.theDealer.drawOne());
			int s = bGame.gameLogic.handTotal(bGame.bankerHand);
			// Update ImageView for the third banker card.
			bankerCardsIV[2].setImage(new Image("/cardImages/" + bGame.bankerHand.get(2).suite + bGame.bankerHand.get(2).value + ".png"));
			// Fade in the third card. 
			SequentialTransition seqThirdCard = new SequentialTransition(fadeInCard[1][2]);
			// Now evaluate winner
			seqThirdCard.setOnFinished(e -> {
				bankerScore.setText("Score: " + s);
				checkWinner();
			});
			seqThirdCard.play();
		}
		else {
			checkWinner();
		}
	}
	
	// checkWinner() - Determine if the player won with their choice.
	void checkWinner() {
		String results = "";
		String winner = bGame.gameLogic.whoWon(bGame.playerHand, bGame.bankerHand);
		double winnings = bGame.evaluateWinnings();
		bGame.totalWinnings += bGame.evaluateWinnings();
		
		results += (winner + " wins.\n");
		if(winner == bGame.getBetChoice()) {
			results += ("Congratulations! You win!\n");
			results += ("Amount won: $" + winnings + "\n");
			if(bGame.getBetChoice() == "Banker") {
				results += ("(Took out $" + (bGame.currentBet * 0.05) + "\nfor 5% commission.)");
			}
			updateWinnings();
		}
		else {
			results += ("Sorry... You lost...\n");
			results += ("Amount lost: $" + (winnings*-1.0));
			updateWinnings();
		}
		
		messageText.setText(results);		// Send message of results to screen
		startRoundBtn.setDisable(false);	// Re-enable button
	}
	
	// updateWinnings - Adjust money stats in the UI.
	void updateWinnings() {
		DecimalFormat df = new DecimalFormat("#.##");
		moneyStats.setText("Total Winnings: $" + df.format(bGame.totalWinnings) + " | Bet for round: $" + df.format(bGame.currentBet));
	}
	
	// resetGame() - Resets certain JavaFX objects before starting a new round.
	void resetGame() {
		bGame.playerHand.clear();
		bGame.bankerHand.clear();
		bGame.setGotNaturalWin(false);
		playerScore.setText("Score: ");
		bankerScore.setText("Score: ");
	}
	
	// *** For UI ***
	// setupStartScreen() - Sets up the JavaFX objects in the start screen.
	void setupStartScreen() {
		gameTitle.setEditable(false);
		gameTitle.setAlignment(Pos.BASELINE_CENTER);
		gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));
		gameTitle.setStyle("-fx-background-color: -fx-control-inner-background;");
		startBtn.setPrefSize(200, 50);
		startBtn.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		titleScreenButtons.setPadding(new Insets(15, 15, 15, 15));
		titleScreenButtons.getChildren().addAll(startBtn);
	}
	
	// setupTopView() - Sets up the JavaFX objects for the topView in the game screen.
	void setupTopView() {
		// *** Menu Elements ***
		optionsMenu.getItems().addAll(exitProgramMI, freshStartMI);
		mBar.getMenus().add(optionsMenu);
		// *** Money Stats ***
		moneyStats.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		moneyStats.setFill(Color.WHITE);
		updateWinnings();
		topView.getChildren().addAll(mBar, moneyStats);
	}
	
	// setupCenterView() - Sets up the JavaFX objects for the centerView in the game screen.
	void setupCenterView() {
		pHand.getChildren().addAll(playerLabel, playerScore);
		pHand.setMinWidth(200);
		bankerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		bankerLabel.setFill(Color.WHITE);
		bankerScore.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		bankerScore.setFill(Color.WHITE);
		bankerScore.setTextAlignment(TextAlignment.CENTER);
		bHand.getChildren().addAll(bankerLabel, bankerScore);
		bHand.setMinWidth(200);
		bottomView.setPadding(new Insets(15, 15, 15, 15));
		bottomView.getChildren().addAll(playerBtn, bankerBtn, tieBtn, betFieldLabel, betTextField, startRoundBtn);
		playerCards.setMaxWidth(480);
		playerCards.setStyle("-fx-padding: 10;-fx-border-style: solid inside;-fx-border-width: 2;-fx-border-insets: 5;-fx-border-radius: 5;-fx-border-color: yellow;");
		dealerCards.setMaxWidth(480);
		dealerCards.setStyle("-fx-padding: 10;-fx-border-style: solid inside;-fx-border-width: 2;-fx-border-insets: 5;-fx-border-radius: 5;-fx-border-color: yellow;");
		pCardsGP.setHgap(10);
		bCardsGP.setHgap(10);
		for(int i = 0; i < 3; i++) {
			playerCardsIV[i] = new ImageView();
			playerCardsIV[i].setFitWidth(80);
			playerCardsIV[i].setFitHeight(120);
			playerCardsIV[i].setOpacity(0.0);	// Hide card
			GridPane.setRowIndex(playerCardsIV[i], 0);
			GridPane.setColumnIndex(playerCardsIV[i], i);
			pCardsGP.getChildren().add(playerCardsIV[i]);
			
			bankerCardsIV[i] = new ImageView();
			bankerCardsIV[i].setFitWidth(80);
			bankerCardsIV[i].setFitHeight(120);
			bankerCardsIV[i].setOpacity(0.0);	// Hide card
			GridPane.setRowIndex(bankerCardsIV[i], 0);
			GridPane.setColumnIndex(bankerCardsIV[i], i);
			bCardsGP.getChildren().add(bankerCardsIV[i]);
			
			playerCardsIV[i].setImage(new Image("/cardImages/Hearts1.png"));
			bankerCardsIV[i].setImage(new Image("/cardImages/Hearts1.png"));
		}
		centerView.getChildren().addAll(dealerCards, playerCards);
		dealerCards.getChildren().addAll(bHand, bCardsGP);
		playerCards.getChildren().addAll(pHand, pCardsGP);
	}
	
	// setupRightView() - Sets up the JavaFX objects for the rightView in the game screen.
	void setupRightView() {
		// --- Right View ---
		// *** Message Screen - Display messages to player here ***
		messageText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		messageText.setFill(Color.WHITE);
		messageTextBackground.setWidth(400);
		messageTextBackground.setHeight(200);
		rightView.getChildren().addAll(messageTextBackground, messageText);
	}
	
	// setupBottomView() - Sets up the JavaFX objects for the bottomView in the game screen
	void setupBottomView() {
		// --- Bottom View ---
		playerBtn.setPrefSize(150, 50);
		playerBtn.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		bankerBtn.setPrefSize(150, 50);
		bankerBtn.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		tieBtn.setPrefSize(150, 50);
		tieBtn.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		startRoundBtn.setPrefSize(150, 50);
		startRoundBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		betFieldLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		betTextField.setPromptText("Type bet amount here");
		playerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		playerLabel.setFill(Color.WHITE);
		playerScore.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		playerScore.setFill(Color.WHITE);
		playerScore.setTextAlignment(TextAlignment.CENTER);
	}
	
	// setFadeTransitions() - Sets up  FadeTransitions to the player and banker cards.
	void setFadeTransitions() {
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				fadeInCard[i][j] = new FadeTransition();
				fadeInCard[i][j].setDuration(Duration.millis(500));
				fadeInCard[i][j].setFromValue(0.0);
				fadeInCard[i][j].setToValue(1.0);
				fadeOutCard[i][j] = new FadeTransition();
				fadeOutCard[i][j].setDuration(Duration.millis(500));
				fadeOutCard[i][j].setFromValue(1.0);
				fadeOutCard[i][j].setToValue(0.0);
			}
		}
		
		for(int k = 0; k < 3; k++) {
			fadeInCard[0][k].setNode(playerCardsIV[k]);
			fadeInCard[1][k].setNode(bankerCardsIV[k]);
			fadeOutCard[0][k].setNode(playerCardsIV[k]);
			fadeOutCard[1][k].setNode(bankerCardsIV[k]);
		}
	}
}