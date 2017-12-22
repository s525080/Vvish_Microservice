package com.appdev.vvish.service.stitching;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.rmi.CORBA.Util;



public class MergeImageToPhotoSlides {

	public static void main(String[] args) throws Exception {
		ArrayList<String> mediaFiles = new ArrayList<String>();

		mediaFiles.add(
				"https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2Fwedding-pictures-26813-27529-hd-wallpapers.jpg?alt=media&token=8aa63d5c-0e5c-40bd-ba8f-39f469b175fb");
		mediaFiles.add(
				"https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2FWendy_Erwin_WED_0578.jpg?alt=media&token=7723d1d4-cd64-441c-8ad4-61e544609937");
		mediaFiles.add(
				"https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu?alt=media&token=18e99141-6f05-46f2-b87c-1c58ac62812d");
		mediaFiles.add(
				"https://firebasestorage.googleapis.com/v0/b/vvish-new.appspot.com/o/images%2Fj6besXtHhIgeBAY28tpAngbqMY63%2F-KxyKl0eh7iB14HILpdu%2Fbanner-home-02.jpg?alt=media&token=55763dd5-c646-4d10-8a8d-bad7ece8eaa7");
		mediaFiles.add("https://i.pinimg.com/564x/38/75/ea/3875eaae03414a84130dfb372a6687ea.jpg");
		mediaFiles.add("https://i.pinimg.com/originals/2a/3f/1b/2a3f1b6f40ed3f25cc3b5ec5b5c4b0de.jpg");

		List<String> mp4Videos = new ArrayList<>();
		for (Iterator iterator = mediaFiles.iterator(); iterator.hasNext();) {
			String url = (String) iterator.next();

			UUID randomName = new UUID(64, 64);
			randomName = randomName.randomUUID();
			String crd = "./tmp/";
			crd = new File(crd).getCanonicalPath() + "\\";
			String fileName = crd + randomName;
			String completFilePath = DownloadFile(url, fileName);
			System.out.println(completFilePath);
		String extension=Utils.getExtension(completFilePath);
			

			new File(completFilePath).renameTo(new File(new File(completFilePath).getParent() + "\\a01" + extension));

			FileUtils.copyFile(new File(new File(completFilePath).getParent() + "\\a01" + extension),
					new File(new File(completFilePath).getParent() + "\\a02" + extension));

String fileTotal=			Utils.ImageToMp4(new File(completFilePath).getParent() + "\\" + "a%02d"
					+ extension, fileName + ".mp4");
			new File(new File(completFilePath).getParent() + "\\a01" + extension).delete();
			new File(new File(completFilePath).getParent() + "\\a02" + extension).delete();
			
			
			mp4Videos.add(fileTotal);
		}
		System.out.println("You can find the output file here"+Utils.Finalize(Utils.concatenateM(mp4Videos )));;
		
	}

	public static String DownloadFile(String adress, String name) {
		String extension = "";
		BufferedOutputStream outStream = null;
		InputStream is = null;
		try {
			URL url;
			byte[] buf;
			int byteRead, byteWritten = 0;
			url = new URL(getFinalLocation(adress));

			extension=	Utils.getExtension(adress);
			name = name + extension;

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
		Utils.reScaleImage(name, extension);
		return name;
	}

	// Make sure the url has stream not any redirections
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
