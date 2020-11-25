package net.razpadonline;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.io.File;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileUpload {
	

	
	
	static ArrayList<String> ar = new ArrayList<String>();
	public static String path = "";
	public static String rotate = "";
	public static void main(String[] args) throws IOException, InterruptedException {
		path = args[0];
		rotate = args[1];
		Path faxFolder = Paths.get(path);
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
						
						if (fileName.endsWith(".JPG")) {
							upload(fileName);	
						}
					}
					ar.add(fileName);
				}
			}
			valid = watchKey.reset();

		} while (valid);	
	}
	
	public static void upload(String fileToUpload) {
		String url = "http://192.168.107.176/index.php/Prod/uploadimage";
		String charset = "UTF-8";
		File uploadFile = new File(path + fileToUpload);
		System.out.println("uploading" + fileToUpload);
		try {
            MultipartUtility multipart = new MultipartUtility(url, charset);
             
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");   
            multipart.addFilePart("fileUpload", uploadFile);
            multipart.addFormField("rotate", rotate);
            
            
            List<String> response = multipart.finish();
            uploadFile.delete();
            for (String line : response) {
                System.out.println(line);
                
            }
            
        } catch (IOException ex) {
            System.err.println(ex);
            System.out.println("failed to upload");
        }
    }	
}