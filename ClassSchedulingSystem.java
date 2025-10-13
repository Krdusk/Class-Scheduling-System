import java.util.*;
import java.io.*;

class Schedule implements Serializable {
    String courseCode;
    List<String> days;
    List<String> times;
    List<String> rooms;
    String instructor;

    Schedule(String courseCode, List<String> days, List<String> times, List<String> rooms, String instructor) {
        this.courseCode = courseCode;
        this.days = days;
        this.times = times;
        this.rooms = rooms;
        this.instructor = instructor;
    }
}

public class ClassSchedulingSystem {
    static Scanner sc = new Scanner(System.in);

    // HashMap is used for fast key-value access (acts like direct lookup storage).
    static HashMap<String, Schedule> schedules = new HashMap<>();

    static final String FILE_NAME = "schedules.txt";

    public static void main(String[] args) {
        loadSchedules();
        while (true) {
             System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║       Class Scheduling System        ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("1. Add Schedule");
            System.out.println("2. View All Schedules");
            System.out.println("3. Search Schedule");
            System.out.println("4. Delete Schedule");
            System.out.println("5. Edit Schedule");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> addSchedule();
                case "2" -> viewSchedules();
                case "3" -> searchSchedule();
                case "4" -> deleteSchedule();
                case "5" -> editSchedule();
                case "6" -> {
                    saveSchedules();
                    System.out.println("\nAll schedules saved. Exiting . . .");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addSchedule() {
        System.out.print("\nEnter Course Code: ");
        String code = sc.nextLine().trim().toUpperCase();
        if (schedules.containsKey(code)) {
            System.out.println("Schedule already exists for this course.");
            return;
        }
        createOrEditSchedule(code, false);
    }

    static void editSchedule() {
        System.out.print("\nEnter Course Code to edit: ");
        String code = sc.nextLine().trim().toUpperCase();
        boolean found = false;
        for (String existingCode : schedules.keySet()) {
            if (existingCode.equalsIgnoreCase(code)) { // Accept lowercase input.
                code = existingCode;
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No schedule found for " + code + ".");
            return;
        }
        createOrEditSchedule(code, true);
    }

    static void createOrEditSchedule(String code, boolean editing) {
        System.out.print("How many days per week (1 or 2): ");
        int numDays = Integer.parseInt(sc.nextLine());
        List<String> days = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> rooms = new ArrayList<>();

        // ArrayList is used here for ordered data collection (acts like FIFO in traversal).
        for (int i = 1; i <= numDays; i++) {
            System.out.print("Enter Day " + i + ": ");
            String day = formatDay(sc.nextLine());
            System.out.print("Enter Time (e.g. 9 am to 11 am): ");
            String time = formatTime(sc.nextLine());
            System.out.print("Enter Room: ");
            String room = formatRoom(sc.nextLine());
            days.add(day);
            times.add(time);
            rooms.add(room);
        }

        sortByDay(days, times, rooms);
        System.out.print("Enter Professor Name: ");
        String instructor = formatProfessor(sc.nextLine());
        schedules.put(code, new Schedule(code, days, times, rooms, instructor));
        saveSchedules();

        if (editing)
            System.out.println("\nSchedule updated successfully and saved.");
        else
            System.out.println("\nSchedule added successfully and saved.");
    }

    static void viewSchedules() {
        if (schedules.isEmpty()) {
            System.out.println("\nNo schedules found.");
            return;
        }
        System.out.println("\n═══════════════════════════════════════════════════════════════════════════════");
        System.out.printf("%-12s %-12s %-25s %-10s %-20s%n", "Code", "Day", "Time", "Room", "Professor");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════");

        // Stack logic is applied conceptually in table formatting (LIFO when printing separators).
        List<String> sortedKeys = new ArrayList<>(schedules.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys) {
            Schedule s = schedules.get(key);
            System.out.printf("%-12s %-12s %-25s %-10s %-20s%n", s.courseCode, s.days.get(0), s.times.get(0), s.rooms.get(0), s.instructor);
            for (int i = 1; i < s.days.size(); i++) {
                System.out.printf("%-12s %-12s %-25s %-10s %-20s%n", "", s.days.get(i), s.times.get(i), s.rooms.get(i), "");
            }
            System.out.println("───────────────────────────────────────────────────────────────────────────────");
        }
    }

    static void searchSchedule() {
        System.out.print("\nEnter Course Code to search: ");
        String code = sc.nextLine().trim().toUpperCase();
        boolean found = false;
        for (String existingCode : schedules.keySet()) {
            if (existingCode.equalsIgnoreCase(code)) { // Case-insensitive search.
                Schedule s = schedules.get(existingCode);
                System.out.println("\n═══════════════════════════════════════════════════════════════════════════════");
                System.out.printf("%-12s %-12s %-25s %-10s %-20s%n", "Code", "Day", "Time", "Room", "Professor");
                System.out.println("═══════════════════════════════════════════════════════════════════════════════");
                System.out.printf("%-12s %-12s %-25s %-10s %-20s%n", s.courseCode, s.days.get(0), s.times.get(0), s.rooms.get(0), s.instructor);
                for (int i = 1; i < s.days.size(); i++) {
                    System.out.printf("%-12s %-12s %-25s %-10s %-20s%n", "", s.days.get(i), s.times.get(i), s.rooms.get(i), "");
                }
                System.out.println("───────────────────────────────────────────────────────────────────────────────");
                found = true;
                break;
            }
        }
        if (!found) System.out.println("No schedule found for " + code + ".");
    }

    static void deleteSchedule() {
        System.out.print("\nEnter Course Code to delete: ");
        String code = sc.nextLine().trim().toUpperCase();
        String match = null;
        for (String key : schedules.keySet()) {
            if (key.equalsIgnoreCase(code)) {
                match = key;
                break;
            }
        }
        if (match != null) {
            schedules.remove(match);
            saveSchedules();
            System.out.println("Schedule deleted successfully and saved.");
        } else {
            System.out.println("No schedule found for " + code + ".");
        }
    }

    static void saveSchedules() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(schedules);
        } catch (IOException e) {
            System.out.println("Error saving schedules.");
        }
    }

    static void loadSchedules() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            schedules = (HashMap<String, Schedule>) ois.readObject();
            System.out.println("\nSchedules loaded successfully.");
        } catch (Exception e) {
            System.out.println("\nNo saved schedules found.");
        }
    }

