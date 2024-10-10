import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileOrganizer {
    public static void main(String[] args) {
        // Check if a directory path is provided as a command-line argument
        if (args.length == 0) {
            System.out.println("Please provide a directory path.");
            return;
        }

        String directoryPath = args[0]; // Get the directory path from the argument
        organizeFiles(directoryPath); // Call the method to organize files
    }

    private static void organizeFiles(String directoryPath) {
        File folder = new File(directoryPath);

        // Check if the provided path is a valid directory
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid directory. Please check the path.");
            return;
        }

        // Define file type mappings
        Map<String, String> folderMap = new HashMap<>();
        folderMap.put("txt", "Documents");
        folderMap.put("jpg", "Images");
        folderMap.put("jpeg", "Images");
        folderMap.put("png", "Images");
        folderMap.put("pdf", "Documents");
        folderMap.put("docx", "Documents");
        folderMap.put("mp4", "Videos");
        folderMap.put("mp3", "Music");
        // Add other file types as needed

        // List all files in the directory
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String extension = getFileExtension(file);
                    String targetDirectory = folderMap.get(extension);
                    if (targetDirectory != null) {
                        // Create the target folder if it doesn't exist
                        File targetFolder = new File(folder, targetDirectory);
                        if (!targetFolder.exists()) {
                            targetFolder.mkdirs(); // Create directories
                        }
                        // Move the file to the target folder
                        boolean success = file.renameTo(new File(targetFolder, file.getName()));
                        if (success) {
                            System.out.println("Moved: " + file.getName() + " to " + targetDirectory);
                        } else {
                            System.out.println("Failed to move: " + file.getName());
                        }
                    }
                }
            }
        }
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOfDot = name.lastIndexOf('.');
        if (lastIndexOfDot > 0 && lastIndexOfDot < name.length() - 1) {
            return name.substring(lastIndexOfDot + 1).toLowerCase(); // Return the file extension in lowercase
        }
        return ""; // Return empty string if no extension
    }
}
