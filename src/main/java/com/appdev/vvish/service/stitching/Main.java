package com.appdev.vvish.service.stitching;

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
import java.util.concurrent.SynchronousQueue;

import javax.net.ssl.HttpsURLConnection;

public class Main {

	public static void main(String[] args) throws Exception {
		String crd = "./tmp/";
		System.out.println(new File(crd).getCanonicalPath());
		crd=new File(crd).getCanonicalPath();
		SettingsPath.WIN32_FFMPEG=new File("./lib/ffmpeg.exe").getCanonicalPath();
		
		SettingsPath.MENCODER=new File("./lib/mencoder.exe").getCanonicalPath();
		System.out.println(new File(SettingsPath.WIN32_FFMPEG).exists());
		ArrayList<String> videoList = new ArrayList<>();
		videoList.add("https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FAcyS4U1uhPZRZTUYAvNv6otUbHH2%2F-L-m224sP1hdzPANjaTp?alt=media&token=32227b95-6bdb-4506-88e5-4920285828b8");
		videoList.add("https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FAcyS4U1uhPZRZTUYAvNv6otUbHH2%2F-L-m1brVDmg56feIssOB?alt=media&token=f7773d73-9f07-4d9d-a334-838d6b299d24");
//		videoList.add("https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/videos%2FAcyS4U1uhPZRZTUYAvNv6otUbHH2%2F-L-m1CjXivskZDBv6Kqj?alt=media&token=b9ba3164-dc5c-4ae4-8d16-b54d665a6336");

		Main.MergeUrlList(videoList);
	}
	// Main function to give urlList
	public static String MergeUrlList(ArrayList<String> videoList) throws Exception{
		
		
		
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

		return concatenated;
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
