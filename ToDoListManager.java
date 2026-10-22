import java.util.LinkedList;
import java.util.Scanner;

public class ToDoListManager {
    public static void main(String[] args) {
        var tasks = new LinkedList<String>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("To-Do Task Manager Commands: Add, Remove, List, Done, and Quit");
        
        while (running) {
            System.out.println("\nEnter Command:");
            String command = scanner.nextLine();

            switch(command) {
                case "Add":
                System.out.print("Add Task:");
                String newTasks = scanner.nextLine();
                tasks.add(newTasks);
                System.out.println("Tasks Added.");
                break;

                case "List":
                if(tasks.isEmpty()) {
                    System.out.println("Your Task List is Empty.");
                } else {
                    System.out.println("Your Task List:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                }
                break;

                case "Remove":
                if (tasks.isEmpty()) {
                    System.out.println("Your Task List is Empty.");
                } else {
                System.out.println("Enter Task Number to Remove:");
                

                    try {
                        int indexToRemove = Integer.parseInt(scanner.nextLine()) - 1;
                        if (indexToRemove >= 0 && indexToRemove < tasks.size()) {
                            String removed = tasks.remove(indexToRemove);
                            System.out.println("Removed:" + removed);
                        } else {
                            System.out.println("Invalid Task Number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please Enter a Valid Task Number.");
                    }
                }
                break;

                case "Done":
                if (tasks.isEmpty()) {
                    System.out.println("Your Taks List is Empty.");
                } else {
                    System.out.println("Enter Task Number to Mark as Done:");
                    if (scanner.hasNextInt()) {
                        int indexDone = scanner.nextInt() - 1;
                        scanner.nextLine();

                    if (indexDone >= 0 && indexDone < tasks.size()) {
                        String completed = tasks.remove(indexDone);
                        System.out.println("Task Marked as Done and Removed from List.");
                    } else {
                        System.out.println("Invalid Task Number.");
                    }
                    } else {
                        System.out.println("Please Enter a Valid Task Number.");
                        scanner.nextLine();
                    }
                }
                break;

                case "Quit":
                    running = false;
                    System.out.println("Exiting Application.");
                break;

                default:
                    System.out.println("Unknown Command.");
                break;
            }
        }
        scanner.close();
    }
}
