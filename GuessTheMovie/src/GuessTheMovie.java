import java.util.Scanner;
import java.io.File;

class Game{
    private int lettersCount;
    private String movie;
    Game(){
        this.movie = movie;
    }

    public void setLettersCount(String x) {
        this.lettersCount = x.length();
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getLettersCount() {
        return lettersCount;
    }

    public String getMovie() {
        return movie;
    }
}

public class GuessTheMovie {
    public static void main(String[] args) throws Exception {
        File file = new File("movies.txt");
        Scanner moviesListCount = new Scanner(file);
        int linesCount=0; int n=0;
        while(moviesListCount.hasNextLine()){
            String line = moviesListCount.nextLine();
            linesCount++;
        }
        String[] movies = new String[linesCount];
        Scanner moviesList = new Scanner(file);
        while(moviesList.hasNextLine()){
            String line = moviesList.nextLine();
            movies[n]=line;
            n++;
        }
        int rNumber = (int) (Math.random()*linesCount);

        System.out.println("Guess the movie title!");
        Game movie = new Game();
        movie.setMovie(movies[rNumber]);
        String guessStat = movie.getMovie();
        movie.setLettersCount(guessStat);
        System.out.println(guessStat.toString());


        for(int i=0;i<movie.getLettersCount();i++){
            if(movie.getMovie().charAt(i) == ' '){
                StringBuilder str = new StringBuilder(guessStat);
                str.setCharAt(i, ' ');
                guessStat = str.toString();
            } else {
                StringBuilder str = new StringBuilder(guessStat);
                str.setCharAt(i, '_');
                guessStat = str.toString();
            }
        }
        boolean hasWon = false;
        int tries = 0;

        while(!(hasWon) && tries<10){
            System.out.print("You are guessing movie that name is: ");
            Scanner scanner2 = new Scanner(guessStat);
            System.out.print(guessStat.toString());
            System.out.print("\nYou have guessed (" + tries + ") wrong letters.");
            System.out.print("\nGuess a letter: ");
            Scanner scanner = new Scanner(System.in);
            char guess = scanner.next().charAt(0);
            int matches = 0;

            for(int i=0;i<movie.getLettersCount();i++){
                char letter = movie.getMovie().charAt(i);
                if(guess == letter){
                    StringBuilder str = new StringBuilder(guessStat);
                    str.setCharAt(i, letter);
                    guessStat = str.toString();
                    matches++;
                }
            }
            if(matches==0){
                tries++;}
            System.out.println("*********************************");
            if(guessStat.equals(movie.getMovie())){
                hasWon = true;
                System.out.println("YOU GUESSED THE TITLE OF THE MOVIE!!! THE TITLE: " + guessStat.toString());
                break;
            } else if(tries==10){
                System.out.println("YOU LOST! TRY AGAIN!");
                break;
            }
        }
    }
}
