import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Baratheon_App {

    static GeneologikoDentro gd;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        runMenu();
    }

    public static void runMenu() {
        int choice;
        scanner = new Scanner(System.in);
        do {
            System.out.println("Please select a function from the menu below");
            System.out.println("1. Load family tree from CSV file");
            System.out.println("2. Find relationship between family members");
            System.out.println("3. Exit");
            System.out.println("Enter your choice:");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                choice = 0;
            }
            switch (choice) {
                case 1:
                    loadCSVTree();
                    break;
                case 2:
                    determineRelationship();
                    break;
            }
        } while (choice != 3);
        scanner.close();

    }

    public static void loadCSVTree() {
        gd = new GeneologikoDentro();
        String file = "src/input.csv";

        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length == 2) {
                    String name = row[0].trim();
                    String gender = row[1].trim();
                    Person p = gd.addPersonToTree(name);
                    p.setGender(gender);

                    System.out.println("Name: " + name + ", Gender: " + gender);
                } else if (row.length == 3) {
                    String name1 = row[0].trim();
                    String role = row[1].trim();
                    String name2 = row[2].trim();

                    Person p1 = gd.addPersonToTree(name1);
                    Person p2 = gd.addPersonToTree(name2);

                    GeneologikoDentro.setRelationship(p1, p2, role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void determineRelationship() {
        String name1, name2;
        Person p1, p2;
        scanner = new Scanner(System.in);

        do {
            System.out.println("Give the name of the first person");
            name1 = scanner.nextLine();
            p1 = gd.getgTree().get(name1);
            if (p1 == null) {
                System.out.println("Person not found. Please enter the name correctly");
            }
        } while (p1 == null);

        do {
            System.out.println("Give the name of the second person");
            name2 = scanner.nextLine();
            p2 = gd.getgTree().get(name2);
            if (p2 == null) {
                System.out.println("Person not found. Please enter the name correctly");
            }
        } while (p2 == null);

        determineAndPrintRelationship(p1, p2);

    }

    private static void determineAndPrintRelationship(Person p1, Person p2) {
        if (gd.isSpouse(p1, p2)) {
            System.out.println(p1.getName() + " is the spouse of " + p2.getName());
        } else if (gd.isFather(p1, p2)) {
            System.out.println(p1.getName() + " is the father of " + p2.getName());
        } else if (gd.isMother(p1, p2)) {
            System.out.println(p1.getName() + " is the mother of " + p2.getName());
        } else if (gd.isSon(p1, p2)) {
            System.out.println(p1.getName() + " is the son of " + p2.getName());
        } else if (gd.isDaughter(p1, p2)) {
            System.out.println(p1.getName() + " is the daughter of " + p2.getName());
        } else if (gd.isBrother(p1, p2)) {
            System.out.println(p1.getName() + " is the brother of " + p2.getName());
        } else if (gd.isSister(p1, p2)) {
            System.out.println(p1.getName() + " is the sister of " + p2.getName());
        } else if (gd.isGrandfather(p1, p2)) {
            System.out.println(p1.getName() + " is the grandfather of " + p2.getName());
        } else if (gd.isGrandmother(p1, p2)) {
            System.out.println(p1.getName() + " is the grandmother of " + p2.getName());
        } else if (gd.isGrandson(p1, p2)) {
            System.out.println(p1.getName() + " is the grandson of " + p2.getName());
        } else if (gd.isGranddaughter(p1, p2)) {
            System.out.println(p1.getName() + " is the granddaughter of " + p2.getName());
        } else if (gd.isCousin(p1, p2)) {
            System.out.println(p1.getName() + " is the cousin of " + p2.getName());
        } else if (gd.isUncle(p1, p2)) {
            System.out.println(p1.getName() + " is the uncle of " + p2.getName());
        } else if (gd.isAunt(p1, p2)) {
            System.out.println(p1.getName() + " is the aunt of " + p2.getName());
        } else if (gd.isNephew(p1, p2)) {
            System.out.println(p1.getName() + " is the nephew of " + p2.getName());
        } else if (gd.isNiece(p1, p2)) {
            System.out.println(p1.getName() + " is the niece of " + p2.getName());
        } else {
            System.out.println("No direct relationship found between " + p1.getName() + " and " + p2.getName());
        }
    }

}
