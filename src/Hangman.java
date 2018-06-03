/*
 * this program are made by 
 * Linxuan Huang (ID lxh0412)
 * and
 * Yibo Yan (ID qyan) 
 */

import acm.program.*;
import acm.util.*;
import java.io.*;    // for File
import java.util.*;  // for Scanner

public class Hangman extends HangmanProgram {
	
	public void run() {
		intro();		//print out the introductory information
		String filename = gainFilename();
		playMultipleGames(filename);		//play multiple Hangman
	}
	
	/*
	 * gain the dictionary filename users want to input
	 * and return the filename as String
	 */
	private String gainFilename() {
		String filename = promptUserForFile("Dictionary filename?", "res/");		//gain the filename
		return filename;
	}
	
	/*
	 * play multiple games
	 * 
	 * run the game at first
	 * count necessary information
	 * rerun the game again and again
	 * recount necessary information again and again
	 * print out statics
	 */
	private void playMultipleGames(String filename) {
		int playTime = 1;		//enter in initial time of play
		int winTime = 0;		//enter in initial time of win
		String randomWord = getRandomWord(filename);		//generate random word
		int guessCount = playOneGame(randomWord);		//play the game and return the guess each turn left
		int bestGuess = guessCount;		//set initial best guess as the guess left at the first time
		if (guessCount > 0) {		//count times of win
			winTime++;
		}
		while (readBoolean("Play again (Y/N)?", "Y", "N")) {		//ask whether run the game again
			canvas.clear();
			randomWord = getRandomWord(filename);		
			guessCount = playOneGame(randomWord);
			if (guessCount > 0) {
				winTime++;
			}
			if (bestGuess < guessCount) {		//switch the new best guess number
				bestGuess = guessCount;
			}
			playTime++;		//count the play times
		}
		stats(playTime, winTime, bestGuess);		//print out overall statics
	}
	
	/* print out some basic instruction
	 * let users know the rule of game
	 * (non-Javadoc)
	 * @see HangmanInterface#intro()
	 */
	private void intro() {
		println("CS 106A Hangman!");
		println("I will think of a random word.");
		println("You'll try to guess its letters.");
		println("Every time you guess a letter");
		println("that isn't in my word, a new body");
		println("part of the hanging man appears.");
		println("Guess correctly to avoid the gallows!");
		println();
	}
	
	/*
	 * play single game by 5 steps
	 * print out info
	 * display the picture
	 * check the guessed letter
	 * print out info about whether you win
	 * return the left guess
	 */
	private int playOneGame(String secretWord) {
		String hint = generatedhint(secretWord);		//generate the first hint
		String guessedLetters = "";		//store the letters that have been guessed 
		int guessCount = 8;		//create the initial value of guess left
		displayHangman(guessCount);		//display the matched picture of little hangman
		while ( hint.equals(secretWord) == false && guessCount != 0) {		//when users haven't guess out the word and haven't run out of chance
			println();
			println("Secret word : " + hint);
			println("Your guesses: " + guessedLetters);
			println("Guesses left: " + guessCount);
			char newGuessLetter = readGuess(guessedLetters);		//store the new guessed letter as char
			String newGuess = "" + newGuessLetter;		//convert the new guessed letter to string
			
			guessCount = printEveryStepInfo(secretWord, newGuess, guessCount);		//tell users whether they guess the right letter
			
			canvas.clear();		//clear the previous picture
			displayHangman(guessCount);		//display the new picture
			guessedLetters += newGuessLetter; 		//add new guessed character to guessed letters string  
			hint = createHint(secretWord, guessedLetters);		//create the new hint
		}
		printFinalInfo(hint, secretWord);		//print info tell user whether they have won the game
		return guessCount;
	}
	
	/*
	 * tell users whether they guess the right letter
	 * and return the number of guess left
	 */
	private int printEveryStepInfo(String secretWord, String newGuess, int guessCount) {
		if (secretWord.contains(newGuess)) {		//when guessed letter is one character of secret word
			println("Correct!");
		} else {
			println("Incorrect.");		//when guessed letter is not
			guessCount--;		//refresh the left guess time
			return guessCount;
		}
		return guessCount;
	}
	
	/*
	 * generate the first hint
	 * and return a series of dash
	 * and the number of dash
	 * just as same as
	 * the number of letters of secret word
	 */
	private String generatedhint (String secretWord) {
		int numbersOfSecretWord = secretWord.length();		//count how many dash should we generate
		String hint = "";
		char dash = '-';
		for (int i = 0; i < numbersOfSecretWord; i++) {		//generate the first hint made up of dashes
			hint += dash;
		}
		
		return hint;		//return string hint
	}
	
