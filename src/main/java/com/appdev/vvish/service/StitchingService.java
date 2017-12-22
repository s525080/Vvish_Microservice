package com.appdev.vvish.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;

import com.appdev.vvish.service.stitching.Utils;

@Service
public class StitchingService {
	
	
public  String MergeUrlList(ArrayList<String> videoList) throws Exception{
		
		
		
		List<String> files = new ArrayList<>();
		
		for (String string : videoList) {
		
		
			UUID randomName = new UUID(64, 64);
			randomName = randomName.randomUUID();
			String crd = "./tmp/";
			crd=	new File(crd).getCanonicalPath()+"\\";
			String fileName = crd  + randomName;
			System.out.println("file Name - "+ fileName);
			
			//Download files
			DownloadVide(string, fileName);
			
			// just adds Fade-in effect in the downloading mp4 files
			String output=Utils.convert(fileName, fileName + ".mp4");
			//new File(fileName).delete();
			
			files.add(output);
		}
// concat/merge all the videos to one
		String concatenated = Utils.concatenateM(files);
		//convert the video to such format that every device supports it
	String file=	Utils.Finalize(concatenated);
		System.out.println("You can find the file here:"+file);

		return file;
	}

	public static void DownloadVide(String adress, String name) {
		BufferedOutputStream outStream = null;
		InputStream is = null;
		try {
			URL url;
			byte[] buf;
			int byteRead, byteWritten = 0;
			url = new URL(getFinalLocation(adress));

			String destinationDir;
			String localFileName;
			outStream = new BufferedOutputStream(new FileOutputStream(name));

			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			int size = 1024;
			buf = new byte[size];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				outStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
//Make sure the url has stream not any redirections
	public static String getFinalLocation(String address) throws Exception {
		URL url = new URL(address);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		int status = conn.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_SEE_OTHER) {
				String newLocation = conn.getHeaderField("Location");
				return getFinalLocation(newLocation);
			}
		}
		return address;
	}

}
