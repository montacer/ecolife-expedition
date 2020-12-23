package ch.itsforward.ecolifeexpedition.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MediaFileUtils {

	public static String FILE_SEPERATOR = FileSystems.getDefault().getSeparator();

	public static Path updateMediaFile(String directoryUri, String prefix, byte[] content) {
		Path file = Paths.get(directoryUri + FILE_SEPERATOR + prefix);
		if (Files.exists(file)) {
			try {
				Files.delete(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		file = createMediaFile(directoryUri, prefix, content);
		return file;

	}

	public static Path createMediaFile(String directoryUri, String prefix, byte[] content) {
		Path file = saveRandomNameFileToPath(directoryUri, prefix);
		try {
			Files.write(file, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static Path saveRandomNameFileToPath(String directoryUri, String prefix) {
		boolean created = false;

		Path directory = Paths.get(directoryUri);
		Path file = Paths.get(directoryUri + FILE_SEPERATOR + generateFileName(prefix));

		try {
			if (!Files.exists(directory)) {
				Files.createDirectory(directory);
			}
			Files.createFile(file);
			created = true;
		} catch (Exception e) {
			e.printStackTrace();
			file = null;
		} finally {
			return file;
		}

	}

	public static String generateFileName(String prefix) {
		StringBuilder sb = new StringBuilder(prefix);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSSSSSSSS");
		String dateStampRandomString = null;
		dateStampRandomString = LocalDateTime.now().format(formatter) + "_"
				+ generateRamdomString(48 /* numeral '0' */, 122 /* letter Z */, 10);
		sb.append(dateStampRandomString);
		return sb.toString();

	}

	private static String generateRamdomString(int leftLimit, int rightLimit, int targetStringLength) {

		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public static void main(String[] args) {
		System.out.println(saveRandomNameFileToPath("./uploadedVideo", "video"));
	}

}
