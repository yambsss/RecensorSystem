import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    // ArrayList for storing questions
    private static List<String> questions = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== RECENSOR: Study Assistant ===");
            System.out.println("1) Upload File (.txt or .pdf)");
            System.out.println("2) View Generated Questions");
            System.out.println("3) Export Reviewer to Text");
            System.out.println("4) Exit");
            System.out.print("Select option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> uploadFile();
                case 2 -> viewQuestions();
                case 3 -> exportReviewer();
                case 4 -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void uploadFile() {
        System.out.print("Enter file path: ");
        String path = sc.nextLine();

        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        try {
            String text = readFile(file);
            generateQuestions(text);
            System.out.println("✅ File processed successfully!");
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static String readFile(File file) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line).append(" ");
        }
        return sb.toString();
    }

    private static void generateQuestions(String text) {
        questions.clear();
        String[] sentences = text.split("\\. ");
        for (String s : sentences) {
            s = s.trim();
            if (s.length() > 10) {
                questions.add("Explain: " + s);
            }
        }
    }

    private static void viewQuestions() {
        if (questions.isEmpty()) {
            System.out.println("No questions generated yet.");
            return;
        }
        System.out.println("\nGenerated Review Questions:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i));
        }
    }

    private static void exportReviewer() {
        if (questions.isEmpty()) {
            System.out.println("No data to export.");
            return;
        }
        try {
            String fileName = "reviewer_" + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
                pw.println("=== RECENSOR REVIEWER ===");
                for (String q : questions) pw.println(q);
            }
            System.out.println("✅ Reviewer saved as " + fileName);
        } catch (IOException e) {
            System.out.println("Error exporting: " + e.getMessage());
        }
    }
}
