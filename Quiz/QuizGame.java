import java.io.*;
import java.util.*;

class QuizGame {
    static class Question {
        String question;
        String[] options;
        int correctOption;

        Question(String question, String[] options, int correctOption) {
            this.question = question;
            this.options = options;
            this.correctOption = correctOption;
        }
    }

    static List<Question> loadQuestions(String filePath) throws IOException {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 5); 
                if (parts.length == 5) {
                    String questionText = parts[0];
                    String[] options = Arrays.copyOfRange(parts, 1, 4);
                    int correctOption = Integer.parseInt(parts[4]);
                    questions.add(new Question(questionText, options, correctOption));
                }
            }
        }
        return questions;
    }

    static void playQuiz(List<Question> questions) {
        Scanner scanner = new Scanner(System.in);
        Collections.shuffle(questions); 
        int score = 0;

        System.out.println("\nWelcome to the Quiz Game! Answer the questions below:");

        for (int i = 0; i < Math.min(5, questions.size()); i++) { 
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + q.question);
            for (int j = 0; j < q.options.length; j++) {
                System.out.println((j + 1) + ". " + q.options[j]);
            }

            System.out.print("Your answer (1-3): ");
            int answer;
            try {
                answer = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Skipping question.");
                scanner.next(); 
                continue;
            }

            if (answer == q.correctOption) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + q.correctOption);
            }
        }

        System.out.println("\nQuiz Finished! Your final score is: " + score);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Quiz Game!");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("\nHello, " + name + "! Loading questions...");

        String filePath = "questions.txt"; 
        try {
            List<Question> questions = loadQuestions(filePath);
            if (questions.isEmpty()) {
                System.out.println("No questions found in the file.");
                return;
            }

            playQuiz(questions);
        } catch (IOException e) {
            System.out.println("Error loading questions: " + e.getMessage());
        }
    }
}
