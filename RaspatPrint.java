

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class RaspatPrint {
	public static String shopname = "";
	static ArrayList<String> ar = new ArrayList<String>();
	public static void main(String[] args) throws IOException,
	InterruptedException {
		shopname = args[0];
		printfiles();
		Path faxFolder = Paths.get("smb://192.168.107.176/debprint/" + shopname);
		WatchService watchService = FileSystems.getDefault().newWatchService();
		faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					if(!ar.contains(fileName)) {
						printfiles();
					}
					ar.add(fileName);
				}
			}
			valid = watchKey.reset();

		} while (valid);	
	}
	
	public static void printfiles() throws IOException {
		try (Stream<Path> filePathStream=Files.walk(Paths.get("smb://192.168.107.176/debprint/" + shopname))) {
		    filePathStream.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		            System.out.println(filePath);
		            try {
		            	Stream<String> lines = Files.lines(filePath);
		                String data = lines.collect(Collectors.joining("\n"));
		                lines.close();
		        		
		            	ToPrint p = new ToPrint("LPT1");
		    			p.printLabel(data);
		    			System.out.println(data);
						Files.delete(filePath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						
					}
		        }
		    });
		}
		
	}

}
