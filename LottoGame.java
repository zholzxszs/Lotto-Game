import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Timer;

public class LottoGame extends JFrame {
	private AudioInputStream audioInputStream;
	private AudioInputStream audioInputStream1;
	private AudioInputStream audioInputStream2;
	private AudioInputStream audioInputStream3;
	private AudioInputStream audioInputStream4;
	private Clip mouseClick;
	private Clip mouseClick1;
	private Clip bgmConsolationSFX;
	private Clip bgmWinnerJackpotSFX;
	private Clip bgmTryAgain;
	private Clip drawBallsMusic;
	private Clip bgMusic;
	private String disp;
	private String disp1;
	private String dispA;
	private String dispB;
	LocalDateTime currentDateTime;
	private int agent;
	private int count;
	private int prizepool;
	private int playAgain;
	private boolean isSubmitClicked;
	private boolean isContinueClicked;
	private boolean iscbGrandLottoclicked;
	private boolean iscbUltraLottoclicked;
	private boolean iscbLottoclicked;
	private boolean isMute;
	private boolean isTimerOnGoing;
	private boolean isPlayAgainClicked;
	private int[] storeSixRandomNumbers;
	private String displayChosenNumbersA[];
	private String displayChosenNumbersB[];
	private LinkedList<String> userChosenNumbersAList = new LinkedList<>();
	private LinkedList<String> userChosenNumbersBList = new LinkedList<>();

	public LottoGame() {
		initComponents();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		mousePointer();
		panelGameField2.setVisible(false);
		panelGameField3.setVisible(false);

		//FOR COMPONENTS VISIBLITY
		setVisibleComponents();

		//FOR CALLING FUNCTION
        setDateandTime();
		setForegroundText();
		setRandomAgent();

		// DECLARATION
		forDeclarationIV();

		//FOR MUSIC
		try {
			//FOR BG GAME MUSIC
			final String soundFile = "BackgroundMusic/gameMusicBackground.wav";
			audioInputStream = AudioSystem.getAudioInputStream(LottoStartUp.class.getResource(soundFile));
			bgMusic = AudioSystem.getClip();
			bgMusic.open(audioInputStream);
			bgMusic.start();
			if(!isPlayAgainClicked) {
				startGame();
			}
			
			//FOR DRAW
			final String soundFile1 = "BackgroundMusic/newMusicRollBall.wav";
			audioInputStream1 = AudioSystem.getAudioInputStream(LottoStartUp.class.getResource(soundFile1));
			drawBallsMusic = AudioSystem.getClip();
			drawBallsMusic.open(audioInputStream1);

			//FOR CONSOLATION
			final String soundFile2 = "BackgroundMusic/ConsolationSFX.wav";
			audioInputStream2 = AudioSystem.getAudioInputStream(LottoStartUp.class.getResource(soundFile2));
			bgmConsolationSFX = AudioSystem.getClip();
			bgmConsolationSFX.open(audioInputStream2);

			//FOR WINNER JACKPOT PRIZE
			final String soundFile3 = "BackgroundMusic/JackpotWinnerSFX.wav";
			audioInputStream3 = AudioSystem.getAudioInputStream(LottoStartUp.class.getResource(soundFile3));
			bgmWinnerJackpotSFX = AudioSystem.getClip();
			bgmWinnerJackpotSFX.open(audioInputStream3);

			//FOR TRY AGAIN
			final String soundFile4 = "BackgroundMusic/TryAgainSFX.wav";
			audioInputStream4 = AudioSystem.getAudioInputStream(LottoStartUp.class.getResource(soundFile4));
			bgmTryAgain = AudioSystem.getClip();
			bgmTryAgain.open(audioInputStream4);

		} catch(Exception e3) {
			e3.printStackTrace();
		}
	}

	public void startGame() {
        setVisible(true);

		if(!isPlayAgainClicked) {
			JOptionPane.showMessageDialog(
                null,
                "Welcome to Lucky Lotto!\n\n" +
                        "This Game has 3 Categories to Play:\n" +
                        "   [ 1 ] Lotto\n" +
                        "   [ 2 ] Ultra Lotto\n" +
                        "   [ 3 ] Grand Lotto\n\n" +
                        "Jackpot Prizes:\n" +
                        "   [ - ] Lotto: PHP 1,000,000\n" +
                        "   [ - ] Grand Lotto: PHP 10,000,000\n" +
                        "   [ - ] Ultra Lotto: PHP 100,000,000\n\n" +
                        "Guessing Prizes:\n" +
                        "   [ - ] 1-3 numbers: Lotto - PHP 1,500, Grand Lotto - PHP 3,000, Ultra Lotto - PHP 4,500\n" +
                        "   [ - ] 4-5 numbers: Lotto - PHP 3,000, Grand Lotto - PHP 4,500, Ultra Lotto - PHP 6,000\n\n" +
                        "Consolation Prize:\n" +
                        "   [ - ] If you don't guess any numbers, you will receive a consolation prize of PHP 20.00\n\n" +
                        "   [ / ] You have given 2 Card to Play. Good luck!",
                "Lucky Lotto",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon("Images/Welcome!!.gif")
        );
        // HOW TO PLAY DISPLAY
        displayHowToPlay();
		}
    }

	private void forDeclarationIV() {
		count = 0;
		disp = "";
		disp1 = "";
		dispA = "";
		dispB = "";
		prizepool = 0;
		isSubmitClicked = false;
		isContinueClicked = false;
		iscbGrandLottoclicked = false;
		iscbUltraLottoclicked = false;
		iscbLottoclicked = false;
		isMute = false;
		isTimerOnGoing = false;
		isPlayAgainClicked = false;
	}

