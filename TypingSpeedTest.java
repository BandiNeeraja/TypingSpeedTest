import java.util.Random;
import java.util.Scanner;

public class TypingSpeedTest {

    private static final String[] keywords = { "the", "a", "is", "and", "or", "but", "not", "in", "on", "at", "it",
            "to", "of", "as" };
    private static final String[] easyOneLineWords = { "cat", "dog", "sun", "run", "eat", "sky", "big", "red", "hot",
            "cold" };
    private static final String[] mediumOneLineWords = { "sleep", "walk", "talk", "think", "read", "write", "learn",
            "grow", "change", "create" };
    private static final String[] hardOneLineWords = { "believe", "achieve", "receive", "conceive", "perceive",
            "describe", "imagine", "explore", "discover", "analyze" };

    private static final String[] easySentences = {
            "The cat sat on the mat.", "The dog ran fast.", "The sun is hot.", "I like to eat.", "The sky is blue."
    };
    private static final String[] mediumSentences = {
            "The quick brown fox jumps over the lazy dog.", "Sphinx of black quartz, judge my vow.",
            "Pack my box with five dozen liquor jugs.", "How vexingly quick daft zebras jump.",
            "Jived fox nymph grabs quick waltz."
    };
    private static final String[] hardSentences = {
            "The complex algorithms of the quantum computer defied classical understanding.",
            "The philosophical implications of artificial intelligence remain a subject of ongoing debate.",
            "The intricate tapestry of human history is woven with threads of triumph and tragedy.",
            "The enigmatic beauty of the cosmos inspires awe and wonder in the hearts of humankind.",
            "The relentless pursuit of knowledge has propelled humanity to the pinnacle of scientific achievement."
    };

    private static final int KEYWORD_TEST_COUNT = 5;
    private static final int ONE_LINE_WORD_TEST_COUNT = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to the Typing Speed Test!");

        boolean playAgain = true;
        while (playAgain) {
            int choice = getDifficulty(scanner);

            switch (choice) {
                case 1:
                    runTest(scanner, random, keywords, KEYWORD_TEST_COUNT, easyOneLineWords, easySentences);
                    break;
                case 2:
                    runTest(scanner, random, mediumOneLineWords, ONE_LINE_WORD_TEST_COUNT, mediumOneLineWords,
                            mediumSentences);
                    break;
                case 3:
                    runTest(scanner, random, hardOneLineWords, ONE_LINE_WORD_TEST_COUNT, hardOneLineWords,
                            hardSentences);
                    break;
            }

            playAgain = playAgain(scanner);
        }

        System.out.println("Thank you for playing!");
        scanner.close();
    }

    private static int getDifficulty(Scanner scanner) {
        System.out.println("\nChoose Difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");

        return getValidIntInput(scanner, 1, 3);
    }

    private static int getValidIntInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ":");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number:");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static void runTest(Scanner scanner, Random random, String[] wordList, int testCount, String[] oneLineWords,
            String[] sentences) {
        System.out.println("\nKeyword Test:");
        runWordTest(scanner, random, wordList, testCount);

        System.out.println("\nOne-Line Word Test:");
        runWordTest(scanner, random, oneLineWords, ONE_LINE_WORD_TEST_COUNT);

        System.out.println("\nSentence Test:");
        String sentence = sentences[random.nextInt(sentences.length)];
        runSentenceTest(scanner, sentence);
    }

    private static void runWordTest(Scanner scanner, Random random, String[] wordList, int testCount) {
        for (int i = 0; i < testCount; i++) {
            String word = wordList[random.nextInt(wordList.length)];
            System.out.println("Type: " + word);
            runSingleWordTest(scanner, word);
            System.out.println();
        }
    }

    private static void runSingleWordTest(Scanner scanner, String word) {
        long startTime = System.currentTimeMillis();
        String userInput = scanner.nextLine();
        long endTime = System.currentTimeMillis();

        double timeTaken = (endTime - startTime) / 60000.0;
        int wpm = (int) (word.length() / timeTaken);

        int errors = calculateErrors(word, userInput);
        double accuracy = calculateAccuracy(word, errors);

        displayResults(wpm, accuracy, errors);
    }

    private static void runSentenceTest(Scanner scanner, String sentence) {
        System.out.println("Type the following sentence:");
        System.out.println(sentence);

        long startTime = System.currentTimeMillis();
        String userInput = scanner.nextLine();
        long endTime = System.currentTimeMillis();

        double timeTaken = (endTime - startTime) / 60000.0;
        int wordsTyped = userInput.split("\\s+").length;
        int wpm = (int) (wordsTyped / timeTaken);

        int errors = calculateErrors(sentence, userInput);
        double accuracy = calculateAccuracy(sentence, errors);

        displayResults(wpm, accuracy, errors);
    }

    private static void displayResults(int wpm, double accuracy, int errors) {
        System.out.println("\nResults:");
        System.out.println("Words per minute: " + wpm);
        System.out.println("Accuracy: " + String.format("%.2f", accuracy) + "%");
        if (errors > 0) {
            System.out.println("Errors: " + errors);
        }
    }

    private static int calculateErrors(String original, String user) {
        int errors = 0;
        String[] originalWords = original.split("\\s+");
        String[] userWords = user.split("\\s+");

        int minLength = Math.min(originalWords.length, userWords.length);

        for (int i = 0; i < minLength; i++) {
            if (!originalWords[i].equals(userWords[i])) {
                errors++;
            }
        }
        errors += Math.abs(originalWords.length - userWords.length);

        return errors;
    }

    private static double calculateAccuracy(String sentence, int errors) {
        int totalChars = sentence.length();
        if (totalChars == 0) {
            return 0;
        }
        return ((double) (totalChars - errors) / totalChars) * 100;
    }

    private static boolean playAgain(Scanner scanner) {
        System.out.print("Do you want to play again? (yes/no): ");
        String playChoice = scanner.nextLine().toLowerCase();
        return playChoice.equals("yes");
    }
}