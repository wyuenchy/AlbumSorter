import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;

import org.jaudiotagger.audio.*;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class sortingAlgo {
	
	static String ext(String fullName) {
	    String fileName = new File(fullName).getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
	
	static String artist(Tag tag){
		String AA;
		AA = tag.getFirst(FieldKey.ALBUM_ARTIST);
		if(AA.equals("")) {
			AA = tag.getFirst(FieldKey.ARTIST);
		}
		return AA;
	}
	
	static void moveFolder(String target, String targetName,File audioFile,String dest) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {

		AudioFile f = AudioFileIO.read(audioFile);
		Tag tag = f.getTag();
		//String Album = tag.getFirst(FieldKey.ALBUM);
		String AA = artist(tag);
		//System.out.println(AA);
		Path toMove = Paths.get(dest+"/"+AA);
		if(Files.exists(toMove)!= true) {
			Files.createDirectories(toMove);
		}
		Files.move(Paths.get(target), Paths.get(dest+"/"+AA+"/"+targetName), StandardCopyOption.REPLACE_EXISTING);		
	}
	
	static void moveFile(String target, String targetName, String dest) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		File audioFile = new File(target);
		AudioFile f = AudioFileIO.read(audioFile);
		Tag tag = f.getTag();
		String Album = tag.getFirst(FieldKey.ALBUM);
		String AA = artist(tag);
		//System.out.println(AA);
		Path toMove = Paths.get(dest+"/"+AA+" - "+Album);
		if(Files.exists(toMove)!= true) {
			Files.createDirectories(toMove);
		}
		Files.move(Paths.get(target), Paths.get(dest+"/"+AA+" - "+Album+"/"+targetName), StandardCopyOption.REPLACE_EXISTING);		
	}
	
	public static void algo(String source, String dest) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		System.out.println(source);
		System.out.println(dest);
		Boolean thirdLayer = false;
		File folder = new File(source);
		File[] fileList = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !name.equals(".DS_Store");
			}
		});
		for(File file : fileList) {
			String targetName = file.getName();
			String target = file.getPath();
			//System.out.println(target);
			//System.out.println(targetName);
			if(file.isFile()){
				//System.out.println(target);
				moveFile(target,targetName,dest);
			
			}else {
				File secFolder = new File(target+"/");
				File[] secList = secFolder.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return !name.equals(".DS_Store");
					}
				});
				for(File file2 : secList) {
					targetName = file2.getName();
					target = file2.getPath();
					//System.out.println(targetName);
					if(file2.isFile() && !thirdLayer){
						//System.out.println(targetName);
						System.out.println(ext(file2.getPath()));
						if(ext(file2.getPath()).equals("flac")||ext(file2.getPath()).equals("m4a")||ext(file2.getPath()).equals("wav")) {
							//System.out.println(target);
							//System.out.println(targetName);
							moveFolder(file.getPath(),file.getName(),file2,dest);
							//moveFile(target,targetName);
						}
						
					}else {
						File thirdFolder = new File(file2.getPath()+"/");
						File[] thirdList = thirdFolder.listFiles(new FilenameFilter() {
							@Override
							public boolean accept(File dir, String name) {
								return !name.equals(".DS_Store");
							}
						});
							for(File file3 : thirdList) {
								targetName = file3.getName();
								target = file3.getPath();
								//System.out.println(targetName);
								if(file3.isFile()){
									if(ext(file3.getPath()).equals("flac")) {
										moveFolder(file.getPath(),file.getName(),file3,dest);
										thirdLayer = true;
										break;
								}
							}
						}
					}
				thirdLayer = false;
				break;
				}
			}
		}
		Index.fin();
	}
}
