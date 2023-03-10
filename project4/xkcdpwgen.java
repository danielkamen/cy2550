

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class xkcdpwgen{
  public static void main(String[] args) throws Exception {
    // initalizes the random num seed
    Random randomNum = new Random();

    // SHOULD represent the local path of words.txt
    // C:\\Users\\Danth\\IdeaProjects\\CS3500\\Assignment1-2-3\\src\\cy2550\\words.txt
    // 
    File wordsList = new File("./words.txt");

    // reads the file line by like
    BufferedReader br
            = new BufferedReader(new FileReader(wordsList));

    String st;
    ArrayList<String> wordListMaster = new ArrayList<>();

    while ((st = br.readLine()) != null) {
      wordListMaster.add(st);
    }

    int wordListSize = wordListMaster.size();

    // the default password generating parameters
    int words = 4;
    int caps = 0;
    int symbols = 0;
    int numbers = 0;

    // given bad inputs
    for (int i = 0; i < args.length; i++) {
      if (args[i + 1] == null) {
        break;
      }

      switch (args[i]) {

        // determins how many words at made.
        case "-w":
        case "--words":
          if (Character.isDigit(args[i+1].charAt(0))) {
            // gets next input and then updates words
            words = Integer.parseInt(args[i+1]);
          }
          i++;
          break;
        // determins how many words at capitlize 9first letter only)
        case "-c":
        case "--caps":
          if (Character.isDigit(args[i+1].charAt(0))) {
            caps = Integer.parseInt(args[i+1]);
          }
          i++;
          break;
        // how many special symbols are added , only in beginning, end, or between words. This
        // implementation uses ascii values 33-47.
        case "-s":
        case "--symbols":
          if (Character.isDigit(args[i+1].charAt(0))) {
            symbols = Integer.parseInt(args[i+1]);
          }
          i++;
          break;
        // how many numbers are added , only in beginning, end, or between words
        case "-n":
        case "--numbers":
          if (Character.isDigit(args[i+1].charAt(0))) {
            numbers = Integer.parseInt(args[i+1]);
          }
          i++;
          break;
        default:
          break;
      }

    }

    // collects the length of each word in password
    int[] wordLengthArr = new int[words];
    // builds password
    StringBuilder password = new StringBuilder();
    // temp since caps is deleted
    int tempCaps = caps;


    // makes a password with words number of words
    // String newWord = wordListMaster.get(ranNumInList).substring(0, 1).toUpperCase();
    // makes a password with words number of words
    if (caps > words) {
      caps = words;
    }
    for (int w = 0; w < words; w++) {
      int ranNumInList = randomNum.nextInt(wordListSize);

      int randomCapOrNot = (int) Math.round(Math.random());

      String newWord = wordListMaster.get(ranNumInList);
      wordLengthArr[w] = newWord.length();
      if (caps == words - w) {
        newWord = newWord.substring(0, 1).toUpperCase() + newWord.substring(1);
        password.append(newWord);
        caps--;
      } else if (randomCapOrNot == 1 && caps > 0) {
        newWord = newWord.substring(0, 1).toUpperCase() + newWord.substring(1);
        password.append(newWord);
        caps--;
      }
      else {
        password.append(newWord);
      }
    }

    // will store count of positions where char and num added
    // ex is: appleBottomJeansBoots
    // numAddSumbolAddedPositio = [0, 0, 0, 0, 0]
    // when a symbol is added in bwteeen second and third word, &, 123appleBottom&JeansBoots
    // numAddSumbolAddedPositio = [3, 0, 1, 0, 0]

    int[] numAndSymbolsAddedPosition = new int[words + 1];

    while (numbers + symbols > 0) {
      // Replaces some letters with symbols and numbers if there are an
      int randSpot = (int) (Math.random() * (words + 1));
      int randNum = (int) (Math.random() * 10);
      int randSym = (int) (Math.random() * (47-33) + 33);

      // iterates over two arrays to get the position new character will be added at.
      int position = 0;
      for (int i = 0; i < randSpot; i++) {
        position = position + wordLengthArr[i] +  numAndSymbolsAddedPosition[i];
      }

      if (numbers > 0) {
        password.insert(position, String.valueOf(randNum));
        numAndSymbolsAddedPosition[randSpot]++;
        numbers--;
      } else {
        password.insert(position, new Character((char)
                randSym).toString());
        numAndSymbolsAddedPosition[randSpot]++;
        symbols--;
      }
    }
    System.out.print(password);
    System.out.print(System.lineSeparator());
 System.out.print(System.lineSeparator());
	
  }
}
