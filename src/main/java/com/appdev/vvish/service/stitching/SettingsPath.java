package com.appdev.vvish.service.stitching;

import java.io.File;
import java.io.IOException;

public class SettingsPath {

	public static  String WIN32_FFMPEG = "";
	public static  String MAC_FFMPEG = "";
	public static  String MENCODER = "";
	public static  String WIN_SCREEN_DRAWING = "";

	public static  String ARTICLE_LOCAL_FILE = "";
	public static  String NOTE_LOCAL_FILE = "";
	public static  String KEY_VALUE_LOCAL_FILE = "";
	public static  String WEBPLAYER = "";
	public static  String tmpLink = "";
	public static  String UPLOAD_LATER_JSON = "";

	static {		try {
		SettingsPath.WIN32_FFMPEG=new File("./lib/ffmpeg.exe").getCanonicalPath();
		SettingsPath.MENCODER=new File("./lib/mencoder.exe").getCanonicalPath();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		//WIN32_FFMPEG="";	//new File(".").getAbsolutePath()+"//"+"ffmpeg.exe";
		
			
//				ClassLoader.getSystemResource( "ffmpeg.exe" ).toString() ;
		
		
		

}
}