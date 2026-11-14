package handlers;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



import main.ApplicationSettings;
import persist.PictureDM;
import persist.TagDM;
import utils.Thumbnails;
import domain.ApplicationException;
import domain.Picture;
import domain.Tag;

public class PictureHandler {
	private Stream<Path> pathStream;
	public PictureHandler() throws IOException,ApplicationException {
		
		this.pathStream = Files.find(Paths.get(ApplicationSettings.importFolder),999, (p,bfa) -> bfa.isRegularFile() && (p.toString().endsWith("jpg") || p.toString().endsWith("JPG")));
		pathStream.collect(Collectors.toList());
		
	}
	
	
	public static Picture importPicture(String importFilename) throws IOException, ApplicationException{
		Metadata metadata = readExifMetadata(importFilename);
		
		
		
		LocalDate date;
		
		String dateString = extractDate(metadata);
		boolean flag = false;
		if (dateString != null) {
			
			date = LocalDate.parse(dateString,DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
			
			
		}else {
			flag=true;
			date=LocalDate.of(2000, 1, 1);
		}
		
		
		Path targetDir = Paths.get(ApplicationSettings.managedImagesFolder,
									String.valueOf(date.getYear()),
									String.format("%02d",date.getMonthValue()),
									String.format("%02d",date.getDayOfMonth()));
		Files.createDirectories(targetDir);
		
		Path targetFile = targetDir.resolve(new File(importFilename).getName());
		Files.copy(Paths.get(importFilename), targetFile, StandardCopyOption.REPLACE_EXISTING);
		
		Picture picture = new Picture();
		// Guardar sempre com '/' (no Windows o Path usa '\'), porque getAppPath e Thumbnails assumem '/'
		picture.setNome(targetFile.toString().replace('\\', '/'));
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataformatada = date.format(formater);
		picture.setData(dataformatada);
		picture.setNoExif(flag);
		
		PictureDM.getInstance().insert(picture);
		
		if(flag) {
			
			
			addDefaultTags(picture,date,true);
		}
		else {
			addDefaultTags(picture,date,false);
		}
		
		Thumbnails.makeThumbnail(picture);
		Files.deleteIfExists(targetFile);
		
		
		
		return picture;
		
		
				
	}
	
	private static void addDefaultTags(Picture picture, LocalDate date, boolean noExif) throws ApplicationException {
		
		
		TagDM tagDM = TagDM.getInstance();
		PictureDM pictureDM = PictureDM.getInstance();
		
		
		
		Tag tagYear = tagDM.makeTag("y"+date.getYear());
		Tag tagMonth = tagDM.makeTag("m" + String.format("%02d", date.getMonthValue()));
		Tag tagDay = tagDM.makeTag("d" + String.format("%02d", date.getDayOfMonth()));
		
		pictureDM.addTag(picture, tagYear);
		pictureDM.addTag(picture, tagMonth);
		pictureDM.addTag(picture, tagDay);
		
		
		
		if(noExif) {
			Tag tagNoExif = tagDM.makeTag("NoExif");
			pictureDM.addTag(picture,tagNoExif);
			
		}
		
	}
	
	
	
	private static Metadata readExifMetadata(String filename) {
		try {
			return ImageMetadataReader.readMetadata(new File(filename));
			
		}catch(Exception ex) {
			return null;
		}
	}
	
	private static String extractDate(Metadata metadata) {
		if(metadata == null) {
			return null;
		}
		ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
		if(exifSubIFDDirectory != null ) {
			return exifSubIFDDirectory.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		}
		return null;
	}
	
	public static void initializeDB() throws IOException {
		try {
			ApplicationSettings settings = new ApplicationSettings();
			List<Path> pathList = settings.getPaths();
			for(Path file : pathList) {
				String importfile = file.toString();
				PictureHandler.importPicture(importfile);
				
			}
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
}