	private void mousePointer() {
		try{
			Image cursorImage = ImageIO.read(new File("Images/mousePointer.png"));
			Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "CustomCursor");
            setCursor(customCursor);
		}catch(Exception e){
		}
	}

	private void mouseHover() {
		try{
			Image cursorImage = ImageIO.read(new File("Images/mouseHover.png"));
			Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "CustomCursor");
            setCursor(customCursor);
		}catch(Exception e){

		}
	}

	private void setDateandTime() {
		currentDateTime = LocalDateTime.now();
		lblReceiptDateOutput.setText(currentDateTime.getDayOfMonth() + " / " + currentDateTime.getMonthValue() + " / " + currentDateTime.getYear());
		lblReceiptDrawOutput.setText(currentDateTime.getDayOfMonth() + " / " + currentDateTime.getMonthValue() + " / " + currentDateTime.getYear() + "  " + currentDateTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
	}

	private void setRandomAgent() {
		if(isSubmitClicked == true) {
			agent = new Random().nextInt(50000000)+10000000;
			lblReceiptAgentOutput.setText(agent+"");
		}
	}

	private void clickSoundEffect() {
		try {
			final String soundFile = "BackgroundMusic/Click.wav";
			audioInputStream = AudioSystem.getAudioInputStream(LottoStartUp.class.getResource(soundFile));
			mouseClick = AudioSystem.getClip();
			mouseClick.open(audioInputStream);
			mouseClick.start();
		} catch(Exception e3) {
			e3.printStackTrace();
		}
	}

	private void btnRollSoundEffect() {
		try {
			final String soundFile = "BackgroundMusic/btnRollBallSFX.wav";
			audioInputStream = AudioSystem.getAudioInputStream(LottoStartUp.class.getResource(soundFile));
			mouseClick1 = AudioSystem.getClip();
			mouseClick1.open(audioInputStream);
			mouseClick1.start();
		} catch(Exception e3) {
			e3.printStackTrace();
		}
	}

	private void drawTimer() {
		isTimerOnGoing = true;
		if(isSubmitClicked) {
			Timer timer = new Timer();
			drawBallsMusic.start();
			bgMusic.stop();

			if(!isMute) {
				bgMusic.stop();
			}

			timer.schedule(new TimerTask() {
				String arr[]=txtDrawTimer.getText().split(":");
				int min=Integer.parseInt(arr[1])*60; 
				public void run(){
					min--;
					txtDrawTimer.setText("00:00:"+(min));
					if(min==0){
						timer.cancel(); 
						JOptionPane.showMessageDialog(null, "You may now start rerolling. Goodluck!", "Lucky Lotto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/RollBallJOP.gif"));
						btnRollBall.setVisible(true);
						txtDrawTimer.setVisible(false);
						drawBallsMusic.stop();
						if(!isMute) {
							bgMusic.start();
						}
						else {
							bgMusic.stop();
						}
						isTimerOnGoing = false;
					}
				}
			}, 2000,1000);
		}
	}

	private void fiveSeconds() {
		Timer timer = new Timer();
		final int[] seconds = {5}; 
	
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (seconds[0] >= 0) {
					seconds[0]--;
				} else {
					timer.cancel();
					btnContinue.setVisible(true);
				}
			}
		}, 0, 1000);
	}

	private void fiveSecondsPlayAgain() {
		Timer timer = new Timer();
		final int[] seconds = {5}; 
	
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (seconds[0] >= 0) {
					seconds[0]--;
				} else {
					timer.cancel();
					btnPlayAgain.setVisible(true);
				}
			}
		}, 0, 1000);
	}

	private void doReselect() {
		if(iscbLottoclicked) {
			btnNumbersSetForeground();
		}
		else if(iscbUltraLottoclicked) {
			btnNumbersSetForeground();
		}
		else if(iscbGrandLottoclicked) {
			btnNumbersSetForeground();
		}
	}

	// SELECTED NUMBERS IN A
	private void isSelectedNumberA(ActionEvent e, JButton button) {
	
		if (userChosenNumbersAList.size() < 6 && !userChosenNumbersAList.contains(button.getText())) {
			txtStoreChosenNumbersA.setText(txtStoreChosenNumbersA.getText() + button.getText() + " ");
			button.setBackground(Color.RED);
			button.setForeground(Color.WHITE);
			userChosenNumbersAList.add(button.getText());
		} else if (userChosenNumbersAList.contains(button.getText())) {
			JOptionPane.showMessageDialog(null, "You have already selected this number!", "Warning", JOptionPane.ERROR_MESSAGE, new ImageIcon("Images/Warning.gif"));
		} else {
			JOptionPane.showMessageDialog(null, "You have already selected six numbers!", "Warning", JOptionPane.ERROR_MESSAGE, new ImageIcon("Images/Warning.gif"));
		}
	}
	
	// SELECTED NUMBERS IN B
	private void isSelectedNumberB(ActionEvent e, JButton button) {
	
		if (userChosenNumbersBList.size() < 6 && !userChosenNumbersBList.contains(button.getText())) {
			txtStoreChosenNumbersB.setText(txtStoreChosenNumbersB.getText() + button.getText() + " ");
			button.setBackground(Color.RED);
			button.setForeground(Color.WHITE);
			userChosenNumbersBList.add(button.getText());
		} else if (userChosenNumbersBList.contains(button.getText())) {
			JOptionPane.showMessageDialog(null, "You have already selected this number!", "Warning", JOptionPane.ERROR_MESSAGE, new ImageIcon("Images/Warning.gif"));
		} else {
			JOptionPane.showMessageDialog(null, "You have already selected six numbers!", "Warning", JOptionPane.ERROR_MESSAGE, new ImageIcon("Images/Warning.gif"));
		}
	}

	private void btnSubmitMouseClicked(MouseEvent e) {
		clickSoundEffect();

		displayChosenNumbersA = txtStoreChosenNumbersA.getText().split(" ");
		displayChosenNumbersB = txtStoreChosenNumbersB.getText().split(" ");

		if((displayChosenNumbersA.length != 6) || (displayChosenNumbersB.length != 6)) {
			JOptionPane.showMessageDialog(null, "You have to select 6 'six' Numbers \nfor both Card A and Card B!");
		}
		else {
			isSubmitClicked = true;
			btnReselect.setVisible(false);
			JOptionPane.showMessageDialog(null, "You have to wait 1 minute before the ball roll start", "Lucky Lotto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/TimeJOP.gif"));
			lblGameCategory.setIcon(new ImageIcon("Images/gameTitle.png"));
			setVisibleReceipt();
			txtDrawTimer.setVisible(true);
			falseVisibleChooseNumbers();
			setRandomAgent();

			for(int a = 0 ; a < displayChosenNumbersA.length && a < displayChosenNumbersB.length; a++) {
				dispA = dispA + displayChosenNumbersA[a] + "     ";
				dispB = dispB + displayChosenNumbersB[a] + "     ";
			}
			lblYourNumbersA.setText(dispA);
			lblYourNumbersB.setText(dispB);
		}
		drawTimer();
	}

	private void btnHelpMouseClicked(MouseEvent e) {
		clickSoundEffect();
		disp = "Instructions\"Instructions:\n\n" +
        "[ 1 ] Choose a category from the upper Combo Box.\n" +
        "[ 2 ] Select 6 numbers for both Card A and Card B.\n" +
        "[ 3 ] Use the \"Reselect\" button to reset your selection if needed.\n" +
        "[ 4 ] Click the \"Submit\" button when you've finalized your choices.\n" +
        "[ 5 ] Wait for 1 minute for the ball roll and to reveal the winning numbers.";

		JOptionPane.showMessageDialog(null, disp, "How to Play", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/Hellpp.gif"));
		mouseHover();
	}

	private void btnRollBallMouseClicked(MouseEvent e) {
		btnRollSoundEffect();
		mouseHover();
		clickSoundEffect();
		count++;

		int maximumNumbers = 0;

		if (iscbLottoclicked) {
			maximumNumbers = 42;
		} 
		else if (iscbUltraLottoclicked) {
			maximumNumbers = 58;
		}
		else if (iscbGrandLottoclicked) {
			maximumNumbers = 55;
		}

		storeSixRandomNumbers = generateRandomNumbers(maximumNumbers);

		if (count <= 6) {
			if(count == 1) {
				lblFirstB.setVisible(true);
				lblDrawFirstBall.setVisible(true);
				lblDrawFirstNumber.setVisible(true);
				lblDrawFirstNumber.setText(String.valueOf(storeSixRandomNumbers[0]));
			}
			else if (count == 2) {
				lblSecondB.setVisible(true);
				lblDrawSecondBall.setVisible(true);
				lblDrawSecondNumber.setVisible(true);
				lblDrawSecondNumber.setText(String.valueOf(storeSixRandomNumbers[1]));
			}
			else if(count == 3) {
				lblThirdB.setVisible(true);
				lblDrawThirdBall.setVisible(true);
				lblDrawThirdNumber.setVisible(true);
				lblDrawThirdNumber.setText(String.valueOf(storeSixRandomNumbers[2]));
			}
			else if(count == 4) {
				lblFourthB.setVisible(true);
				lblDrawFourthBall.setVisible(true);
				lblDrawFourthNumber.setVisible(true);
				lblDrawFourthNumber.setText(String.valueOf(storeSixRandomNumbers[3]));
			}
			else if(count == 5) {
				lblFifthB.setVisible(true);
				lblDrawFifthBall.setVisible(true);
				lblDrawFifthNumber.setVisible(true);
				lblDrawFifthNumber.setText(String.valueOf(storeSixRandomNumbers[4]));
			}
			else {
				lblSixthB.setVisible(true);
				lblDrawSixthBall.setVisible(true);
				lblDrawSixthNumber.setVisible(true);
				lblDrawSixthNumber.setText(String.valueOf(storeSixRandomNumbers[5]));
			}
		}

		if (count == 6) {
			btnRollBall.setVisible(false);
			fiveSeconds();
		}
	}

	private int[] generateRandomNumbers(int upperBound) {
		int randomNumber = 0;
		int[] randomNumbers = new int[6];

		if(isSubmitClicked == true) {
			Random random = new Random();

			for (int i = 0; i < randomNumbers.length; i++) {
				do {
					randomNumber = random.nextInt(upperBound) + 1;
				} while (isExist(randomNumbers, i, randomNumber));

				randomNumbers[i] = randomNumber;
				disp += randomNumbers[i] + " ";
			}
		}

		disp1 = "";
		String collectedSixRandomNumbers[] = disp.split(" ");
		for(String a : collectedSixRandomNumbers) {
			disp1 = disp1 + a + " ";
		}
		lblStoreSixRN.setText(disp1);
		return randomNumbers;
	}

	private boolean isExist(int[] array, int length, int value) {
		for (int i = 0; i < length; i++) {
			if (array[i] == value) {
				return true;
			}
		}
		return false;
	}

	private void btnReselectMouseClicked(MouseEvent e) {
		clickSoundEffect();
		mouseHover();
		txtStoreChosenNumbersA.setText(null);
		txtStoreChosenNumbersB.setText(null);
		userChosenNumbersAList.clear();
		userChosenNumbersBList.clear();
		doReselect();
	}

	private void btnContinueMouseClicked(MouseEvent e) {
		LocalDateTime ldt = LocalDateTime.now();
		isContinueClicked = true;
		mouseHover();
		clickSoundEffect();
		panelGameField2.setVisible(true);
		btnContinue.setVisible(false);
		btnNext.setVisible(true);
		setVisibleGameFieldComponents();
		displayResultPanel2();
		lblDate2.setText(ldt.getMonth() + " " +ldt.getDayOfMonth() + "," + ldt.getYear());
	}

	private void displayResultPanel2() {
		String winningCombinations[] = lblStoreSixRN.getText().split(" ");

		lblWN1.setText(winningCombinations[0]);
		lblWN2.setText(winningCombinations[1]);
		lblWN3.setText(winningCombinations[2]);
		lblWN4.setText(winningCombinations[3]);
		lblWN5.setText(winningCombinations[4]);
		lblWN6.setText(winningCombinations[5]);

		if(iscbLottoclicked) {
			lblCategoryChosen.setIcon(new ImageIcon("Images/pLotto.png"));
			lblGamePrize.setIcon(new ImageIcon("Images/PHP 1 000 000.png"));
		}
		else if(iscbUltraLottoclicked) {
			lblCategoryChosen.setIcon(new ImageIcon("Images/pUltraLotto.png"));
			lblGamePrize.setIcon(new ImageIcon("Images/PHP 100 000 000.png"));
		}
		else if(iscbGrandLottoclicked) {
			lblCategoryChosen.setIcon(new ImageIcon("Images/pGrandLotto.png"));
			lblGamePrize.setIcon(new ImageIcon("Images/PHP 10 000 000.png"));
		}
	}

	private void btnNextMouseClicked(MouseEvent e) {
		fiveSecondsPlayAgain();
		bgMusic.stop();
		bgMusic.close();
		btnNext.setVisible(false);
		mouseHover();
		clickSoundEffect();
		setVisiblePanel3();

		if(isContinueClicked) {
			disp = "";
			disp1 = "";
			int countA = 0;
			int countB = 0;

			String sixNumbersResult[] = lblStoreSixRN.getText().split(" ");
			String cardANumbers[] = txtStoreChosenNumbersA.getText().split(" ");
			String cardBNumbers[] = txtStoreChosenNumbersB.getText().split(" ");
			int storeMatchNumbersA[] = new int[6];
			int storeMatchNumbersB[] = new int[6];

			for(int a = 0; a < sixNumbersResult.length; a++) {
				for(int b = 0; b < cardANumbers.length && a < cardBNumbers.length; b++) {
					if(sixNumbersResult[a].equals(cardANumbers[b])) {
						storeMatchNumbersA[a] = Integer.parseInt(cardANumbers[b]);
						disp = disp + storeMatchNumbersA[a] + "     ";
						countA++;
					}
					if(sixNumbersResult[a].equals(cardBNumbers[b])){
						storeMatchNumbersB[a] = Integer.parseInt(cardBNumbers[b]);
						disp1 = disp1 + storeMatchNumbersB[a] + "     ";
						countB++;
					}
				}
			}

			if(iscbLottoclicked) {
				if(countA == 1 || countB == 1 || countA == 2 || countB == 2 || countA == 3 || countB == 3 || countA == 4 || countB == 4 || countA == 5 || countB == 5) {
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON.png"));
					if((countA == 1 || countA == 2 || countA == 3) && (countB == 1 || countB == 2 || countB == 3)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/3Kl (1).gif"));
					}
					else if((countA == 1 || countA == 2 || countA == 3) && (countB == 4 || countB == 5 ) || (countA == 4 || countA == 5) && (countB == 1 || countB == 2 || countB == 3 )) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/4.5Kl.gif"));
					}
					else if((countA == 4 || countA == 5 ) && (countB == 4 || countB == 5 )) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/6Kl.gif"));
					}
					else if((countA == 1 || countA == 2 || countA == 3) && (countB == 0)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblUserPrize.setIcon(new ImageIcon("Images/1.5kL.gif"));
					}
					else if((countA == 0) && (countB == 1 || countB == 2 || countB == 3)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp1);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblCardA.setText(null);
						lblCardA.setText("Card B");
						lblUserPrize.setIcon(new ImageIcon("Images/1.5kL.gif"));
					}
					else if((countA == 4 || countA == 5) && (countB == 0)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblUserPrize.setIcon(new ImageIcon("Images/3Kl (1).gif"));
					}
					else if((countA == 0) && (countB == 4 || countB == 5)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp1);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblCardA.setText(null);
						lblCardA.setText("Card B");
						lblUserPrize.setIcon(new ImageIcon("Images/3Kl (1).gif"));
					}
				}
				else if(countA == 0 && countB == 0) {
					bgmTryAgain.start();
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblCorrectNumbersA.setVisible(false);
					lblCardA.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/Consolation Prize.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/ConsolationPrize.gif"));
					lblCorrectNumbers.setIcon(new ImageIcon("Images/NOT LUCKY DAY.png"));
				}
				else if(countA == 6 && countB == 6) {
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp);
					lblCorrectNumbersB.setText(disp1);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner1M.gif"));
					putFireworks();
				}
				else if (countA == 6){
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp);
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner1M.gif"));
					putFireworks();
				}
				else {
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp1);
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner1M.gif"));
					putFireworks();
				}
			}
			else if(iscbUltraLottoclicked){
				if(countA == 1 || countB == 1 || countA == 2 || countB == 2 || countA == 3 || countB == 3 || countA == 4 || countB == 4 || countA == 5 || countB == 5) {
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON.png"));
					if((countA == 1 || countA == 2 || countA == 3) && (countB == 1 || countB == 2 || countB == 3)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/9kUL.gif"));
					}
					else if((countA == 1 || countA == 2 || countA == 3) && (countB == 4 || countB == 5 ) || (countA == 4 || countA == 5) && (countB == 1 || countB == 2 || countB == 3 )) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/10.5kUL.gif"));
					}
					else if((countA == 4 || countA == 5 ) && (countB == 4 || countB == 5 )) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/12kUL.gif"));
					}
					else if((countA == 1 || countA == 2 || countA == 3) && (countB == 0)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblUserPrize.setIcon(new ImageIcon("Images/4.5kUL.gif"));
					}
					else if((countA == 0) && (countB == 1 || countB == 2 || countB == 3)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp1);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblCardA.setText(null);
						lblCardA.setText("Card B");
						lblUserPrize.setIcon(new ImageIcon("Images/4.5kUL.gif"));
					}
					else if((countA == 4 || countA == 5) && (countB == 0)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblUserPrize.setIcon(new ImageIcon("Images/6kUL.gif"));
					}
					else if((countA == 0) && (countB == 4 || countB == 5)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp1);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblCardA.setText(null);
						lblCardA.setText("Card B");
						lblUserPrize.setIcon(new ImageIcon("Images/6kUL.gif"));
					}
				}
				else if(countA == 0 && countB == 0) {
					bgmTryAgain.start();
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblCorrectNumbersA.setVisible(false);
					lblCardA.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/Consolation Prize.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/ConsolationPrize.gif"));
					lblCorrectNumbers.setIcon(new ImageIcon("Images/NOT LUCKY DAY.png"));
				}
				else if(countA == 6 && countB == 6) {
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp);
					lblCorrectNumbersB.setText(disp1);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner100M.gif"));
					putFireworks();
				}
				else if (countA == 6){
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp);
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner100M.gif"));
					putFireworks();
					//YUNG FIREWORKS AND CONFETTI WAG KALIMUTAN
				}
				else { // (countB == 6)
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp1);
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner100M.gif"));
					putFireworks();
				}
			}
			else if(iscbGrandLottoclicked){
				lblWonCP.setIcon(new ImageIcon("Images/YOU WON.png"));
				if(countA == 1 || countB == 1 || countA == 2 || countB == 2 || countA == 3 || countB == 3 || countA == 4 || countB == 4 || countA == 5 || countB == 5) {
					bgmConsolationSFX.start();
					if((countA == 1 || countA == 2 || countA == 3) && (countB == 1 || countB == 2 || countB == 3)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/6kGL.gif"));
					}
					else if((countA == 1 || countA == 2 || countA == 3) && (countB == 4 || countB == 5 ) || (countA == 4 || countA == 5) && (countB == 1 || countB == 2 || countB == 3 )) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/7.5kGL.gif"));
					}
					else if((countA == 4 || countA == 5 ) && (countB == 4 || countB == 5 )) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setText(disp1);
						lblUserPrize.setIcon(new ImageIcon("Images/9kGL.gif"));
					}
					else if((countA == 1 || countA == 2 || countA == 3) && (countB == 0)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblUserPrize.setIcon(new ImageIcon("Images/3kGL.gif"));
					}
					else if((countA == 0) && (countB == 1 || countB == 2 || countB == 3)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp1);
						lblCardA.setText(null);
						lblCardA.setText("Card B");
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblUserPrize.setIcon(new ImageIcon("Images/3kGL.gif"));
					}
					else if((countA == 4 || countA == 5) && (countB == 0)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp);
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblUserPrize.setIcon(new ImageIcon("Images/4.5kGL.gif"));
					}
					else if((countA == 0) && (countB == 4 || countB == 5)) {
						bgmConsolationSFX.start();
						lblCorrectNumbersA.setText(disp1);
						lblCardA.setText(null);
						lblCardA.setText("Card B");
						lblCorrectNumbersB.setVisible(false);
						lblCardB.setVisible(false);
						lblUserPrize.setIcon(new ImageIcon("Images/4.5kGL.gif"));
					}
				}
				else if(countA == 0 && countB == 0) {
					bgmTryAgain.start();
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblCorrectNumbersA.setVisible(false);
					lblCardA.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/Consolation Prize.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/ConsolationPrize.gif"));
					lblCorrectNumbers.setIcon(new ImageIcon("Images/NOT LUCKY DAY.png"));
				}
				else if(countA == 6 && countB == 6) {
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp);
					lblCorrectNumbersB.setText(disp1);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner10M.gif"));
					putFireworks();
				}
				else if (countA == 6){
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp);
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner10M.gif"));
					putFireworks();
				}
				else { // (countB == 6)
					bgmWinnerJackpotSFX.start();
					lblCorrectNumbersA.setText(disp1);
					lblCorrectNumbersB.setVisible(false);
					lblCardB.setVisible(false);
					lblWonCP.setIcon(new ImageIcon("Images/YOU WON JACKPOT PRIZE.png"));
					lblUserPrize.setIcon(new ImageIcon("Images/Winner10M.gif"));
					putFireworks();
				}
			}
		}
	}

	private void displayHowToPlay() {
		String dispHowToPlay = "How To Play:\n" +
        "   [ 1 ] Choose a category from the upper Combo Box.\n" +
        "   [ 2 ] Select 6 numbers for both Card A and Card B.\n" +
        "   [ 3 ] Use the \"Reselect\" button to reset your selection if needed.\n" +
        "   [ 4 ] Click the \"Submit\" button when you've finalized your choices.\n" +
        "   [ 5 ] Wait for 1 minute for the ball roll and to reveal the winning numbers.";

		JOptionPane.showMessageDialog(null, dispHowToPlay, "How to Play", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/How to Play.gif"));
	}

	private void cbCategories(ActionEvent e) {
		cbCategoriesVisible();

		if(cbCategories.getSelectedItem().equals("Lotto 6/42")) {
			iscbLottoclicked = true;
			lblGameCategory.setText(prizepool+"");
			lblGameCategory.setIcon(new ImageIcon("Images/Lotto.png"));
			lblReceiptLotto.setIcon(new ImageIcon("Images/ReceiptLotto.png"));
			visibleComponentsCategories();
		} 
		else if(cbCategories.getSelectedItem().equals("Ultra Lotto 6/58")) {
			iscbUltraLottoclicked = true;
			lblGameCategory.setIcon(new ImageIcon("Images/ultraLotto.png"));
			lblReceiptLotto.setIcon(new ImageIcon("Images/ReceiptUltraLotto.png"));
			visibleComponentsCategories();
		}
		else if(cbCategories.getSelectedItem().equals("Grand Lotto 6/55")) {
			iscbGrandLottoclicked = true;
			lblGameCategory.setIcon(new ImageIcon("Images/grandLotto.png"));
			lblReceiptLotto.setIcon(new ImageIcon("Images/ReceiptGrandLotto.png"));
			visibleComponentsCategories();
		}
	}

	private void btnPlayAgainMouseClicked(MouseEvent e) {
		isPlayAgainClicked = true;
		btnPlayAgain.setVisible(false);
		mouseHover();
		clickSoundEffect();
		btnPlayAgain.setIcon(new ImageIcon("Images/Play Again Click.gif"));

		playAgain = JOptionPane.showConfirmDialog(null, "Do you want to Play Again?", "Lucky Lotto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);

		if(playAgain == JOptionPane.YES_OPTION) {
			this.dispose();
			new LottoGame().setVisible(true);
		}
		else if(playAgain == -1 || playAgain == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Thank you for Playing!", "Thank You!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/Thank You.gif"));
			this.dispose();
			bgMusic.close();
			bgmTryAgain.close();
			bgmConsolationSFX.close();
			bgmWinnerJackpotSFX.close();
		}
	}

	private void cbCategoriesMouseClicked(MouseEvent e) {
		clickSoundEffect();
		mouseHover();
	}

	private void cbCategoriesMouseEntered(MouseEvent e) {
		mouseHover();
	}

	private void cbCategoriesMousePressed(MouseEvent e) {
		mouseHover();
	}

	private void cbCategoriesMouseExited(MouseEvent e) {
		mousePointer();
	}

	private void btnReselectMouseEntered(MouseEvent e) {
		btnReselect.setIcon(new ImageIcon("Images/ReselectHover.jpg"));
		mouseHover();
	}

	private void btnReselectMouseExited(MouseEvent e) {
		btnReselect.setIcon(new ImageIcon("Images/Reselect.jpg"));
		mousePointer();
	}

	private void btnReselectMousePressed(MouseEvent e) {
		mouseHover();
	}

	private void btnNumbersEntered(MouseEvent e) {
		mouseHover();
	}

	private void btnNumbersExited(MouseEvent e) {
		mousePointer();
	}

	private void btnNumbersPressed(MouseEvent e) {
		mouseHover();
	}

	private void btnContinueMouseEntered(MouseEvent e) {
		btnContinue.setIcon(new ImageIcon("Images/ClickMe.gif"));
		mouseHover();
	}

	private void btnContinueMouseExited(MouseEvent e) {
		btnContinue.setIcon(new ImageIcon("Images/btnContiue.gif"));
		mousePointer();
	}

	private void btnContinueMousePressed(MouseEvent e) {
		mouseHover();
	}

	private void btnSubmitMouseEntered(MouseEvent e) {
		btnSubmit.setIcon(new ImageIcon("Images/ClickMe.gif"));
		mouseHover();
	}

	private void btnSubmitMouseExited(MouseEvent e) {
		btnSubmit.setIcon(new ImageIcon("Images/btnSubmit.gif"));
		mousePointer();
	}

	private void btnSubmitMousePressed(MouseEvent e) {
		mouseHover();
	}

	private void btnNumbersClicked(MouseEvent e) {
		clickSoundEffect();
		mouseHover();
	}

	private void btnRollBallMousePressed(MouseEvent e) {
		mouseHover();
	}

	private void btnRollBallMouseEntered(MouseEvent e) {
		btnRollBall.setIcon(new ImageIcon("Images/ClickMe.gif"));
		mouseHover();
	}

	private void btnRollBallMouseExited(MouseEvent e) {
		btnRollBall.setIcon(new ImageIcon("Images/RolllBall.gif"));
		mousePointer();
	}

	private void btnUnmuteMouseClicked(MouseEvent e) {
		isMute = true;
		clickSoundEffect();
		btnMute.setVisible(true);
		btnUnmute.setVisible(false);
		mouseHover();
		bgMusic.stop();
	}

	private void btnMuteMouseClicked(MouseEvent e) {
		clickSoundEffect();
		btnMute.setVisible(false);
		btnUnmute.setVisible(true);
		mousePointer();
		if(!isTimerOnGoing) {
			bgMusic.start();
		}
	}
	private void btnUnmuteMouseEntered(MouseEvent e) {
		btnUnmute.setIcon(new ImageIcon("Images/hoverMute.jpg"));
		mouseHover();
	}

	private void btnUnmuteMouseExited(MouseEvent e) {
		btnUnmute.setIcon(new ImageIcon("Images/btnUnmute.jpg"));
		mousePointer();
	}

	private void btnMuteMouseEntered(MouseEvent e) {
		btnMute.setIcon(new ImageIcon("Images/hoverUnmute.jpg"));
		mouseHover();
	}

	private void btnMuteMouseExited(MouseEvent e) {
		btnMute.setIcon(new ImageIcon("Images/btnMute.jpg"));
		mousePointer();
	}

	private void btnHelpMouseEntered(MouseEvent e) {
		btnHelp.setIcon(new ImageIcon("Images/hoverHelp.jpg"));
		mouseHover();
	}

	private void btnHelpMouseExited(MouseEvent e) {
		btnHelp.setIcon(new ImageIcon("Images/btnHelp.jpg"));
		mousePointer();
	}

	private void thisMouseClicked(MouseEvent e) {
		clickSoundEffect();
	}

	private void btnNextMouseEntered(MouseEvent e) {
		btnNext.setIcon(new ImageIcon("Images/ClickMe.gif"));
		mouseHover();
	}

	private void btnNextMouseExited(MouseEvent e) {
		btnNext.setIcon(new ImageIcon("Images/Next.gif"));
		mousePointer();
	}

	private void btnNextMousePressed(MouseEvent e) {
		mouseHover();
	}

	private void btnPlayAgainMouseEntered(MouseEvent e) {
		btnPlayAgain.setIcon(new ImageIcon("Images/Play Again Click.gif"));
		mouseHover();
	}

	private void btnPlayAgainMouseExited(MouseEvent e) {
		btnPlayAgain.setIcon(new ImageIcon("Images/PLAY AGAIN.gif"));
		mousePointer();
	}

	private void btnPlayAgainMousePressed(MouseEvent e) {
		mouseHover();
	}

	private void btnA1(ActionEvent e) {
		isSelectedNumberA(e, btnA1);
	}
	
	private void btnA2(ActionEvent e) {
		isSelectedNumberA(e, btnA2);
	}
	
	private void btnA3(ActionEvent e) {
		isSelectedNumberA(e, btnA3);
	}
	
	private void btnA4(ActionEvent e) {
		isSelectedNumberA(e, btnA4);
	}
	
	private void btnA5(ActionEvent e) {
		isSelectedNumberA(e, btnA5);
	}
	
	private void btnA6(ActionEvent e) {
		isSelectedNumberA(e, btnA6);
	}
	
	private void btnA7(ActionEvent e) {
		isSelectedNumberA(e, btnA7);
	}
	
	private void btnA8(ActionEvent e) {
		isSelectedNumberA(e, btnA8);
	}
	
	private void btnA9(ActionEvent e) {
		isSelectedNumberA(e, btnA9);
	}
	
	private void btnA10(ActionEvent e) {
		isSelectedNumberA(e, btnA10);
	}
	
	private void btnA11(ActionEvent e) {
		isSelectedNumberA(e, btnA11);
	}
	
	private void btnA12(ActionEvent e) {
		isSelectedNumberA(e, btnA12);
	}
	
	private void btnA13(ActionEvent e) {
		isSelectedNumberA(e, btnA13);
	}
	
	private void btnA14(ActionEvent e) {
		isSelectedNumberA(e, btnA14);
	}
	
	private void btnA15(ActionEvent e) {
		isSelectedNumberA(e, btnA15);
	}
	
	private void btnA16(ActionEvent e) {
		isSelectedNumberA(e, btnA16);
	}
	
	private void btnA17(ActionEvent e) {
		isSelectedNumberA(e, btnA17);
	}
	
	private void btnA18(ActionEvent e) {
		isSelectedNumberA(e, btnA18);
	}
	
	private void btnA19(ActionEvent e) {
		isSelectedNumberA(e, btnA19);
	}
	
	private void btnA20(ActionEvent e) {
		isSelectedNumberA(e, btnA20);
	}
	
	private void btnA21(ActionEvent e) {
		isSelectedNumberA(e, btnA21);
	}
	
	private void btnA22(ActionEvent e) {
		isSelectedNumberA(e, btnA22);
	}
	
	private void btnA23(ActionEvent e) {
		isSelectedNumberA(e, btnA23);
	}
	
	private void btnA24(ActionEvent e) {
		isSelectedNumberA(e, btnA24);
	}
	
	private void btnA25(ActionEvent e) {
		isSelectedNumberA(e, btnA25);
	}
	
	private void btnA26(ActionEvent e) {
		isSelectedNumberA(e, btnA26);
	}
	
	private void btnA27(ActionEvent e) {
		isSelectedNumberA(e, btnA27);
	}
	
	private void btnA28(ActionEvent e) {
		isSelectedNumberA(e, btnA28);
	}
	
	private void btnA29(ActionEvent e) {
		isSelectedNumberA(e, btnA29);
	}
	
	private void btnA30(ActionEvent e) {
		isSelectedNumberA(e, btnA30);
	}
	
	private void btnA31(ActionEvent e) {
		isSelectedNumberA(e, btnA31);
	}
	
	private void btnA32(ActionEvent e) {
		isSelectedNumberA(e, btnA32);
	}
	
	private void btnA33(ActionEvent e) {
		isSelectedNumberA(e, btnA33);
	}
	
	private void btnA34(ActionEvent e) {
		isSelectedNumberA(e, btnA34);
	}
	
	private void btnA35(ActionEvent e) {
		isSelectedNumberA(e, btnA35);
	}
	
	private void btnA36(ActionEvent e) {
		isSelectedNumberA(e, btnA36);
	}
	
	private void btnA37(ActionEvent e) {
		isSelectedNumberA(e, btnA37);
	}
	
	private void btnA38(ActionEvent e) {
		isSelectedNumberA(e, btnA38);
	}
	
	private void btnA39(ActionEvent e) {
		isSelectedNumberA(e, btnA39);
	}
	
	private void btnA40(ActionEvent e) {
		isSelectedNumberA(e, btnA40);
	}
	
	private void btnA41(ActionEvent e) {
		isSelectedNumberA(e, btnA41);
	}
	
	private void btnA42(ActionEvent e) {
		isSelectedNumberA(e, btnA42);
	}
	
	private void btnA43(ActionEvent e) {
		isSelectedNumberA(e, btnA43);
	}
	
	private void btnA44(ActionEvent e) {
		isSelectedNumberA(e, btnA44);
	}
	
	private void btnA45(ActionEvent e) {
		isSelectedNumberA(e, btnA45);
	}
	
	private void btnA46(ActionEvent e) {
		isSelectedNumberA(e, btnA46);
	}
	
	private void btnA47(ActionEvent e) {
		isSelectedNumberA(e, btnA47);
	}
	
	private void btnA48(ActionEvent e) {
		isSelectedNumberA(e, btnA48);
	}
	
	private void btnA49(ActionEvent e) {
		isSelectedNumberA(e, btnA49);
	}
	
	private void btnA50(ActionEvent e) {
		isSelectedNumberA(e, btnA50);
	}
	
	private void btnA51(ActionEvent e) {
		isSelectedNumberA(e, btnA51);
	}
	
	private void btnA52(ActionEvent e) {
		isSelectedNumberA(e, btnA52);
	}
	
	private void btnA53(ActionEvent e) {
		isSelectedNumberA(e, btnA53);
	}
	
	private void btnA54(ActionEvent e) {
		isSelectedNumberA(e, btnA54);
	}
	
	private void btnA55(ActionEvent e) {
		isSelectedNumberA(e, btnA55);
	}
	
	private void btnA56(ActionEvent e) {
		isSelectedNumberA(e, btnA56);
	}
	
	private void btnA57(ActionEvent e) {
		isSelectedNumberA(e, btnA57);
	}
	
	private void btnA58(ActionEvent e) {
		isSelectedNumberA(e, btnA58);
	}
	
	private void btnB1(ActionEvent e) {
		isSelectedNumberB(e, btnB1);
	}
	
	private void btnB2(ActionEvent e) {
		isSelectedNumberB(e, btnB2);
	}
	
	private void btnB3(ActionEvent e) {
		isSelectedNumberB(e, btnB3);
	}
	
	private void btnB4(ActionEvent e) {
		isSelectedNumberB(e, btnB4);
	}
	
	private void btnB5(ActionEvent e) {
		isSelectedNumberB(e, btnB5);
	}
	
	private void btnB6(ActionEvent e) {
		isSelectedNumberB(e, btnB6);
	}
	
	private void btnB7(ActionEvent e) {
		isSelectedNumberB(e, btnB7);
	}
	
	private void btnB8(ActionEvent e) {
		isSelectedNumberB(e, btnB8);
	}
	
	private void btnB9(ActionEvent e) {
		isSelectedNumberB(e, btnB9);
	}
	
	private void btnB10(ActionEvent e) {
		isSelectedNumberB(e, btnB10);
	}
	
	private void btnB11(ActionEvent e) {
		isSelectedNumberB(e, btnB11);
	}
	
	private void btnB12(ActionEvent e) {
		isSelectedNumberB(e, btnB12);
	}
	
	private void btnB13(ActionEvent e) {
		isSelectedNumberB(e, btnB13);
	}
	
	private void btnB14(ActionEvent e) {
		isSelectedNumberB(e, btnB14);
	}
	
	private void btnB15(ActionEvent e) {
		isSelectedNumberB(e, btnB15);
	}
	
	private void btnB16(ActionEvent e) {
		isSelectedNumberB(e, btnB16);
	}
	
	private void btnB17(ActionEvent e) {
		isSelectedNumberB(e, btnB17);
	}
	
	private void btnB18(ActionEvent e) {
		isSelectedNumberB(e, btnB18);
	}
	
	private void btnB19(ActionEvent e) {
		isSelectedNumberB(e, btnB19);
	}
	
	private void btnB20(ActionEvent e) {
		isSelectedNumberB(e, btnB20);
	}
	
	private void btnB21(ActionEvent e) {
		isSelectedNumberB(e, btnB21);
	}
	
	private void btnB22(ActionEvent e) {
		isSelectedNumberB(e, btnB22);
	}
	
	private void btnB23(ActionEvent e) {
		isSelectedNumberB(e, btnB23);
	}
	
	private void btnB24(ActionEvent e) {
		isSelectedNumberB(e, btnB24);
	}
	
	private void btnB25(ActionEvent e) {
		isSelectedNumberB(e, btnB25);
	}
	
	private void btnB26(ActionEvent e) {
		isSelectedNumberB(e, btnB26);
	}
	
	private void btnB27(ActionEvent e) {
		isSelectedNumberB(e, btnB27);
	}
	
	private void btnB28(ActionEvent e) {
		isSelectedNumberB(e, btnB28);
	}
	
	private void btnB29(ActionEvent e) {
		isSelectedNumberB(e, btnB29);
	}
	
	private void btnB30(ActionEvent e) {
		isSelectedNumberB(e, btnB30);
	}
	
	private void btnB31(ActionEvent e) {
		isSelectedNumberB(e, btnB31);
	}
	
	private void btnB32(ActionEvent e) {
		isSelectedNumberB(e, btnB32);
	}
	
	private void btnB33(ActionEvent e) {
		isSelectedNumberB(e, btnB33);
	}
	
	private void btnB34(ActionEvent e) {
		isSelectedNumberB(e, btnB34);
	}
	
	private void btnB35(ActionEvent e) {
		isSelectedNumberB(e, btnB35);
	}
	
	private void btnB36(ActionEvent e) {
		isSelectedNumberB(e, btnB36);
	}
	
	private void btnB37(ActionEvent e) {
		isSelectedNumberB(e, btnB37);
	}
	
	private void btnB38(ActionEvent e) {
		isSelectedNumberB(e, btnB38);
	}
	
	private void btnB39(ActionEvent e) {
		isSelectedNumberB(e, btnB39);
	}
	
	private void btnB40(ActionEvent e) {
		isSelectedNumberB(e, btnB40);
	}
	
	private void btnB41(ActionEvent e) {
		isSelectedNumberB(e, btnB41);
	}
	
	private void btnB42(ActionEvent e) {
		isSelectedNumberB(e, btnB42);
	}
	
	private void btnB43(ActionEvent e) {
		isSelectedNumberB(e, btnB43);
	}
	
	private void btnB44(ActionEvent e) {
		isSelectedNumberB(e, btnB44);
	}
	
	private void btnB45(ActionEvent e) {
		isSelectedNumberB(e, btnB45);
	}
	
	private void btnB46(ActionEvent e) {
		isSelectedNumberB(e, btnB46);
	}
	
	private void btnB47(ActionEvent e) {
		isSelectedNumberB(e, btnB47);
	}
	
	private void btnB48(ActionEvent e) {
		isSelectedNumberB(e, btnB48);
	}
	
	private void btnB49(ActionEvent e) {
		isSelectedNumberB(e, btnB49);
	}
	
	private void btnB50(ActionEvent e) {
		isSelectedNumberB(e, btnB50);
	}
	
	private void btnB51(ActionEvent e) {
		isSelectedNumberB(e, btnB51);
	}
	
	private void btnB52(ActionEvent e) {
		isSelectedNumberB(e, btnB52);
	}
	
	private void btnB53(ActionEvent e) {
		isSelectedNumberB(e, btnB53);
	}
	
	private void btnB54(ActionEvent e) {
		isSelectedNumberB(e, btnB54);
	}
	
	private void btnB55(ActionEvent e) {
		isSelectedNumberB(e, btnB55);
	}
	
	private void btnB56(ActionEvent e) {
		isSelectedNumberB(e, btnB56);
	}
	
	private void btnB57(ActionEvent e) {
		isSelectedNumberB(e, btnB57);
	}
	
	private void btnB58(ActionEvent e) {
		isSelectedNumberB(e, btnB58);
	}

	private void setVisiblePanel3() {
		JComponent[] componentsToHide = {panelGameField2, lblFireworks1, lblFireworks2, lblFireworks3,
			lblFireworks4, lblFireworks5, lblFireworks6, lblFireworks7,
			lblFireworks8, lblFireworks9, lblPartyPopper};

		JComponent[] componentsToShow = {panelGameField3, lblP2Congratulations, lblWonCP, lblUserPrize,
			lblCorrectNumbers, lblCorrectNumbersA, lblCorrectNumbersB,
			lblCardA, lblCardB, lblPanelbgGF};

		for (JComponent component : componentsToHide) {
			component.setVisible(false);
		}

		for (JComponent component : componentsToShow) {
			component.setVisible(true);
		}
	}

	private void putFireworks() {
		JLabel[] fireworksLabels = {lblFireworks1, lblFireworks2, lblFireworks3, lblFireworks4, lblFireworks5, 
			lblFireworks6, lblFireworks7, lblFireworks8,
			lblFireworks9, lblPartyPopper};

		for (JLabel label : fireworksLabels) {
			label.setVisible(true);
		}
	}

	private void setVisibleGameFieldComponents() {
		JLabel[] labelsToHide = {lblFirstB, lblDrawFirstBall, lblDrawFirstNumber,
			lblSecondB, lblDrawSecondBall, lblDrawSecondNumber,
			lblThirdB, lblDrawThirdBall, lblDrawThirdNumber,
			lblFourthB, lblDrawFourthBall, lblDrawFourthNumber,
			lblFifthB, lblDrawFifthBall, lblDrawFifthNumber,
			lblSixthB, lblDrawSixthBall, lblDrawSixthNumber,
			lblPrize, lblPrizepool, lblGameField};

		for (JLabel label : labelsToHide) {
			label.setVisible(false);
		}
	}

	private void setVisibleComponents() {
		// For Game Field
		JComponent[] gameFieldComponents = {btnRollBall, btnReselect, lblA, lblB, lblChooseA, lblChooseB,
			lblGameField, lblPrizepool, txtDrawTimer, btnSubmit, lblPrize};

		for (JComponent component : gameFieldComponents) {
			component.setVisible(false);
		}

		// For Receipt
		JLabel[] receiptLabels = {lblUserChosenNumbers, lblReceiptGameTitle, lblReceiptGameTitle2, lblReceiptA,
			lblReceiptAgent, lblReceiptAgentOutput, lblReceiptB, lblReceiptDate,
			lblReceiptDateOutput, lblReceiptDraw, lblReceiptDrawOutput, lblReceiptLotto,
			lblDashedLines1, lblDashedLines2};

		for (JLabel label : receiptLabels) {
			label.setVisible(false);
		}

		JButton[] buttonsA = {btnA1, btnA2, btnA3, btnA4, btnA5, btnA6, btnA7, btnA8, btnA9, btnA10,
			btnA11, btnA12, btnA13, btnA14, btnA15, btnA16, btnA17, btnA18, btnA19, btnA20,
			btnA21, btnA22, btnA23, btnA24, btnA25, btnA26, btnA27, btnA28, btnA29, btnA30,
			btnA31, btnA32, btnA33, btnA34, btnA35, btnA36, btnA37, btnA38, btnA39, btnA40,
			btnA41, btnA42, btnA43, btnA44, btnA45, btnA46, btnA47, btnA48, btnA49, btnA50,
			btnA51, btnA52, btnA53, btnA54, btnA55, btnA56, btnA57, btnA58};

		for (JButton button : buttonsA) {
			button.setVisible(false);
		}

		JButton[] buttonsB = {btnB1, btnB2, btnB3, btnB4, btnB5, btnB6, btnB7, btnB8, btnB9, btnB10,
			btnB11, btnB12, btnB13, btnB14, btnB15, btnB16, btnB17, btnB18, btnB19, btnB20,
			btnB21, btnB22, btnB23, btnB24, btnB25, btnB26, btnB27, btnB28, btnB29, btnB30,
			btnB31, btnB32, btnB33, btnB34, btnB35, btnB36, btnB37, btnB38, btnB39, btnB40,
			btnB41, btnB42, btnB43, btnB44, btnB45, btnB46, btnB47, btnB48, btnB49, btnB50,
			btnB51, btnB52, btnB53, btnB54, btnB55, btnB56, btnB57, btnB58};

		for (JButton button : buttonsB) {
			button.setVisible(false);
		}

		JLabel[] drawLabels = {lblDrawFirstBall, lblDrawSecondBall, lblDrawThirdBall, lblDrawFourthBall,
			lblDrawFifthBall, lblDrawSixthBall, lblFirstB, lblSecondB, lblThirdB,
			lblFourthB, lblFifthB, lblSixthB, lblDrawFirstNumber, lblDrawSecondNumber,
			lblDrawThirdNumber, lblDrawFourthNumber, lblDrawFifthNumber, lblDrawSixthNumber};

		for (JLabel label : drawLabels) {
			label.setVisible(false);
		}

		JButton[] otherButtons = {btnContinue, btnNext};

		for (JButton button : otherButtons) {
			button.setVisible(false);
		}

		lblStoreSixRN.setVisible(false);
		lblDispCorrectNumbers.setVisible(false);
		btnPlayAgain.setVisible(false);
		txtStoreChosenNumbersA.setVisible(false);
		txtStoreChosenNumbersB.setVisible(false);
	}

	private void setForegroundText() {
		JLabel[] blackLabels = {lblYourNumbersA, lblYourNumbersB, lblA, lblB, lblReceiptAgent, lblReceiptAgentOutput,
			lblReceiptDate, lblReceiptDateOutput, lblReceiptDraw, lblReceiptDrawOutput,
			lblDashedLines1, lblDashedLines2, lblYourNumbersA, lblYourNumbersB,
			lblReceiptGameTitle2, lblCorrectNumbersA, lblCorrectNumbersB};

		JLabel[] redLabels = {lblReceiptA, lblReceiptB};

		Font boldFont = new Font("Segoe UI", Font.BOLD, 16);

		for (JLabel label : blackLabels) {
			label.setForeground(Color.BLACK);
		}

		for (JLabel label : redLabels) {
			label.setForeground(Color.RED);
		}

		lblDashedLines1.setFont(boldFont);
		lblDashedLines2.setFont(boldFont);
	}

	private void visibleComponentsCategories() {
		JButton[] buttonsA = {btnA1, btnA2, btnA3, btnA4, btnA5, btnA6, btnA7, btnA8, btnA9, btnA10,
			btnA11, btnA12, btnA13, btnA14, btnA15, btnA16, btnA17, btnA18, btnA19, btnA20,
			btnA21, btnA22, btnA23, btnA24, btnA25, btnA26, btnA27, btnA28, btnA29, btnA30,
			btnA31, btnA32, btnA33, btnA34, btnA35, btnA36, btnA37, btnA38, btnA39, btnA40,
			btnA41, btnA42};

		JButton[] buttonsB = {btnB1, btnB2, btnB3, btnB4, btnB5, btnB6, btnB7, btnB8, btnB9, btnB10,
			btnB11, btnB12, btnB13, btnB14, btnB15, btnB16, btnB17, btnB18, btnB19, btnB20,
			btnB21, btnB22, btnB23, btnB24, btnB25, btnB26, btnB27, btnB28, btnB29, btnB30,
			btnB31, btnB32, btnB33, btnB34, btnB35, btnB36, btnB37, btnB38, btnB39, btnB40,
			btnB41, btnB42};

		for (JButton button : buttonsA) {
			button.setVisible(true);
		}

		for (JButton button : buttonsB) {
			button.setVisible(true);
		}

		if (iscbUltraLottoclicked) {
			lblPrizepool.setIcon(new ImageIcon("Images/prizepoolUltraLotto.gif"));

			JButton[] extraButtonsA = {btnA43, btnA44, btnA45, btnA46, btnA47, btnA48, btnA49, btnA50,
						btnA51, btnA52, btnA53, btnA54, btnA55, btnA56, btnA57, btnA58};
			JButton[] extraButtonsB = {btnB43, btnB44, btnB45, btnB46, btnB47, btnB48, btnB49, btnB50,
						btnB51, btnB52, btnB53, btnB54, btnB55, btnB56, btnB57, btnB58};

			for (JButton button : extraButtonsA) {
				button.setVisible(true);
			}

			for (JButton button : extraButtonsB) {
				button.setVisible(true);
			}
		}
		else if (iscbGrandLottoclicked) {
			lblPrizepool.setIcon(new ImageIcon("Images/prizepoolGrandLotto.gif"));

			JButton[] extraButtonsA = {btnA43, btnA44, btnA45, btnA46, btnA47, btnA48, btnA49, btnA50,
						btnA51, btnA52, btnA53, btnA54, btnA55};
			JButton[] extraButtonsB = {btnB43, btnB44, btnB45, btnB46, btnB47, btnB48, btnB49, btnB50,
						btnB51, btnB52, btnB53, btnB54, btnB55};

			for (JButton button : extraButtonsA) {
				button.setVisible(true);
			}

			for (JButton button : extraButtonsB) {
				button.setVisible(true);
			}
		}
		else {
			lblPrizepool.setIcon(new ImageIcon("Images/prizepoolLotto.gif"));
		}
	}

	private void falseVisibleChooseNumbers() {
		JButton[] buttonsA = {btnA1, btnA2, btnA3, btnA4, btnA5, btnA6, btnA7, btnA8, btnA9, btnA10,
			btnA11, btnA12, btnA13, btnA14, btnA15, btnA16, btnA17, btnA18, btnA19, btnA20,
			btnA21, btnA22, btnA23, btnA24, btnA25, btnA26, btnA27, btnA28, btnA29, btnA30,
			btnA31, btnA32, btnA33, btnA34, btnA35, btnA36, btnA37, btnA38, btnA39, btnA40,
			btnA41, btnA42, btnA43, btnA44, btnA45, btnA46, btnA47, btnA48, btnA49, btnA50,
			btnA51, btnA52, btnA53, btnA54, btnA55, btnA56, btnA57, btnA58};

		JButton[] buttonsB = {btnB1, btnB2, btnB3, btnB4, btnB5, btnB6, btnB7, btnB8, btnB9, btnB10,
					btnB11, btnB12, btnB13, btnB14, btnB15, btnB16, btnB17, btnB18, btnB19, btnB20,
					btnB21, btnB22, btnB23, btnB24, btnB25, btnB26, btnB27, btnB28, btnB29, btnB30,
					btnB31, btnB32, btnB33, btnB34, btnB35, btnB36, btnB37, btnB38, btnB39, btnB40,
					btnB41, btnB42, btnB43, btnB44, btnB45, btnB46, btnB47, btnB48, btnB49, btnB50,
					btnB51, btnB52, btnB53, btnB54, btnB55, btnB56, btnB57, btnB58};

		JComponent[] otherComponents = {lblA, lblB, lblChooseA, lblChooseB, btnSubmit, btnNext};

		for (JButton button : buttonsA) {
			button.setVisible(false);
		}

		for (JButton button : buttonsB) {
			button.setVisible(false);
		}

		for (JComponent component : otherComponents) {
			component.setVisible(false);
		}
	}

	private void btnNumbersSetForeground() {
		JButton[] buttonsA = {btnA1, btnA2, btnA3, btnA4, btnA5, btnA6, btnA7, btnA8, btnA9, btnA10,
			btnA11, btnA12, btnA13, btnA14, btnA15, btnA16, btnA17, btnA18, btnA19, btnA20,
			btnA21, btnA22, btnA23, btnA24, btnA25, btnA26, btnA27, btnA28, btnA29, btnA30,
			btnA31, btnA32, btnA33, btnA34, btnA35, btnA36, btnA37, btnA38, btnA39, btnA40,
			btnA41, btnA42, btnA43, btnA44, btnA45, btnA46, btnA47, btnA48, btnA49, btnA50,
			btnA51, btnA52, btnA53, btnA54, btnA55, btnA56, btnA57, btnA58};

		JButton[] buttonsB = {btnB1, btnB2, btnB3, btnB4, btnB5, btnB6, btnB7, btnB8, btnB9, btnB10,
			btnB11, btnB12, btnB13, btnB14, btnB15, btnB16, btnB17, btnB18, btnB19, btnB20,
			btnB21, btnB22, btnB23, btnB24, btnB25, btnB26, btnB27, btnB28, btnB29, btnB30,
			btnB31, btnB32, btnB33, btnB34, btnB35, btnB36, btnB37, btnB38, btnB39, btnB40,
			btnB41, btnB42, btnB43, btnB44, btnB45, btnB46, btnB47, btnB48, btnB49, btnB50,
			btnB51, btnB52, btnB53, btnB54, btnB55, btnB56, btnB57, btnB58};

		for (JButton button : buttonsA) {
			button.setForeground(Color.BLACK);
			button.setBackground(Color.YELLOW);
		}

		for (JButton button : buttonsB) {
			button.setForeground(Color.BLACK);
			button.setBackground(Color.YELLOW);
		}
	}

	private void setVisibleReceipt() {
		JLabel[] receiptLabels = {lblUserChosenNumbers, lblReceiptGameTitle, lblReceiptGameTitle2,
			lblReceiptA, lblReceiptAgent, lblReceiptAgentOutput, lblReceiptB,
			lblReceiptDate, lblReceiptDateOutput, lblReceiptDraw,
			lblReceiptDrawOutput, lblReceiptLotto, lblDashedLines1,
			lblDashedLines2};

		for (JLabel label : receiptLabels) {
			label.setVisible(true);
		}
	}
	
	private void cbCategoriesVisible() {
		JComponent[] componentsToHide = {cbCategories};

		for (JComponent component : componentsToHide) {
			component.setVisible(false);
		}

		JLabel[] labelsToShow = {lblA, lblB, lblChooseA, lblChooseB, lblGameField, lblPrizepool, lblPrize};

		for (JLabel label : labelsToShow) {
			label.setVisible(true);
		}

		JButton[] buttonsToShow = {btnSubmit, btnReselect};

		for (JButton button : buttonsToShow) {
			button.setVisible(true);
		}
	}

	private void initComponents() {
		panelGameField3 = new JPanel();
		lblPartyPopper = new JLabel();
		lblP2Congratulations = new JLabel();
		lblWonCP = new JLabel();
		lblUserPrize = new JLabel();
		lblCorrectNumbers = new JLabel();
		lblCorrectNumbersA = new JLabel();
		lblCardA = new JLabel();
		lblCorrectNumbersB = new JLabel();
		lblCardB = new JLabel();
		lblFireworks8 = new JLabel();
		lblFireworks9 = new JLabel();
		lblFireworks1 = new JLabel();
		lblFireworks2 = new JLabel();
		lblFireworks3 = new JLabel();
		lblFireworks4 = new JLabel();
		lblFireworks5 = new JLabel();
		lblFireworks6 = new JLabel();
		lblFireworks7 = new JLabel();
		lblPanelbgGF = new JLabel();
		btnNext = new JButton();
		lblDrawFirstNumber = new JLabel();
		lblDrawSecondNumber = new JLabel();
		lblDrawThirdNumber = new JLabel();
		lblDrawFourthNumber = new JLabel();
		lblDrawFifthNumber = new JLabel();
		lblDrawSixthNumber = new JLabel();
		lblStoreSixRN = new JLabel();
		lblDrawFirstBall = new JLabel();
		lblDrawSecondBall = new JLabel();
		lblDrawThirdBall = new JLabel();
		lblDrawFourthBall = new JLabel();
		lblDrawFifthBall = new JLabel();
		lblDrawSixthBall = new JLabel();
		lblFirstB = new JLabel();
		lblSecondB = new JLabel();
		lblThirdB = new JLabel();
		lblFourthB = new JLabel();
		lblFifthB = new JLabel();
		lblSixthB = new JLabel();
		lblPrize = new JLabel();
		btnSubmit = new JButton();
		txtStoreChosenNumbersA = new JTextField();
		txtStoreChosenNumbersB = new JTextField();
		btnA1 = new JButton();
		btnA2 = new JButton();
		btnA3 = new JButton();
		btnA4 = new JButton();
		btnA5 = new JButton();
		btnA6 = new JButton();
		btnA7 = new JButton();
		btnA8 = new JButton();
		btnA9 = new JButton();
		btnA10 = new JButton();
		btnA11 = new JButton();
		btnA12 = new JButton();
		btnA13 = new JButton();
		btnA14 = new JButton();
		btnA15 = new JButton();
		btnA16 = new JButton();
		btnA17 = new JButton();
		btnA18 = new JButton();
		btnA19 = new JButton();
		btnA20 = new JButton();
		btnA21 = new JButton();
		btnA22 = new JButton();
		btnA23 = new JButton();
		btnA24 = new JButton();
		btnA25 = new JButton();
		btnA26 = new JButton();
		btnA27 = new JButton();
		btnA28 = new JButton();
		btnA29 = new JButton();
		btnA30 = new JButton();
		btnA31 = new JButton();
		btnA32 = new JButton();
		btnA33 = new JButton();
		btnA34 = new JButton();
		btnA35 = new JButton();
		btnA36 = new JButton();
		btnA37 = new JButton();
		btnA38 = new JButton();
		btnA39 = new JButton();
		btnA40 = new JButton();
		btnA41 = new JButton();
		btnA42 = new JButton();
		btnA43 = new JButton();
		btnA44 = new JButton();
		btnA45 = new JButton();
		btnA46 = new JButton();
		btnA47 = new JButton();
		btnA48 = new JButton();
		btnA49 = new JButton();
		btnA50 = new JButton();
		btnA51 = new JButton();
		btnA52 = new JButton();
		btnA53 = new JButton();
		btnA54 = new JButton();
		btnA55 = new JButton();
		btnA56 = new JButton();
		btnA57 = new JButton();
		btnA58 = new JButton();
		btnB1 = new JButton();
		btnB2 = new JButton();
		btnB3 = new JButton();
		btnB4 = new JButton();
		btnB5 = new JButton();
		btnB6 = new JButton();
		btnB7 = new JButton();
		btnB8 = new JButton();
		btnB9 = new JButton();
		btnB10 = new JButton();
		btnB11 = new JButton();
		btnB12 = new JButton();
		btnB13 = new JButton();
		btnB14 = new JButton();
		btnB15 = new JButton();
		btnB16 = new JButton();
		btnB17 = new JButton();
		btnB18 = new JButton();
		btnB19 = new JButton();
		btnB20 = new JButton();
		btnB21 = new JButton();
		btnB22 = new JButton();
		btnB23 = new JButton();
		btnB24 = new JButton();
		btnB25 = new JButton();
		btnB26 = new JButton();
		btnB27 = new JButton();
		btnB28 = new JButton();
		btnB29 = new JButton();
		btnB30 = new JButton();
		btnB31 = new JButton();
		btnB32 = new JButton();
		btnB33 = new JButton();
		btnB34 = new JButton();
		btnB35 = new JButton();
		btnB36 = new JButton();
		btnB37 = new JButton();
		btnB38 = new JButton();
		btnB39 = new JButton();
		btnB40 = new JButton();
		btnB41 = new JButton();
		btnB42 = new JButton();
		btnB43 = new JButton();
		btnB44 = new JButton();
		btnB45 = new JButton();
		btnB46 = new JButton();
		btnB47 = new JButton();
		btnB48 = new JButton();
		btnB49 = new JButton();
		btnB50 = new JButton();
		btnB51 = new JButton();
		btnB52 = new JButton();
		btnB53 = new JButton();
		btnB54 = new JButton();
		btnB55 = new JButton();
		btnB56 = new JButton();
		btnB57 = new JButton();
		btnB58 = new JButton();
		lblA = new JLabel();
		lblB = new JLabel();
		lblReceiptGameTitle2 = new JLabel();
		lblChooseA = new JLabel();
		lblChooseB = new JLabel();
		lblPrizepool = new JLabel();
		lblReceiptA = new JLabel();
		lblReceiptB = new JLabel();
		lblYourNumbersA = new JLabel();
		lblYourNumbersB = new JLabel();
		lblReceiptDateOutput = new JLabel();
		lblDashedLines1 = new JLabel();
		lblDashedLines2 = new JLabel();
		lblReceiptDate = new JLabel();
		lblReceiptAgent = new JLabel();
		lblReceiptDraw = new JLabel();
		lblReceiptDrawOutput = new JLabel();
		lblReceiptAgentOutput = new JLabel();
		lblReceiptLotto = new JLabel();
		lblReceiptGameTitle = new JLabel();
		btnPlayAgain = new JLabel();
		btnReselect = new JButton();
		btnUnmute = new JButton();
		btnMute = new JButton();
		btnHelp = new JButton();
		cbCategories = new JComboBox<>();
		txtDrawTimer = new JLabel();
		lblUserChosenNumbers = new JLabel();
		btnContinue = new JButton();
		lblDispCorrectNumbers = new JLabel();
		btnRollBall = new JButton();
		lblGameField = new JLabel();
		lblGameCategory = new JLabel();
		lblGameBackground = new JLabel();
		panelGameField2 = new JPanel();
		lblWN1 = new JLabel();
		lblWN2 = new JLabel();
		lblWN3 = new JLabel();
		lblWN4 = new JLabel();
		lblWN5 = new JLabel();
		lblWN6 = new JLabel();
		lblWinningNumber1 = new JLabel();
		lblWinningNumber2 = new JLabel();
		lblWinningNumber3 = new JLabel();
		lblWinningNumber4 = new JLabel();
		lblWinningNumber5 = new JLabel();
		lblWinningNumber6 = new JLabel();
		lblWinningCombinations = new JLabel();
		lblCategoryChosen = new JLabel();
		lblCongratulations = new JLabel();
		lblJackpotPrize = new JLabel();
		lblGamePrize = new JLabel();
		lblJackpotWinner = new JLabel();
		lblDate2 = new JLabel();
		lblbgGameField = new JLabel();

		//======== this ========
		setTitle("Lucky Lotto");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				thisMouseClicked(e);
			}
		});
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//======== panelGameField3 ========
		{
			panelGameField3.setBackground(new Color(0x00cccc));
			panelGameField3.setLayout(null);

			//---- lblPartyPopper ----
			lblPartyPopper.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\partPopperGIF.gif"));
			panelGameField3.add(lblPartyPopper);
			lblPartyPopper.setBounds(0, 280, 530, 545);

			//---- lblP2Congratulations ----
			lblP2Congratulations.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Congratulations.png"));
			panelGameField3.add(lblP2Congratulations);
			lblP2Congratulations.setBounds(200, 75, 610, 65);

			//---- lblWonCP ----
			lblWonCP.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Consolation Prize.png"));
			panelGameField3.add(lblWonCP);
			lblWonCP.setBounds(105, 150, 820, 60);

			//---- lblUserPrize ----
			lblUserPrize.setOpaque(true);
			lblUserPrize.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ConsolationPrize.gif"));
			panelGameField3.add(lblUserPrize);
			lblUserPrize.setBounds(105, 210, 825, 105);

			//---- lblCorrectNumbers ----
			lblCorrectNumbers.setHorizontalAlignment(SwingConstants.CENTER);
			lblCorrectNumbers.setFont(new Font("Arial Black", Font.BOLD, 22));
			lblCorrectNumbers.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\CorrectNumbers.png"));
			panelGameField3.add(lblCorrectNumbers);
			lblCorrectNumbers.setBounds(180, 330, 655, 60);

			//---- lblCorrectNumbersA ----
			lblCorrectNumbersA.setHorizontalAlignment(SwingConstants.CENTER);
			lblCorrectNumbersA.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 42));
			lblCorrectNumbersA.setOpaque(true);
			lblCorrectNumbersA.setBackground(Color.yellow);
			panelGameField3.add(lblCorrectNumbersA);
			lblCorrectNumbersA.setBounds(180, 380, 655, 100);

			//---- lblCardA ----
			lblCardA.setText("CARD A");
			lblCardA.setHorizontalAlignment(SwingConstants.CENTER);
			lblCardA.setFont(new Font("Arial Black", Font.BOLD, 22));
			lblCardA.setForeground(Color.white);
			panelGameField3.add(lblCardA);
			lblCardA.setBounds(315, 475, 385, 40);

			//---- lblCorrectNumbersB ----
			lblCorrectNumbersB.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 42));
			lblCorrectNumbersB.setHorizontalAlignment(SwingConstants.CENTER);
			lblCorrectNumbersB.setBackground(Color.yellow);
			lblCorrectNumbersB.setOpaque(true);
			panelGameField3.add(lblCorrectNumbersB);
			lblCorrectNumbersB.setBounds(185, 545, 650, 100);

			//---- lblCardB ----
			lblCardB.setText("CARD B");
			lblCardB.setHorizontalAlignment(SwingConstants.CENTER);
			lblCardB.setFont(new Font("Arial Black", Font.BOLD, 22));
			lblCardB.setForeground(Color.white);
			panelGameField3.add(lblCardB);
			lblCardB.setBounds(315, 640, 385, 40);

			//---- lblFireworks8 ----
			lblFireworks8.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworksss.gif"));
			panelGameField3.add(lblFireworks8);
			lblFireworks8.setBounds(30, 10, 480, 480);

			//---- lblFireworks9 ----
			lblFireworks9.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworksss.gif"));
			panelGameField3.add(lblFireworks9);
			lblFireworks9.setBounds(510, 10, 480, 480);

			//---- lblFireworks1 ----
			lblFireworks1.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworks....gif"));
			panelGameField3.add(lblFireworks1);
			lblFireworks1.setBounds(90, 20, 118, 118);

			//---- lblFireworks2 ----
			lblFireworks2.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworks....gif"));
			panelGameField3.add(lblFireworks2);
			lblFireworks2.setBounds(210, 90, 118, 118);

			//---- lblFireworks3 ----
			lblFireworks3.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworks....gif"));
			panelGameField3.add(lblFireworks3);
			lblFireworks3.setBounds(330, 20, 118, 118);

			//---- lblFireworks4 ----
			lblFireworks4.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworks....gif"));
			panelGameField3.add(lblFireworks4);
			lblFireworks4.setBounds(450, 90, 118, 118);

			//---- lblFireworks5 ----
			lblFireworks5.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworks....gif"));
			panelGameField3.add(lblFireworks5);
			lblFireworks5.setBounds(570, 20, 118, 118);

			//---- lblFireworks6 ----
			lblFireworks6.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworks....gif"));
			panelGameField3.add(lblFireworks6);
			lblFireworks6.setBounds(690, 90, 118, 118);

			//---- lblFireworks7 ----
			lblFireworks7.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\fireworks....gif"));
			panelGameField3.add(lblFireworks7);
			lblFireworks7.setBounds(810, 20, 118, 118);

			//---- lblPanelbgGF ----
			lblPanelbgGF.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\bgGameField2.gif"));
			panelGameField3.add(lblPanelbgGF);
			lblPanelbgGF.setBounds(0, 0, 1020, 785);

			{
				// compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < panelGameField3.getComponentCount(); i++) {
					Rectangle bounds = panelGameField3.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = panelGameField3.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				panelGameField3.setMinimumSize(preferredSize);
				panelGameField3.setPreferredSize(preferredSize);
			}
		}
		contentPane.add(panelGameField3);
		panelGameField3.setBounds(375, 95, 1020, 785);

		//---- btnNext ----
		btnNext.setBorderPainted(false);
		btnNext.setFocusable(false);
		btnNext.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Next.gif"));
		btnNext.setBorder(null);
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNextMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNextMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNextMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNextMousePressed(e);
			}
		});
		contentPane.add(btnNext);
		btnNext.setBounds(725, 10, 260, 70);

		//---- lblDrawFirstNumber ----
		lblDrawFirstNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrawFirstNumber.setFont(new Font("Impact", Font.PLAIN, 62));
		contentPane.add(lblDrawFirstNumber);
		lblDrawFirstNumber.setBounds(560, 380, 100, 100);

		//---- lblDrawSecondNumber ----
		lblDrawSecondNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrawSecondNumber.setFont(new Font("Impact", Font.PLAIN, 62));
		contentPane.add(lblDrawSecondNumber);
		lblDrawSecondNumber.setBounds(860, 385, 100, 100);

		//---- lblDrawThirdNumber ----
		lblDrawThirdNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrawThirdNumber.setFont(new Font("Impact", Font.PLAIN, 62));
		contentPane.add(lblDrawThirdNumber);
		lblDrawThirdNumber.setBounds(1150, 380, 100, 100);

		//---- lblDrawFourthNumber ----
		lblDrawFourthNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrawFourthNumber.setFont(new Font("Impact", Font.PLAIN, 62));
		contentPane.add(lblDrawFourthNumber);
		lblDrawFourthNumber.setBounds(560, 645, 100, 100);

		//---- lblDrawFifthNumber ----
		lblDrawFifthNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrawFifthNumber.setFont(new Font("Impact", Font.PLAIN, 62));
		contentPane.add(lblDrawFifthNumber);
		lblDrawFifthNumber.setBounds(860, 645, 100, 100);

		//---- lblDrawSixthNumber ----
		lblDrawSixthNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrawSixthNumber.setFont(new Font("Impact", Font.PLAIN, 62));
		contentPane.add(lblDrawSixthNumber);
		lblDrawSixthNumber.setBounds(1150, 645, 100, 100);

		//---- lblStoreSixRN ----
		lblStoreSixRN.setBackground(Color.red);
		lblStoreSixRN.setOpaque(true);
		lblStoreSixRN.setFont(lblStoreSixRN.getFont().deriveFont(lblStoreSixRN.getFont().getStyle() | Font.BOLD, lblStoreSixRN.getFont().getSize() + 8f));
		contentPane.add(lblStoreSixRN);
		lblStoreSixRN.setBounds(10, 10, 350, 60);

		//---- lblDrawFirstBall ----
		lblDrawFirstBall.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ballFirst.png"));
		contentPane.add(lblDrawFirstBall);
		lblDrawFirstBall.setBounds(500, 325, 210, 210);

		//---- lblDrawSecondBall ----
		lblDrawSecondBall.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ballSecond.png"));
		contentPane.add(lblDrawSecondBall);
		lblDrawSecondBall.setBounds(799, 323, 210, 210);

		//---- lblDrawThirdBall ----
		lblDrawThirdBall.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ballThird.png"));
		contentPane.add(lblDrawThirdBall);
		lblDrawThirdBall.setBounds(1089, 323, 210, 210);

		//---- lblDrawFourthBall ----
		lblDrawFourthBall.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ballFourth.png"));
		contentPane.add(lblDrawFourthBall);
		lblDrawFourthBall.setBounds(500, 590, 210, 210);

		//---- lblDrawFifthBall ----
		lblDrawFifthBall.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ballFifth.png"));
		contentPane.add(lblDrawFifthBall);
		lblDrawFifthBall.setBounds(800, 590, 210, 210);

		//---- lblDrawSixthBall ----
		lblDrawSixthBall.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ballSixth.png"));
		contentPane.add(lblDrawSixthBall);
		lblDrawSixthBall.setBounds(1089, 588, 210, 210);

		//---- lblFirstB ----
		lblFirstB.setBackground(new Color(0x00cccc));
		lblFirstB.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\First.png"));
		contentPane.add(lblFirstB);
		lblFirstB.setBounds(515, 290, 185, 40);

		//---- lblSecondB ----
		lblSecondB.setBackground(new Color(0x00cccc));
		lblSecondB.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Second.png"));
		contentPane.add(lblSecondB);
		lblSecondB.setBounds(815, 290, 185, 40);

		//---- lblThirdB ----
		lblThirdB.setBackground(new Color(0x00cccc));
		lblThirdB.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Third.png"));
		contentPane.add(lblThirdB);
		lblThirdB.setBounds(1110, 290, 185, 40);

		//---- lblFourthB ----
		lblFourthB.setBackground(new Color(0x00cccc));
		lblFourthB.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Fourth.png"));
		contentPane.add(lblFourthB);
		lblFourthB.setBounds(510, 555, 185, 40);

		//---- lblFifthB ----
		lblFifthB.setBackground(new Color(0x00cccc));
		lblFifthB.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Fifth.png"));
		contentPane.add(lblFifthB);
		lblFifthB.setBounds(815, 555, 185, 40);

		//---- lblSixthB ----
		lblSixthB.setBackground(new Color(0x00cccc));
		lblSixthB.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Sixth.png"));
		contentPane.add(lblSixthB);
		lblSixthB.setBounds(1105, 555, 185, 40);

		//---- lblPrize ----
		lblPrize.setText("JACKPOT PRIZE");
		lblPrize.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrize.setFont(new Font("Arial", Font.BOLD, 29));
		lblPrize.setForeground(Color.white);
		contentPane.add(lblPrize);
		lblPrize.setBounds(685, 115, 365, 30);

		//---- btnSubmit ----
		btnSubmit.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\btnSubmit.gif"));
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSubmitMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSubmitMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSubmitMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnSubmitMousePressed(e);
			}
		});
		contentPane.add(btnSubmit);
		btnSubmit.setBounds(755, 265, 260, 70);

		//---- txtStoreChosenNumbersA ----
		txtStoreChosenNumbersA.setEditable(false);
		contentPane.add(txtStoreChosenNumbersA);
		txtStoreChosenNumbersA.setBounds(1275, 115, 100, txtStoreChosenNumbersA.getPreferredSize().height);

		//---- txtStoreChosenNumbersB ----
		txtStoreChosenNumbersB.setEditable(false);
		contentPane.add(txtStoreChosenNumbersB);
		txtStoreChosenNumbersB.setBounds(1275, 140, 100, 22);

		//---- btnA1 ----
		btnA1.setText("1");
		btnA1.setFont(btnA1.getFont().deriveFont(btnA1.getFont().getStyle() | Font.BOLD, btnA1.getFont().getSize() + 4f));
		btnA1.setBackground(Color.yellow);
		btnA1.addActionListener(e -> btnA1(e));
		btnA1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA1);
		btnA1.setBounds(445, 345, 60, 35);

		//---- btnA2 ----
		btnA2.setText("2");
		btnA2.setFont(btnA2.getFont().deriveFont(btnA2.getFont().getStyle() | Font.BOLD, btnA2.getFont().getSize() + 4f));
		btnA2.setBackground(Color.yellow);
		btnA2.addActionListener(e -> btnA2(e));
		btnA2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA2);
		btnA2.setBounds(515, 345, 60, 35);

		//---- btnA3 ----
		btnA3.setText("3");
		btnA3.setFont(btnA3.getFont().deriveFont(btnA3.getFont().getStyle() | Font.BOLD, btnA3.getFont().getSize() + 4f));
		btnA3.setBackground(Color.yellow);
		btnA3.addActionListener(e -> btnA3(e));
		btnA3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA3);
		btnA3.setBounds(585, 345, 60, 35);

		//---- btnA4 ----
		btnA4.setText("4");
		btnA4.setFont(btnA4.getFont().deriveFont(btnA4.getFont().getStyle() | Font.BOLD, btnA4.getFont().getSize() + 4f));
		btnA4.setBackground(Color.yellow);
		btnA4.addActionListener(e -> btnA4(e));
		btnA4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA4);
		btnA4.setBounds(655, 345, 60, 35);

		//---- btnA5 ----
		btnA5.setText("5");
		btnA5.setFont(btnA5.getFont().deriveFont(btnA5.getFont().getStyle() | Font.BOLD, btnA5.getFont().getSize() + 4f));
		btnA5.setBackground(Color.yellow);
		btnA5.addActionListener(e -> btnA5(e));
		btnA5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA5);
		btnA5.setBounds(725, 345, 60, 35);

		//---- btnA6 ----
		btnA6.setText("6");
		btnA6.setFont(btnA6.getFont().deriveFont(btnA6.getFont().getStyle() | Font.BOLD, btnA6.getFont().getSize() + 4f));
		btnA6.setBackground(Color.yellow);
		btnA6.addActionListener(e -> btnA6(e));
		btnA6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA6);
		btnA6.setBounds(795, 345, 60, 35);

		//---- btnA7 ----
		btnA7.setText("7");
		btnA7.setFont(btnA7.getFont().deriveFont(btnA7.getFont().getStyle() | Font.BOLD, btnA7.getFont().getSize() + 4f));
		btnA7.setBackground(Color.yellow);
		btnA7.addActionListener(e -> btnA7(e));
		btnA7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA7);
		btnA7.setBounds(445, 385, 60, 35);

		//---- btnA8 ----
		btnA8.setText("8");
		btnA8.setFont(btnA8.getFont().deriveFont(btnA8.getFont().getStyle() | Font.BOLD, btnA8.getFont().getSize() + 4f));
		btnA8.setBackground(Color.yellow);
		btnA8.addActionListener(e -> btnA8(e));
		btnA8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA8);
		btnA8.setBounds(515, 385, 60, 35);

		//---- btnA9 ----
		btnA9.setText("9");
		btnA9.setFont(btnA9.getFont().deriveFont(btnA9.getFont().getStyle() | Font.BOLD, btnA9.getFont().getSize() + 4f));
		btnA9.setBackground(Color.yellow);
		btnA9.addActionListener(e -> btnA9(e));
		btnA9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA9);
		btnA9.setBounds(585, 385, 60, 35);

		//---- btnA10 ----
		btnA10.setText("10");
		btnA10.setFont(btnA10.getFont().deriveFont(btnA10.getFont().getStyle() | Font.BOLD, btnA10.getFont().getSize() + 4f));
		btnA10.setBackground(Color.yellow);
		btnA10.addActionListener(e -> btnA10(e));
		btnA10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA10);
		btnA10.setBounds(655, 385, 60, 35);

		//---- btnA11 ----
		btnA11.setText("11");
		btnA11.setFont(btnA11.getFont().deriveFont(btnA11.getFont().getStyle() | Font.BOLD, btnA11.getFont().getSize() + 4f));
		btnA11.setBackground(Color.yellow);
		btnA11.addActionListener(e -> btnA11(e));
		btnA11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA11);
		btnA11.setBounds(725, 385, 60, 35);

		//---- btnA12 ----
		btnA12.setText("12");
		btnA12.setFont(btnA12.getFont().deriveFont(btnA12.getFont().getStyle() | Font.BOLD, btnA12.getFont().getSize() + 4f));
		btnA12.setBackground(Color.yellow);
		btnA12.addActionListener(e -> btnA12(e));
		btnA12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA12);
		btnA12.setBounds(795, 385, 60, 35);

		//---- btnA13 ----
		btnA13.setText("13");
		btnA13.setFont(btnA13.getFont().deriveFont(btnA13.getFont().getStyle() | Font.BOLD, btnA13.getFont().getSize() + 4f));
		btnA13.setBackground(Color.yellow);
		btnA13.addActionListener(e -> btnA13(e));
		btnA13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA13);
		btnA13.setBounds(445, 425, 60, 35);

		//---- btnA14 ----
		btnA14.setText("14");
		btnA14.setFont(btnA14.getFont().deriveFont(btnA14.getFont().getStyle() | Font.BOLD, btnA14.getFont().getSize() + 4f));
		btnA14.setBackground(Color.yellow);
		btnA14.addActionListener(e -> btnA14(e));
		btnA14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA14);
		btnA14.setBounds(515, 425, 60, 35);

		//---- btnA15 ----
		btnA15.setText("15");
		btnA15.setFont(btnA15.getFont().deriveFont(btnA15.getFont().getStyle() | Font.BOLD, btnA15.getFont().getSize() + 4f));
		btnA15.setBackground(Color.yellow);
		btnA15.addActionListener(e -> btnA15(e));
		btnA15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA15);
		btnA15.setBounds(585, 425, 60, 35);

		//---- btnA16 ----
		btnA16.setText("16");
		btnA16.setFont(btnA16.getFont().deriveFont(btnA16.getFont().getStyle() | Font.BOLD, btnA16.getFont().getSize() + 4f));
		btnA16.setBackground(Color.yellow);
		btnA16.addActionListener(e -> btnA16(e));
		btnA16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA16);
		btnA16.setBounds(655, 425, 60, 35);

		//---- btnA17 ----
		btnA17.setText("17");
		btnA17.setFont(btnA17.getFont().deriveFont(btnA17.getFont().getStyle() | Font.BOLD, btnA17.getFont().getSize() + 4f));
		btnA17.setBackground(Color.yellow);
		btnA17.addActionListener(e -> btnA17(e));
		btnA17.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA17);
		btnA17.setBounds(725, 425, 60, 35);

		//---- btnA18 ----
		btnA18.setText("18");
		btnA18.setFont(btnA18.getFont().deriveFont(btnA18.getFont().getStyle() | Font.BOLD, btnA18.getFont().getSize() + 4f));
		btnA18.setBackground(Color.yellow);
		btnA18.addActionListener(e -> btnA18(e));
		btnA18.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA18);
		btnA18.setBounds(795, 425, 60, 35);

		//---- btnA19 ----
		btnA19.setText("19");
		btnA19.setFont(btnA19.getFont().deriveFont(btnA19.getFont().getStyle() | Font.BOLD, btnA19.getFont().getSize() + 4f));
		btnA19.setBackground(Color.yellow);
		btnA19.addActionListener(e -> btnA19(e));
		btnA19.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA19);
		btnA19.setBounds(445, 465, 60, 35);

		//---- btnA20 ----
		btnA20.setText("20");
		btnA20.setFont(btnA20.getFont().deriveFont(btnA20.getFont().getStyle() | Font.BOLD, btnA20.getFont().getSize() + 4f));
		btnA20.setBackground(Color.yellow);
		btnA20.addActionListener(e -> btnA20(e));
		btnA20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA20);
		btnA20.setBounds(515, 465, 60, 35);

		//---- btnA21 ----
		btnA21.setText("21");
		btnA21.setFont(btnA21.getFont().deriveFont(btnA21.getFont().getStyle() | Font.BOLD, btnA21.getFont().getSize() + 4f));
		btnA21.setBackground(Color.yellow);
		btnA21.addActionListener(e -> btnA21(e));
		btnA21.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA21);
		btnA21.setBounds(585, 465, 60, 35);

		//---- btnA22 ----
		btnA22.setText("22");
		btnA22.setFont(btnA22.getFont().deriveFont(btnA22.getFont().getStyle() | Font.BOLD, btnA22.getFont().getSize() + 4f));
		btnA22.setBackground(Color.yellow);
		btnA22.addActionListener(e -> btnA22(e));
		btnA22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA22);
		btnA22.setBounds(655, 465, 60, 35);

		//---- btnA23 ----
		btnA23.setText("23");
		btnA23.setFont(btnA23.getFont().deriveFont(btnA23.getFont().getStyle() | Font.BOLD, btnA23.getFont().getSize() + 4f));
		btnA23.setBackground(Color.yellow);
		btnA23.addActionListener(e -> btnA23(e));
		btnA23.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA23);
		btnA23.setBounds(725, 465, 60, 35);

		//---- btnA24 ----
		btnA24.setText("24");
		btnA24.setFont(btnA24.getFont().deriveFont(btnA24.getFont().getStyle() | Font.BOLD, btnA24.getFont().getSize() + 4f));
		btnA24.setBackground(Color.yellow);
		btnA24.addActionListener(e -> btnA24(e));
		btnA24.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA24);
		btnA24.setBounds(795, 465, 60, 35);

		//---- btnA25 ----
		btnA25.setText("25");
		btnA25.setFont(btnA25.getFont().deriveFont(btnA25.getFont().getStyle() | Font.BOLD, btnA25.getFont().getSize() + 4f));
		btnA25.setBackground(Color.yellow);
		btnA25.addActionListener(e -> btnA25(e));
		btnA25.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA25);
		btnA25.setBounds(445, 505, 60, 35);

		//---- btnA26 ----
		btnA26.setText("26");
		btnA26.setFont(btnA26.getFont().deriveFont(btnA26.getFont().getStyle() | Font.BOLD, btnA26.getFont().getSize() + 4f));
		btnA26.setBackground(Color.yellow);
		btnA26.addActionListener(e -> btnA26(e));
		btnA26.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA26);
		btnA26.setBounds(515, 505, 60, 35);

		//---- btnA27 ----
		btnA27.setText("27");
		btnA27.setFont(btnA27.getFont().deriveFont(btnA27.getFont().getStyle() | Font.BOLD, btnA27.getFont().getSize() + 4f));
		btnA27.setBackground(Color.yellow);
		btnA27.addActionListener(e -> btnA27(e));
		btnA27.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA27);
		btnA27.setBounds(585, 505, 60, 35);

		//---- btnA28 ----
		btnA28.setText("28");
		btnA28.setFont(btnA28.getFont().deriveFont(btnA28.getFont().getStyle() | Font.BOLD, btnA28.getFont().getSize() + 4f));
		btnA28.setBackground(Color.yellow);
		btnA28.addActionListener(e -> btnA28(e));
		btnA28.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA28);
		btnA28.setBounds(655, 505, 60, 35);

		//---- btnA29 ----
		btnA29.setText("29");
		btnA29.setFont(btnA29.getFont().deriveFont(btnA29.getFont().getStyle() | Font.BOLD, btnA29.getFont().getSize() + 4f));
		btnA29.setBackground(Color.yellow);
		btnA29.addActionListener(e -> btnA29(e));
		btnA29.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA29);
		btnA29.setBounds(725, 505, 60, 35);

		//---- btnA30 ----
		btnA30.setText("30");
		btnA30.setFont(btnA30.getFont().deriveFont(btnA30.getFont().getStyle() | Font.BOLD, btnA30.getFont().getSize() + 4f));
		btnA30.setBackground(Color.yellow);
		btnA30.addActionListener(e -> btnA30(e));
		btnA30.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA30);
		btnA30.setBounds(795, 505, 60, 35);

		//---- btnA31 ----
		btnA31.setText("31");
		btnA31.setFont(btnA31.getFont().deriveFont(btnA31.getFont().getStyle() | Font.BOLD, btnA31.getFont().getSize() + 4f));
		btnA31.setBackground(Color.yellow);
		btnA31.addActionListener(e -> btnA31(e));
		btnA31.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA31);
		btnA31.setBounds(445, 545, 60, 35);

		//---- btnA32 ----
		btnA32.setText("32");
		btnA32.setFont(btnA32.getFont().deriveFont(btnA32.getFont().getStyle() | Font.BOLD, btnA32.getFont().getSize() + 4f));
		btnA32.setBackground(Color.yellow);
		btnA32.addActionListener(e -> btnA32(e));
		btnA32.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA32);
		btnA32.setBounds(515, 545, 60, 35);

		//---- btnA33 ----
		btnA33.setText("33");
		btnA33.setFont(btnA33.getFont().deriveFont(btnA33.getFont().getStyle() | Font.BOLD, btnA33.getFont().getSize() + 4f));
		btnA33.setBackground(Color.yellow);
		btnA33.addActionListener(e -> btnA33(e));
		btnA33.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA33);
		btnA33.setBounds(585, 545, 60, 35);

		//---- btnA34 ----
		btnA34.setText("34");
		btnA34.setFont(btnA34.getFont().deriveFont(btnA34.getFont().getStyle() | Font.BOLD, btnA34.getFont().getSize() + 4f));
		btnA34.setBackground(Color.yellow);
		btnA34.addActionListener(e -> btnA34(e));
		btnA34.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA34);
		btnA34.setBounds(655, 545, 60, 35);

		//---- btnA35 ----
		btnA35.setText("35");
		btnA35.setFont(btnA35.getFont().deriveFont(btnA35.getFont().getStyle() | Font.BOLD, btnA35.getFont().getSize() + 4f));
		btnA35.setBackground(Color.yellow);
		btnA35.addActionListener(e -> btnA35(e));
		btnA35.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA35);
		btnA35.setBounds(725, 545, 60, 35);

		//---- btnA36 ----
		btnA36.setText("36");
		btnA36.setFont(btnA36.getFont().deriveFont(btnA36.getFont().getStyle() | Font.BOLD, btnA36.getFont().getSize() + 4f));
		btnA36.setBackground(Color.yellow);
		btnA36.addActionListener(e -> btnA36(e));
		btnA36.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA36);
		btnA36.setBounds(795, 545, 60, 35);

		//---- btnA37 ----
		btnA37.setText("37");
		btnA37.setFont(btnA37.getFont().deriveFont(btnA37.getFont().getStyle() | Font.BOLD, btnA37.getFont().getSize() + 4f));
		btnA37.setBackground(Color.yellow);
		btnA37.addActionListener(e -> btnA37(e));
		btnA37.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA37);
		btnA37.setBounds(445, 585, 60, 35);

		//---- btnA38 ----
		btnA38.setText("38");
		btnA38.setFont(btnA38.getFont().deriveFont(btnA38.getFont().getStyle() | Font.BOLD, btnA38.getFont().getSize() + 4f));
		btnA38.setBackground(Color.yellow);
		btnA38.addActionListener(e -> btnA38(e));
		btnA38.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA38);
		btnA38.setBounds(515, 585, 60, 35);

		//---- btnA39 ----
		btnA39.setText("39");
		btnA39.setFont(btnA39.getFont().deriveFont(btnA39.getFont().getStyle() | Font.BOLD, btnA39.getFont().getSize() + 4f));
		btnA39.setBackground(Color.yellow);
		btnA39.addActionListener(e -> btnA39(e));
		btnA39.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA39);
		btnA39.setBounds(585, 585, 60, 35);

		//---- btnA40 ----
		btnA40.setText("40");
		btnA40.setFont(btnA40.getFont().deriveFont(btnA40.getFont().getStyle() | Font.BOLD, btnA40.getFont().getSize() + 4f));
		btnA40.setBackground(Color.yellow);
		btnA40.addActionListener(e -> btnA40(e));
		btnA40.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA40);
		btnA40.setBounds(655, 585, 60, 35);

		//---- btnA41 ----
		btnA41.setText("41");
		btnA41.setFont(btnA41.getFont().deriveFont(btnA41.getFont().getStyle() | Font.BOLD, btnA41.getFont().getSize() + 4f));
		btnA41.setBackground(Color.yellow);
		btnA41.addActionListener(e -> btnA41(e));
		btnA41.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA41);
		btnA41.setBounds(725, 585, 60, 35);

		//---- btnA42 ----
		btnA42.setText("42");
		btnA42.setFont(btnA42.getFont().deriveFont(btnA42.getFont().getStyle() | Font.BOLD, btnA42.getFont().getSize() + 4f));
		btnA42.setBackground(Color.yellow);
		btnA42.addActionListener(e -> btnA42(e));
		btnA42.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA42);
		btnA42.setBounds(795, 585, 60, 35);

		//---- btnA43 ----
		btnA43.setText("43");
		btnA43.setFont(btnA43.getFont().deriveFont(btnA43.getFont().getStyle() | Font.BOLD, btnA43.getFont().getSize() + 4f));
		btnA43.setBackground(Color.yellow);
		btnA43.addActionListener(e -> btnA43(e));
		btnA43.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA43);
		btnA43.setBounds(445, 625, 60, 35);

		//---- btnA44 ----
		btnA44.setText("44");
		btnA44.setFont(btnA44.getFont().deriveFont(btnA44.getFont().getStyle() | Font.BOLD, btnA44.getFont().getSize() + 4f));
		btnA44.setBackground(Color.yellow);
		btnA44.addActionListener(e -> btnA44(e));
		btnA44.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA44);
		btnA44.setBounds(515, 625, 60, 35);

		//---- btnA45 ----
		btnA45.setText("45");
		btnA45.setFont(btnA45.getFont().deriveFont(btnA45.getFont().getStyle() | Font.BOLD, btnA45.getFont().getSize() + 4f));
		btnA45.setBackground(Color.yellow);
		btnA45.addActionListener(e -> btnA45(e));
		btnA45.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA45);
		btnA45.setBounds(585, 625, 60, 35);

		//---- btnA46 ----
		btnA46.setText("46");
		btnA46.setFont(btnA46.getFont().deriveFont(btnA46.getFont().getStyle() | Font.BOLD, btnA46.getFont().getSize() + 4f));
		btnA46.setBackground(Color.yellow);
		btnA46.addActionListener(e -> btnA46(e));
		btnA46.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA46);
		btnA46.setBounds(655, 625, 60, 35);

		//---- btnA47 ----
		btnA47.setText("47");
		btnA47.setFont(btnA47.getFont().deriveFont(btnA47.getFont().getStyle() | Font.BOLD, btnA47.getFont().getSize() + 4f));
		btnA47.setBackground(Color.yellow);
		btnA47.addActionListener(e -> btnA47(e));
		btnA47.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA47);
		btnA47.setBounds(725, 625, 60, 35);

		//---- btnA48 ----
		btnA48.setText("48");
		btnA48.setFont(btnA48.getFont().deriveFont(btnA48.getFont().getStyle() | Font.BOLD, btnA48.getFont().getSize() + 4f));
		btnA48.setBackground(Color.yellow);
		btnA48.addActionListener(e -> btnA48(e));
		btnA48.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA48);
		btnA48.setBounds(795, 625, 60, 35);

		//---- btnA49 ----
		btnA49.setText("49");
		btnA49.setFont(btnA49.getFont().deriveFont(btnA49.getFont().getStyle() | Font.BOLD, btnA49.getFont().getSize() + 4f));
		btnA49.setBackground(Color.yellow);
		btnA49.addActionListener(e -> btnA49(e));
		btnA49.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA49);
		btnA49.setBounds(445, 665, 60, 35);

		//---- btnA50 ----
		btnA50.setText("50");
		btnA50.setFont(btnA50.getFont().deriveFont(btnA50.getFont().getStyle() | Font.BOLD, btnA50.getFont().getSize() + 4f));
		btnA50.setBackground(Color.yellow);
		btnA50.addActionListener(e -> btnA50(e));
		btnA50.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA50);
		btnA50.setBounds(515, 665, 60, 35);

		//---- btnA51 ----
		btnA51.setText("51");
		btnA51.setFont(btnA51.getFont().deriveFont(btnA51.getFont().getStyle() | Font.BOLD, btnA51.getFont().getSize() + 4f));
		btnA51.setBackground(Color.yellow);
		btnA51.addActionListener(e -> btnA51(e));
		btnA51.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA51);
		btnA51.setBounds(585, 665, 60, 35);

		//---- btnA52 ----
		btnA52.setText("52");
		btnA52.setFont(btnA52.getFont().deriveFont(btnA52.getFont().getStyle() | Font.BOLD, btnA52.getFont().getSize() + 4f));
		btnA52.setBackground(Color.yellow);
		btnA52.addActionListener(e -> btnA52(e));
		btnA52.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA52);
		btnA52.setBounds(655, 665, 60, 35);

		//---- btnA53 ----
		btnA53.setText("53");
		btnA53.setFont(btnA53.getFont().deriveFont(btnA53.getFont().getStyle() | Font.BOLD, btnA53.getFont().getSize() + 4f));
		btnA53.setBackground(Color.yellow);
		btnA53.addActionListener(e -> btnA53(e));
		btnA53.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA53);
		btnA53.setBounds(725, 665, 60, 35);

		//---- btnA54 ----
		btnA54.setText("54");
		btnA54.setFont(btnA54.getFont().deriveFont(btnA54.getFont().getStyle() | Font.BOLD, btnA54.getFont().getSize() + 4f));
		btnA54.setBackground(Color.yellow);
		btnA54.addActionListener(e -> btnA54(e));
		btnA54.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA54);
		btnA54.setBounds(795, 665, 60, 35);

		//---- btnA55 ----
		btnA55.setText("55");
		btnA55.setFont(btnA55.getFont().deriveFont(btnA55.getFont().getStyle() | Font.BOLD, btnA55.getFont().getSize() + 4f));
		btnA55.setBackground(Color.yellow);
		btnA55.addActionListener(e -> btnA55(e));
		btnA55.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA55);
		btnA55.setBounds(445, 705, 60, 35);

		//---- btnA56 ----
		btnA56.setText("56");
		btnA56.setFont(btnA56.getFont().deriveFont(btnA56.getFont().getStyle() | Font.BOLD, btnA56.getFont().getSize() + 4f));
		btnA56.setBackground(Color.yellow);
		btnA56.addActionListener(e -> btnA56(e));
		btnA56.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA56);
		btnA56.setBounds(515, 705, 60, 35);

		//---- btnA57 ----
		btnA57.setText("57");
		btnA57.setFont(btnA57.getFont().deriveFont(btnA57.getFont().getStyle() | Font.BOLD, btnA57.getFont().getSize() + 4f));
		btnA57.setBackground(Color.yellow);
		btnA57.addActionListener(e -> btnA57(e));
		btnA57.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA57);
		btnA57.setBounds(585, 705, 60, 35);

		//---- btnA58 ----
		btnA58.setText("58");
		btnA58.setFont(btnA58.getFont().deriveFont(btnA58.getFont().getStyle() | Font.BOLD, btnA58.getFont().getSize() + 4f));
		btnA58.setBackground(Color.yellow);
		btnA58.addActionListener(e -> btnA58(e));
		btnA58.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnA58);
		btnA58.setBounds(655, 705, 60, 35);

		//---- btnB1 ----
		btnB1.setText("1");
		btnB1.setFont(btnB1.getFont().deriveFont(btnB1.getFont().getStyle() | Font.BOLD, btnB1.getFont().getSize() + 4f));
		btnB1.setBackground(Color.yellow);
		btnB1.addActionListener(e -> btnB1(e));
		btnB1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB1);
		btnB1.setBounds(915, 345, 60, 35);

		//---- btnB2 ----
		btnB2.setText("2");
		btnB2.setFont(btnB2.getFont().deriveFont(btnB2.getFont().getStyle() | Font.BOLD, btnB2.getFont().getSize() + 4f));
		btnB2.setBackground(Color.yellow);
		btnB2.addActionListener(e -> btnB2(e));
		btnB2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB2);
		btnB2.setBounds(985, 345, 60, 35);

		//---- btnB3 ----
		btnB3.setText("3");
		btnB3.setFont(btnB3.getFont().deriveFont(btnB3.getFont().getStyle() | Font.BOLD, btnB3.getFont().getSize() + 4f));
		btnB3.setBackground(Color.yellow);
		btnB3.addActionListener(e -> btnB3(e));
		btnB3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB3);
		btnB3.setBounds(1055, 345, 60, 35);

		//---- btnB4 ----
		btnB4.setText("4");
		btnB4.setFont(btnB4.getFont().deriveFont(btnB4.getFont().getStyle() | Font.BOLD, btnB4.getFont().getSize() + 4f));
		btnB4.setBackground(Color.yellow);
		btnB4.addActionListener(e -> btnB4(e));
		btnB4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB4);
		btnB4.setBounds(1125, 345, 60, 35);

		//---- btnB5 ----
		btnB5.setText("5");
		btnB5.setFont(btnB5.getFont().deriveFont(btnB5.getFont().getStyle() | Font.BOLD, btnB5.getFont().getSize() + 4f));
		btnB5.setBackground(Color.yellow);
		btnB5.addActionListener(e -> btnB5(e));
		btnB5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB5);
		btnB5.setBounds(1195, 345, 60, 35);

		//---- btnB6 ----
		btnB6.setText("6");
		btnB6.setFont(btnB6.getFont().deriveFont(btnB6.getFont().getStyle() | Font.BOLD, btnB6.getFont().getSize() + 4f));
		btnB6.setBackground(Color.yellow);
		btnB6.addActionListener(e -> btnB6(e));
		btnB6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB6);
		btnB6.setBounds(1265, 345, 60, 35);

		//---- btnB7 ----
		btnB7.setText("7");
		btnB7.setFont(btnB7.getFont().deriveFont(btnB7.getFont().getStyle() | Font.BOLD, btnB7.getFont().getSize() + 4f));
		btnB7.setBackground(Color.yellow);
		btnB7.addActionListener(e -> btnB7(e));
		btnB7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB7);
		btnB7.setBounds(915, 385, 60, 35);

		//---- btnB8 ----
		btnB8.setText("8");
		btnB8.setFont(btnB8.getFont().deriveFont(btnB8.getFont().getStyle() | Font.BOLD, btnB8.getFont().getSize() + 4f));
		btnB8.setBackground(Color.yellow);
		btnB8.addActionListener(e -> btnB8(e));
		btnB8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB8);
		btnB8.setBounds(985, 385, 60, 35);

		//---- btnB9 ----
		btnB9.setText("9");
		btnB9.setFont(btnB9.getFont().deriveFont(btnB9.getFont().getStyle() | Font.BOLD, btnB9.getFont().getSize() + 4f));
		btnB9.setBackground(Color.yellow);
		btnB9.addActionListener(e -> btnB9(e));
		btnB9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB9);
		btnB9.setBounds(1055, 385, 60, 35);

		//---- btnB10 ----
		btnB10.setText("10");
		btnB10.setFont(btnB10.getFont().deriveFont(btnB10.getFont().getStyle() | Font.BOLD, btnB10.getFont().getSize() + 4f));
		btnB10.setBackground(Color.yellow);
		btnB10.addActionListener(e -> btnB10(e));
		btnB10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB10);
		btnB10.setBounds(1125, 385, 60, 35);

		//---- btnB11 ----
		btnB11.setText("11");
		btnB11.setFont(btnB11.getFont().deriveFont(btnB11.getFont().getStyle() | Font.BOLD, btnB11.getFont().getSize() + 4f));
		btnB11.setBackground(Color.yellow);
		btnB11.addActionListener(e -> btnB11(e));
		btnB11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB11);
		btnB11.setBounds(1195, 385, 60, 35);

		//---- btnB12 ----
		btnB12.setText("12");
		btnB12.setFont(btnB12.getFont().deriveFont(btnB12.getFont().getStyle() | Font.BOLD, btnB12.getFont().getSize() + 4f));
		btnB12.setBackground(Color.yellow);
		btnB12.addActionListener(e -> btnB12(e));
		btnB12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB12);
		btnB12.setBounds(1265, 385, 60, 35);

		//---- btnB13 ----
		btnB13.setText("13");
		btnB13.setFont(btnB13.getFont().deriveFont(btnB13.getFont().getStyle() | Font.BOLD, btnB13.getFont().getSize() + 4f));
		btnB13.setBackground(Color.yellow);
		btnB13.addActionListener(e -> btnB13(e));
		btnB13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB13);
		btnB13.setBounds(915, 425, 60, 35);

		//---- btnB14 ----
		btnB14.setText("14");
		btnB14.setFont(btnB14.getFont().deriveFont(btnB14.getFont().getStyle() | Font.BOLD, btnB14.getFont().getSize() + 4f));
		btnB14.setBackground(Color.yellow);
		btnB14.addActionListener(e -> btnB14(e));
		btnB14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB14);
		btnB14.setBounds(985, 425, 60, 35);

		//---- btnB15 ----
		btnB15.setText("15");
		btnB15.setFont(btnB15.getFont().deriveFont(btnB15.getFont().getStyle() | Font.BOLD, btnB15.getFont().getSize() + 4f));
		btnB15.setBackground(Color.yellow);
		btnB15.addActionListener(e -> btnB15(e));
		btnB15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB15);
		btnB15.setBounds(1055, 425, 60, 35);

		//---- btnB16 ----
		btnB16.setText("16");
		btnB16.setFont(btnB16.getFont().deriveFont(btnB16.getFont().getStyle() | Font.BOLD, btnB16.getFont().getSize() + 4f));
		btnB16.setBackground(Color.yellow);
		btnB16.addActionListener(e -> btnB16(e));
		btnB16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB16);
		btnB16.setBounds(1125, 425, 60, 35);

		//---- btnB17 ----
		btnB17.setText("17");
		btnB17.setFont(btnB17.getFont().deriveFont(btnB17.getFont().getStyle() | Font.BOLD, btnB17.getFont().getSize() + 4f));
		btnB17.setBackground(Color.yellow);
		btnB17.addActionListener(e -> btnB17(e));
		btnB17.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB17);
		btnB17.setBounds(1195, 425, 60, 35);

		//---- btnB18 ----
		btnB18.setText("18");
		btnB18.setFont(btnB18.getFont().deriveFont(btnB18.getFont().getStyle() | Font.BOLD, btnB18.getFont().getSize() + 4f));
		btnB18.setBackground(Color.yellow);
		btnB18.addActionListener(e -> btnB18(e));
		btnB18.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB18);
		btnB18.setBounds(1265, 425, 60, 35);

		//---- btnB19 ----
		btnB19.setText("19");
		btnB19.setFont(btnB19.getFont().deriveFont(btnB19.getFont().getStyle() | Font.BOLD, btnB19.getFont().getSize() + 4f));
		btnB19.setBackground(Color.yellow);
		btnB19.addActionListener(e -> btnB19(e));
		btnB19.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB19);
		btnB19.setBounds(915, 465, 60, 35);

		//---- btnB20 ----
		btnB20.setText("20");
		btnB20.setFont(btnB20.getFont().deriveFont(btnB20.getFont().getStyle() | Font.BOLD, btnB20.getFont().getSize() + 4f));
		btnB20.setBackground(Color.yellow);
		btnB20.addActionListener(e -> btnB20(e));
		btnB20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB20);
		btnB20.setBounds(985, 465, 60, 35);

		//---- btnB21 ----
		btnB21.setText("21");
		btnB21.setFont(btnB21.getFont().deriveFont(btnB21.getFont().getStyle() | Font.BOLD, btnB21.getFont().getSize() + 4f));
		btnB21.setBackground(Color.yellow);
		btnB21.addActionListener(e -> btnB21(e));
		btnB21.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB21);
		btnB21.setBounds(1055, 465, 60, 35);

		//---- btnB22 ----
		btnB22.setText("22");
		btnB22.setFont(btnB22.getFont().deriveFont(btnB22.getFont().getStyle() | Font.BOLD, btnB22.getFont().getSize() + 4f));
		btnB22.setBackground(Color.yellow);
		btnB22.addActionListener(e -> btnB22(e));
		btnB22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB22);
		btnB22.setBounds(1125, 465, 60, 35);

		//---- btnB23 ----
		btnB23.setText("23");
		btnB23.setFont(btnB23.getFont().deriveFont(btnB23.getFont().getStyle() | Font.BOLD, btnB23.getFont().getSize() + 4f));
		btnB23.setBackground(Color.yellow);
		btnB23.addActionListener(e -> btnB23(e));
		btnB23.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB23);
		btnB23.setBounds(1195, 465, 60, 35);

		//---- btnB24 ----
		btnB24.setText("24");
		btnB24.setFont(btnB24.getFont().deriveFont(btnB24.getFont().getStyle() | Font.BOLD, btnB24.getFont().getSize() + 4f));
		btnB24.setBackground(Color.yellow);
		btnB24.addActionListener(e -> btnB24(e));
		btnB24.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB24);
		btnB24.setBounds(1265, 465, 60, 35);

		//---- btnB25 ----
		btnB25.setText("25");
		btnB25.setFont(btnB25.getFont().deriveFont(btnB25.getFont().getStyle() | Font.BOLD, btnB25.getFont().getSize() + 4f));
		btnB25.setBackground(Color.yellow);
		btnB25.addActionListener(e -> btnB25(e));
		btnB25.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB25);
		btnB25.setBounds(915, 505, 60, 35);

		//---- btnB26 ----
		btnB26.setText("26");
		btnB26.setFont(btnB26.getFont().deriveFont(btnB26.getFont().getStyle() | Font.BOLD, btnB26.getFont().getSize() + 4f));
		btnB26.setBackground(Color.yellow);
		btnB26.addActionListener(e -> btnB26(e));
		btnB26.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB26);
		btnB26.setBounds(985, 505, 60, 35);

		//---- btnB27 ----
		btnB27.setText("27");
		btnB27.setFont(btnB27.getFont().deriveFont(btnB27.getFont().getStyle() | Font.BOLD, btnB27.getFont().getSize() + 4f));
		btnB27.setBackground(Color.yellow);
		btnB27.addActionListener(e -> btnB27(e));
		btnB27.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB27);
		btnB27.setBounds(1055, 505, 60, 35);

		//---- btnB28 ----
		btnB28.setText("28");
		btnB28.setFont(btnB28.getFont().deriveFont(btnB28.getFont().getStyle() | Font.BOLD, btnB28.getFont().getSize() + 4f));
		btnB28.setBackground(Color.yellow);
		btnB28.addActionListener(e -> btnB28(e));
		btnB28.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB28);
		btnB28.setBounds(1125, 505, 60, 35);

		//---- btnB29 ----
		btnB29.setText("29");
		btnB29.setFont(btnB29.getFont().deriveFont(btnB29.getFont().getStyle() | Font.BOLD, btnB29.getFont().getSize() + 4f));
		btnB29.setBackground(Color.yellow);
		btnB29.addActionListener(e -> btnB29(e));
		btnB29.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB29);
		btnB29.setBounds(1195, 505, 60, 35);

		//---- btnB30 ----
		btnB30.setText("30");
		btnB30.setFont(btnB30.getFont().deriveFont(btnB30.getFont().getStyle() | Font.BOLD, btnB30.getFont().getSize() + 4f));
		btnB30.setBackground(Color.yellow);
		btnB30.addActionListener(e -> btnB30(e));
		btnB30.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB30);
		btnB30.setBounds(1265, 505, 60, 35);

		//---- btnB31 ----
		btnB31.setText("31");
		btnB31.setFont(btnB31.getFont().deriveFont(btnB31.getFont().getStyle() | Font.BOLD, btnB31.getFont().getSize() + 4f));
		btnB31.setBackground(Color.yellow);
		btnB31.addActionListener(e -> btnB31(e));
		btnB31.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB31);
		btnB31.setBounds(915, 545, 60, 35);

		//---- btnB32 ----
		btnB32.setText("32");
		btnB32.setFont(btnB32.getFont().deriveFont(btnB32.getFont().getStyle() | Font.BOLD, btnB32.getFont().getSize() + 4f));
		btnB32.setBackground(Color.yellow);
		btnB32.addActionListener(e -> btnB32(e));
		btnB32.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB32);
		btnB32.setBounds(985, 545, 60, 35);

		//---- btnB33 ----
		btnB33.setText("33");
		btnB33.setFont(btnB33.getFont().deriveFont(btnB33.getFont().getStyle() | Font.BOLD, btnB33.getFont().getSize() + 4f));
		btnB33.setBackground(Color.yellow);
		btnB33.addActionListener(e -> btnB33(e));
		btnB33.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB33);
		btnB33.setBounds(1055, 545, 60, 35);

		//---- btnB34 ----
		btnB34.setText("34");
		btnB34.setFont(btnB34.getFont().deriveFont(btnB34.getFont().getStyle() | Font.BOLD, btnB34.getFont().getSize() + 4f));
		btnB34.setBackground(Color.yellow);
		btnB34.addActionListener(e -> btnB34(e));
		btnB34.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB34);
		btnB34.setBounds(1125, 545, 60, 35);

		//---- btnB35 ----
		btnB35.setText("35");
		btnB35.setFont(btnB35.getFont().deriveFont(btnB35.getFont().getStyle() | Font.BOLD, btnB35.getFont().getSize() + 4f));
		btnB35.setBackground(Color.yellow);
		btnB35.addActionListener(e -> btnB35(e));
		btnB35.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB35);
		btnB35.setBounds(1195, 545, 60, 35);

		//---- btnB36 ----
		btnB36.setText("36");
		btnB36.setFont(btnB36.getFont().deriveFont(btnB36.getFont().getStyle() | Font.BOLD, btnB36.getFont().getSize() + 4f));
		btnB36.setBackground(Color.yellow);
		btnB36.addActionListener(e -> btnB36(e));
		btnB36.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB36);
		btnB36.setBounds(1265, 545, 60, 35);

		//---- btnB37 ----
		btnB37.setText("37");
		btnB37.setFont(btnB37.getFont().deriveFont(btnB37.getFont().getStyle() | Font.BOLD, btnB37.getFont().getSize() + 4f));
		btnB37.setBackground(Color.yellow);
		btnB37.addActionListener(e -> btnB37(e));
		btnB37.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB37);
		btnB37.setBounds(915, 585, 60, 35);

		//---- btnB38 ----
		btnB38.setText("38");
		btnB38.setFont(btnB38.getFont().deriveFont(btnB38.getFont().getStyle() | Font.BOLD, btnB38.getFont().getSize() + 4f));
		btnB38.setBackground(Color.yellow);
		btnB38.addActionListener(e -> btnB38(e));
		btnB38.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB38);
		btnB38.setBounds(985, 585, 60, 35);

		//---- btnB39 ----
		btnB39.setText("39");
		btnB39.setFont(btnB39.getFont().deriveFont(btnB39.getFont().getStyle() | Font.BOLD, btnB39.getFont().getSize() + 4f));
		btnB39.setBackground(Color.yellow);
		btnB39.addActionListener(e -> btnB39(e));
		btnB39.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB39);
		btnB39.setBounds(1055, 585, 60, 35);

		//---- btnB40 ----
		btnB40.setText("40");
		btnB40.setFont(btnB40.getFont().deriveFont(btnB40.getFont().getStyle() | Font.BOLD, btnB40.getFont().getSize() + 4f));
		btnB40.setBackground(Color.yellow);
		btnB40.addActionListener(e -> btnB40(e));
		btnB40.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB40);
		btnB40.setBounds(1125, 585, 60, 35);

		//---- btnB41 ----
		btnB41.setText("41");
		btnB41.setFont(btnB41.getFont().deriveFont(btnB41.getFont().getStyle() | Font.BOLD, btnB41.getFont().getSize() + 4f));
		btnB41.setBackground(Color.yellow);
		btnB41.addActionListener(e -> btnB41(e));
		btnB41.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB41);
		btnB41.setBounds(1195, 585, 60, 35);

		//---- btnB42 ----
		btnB42.setText("42");
		btnB42.setFont(btnB42.getFont().deriveFont(btnB42.getFont().getStyle() | Font.BOLD, btnB42.getFont().getSize() + 4f));
		btnB42.setBackground(Color.yellow);
		btnB42.addActionListener(e -> btnB42(e));
		btnB42.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB42);
		btnB42.setBounds(1265, 585, 60, 35);

		//---- btnB43 ----
		btnB43.setText("43");
		btnB43.setFont(btnB43.getFont().deriveFont(btnB43.getFont().getStyle() | Font.BOLD, btnB43.getFont().getSize() + 4f));
		btnB43.setBackground(Color.yellow);
		btnB43.addActionListener(e -> btnB43(e));
		btnB43.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB43);
		btnB43.setBounds(915, 625, 60, 35);

		//---- btnB44 ----
		btnB44.setText("44");
		btnB44.setFont(btnB44.getFont().deriveFont(btnB44.getFont().getStyle() | Font.BOLD, btnB44.getFont().getSize() + 4f));
		btnB44.setBackground(Color.yellow);
		btnB44.addActionListener(e -> btnB44(e));
		btnB44.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB44);
		btnB44.setBounds(985, 625, 60, 35);

		//---- btnB45 ----
		btnB45.setText("45");
		btnB45.setFont(btnB45.getFont().deriveFont(btnB45.getFont().getStyle() | Font.BOLD, btnB45.getFont().getSize() + 4f));
		btnB45.setBackground(Color.yellow);
		btnB45.addActionListener(e -> btnB45(e));
		btnB45.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB45);
		btnB45.setBounds(1055, 625, 60, 35);

		//---- btnB46 ----
		btnB46.setText("46");
		btnB46.setFont(btnB46.getFont().deriveFont(btnB46.getFont().getStyle() | Font.BOLD, btnB46.getFont().getSize() + 4f));
		btnB46.setBackground(Color.yellow);
		btnB46.addActionListener(e -> btnB46(e));
		btnB46.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB46);
		btnB46.setBounds(1125, 625, 60, 35);

		//---- btnB47 ----
		btnB47.setText("47");
		btnB47.setFont(btnB47.getFont().deriveFont(btnB47.getFont().getStyle() | Font.BOLD, btnB47.getFont().getSize() + 4f));
		btnB47.setBackground(Color.yellow);
		btnB47.addActionListener(e -> btnB47(e));
		btnB47.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB47);
		btnB47.setBounds(1195, 625, 60, 35);

		//---- btnB48 ----
		btnB48.setText("48");
		btnB48.setFont(btnB48.getFont().deriveFont(btnB48.getFont().getStyle() | Font.BOLD, btnB48.getFont().getSize() + 4f));
		btnB48.setBackground(Color.yellow);
		btnB48.addActionListener(e -> btnB48(e));
		btnB48.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB48);
		btnB48.setBounds(1265, 625, 60, 35);

		//---- btnB49 ----
		btnB49.setText("49");
		btnB49.setFont(btnB49.getFont().deriveFont(btnB49.getFont().getStyle() | Font.BOLD, btnB49.getFont().getSize() + 4f));
		btnB49.setBackground(Color.yellow);
		btnB49.addActionListener(e -> btnB49(e));
		btnB49.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB49);
		btnB49.setBounds(915, 665, 60, 35);

		//---- btnB50 ----
		btnB50.setText("50");
		btnB50.setFont(btnB50.getFont().deriveFont(btnB50.getFont().getStyle() | Font.BOLD, btnB50.getFont().getSize() + 4f));
		btnB50.setBackground(Color.yellow);
		btnB50.addActionListener(e -> btnB50(e));
		btnB50.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB50);
		btnB50.setBounds(985, 665, 60, 35);

		//---- btnB51 ----
		btnB51.setText("51");
		btnB51.setFont(btnB51.getFont().deriveFont(btnB51.getFont().getStyle() | Font.BOLD, btnB51.getFont().getSize() + 4f));
		btnB51.setBackground(Color.yellow);
		btnB51.addActionListener(e -> btnB51(e));
		btnB51.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB51);
		btnB51.setBounds(1055, 665, 60, 35);

		//---- btnB52 ----
		btnB52.setText("52");
		btnB52.setFont(btnB52.getFont().deriveFont(btnB52.getFont().getStyle() | Font.BOLD, btnB52.getFont().getSize() + 4f));
		btnB52.setBackground(Color.yellow);
		btnB52.addActionListener(e -> btnB52(e));
		btnB52.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB52);
		btnB52.setBounds(1125, 665, 60, 35);

		//---- btnB53 ----
		btnB53.setText("53");
		btnB53.setFont(btnB53.getFont().deriveFont(btnB53.getFont().getStyle() | Font.BOLD, btnB53.getFont().getSize() + 4f));
		btnB53.setBackground(Color.yellow);
		btnB53.addActionListener(e -> btnB53(e));
		btnB53.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB53);
		btnB53.setBounds(1195, 665, 60, 35);

		//---- btnB54 ----
		btnB54.setText("54");
		btnB54.setFont(btnB54.getFont().deriveFont(btnB54.getFont().getStyle() | Font.BOLD, btnB54.getFont().getSize() + 4f));
		btnB54.setBackground(Color.yellow);
		btnB54.addActionListener(e -> btnB54(e));
		btnB54.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB54);
		btnB54.setBounds(1265, 665, 60, 35);

		//---- btnB55 ----
		btnB55.setText("55");
		btnB55.setFont(btnB55.getFont().deriveFont(btnB55.getFont().getStyle() | Font.BOLD, btnB55.getFont().getSize() + 4f));
		btnB55.setBackground(Color.yellow);
		btnB55.addActionListener(e -> btnB55(e));
		btnB55.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB55);
		btnB55.setBounds(915, 705, 60, 35);

		//---- btnB56 ----
		btnB56.setText("56");
		btnB56.setFont(btnB56.getFont().deriveFont(btnB56.getFont().getStyle() | Font.BOLD, btnB56.getFont().getSize() + 4f));
		btnB56.setBackground(Color.yellow);
		btnB56.addActionListener(e -> btnB56(e));
		btnB56.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB56);
		btnB56.setBounds(985, 705, 60, 35);

		//---- btnB57 ----
		btnB57.setText("57");
		btnB57.setFont(btnB57.getFont().deriveFont(btnB57.getFont().getStyle() | Font.BOLD, btnB57.getFont().getSize() + 4f));
		btnB57.setBackground(Color.yellow);
		btnB57.addActionListener(e -> btnB57(e));
		btnB57.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB57);
		btnB57.setBounds(1055, 705, 60, 35);

		//---- btnB58 ----
		btnB58.setText("58");
		btnB58.setFont(btnB58.getFont().deriveFont(btnB58.getFont().getStyle() | Font.BOLD, btnB58.getFont().getSize() + 4f));
		btnB58.setBackground(Color.yellow);
		btnB58.addActionListener(e -> btnB58(e));
		btnB58.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNumbersClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNumbersEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNumbersExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNumbersPressed(e);
			}
		});
		contentPane.add(btnB58);
		btnB58.setBounds(1125, 705, 60, 35);

		//---- lblA ----
		lblA.setText("A");
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		lblA.setFont(new Font("Impact", Font.PLAIN, 52));
		lblA.setForeground(Color.white);
		contentPane.add(lblA);
		lblA.setBounds(615, 275, 60, 55);

		//---- lblB ----
		lblB.setText("B");
		lblB.setHorizontalAlignment(SwingConstants.CENTER);
		lblB.setFont(new Font("Impact", Font.PLAIN, 52));
		lblB.setForeground(Color.white);
		contentPane.add(lblB);
		lblB.setBounds(1030, 275, 175, 55);

		//---- lblReceiptGameTitle2 ----
		lblReceiptGameTitle2.setText("CHECK YOUR NUMBERS HERE!!!");
		lblReceiptGameTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		lblReceiptGameTitle2.setFont(new Font("Arial", Font.BOLD, 17));
		contentPane.add(lblReceiptGameTitle2);
		lblReceiptGameTitle2.setBounds(25, 220, 330, 40);

		//---- lblChooseA ----
		lblChooseA.setOpaque(true);
		lblChooseA.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ABCbg.jpg"));
		contentPane.add(lblChooseA);
		lblChooseA.setBounds(430, 255, 440, 520);

		//---- lblChooseB ----
		lblChooseB.setOpaque(true);
		lblChooseB.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ABCbg.jpg"));
		contentPane.add(lblChooseB);
		lblChooseB.setBounds(900, 255, 440, 520);

		//---- lblPrizepool ----
		lblPrizepool.setBackground(Color.white);
		lblPrizepool.setOpaque(true);
		contentPane.add(lblPrizepool);
		lblPrizepool.setBounds(535, 150, 705, 90);

		//---- lblReceiptA ----
		lblReceiptA.setText("A.");
		lblReceiptA.setFont(new Font("Arial Black", Font.BOLD, 21));
		contentPane.add(lblReceiptA);
		lblReceiptA.setBounds(30, 580, 30, 35);

		//---- lblReceiptB ----
		lblReceiptB.setText("B.");
		lblReceiptB.setFont(new Font("Arial Black", Font.BOLD, 21));
		contentPane.add(lblReceiptB);
		lblReceiptB.setBounds(30, 650, 25, 35);

		//---- lblYourNumbersA ----
		lblYourNumbersA.setFont(lblYourNumbersA.getFont().deriveFont(lblYourNumbersA.getFont().getSize() + 4f));
		contentPane.add(lblYourNumbersA);
		lblYourNumbersA.setBounds(70, 580, 280, 35);

		//---- lblYourNumbersB ----
		lblYourNumbersB.setFont(lblYourNumbersB.getFont().deriveFont(lblYourNumbersB.getFont().getSize() + 4f));
		contentPane.add(lblYourNumbersB);
		lblYourNumbersB.setBounds(70, 650, 280, 35);

		//---- lblReceiptDateOutput ----
		lblReceiptDateOutput.setFont(new Font("Arial", Font.ITALIC, 16));
		contentPane.add(lblReceiptDateOutput);
		lblReceiptDateOutput.setBounds(100, 450, 240, 25);

		//---- lblDashedLines1 ----
		lblDashedLines1.setText("-------------------------------------------------------");
		lblDashedLines1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		contentPane.add(lblDashedLines1);
		lblDashedLines1.setBounds(25, 545, 330, 35);

		//---- lblDashedLines2 ----
		lblDashedLines2.setText("-------------------------------------------------------");
		lblDashedLines2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		contentPane.add(lblDashedLines2);
		lblDashedLines2.setBounds(25, 685, 330, 35);

		//---- lblReceiptDate ----
		lblReceiptDate.setText("DATE:");
		lblReceiptDate.setFont(new Font("Arial", Font.BOLD, 16));
		contentPane.add(lblReceiptDate);
		lblReceiptDate.setBounds(30, 450, 65, 25);

		//---- lblReceiptAgent ----
		lblReceiptAgent.setText("AGENT:");
		lblReceiptAgent.setFont(new Font("Arial", Font.BOLD, 16));
		contentPane.add(lblReceiptAgent);
		lblReceiptAgent.setBounds(30, 475, 65, 25);

		//---- lblReceiptDraw ----
		lblReceiptDraw.setText("DRAW:");
		lblReceiptDraw.setFont(new Font("Arial", Font.BOLD, 16));
		contentPane.add(lblReceiptDraw);
		lblReceiptDraw.setBounds(30, 500, 65, 25);

		//---- lblReceiptDrawOutput ----
		lblReceiptDrawOutput.setFont(new Font("Arial", Font.ITALIC, 16));
		contentPane.add(lblReceiptDrawOutput);
		lblReceiptDrawOutput.setBounds(100, 500, 240, 25);

		//---- lblReceiptAgentOutput ----
		lblReceiptAgentOutput.setFont(new Font("Arial", Font.ITALIC, 16));
		contentPane.add(lblReceiptAgentOutput);
		lblReceiptAgentOutput.setBounds(100, 475, 240, 25);

		//---- lblReceiptLotto ----
		lblReceiptLotto.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ReceiptLuckyLotto.png"));
		contentPane.add(lblReceiptLotto);
		lblReceiptLotto.setBounds(35, 275, 305, 160);

		//---- lblReceiptGameTitle ----
		lblReceiptGameTitle.setText("LUCKY LOTTO");
		lblReceiptGameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblReceiptGameTitle.setFont(new Font("Arial", Font.BOLD, 25));
		lblReceiptGameTitle.setForeground(Color.white);
		contentPane.add(lblReceiptGameTitle);
		lblReceiptGameTitle.setBounds(25, 245, 330, 40);

		//---- btnPlayAgain ----
		btnPlayAgain.setOpaque(true);
		btnPlayAgain.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\PLAY AGAIN.gif"));
		btnPlayAgain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnPlayAgainMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnPlayAgainMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnPlayAgainMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnPlayAgainMousePressed(e);
			}
		});
		contentPane.add(btnPlayAgain);
		btnPlayAgain.setBounds(630, 10, 420, 70);

		//---- btnReselect ----
		btnReselect.setBorderPainted(false);
		btnReselect.setFocusable(false);
		btnReselect.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Reselect.jpg"));
		btnReselect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnReselectMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnReselectMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnReselectMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnReselectMousePressed(e);
			}
		});
		contentPane.add(btnReselect);
		btnReselect.setBounds(1065, 10, 100, 70);

		//---- btnUnmute ----
		btnUnmute.setBorderPainted(false);
		btnUnmute.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\btnUnmute.jpg"));
		btnUnmute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnUnmuteMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUnmuteMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnUnmuteMouseExited(e);
			}
		});
		contentPane.add(btnUnmute);
		btnUnmute.setBounds(1180, 10, 100, 70);

		//---- btnMute ----
		btnMute.setBorderPainted(false);
		btnMute.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\btnMute.jpg"));
		btnMute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnMuteMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMuteMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnMuteMouseExited(e);
			}
		});
		contentPane.add(btnMute);
		btnMute.setBounds(1180, 10, 100, 70);

		//---- btnHelp ----
		btnHelp.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\btnHelp.jpg"));
		btnHelp.setBorderPainted(false);
		btnHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnHelpMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnHelpMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnHelpMouseExited(e);
			}
		});
		contentPane.add(btnHelp);
		btnHelp.setBounds(1295, 10, 100, 70);

		//---- cbCategories ----
		cbCategories.setModel(new DefaultComboBoxModel<>(new String[] {
			"Lotto 6/42",
			"Ultra Lotto 6/58",
			"Grand Lotto 6/55"
		}));
		cbCategories.setFont(new Font("Segoe UI", Font.BOLD, 28));
		cbCategories.setForeground(Color.white);
		cbCategories.setBackground(Color.orange);
		cbCategories.setFocusable(false);
		cbCategories.addActionListener(e -> cbCategories(e));
		cbCategories.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cbCategoriesMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				cbCategoriesMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cbCategoriesMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				cbCategoriesMousePressed(e);
			}
		});
		contentPane.add(cbCategories);
		cbCategories.setBounds(370, 10, 260, 70);

		//---- txtDrawTimer ----
		txtDrawTimer.setFont(new Font("Impact", Font.PLAIN, 61));
		txtDrawTimer.setHorizontalAlignment(SwingConstants.RIGHT);
		txtDrawTimer.setForeground(Color.red);
		txtDrawTimer.setText("00:01:00");
		contentPane.add(txtDrawTimer);
		txtDrawTimer.setBounds(370, 10, 260, 70);

		//---- lblUserChosenNumbers ----
		lblUserChosenNumbers.setBackground(Color.white);
		lblUserChosenNumbers.setOpaque(true);
		lblUserChosenNumbers.setFocusable(false);
		lblUserChosenNumbers.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\ReceiptBackground.jpg"));
		lblUserChosenNumbers.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserChosenNumbers.setBorder(null);
		contentPane.add(lblUserChosenNumbers);
		lblUserChosenNumbers.setBounds(20, 215, 340, 660);

		//---- btnContinue ----
		btnContinue.setBorderPainted(false);
		btnContinue.setFocusable(false);
		btnContinue.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\btnContiue.gif"));
		btnContinue.setBorder(null);
		btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnContinueMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnContinueMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnContinueMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnContinueMousePressed(e);
			}
		});
		contentPane.add(btnContinue);
		btnContinue.setBounds(725, 10, 260, 70);

		//---- lblDispCorrectNumbers ----
		lblDispCorrectNumbers.setOpaque(true);
		contentPane.add(lblDispCorrectNumbers);
		lblDispCorrectNumbers.setBounds(10, 105, 350, 80);

		//---- btnRollBall ----
		btnRollBall.setBorderPainted(false);
		btnRollBall.setFocusable(false);
		btnRollBall.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\RolllBall.gif"));
		btnRollBall.setBorder(null);
		btnRollBall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnRollBallMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRollBallMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRollBallMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnRollBallMousePressed(e);
			}
		});
		contentPane.add(btnRollBall);
		btnRollBall.setBounds(725, 10, 260, 70);

		//---- lblGameField ----
		lblGameField.setBackground(new Color(0xff3333));
		lblGameField.setFocusable(false);
		lblGameField.setFont(lblGameField.getFont().deriveFont(lblGameField.getFont().getSize() + 9f));
		lblGameField.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\bgGameField2.gif"));
		lblGameField.setBorder(null);
		contentPane.add(lblGameField);
		lblGameField.setBounds(375, 95, 1020, 785);

		//---- lblGameCategory ----
		lblGameCategory.setBackground(new Color(0xcc6600));
		lblGameCategory.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\gameTitle.png"));
		lblGameCategory.setFocusable(false);
		contentPane.add(lblGameCategory);
		lblGameCategory.setBounds(20, -15, 340, 240);

		//---- lblGameBackground ----
		lblGameBackground.setBackground(new Color(0xffcc00));
		lblGameBackground.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\gameBackground3.jpg"));
		lblGameBackground.setFocusable(false);
		contentPane.add(lblGameBackground);
		lblGameBackground.setBounds(0, 0, 1420, 900);

		//======== panelGameField2 ========
		{
			panelGameField2.setBackground(new Color(0xff6600));
			panelGameField2.setLayout(null);

			//---- lblWN1 ----
			lblWN1.setHorizontalAlignment(SwingConstants.CENTER);
			lblWN1.setFont(new Font("Impact", Font.PLAIN, 55));
			panelGameField2.add(lblWN1);
			lblWN1.setBounds(80, 355, 85, 75);

			//---- lblWN2 ----
			lblWN2.setHorizontalAlignment(SwingConstants.CENTER);
			lblWN2.setFont(new Font("Impact", Font.PLAIN, 55));
			panelGameField2.add(lblWN2);
			lblWN2.setBounds(235, 355, 85, 75);

			//---- lblWN3 ----
			lblWN3.setHorizontalAlignment(SwingConstants.CENTER);
			lblWN3.setFont(new Font("Impact", Font.PLAIN, 55));
			panelGameField2.add(lblWN3);
			lblWN3.setBounds(390, 355, 85, 75);

			//---- lblWN4 ----
			lblWN4.setHorizontalAlignment(SwingConstants.CENTER);
			lblWN4.setFont(new Font("Impact", Font.PLAIN, 55));
			panelGameField2.add(lblWN4);
			lblWN4.setBounds(545, 355, 85, 75);

			//---- lblWN5 ----
			lblWN5.setHorizontalAlignment(SwingConstants.CENTER);
			lblWN5.setFont(new Font("Impact", Font.PLAIN, 55));
			panelGameField2.add(lblWN5);
			lblWN5.setBounds(700, 355, 85, 75);

			//---- lblWN6 ----
			lblWN6.setHorizontalAlignment(SwingConstants.CENTER);
			lblWN6.setFont(new Font("Impact", Font.PLAIN, 55));
			panelGameField2.add(lblWN6);
			lblWN6.setBounds(855, 355, 85, 75);

			//---- lblWinningNumber1 ----
			lblWinningNumber1.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\b1.png"));
			panelGameField2.add(lblWinningNumber1);
			lblWinningNumber1.setBounds(50, 320, 145, 145);

			//---- lblWinningNumber2 ----
			lblWinningNumber2.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\b2.png"));
			panelGameField2.add(lblWinningNumber2);
			lblWinningNumber2.setBounds(205, 320, 145, 145);

			//---- lblWinningNumber3 ----
			lblWinningNumber3.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\b3.png"));
			panelGameField2.add(lblWinningNumber3);
			lblWinningNumber3.setBounds(360, 320, 145, 145);

			//---- lblWinningNumber4 ----
			lblWinningNumber4.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\b4.png"));
			panelGameField2.add(lblWinningNumber4);
			lblWinningNumber4.setBounds(515, 320, 145, 145);

			//---- lblWinningNumber5 ----
			lblWinningNumber5.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\b5.png"));
			panelGameField2.add(lblWinningNumber5);
			lblWinningNumber5.setBounds(670, 320, 145, 145);

			//---- lblWinningNumber6 ----
			lblWinningNumber6.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\b6.png"));
			panelGameField2.add(lblWinningNumber6);
			lblWinningNumber6.setBounds(825, 320, 145, 145);

			//---- lblWinningCombinations ----
			lblWinningCombinations.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\WINNING COMBINATIONS.png"));
			panelGameField2.add(lblWinningCombinations);
			lblWinningCombinations.setBounds(180, 450, 660, 70);

			//---- lblCategoryChosen ----
			lblCategoryChosen.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\pGrandLotto.png"));
			panelGameField2.add(lblCategoryChosen);
			lblCategoryChosen.setBounds(395, 80, 220, 125);

			//---- lblCongratulations ----
			lblCongratulations.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Congratulations.png"));
			panelGameField2.add(lblCongratulations);
			lblCongratulations.setBounds(200, 15, 610, 65);

			//---- lblJackpotPrize ----
			lblJackpotPrize.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\JackpotPrize.png"));
			panelGameField2.add(lblJackpotPrize);
			lblJackpotPrize.setBounds(185, 635, 610, 60);

			//---- lblGamePrize ----
			lblGamePrize.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\PHP 10 000 000.png"));
			panelGameField2.add(lblGamePrize);
			lblGamePrize.setBounds(115, 530, 790, 105);

			//---- lblJackpotWinner ----
			lblJackpotWinner.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\Jackpot Winner.png"));
			lblJackpotWinner.setHorizontalAlignment(SwingConstants.CENTER);
			panelGameField2.add(lblJackpotWinner);
			lblJackpotWinner.setBounds(225, 205, 585, 65);

			//---- lblDate2 ----
			lblDate2.setHorizontalAlignment(SwingConstants.CENTER);
			lblDate2.setFont(new Font("Arial", Font.BOLD, 23));
			lblDate2.setForeground(Color.white);
			panelGameField2.add(lblDate2);
			lblDate2.setBounds(295, 275, 430, 40);

			//---- lblbgGameField ----
			lblbgGameField.setIcon(new ImageIcon("C:\\Users\\Admin\\Desktop\\Lotto Game\\Images\\bgGameField2.gif"));
			panelGameField2.add(lblbgGameField);
			lblbgGameField.setBounds(0, 0, 1020, 785);

			{
				// compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < panelGameField2.getComponentCount(); i++) {
					Rectangle bounds = panelGameField2.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = panelGameField2.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				panelGameField2.setMinimumSize(preferredSize);
				panelGameField2.setPreferredSize(preferredSize);
			}
		}
		contentPane.add(panelGameField2);
		panelGameField2.setBounds(375, 95, 1020, 785);

		{
			// compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		setSize(1420, 930);
		setLocationRelativeTo(null);
	}

	private JPanel panelGameField3;
	private JLabel lblPartyPopper;
	private JLabel lblP2Congratulations;
	private JLabel lblWonCP;
	private JLabel lblUserPrize;
	private JLabel lblCorrectNumbers;
	private JLabel lblCorrectNumbersA;
	private JLabel lblCardA;
	private JLabel lblCorrectNumbersB;
	private JLabel lblCardB;
	private JLabel lblFireworks8;
	private JLabel lblFireworks9;
	private JLabel lblFireworks1;
	private JLabel lblFireworks2;
	private JLabel lblFireworks3;
	private JLabel lblFireworks4;
	private JLabel lblFireworks5;
	private JLabel lblFireworks6;
	private JLabel lblFireworks7;
	private JLabel lblPanelbgGF;
	private JButton btnNext;
	private JLabel lblDrawFirstNumber;
	private JLabel lblDrawSecondNumber;
	private JLabel lblDrawThirdNumber;
	private JLabel lblDrawFourthNumber;
	private JLabel lblDrawFifthNumber;
	private JLabel lblDrawSixthNumber;
	private JLabel lblStoreSixRN;
	private JLabel lblDrawFirstBall;
	private JLabel lblDrawSecondBall;
	private JLabel lblDrawThirdBall;
	private JLabel lblDrawFourthBall;
	private JLabel lblDrawFifthBall;
	private JLabel lblDrawSixthBall;
	private JLabel lblFirstB;
	private JLabel lblSecondB;
	private JLabel lblThirdB;
	private JLabel lblFourthB;
	private JLabel lblFifthB;
	private JLabel lblSixthB;
	private JLabel lblPrize;
	private JButton btnSubmit;
	private JTextField txtStoreChosenNumbersA;
	private JTextField txtStoreChosenNumbersB;
	private JButton btnA1;
	private JButton btnA2;
	private JButton btnA3;
	private JButton btnA4;
	private JButton btnA5;
	private JButton btnA6;
	private JButton btnA7;
	private JButton btnA8;
	private JButton btnA9;
	private JButton btnA10;
	private JButton btnA11;
	private JButton btnA12;
	private JButton btnA13;
	private JButton btnA14;
	private JButton btnA15;
	private JButton btnA16;
	private JButton btnA17;
	private JButton btnA18;
	private JButton btnA19;
	private JButton btnA20;
	private JButton btnA21;
	private JButton btnA22;
	private JButton btnA23;
	private JButton btnA24;
	private JButton btnA25;
	private JButton btnA26;
	private JButton btnA27;
	private JButton btnA28;
	private JButton btnA29;
	private JButton btnA30;
	private JButton btnA31;
	private JButton btnA32;
	private JButton btnA33;
	private JButton btnA34;
	private JButton btnA35;
	private JButton btnA36;
	private JButton btnA37;
	private JButton btnA38;
	private JButton btnA39;
	private JButton btnA40;
	private JButton btnA41;
	private JButton btnA42;
	private JButton btnA43;
	private JButton btnA44;
	private JButton btnA45;
	private JButton btnA46;
	private JButton btnA47;
	private JButton btnA48;
	private JButton btnA49;
	private JButton btnA50;
	private JButton btnA51;
	private JButton btnA52;
	private JButton btnA53;
	private JButton btnA54;
	private JButton btnA55;
	private JButton btnA56;
	private JButton btnA57;
	private JButton btnA58;
	private JButton btnB1;
	private JButton btnB2;
	private JButton btnB3;
	private JButton btnB4;
	private JButton btnB5;
	private JButton btnB6;
	private JButton btnB7;
	private JButton btnB8;
	private JButton btnB9;
	private JButton btnB10;
	private JButton btnB11;
	private JButton btnB12;
	private JButton btnB13;
	private JButton btnB14;
	private JButton btnB15;
	private JButton btnB16;
	private JButton btnB17;
	private JButton btnB18;
	private JButton btnB19;
	private JButton btnB20;
	private JButton btnB21;
	private JButton btnB22;
	private JButton btnB23;
	private JButton btnB24;
	private JButton btnB25;
	private JButton btnB26;
	private JButton btnB27;
	private JButton btnB28;
	private JButton btnB29;
	private JButton btnB30;
	private JButton btnB31;
	private JButton btnB32;
	private JButton btnB33;
	private JButton btnB34;
	private JButton btnB35;
	private JButton btnB36;
	private JButton btnB37;
	private JButton btnB38;
	private JButton btnB39;
	private JButton btnB40;
	private JButton btnB41;
	private JButton btnB42;
	private JButton btnB43;
	private JButton btnB44;
	private JButton btnB45;
	private JButton btnB46;
	private JButton btnB47;
	private JButton btnB48;
	private JButton btnB49;
	private JButton btnB50;
	private JButton btnB51;
	private JButton btnB52;
	private JButton btnB53;
	private JButton btnB54;
	private JButton btnB55;
	private JButton btnB56;
	private JButton btnB57;
	private JButton btnB58;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblReceiptGameTitle2;
	private JLabel lblChooseA;
	private JLabel lblChooseB;
	private JLabel lblPrizepool;
	private JLabel lblReceiptA;
	private JLabel lblReceiptB;
	private JLabel lblYourNumbersA;
	private JLabel lblYourNumbersB;
	private JLabel lblReceiptDateOutput;
	private JLabel lblDashedLines1;
	private JLabel lblDashedLines2;
	private JLabel lblReceiptDate;
	private JLabel lblReceiptAgent;
	private JLabel lblReceiptDraw;
	private JLabel lblReceiptDrawOutput;
	private JLabel lblReceiptAgentOutput;
	private JLabel lblReceiptLotto;
	private JLabel lblReceiptGameTitle;
	private JLabel btnPlayAgain;
	private JButton btnReselect;
	private JButton btnUnmute;
	private JButton btnMute;
	private JButton btnHelp;
	private JComboBox<String> cbCategories;
	private JLabel txtDrawTimer;
	private JLabel lblUserChosenNumbers;
	private JButton btnContinue;
	private JLabel lblDispCorrectNumbers;
	private JButton btnRollBall;
	private JLabel lblGameField;
	private JLabel lblGameCategory;
	private JLabel lblGameBackground;
	private JPanel panelGameField2;
	private JLabel lblWN1;
	private JLabel lblWN2;
	private JLabel lblWN3;
	private JLabel lblWN4;
	private JLabel lblWN5;
	private JLabel lblWN6;
	private JLabel lblWinningNumber1;
	private JLabel lblWinningNumber2;
	private JLabel lblWinningNumber3;
	private JLabel lblWinningNumber4;
	private JLabel lblWinningNumber5;
	private JLabel lblWinningNumber6;
	private JLabel lblWinningCombinations;
	private JLabel lblCategoryChosen;
	private JLabel lblCongratulations;
	private JLabel lblJackpotPrize;
	private JLabel lblGamePrize;
	private JLabel lblJackpotWinner;
	private JLabel lblDate2;
	private JLabel lblbgGameField;
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