	/*
	 * according to the length of secret word
	 * generate a hint that switch dash by guessed letter
	 * and return the hint as type of String
	 */
	private String createHint(String secretWord, String guessedLetters) {
		int lengthOfSecretWord = secretWord.length();		//gain the length of secret word
		String hint = "";
		for (int i = 0; i < lengthOfSecretWord; i++) {		//generate string with dash and guessed letter
			char ch = secretWord.charAt(i);		//pull out every single character in secret word one by one
			String checkWord = "" + ch;		//convert every single character into String
			if (guessedLetters.contains(checkWord)) {		//if one character has been guessed
				hint += ch;		//add this character into hint String
			} else {		//if not
				hint += '-'; 		//add dash into hint string
			} 
		}
		
		return hint;
	}
	
	/*
	 * print out related information
	 * whether users have guessed the right letter
	 */
	private void printFinalInfo(String hint, String secretWord) {
		if (hint.equals(secretWord)) {
			println("You win! My word was " + "\"" + secretWord +"\".");
		} else {
			println("You lose! My word was " + "\"" + secretWord + "\".");
		}
	}
	
	/*
	 * read the letter users entered in
	 * and check whether users have guessed this letter
	 * and check whether users have entered the illegal information
	 * give instruction to users
	 * until read the legal and non-duplicated letter
	 * return read result to readGuess method as a type of char 
	 */
	private char readGuess(String guessedLetters) {
		String newGuess = readLine("Your guess?");		//read new guess from users
		String newGuessUpCase = newGuess.toUpperCase();		//turn guess into upper case to be case insensitive
		int numbersOfChar = newGuessUpCase.length();		//gain the length of users' enter information
		
		//avoid crash when users enter nothing
		while (numbersOfChar == 0) {
			println("Type a single letter from A-Z.");
			newGuess = readLine("Your guess?");
			newGuessUpCase = newGuess.toUpperCase();
			numbersOfChar = newGuessUpCase.length();
		}
		
		numbersOfChar = newGuessUpCase.length();
		char enteredChar = newGuessUpCase.charAt(0);		//convert user's information from String to Char
		boolean overlap = guessedLetters.contains(newGuessUpCase);		//when users have guessed this letter
		boolean needInfo1 = numbersOfChar > 1;		//when users don't enter a single word
		boolean needInfo2 = Character.isLetter(enteredChar) == false;		//when users don't enter letter
		while (overlap || needInfo1 || needInfo2) {
			if (overlap) {
				println("You already guessed that letter.");
			} else if (needInfo1 || needInfo2) {
				println("Type a single letter from A-Z.");
			} 
			newGuess = readLine("Your guess?");		//prompt users enter the new legal letter
			newGuessUpCase = newGuess.toUpperCase();		//to be case insensitive
			numbersOfChar = newGuessUpCase.length();		//gain the length of users' enter information
			
			//avoid crash when users enter nothing
			while (numbersOfChar == 0) {
				println("Type a single letter from A-Z.");
				newGuess = readLine("Your guess?");
				newGuessUpCase = newGuess.toUpperCase();
				numbersOfChar = newGuessUpCase.length();
			}
			
			enteredChar = newGuessUpCase.charAt(0);
			overlap = guessedLetters.contains(newGuessUpCase);
			needInfo1 = numbersOfChar > 1;
			needInfo2 = Character.isLetter(enteredChar) == false;
		}
		return newGuessUpCase.charAt(0);		//return the new guessed character.
	}
	
	/*
	 * display the related picture
	 * when users guess the wrong letter
	 */
	private void displayHangman(int guessCount) {
		try {
			int fileNumber = guessCount;		//gain the matched file number
			Scanner input = new Scanner(new File("res/display" + fileNumber + ".txt"));		//input the matched picture
			while (input.hasNextLine()) {
			String line = input.nextLine();
			canvas.println(line);
			}
			input.close();
		} catch (FileNotFoundException ex) {
			println("File not found.");
		}
	}
	
	/*
	 * calculate overall statistic
	 * and print out
	 */
	private void stats(int gamesCount, int gamesWon, int best) {
		println();
		double winPercent = (double)gamesWon*100/gamesCount;		//calculate the number of percentage
		println("Overall statistics:");
		println("Games played: " + gamesCount);
		println("Games won: " + gamesWon);
		println("Win percent: " + winPercent + "%");
		println("Best game: " + best + " guess(es) remaining");
		println("Thanks for playing!");
	}
	
	/*
	 * generate the random word
	 * and return it as type of String
	 */
	private String getRandomWord(String filename) {
		String selectedWord = "";
		try {
			Scanner input = new Scanner(new File(filename));
			int numberOfWords = input.nextInt();		//gain the number of words in the certain txt documentation
			int i = RandomGenerator.getInstance().nextInt(1, numberOfWords);		//generate the random number
			for	(int j = 0; j < i; j++) {		//select the random secret word according to random number between first word and last word
				selectedWord = input.next();
			}
			input.close();
		} catch (FileNotFoundException ex) {
			println("File not found.");
		}
		
		return selectedWord;
	}
}