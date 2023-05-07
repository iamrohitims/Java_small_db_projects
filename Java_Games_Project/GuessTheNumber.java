import java.util.Scanner;
import java.util.Random;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        int num = rand.nextInt(100) + 1;
        int guess, tries = 0;

        System.out.println("Welcome to Guess the Number!");
        System.out.println("I'm thinking of a number between 1 and 100...");

        do {
            System.out.print("Guess the number: ");
            guess = input.nextInt();
            tries++;

            if (guess < num) {
                System.out.println("Too low, try again.");
            } else if (guess > num) {
                System.out.println("Too high, try again.");
            }
        } while (guess != num);

        System.out.println("Congratulations! You guessed the number in " + tries + " tries!");
    }
}
