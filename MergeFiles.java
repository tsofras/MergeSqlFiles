import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class MergeFiles {

	static final Map<Double, String> orderedFoldersMap = new TreeMap<Double, String>();

	public static void main(String[] args) throws IOException, InterruptedException {
		String mainFolderPath = "C:\\work\\SqlResources\\resources\\PandC\\core4_migration";
		String mainDataFile = "C:\\work\\SqlResources\\resources\\PandC\\core4_migration\\all_data.sql";
		String mainSchemaFile = "C:\\work\\SqlResources\\resources\\PandC\\core4_migration\\all_schema.sql";
		File folder = new File(mainFolderPath);
		File newDataFile = new File(mainDataFile);
		newDataFile.createNewFile();
		File newSchemaFile = new File(mainSchemaFile);
		newSchemaFile.createNewFile();
		FileWriter newDataWriter = new FileWriter(newDataFile, true);
		FileWriter newSchemaWriter = new FileWriter(newSchemaFile, true);

		putFoldersinOrderedMap(folder);

		System.out.println("------------------------------------------");
		for (Double key : orderedFoldersMap.keySet()) {
			String folderPath = orderedFoldersMap.get(key);
			System.out.println(folderPath);
			File eachFolder = new File(folderPath);
			File[] listFiles = eachFolder.listFiles();
			for (File file : listFiles) {
				String filename = file.getName();
				if(filename.startsWith("data")){
					System.out.println("--> Data :"+filename);
					String comment = "-- "+eachFolder +" "+ filename +" ";
					String fileContents = FileUtils.readFileToString(file, "cp1253");
					String finalString = comment + System.getProperty("line.separator") + fileContents;
					newDataWriter.write(finalString);
				}else if(filename.startsWith("schema")){
					System.out.println("--> Schema :"+filename);					
					String comment = "-- "+eachFolder + " " + filename +" ";
					String fileContents = FileUtils.readFileToString(file,"cp1253");		
					String finalString = comment + System.getProperty("line.separator") + fileContents;
					newSchemaWriter.write(finalString);
				}				
			}
		}


	}


	public static void putFoldersinOrderedMap(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println(fileEntry);
				String folderName = fileEntry.getAbsoluteFile().getName();
				String cutfolderNameToDouble = folderName.substring(0, 3);
				if(folderName.length() > 5){
					cutfolderNameToDouble = folderName.substring(0, 4);
				}

				System.out.println(cutfolderNameToDouble);
				orderedFoldersMap.put(new Double(cutfolderNameToDouble), fileEntry.getPath());
				putFoldersinOrderedMap(fileEntry);
			}
		}    

	}



}