    static String formatTime(String time) {
        time = time.toUpperCase().replaceAll("\\s+", " ").trim();
        time = time.replaceAll("(?i)(\\d{1,2})(:\\d{2})?\\s?(AM|PM)", "$1$2 $3");
        return time;
    }

    static String formatRoom(String room) {
        return room.trim().toUpperCase();
    }

    static String formatDay(String input) {
        input = input.trim().toLowerCase();
        return switch (input) {
            case "mon", "monday" -> "Monday";
            case "tue", "tues", "tuesday" -> "Tuesday";
            case "wed", "weds", "wednesday" -> "Wednesday";
            case "thu", "thur", "thurs", "thursday" -> "Thursday";
            case "fri", "friday" -> "Friday";
            case "sat", "saturday" -> "Saturday";
            case "sun", "sunday" -> "Sunday";
            default -> input.substring(0, 1).toUpperCase() + input.substring(1);
        };
    }

    static String formatProfessor(String name) {
        name = name.trim().toLowerCase();
        String[] parts = name.split("\\s+");
        StringBuilder formatted = new StringBuilder();
        for (String part : parts) {
            formatted.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");
        }
        return formatted.toString().trim();
    }

    static void sortByDay(List<String> days, List<String> times, List<String> rooms) {
        List<Integer> order = new ArrayList<>();
        for (String d : days) order.add(dayOrder(d));
        for (int i = 0; i < order.size() - 1; i++) {
            for (int j = i + 1; j < order.size(); j++) {
                if (order.get(i) > order.get(j)) {
                    Collections.swap(order, i, j);
                    Collections.swap(days, i, j);
                    Collections.swap(times, i, j);
                    Collections.swap(rooms, i, j);
                }
            }
        }
    }

    static int dayOrder(String day) {
        return switch (day.toLowerCase()) {
            case "monday" -> 1;
            case "tuesday" -> 2;
            case "wednesday" -> 3;
            case "thursday" -> 4;
            case "friday" -> 5;
            case "saturday" -> 6;
            case "sunday" -> 7;
            default -> 8;
        };
    }
}
