package com.appdev.vvish.service.stitching;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.SupportedSourceVersion;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;









public class Utils {
	//This is for Fade In
	public static int FADE_IN_FRAMES=50;
	
	
	public static String concatenate(final List<String> files) throws IOException {

		if (files == null)
			return null;

		if (files.size() > 0) {

			BufferedWriter writer = null;
			UUID randomName = new UUID(64, 64);
			randomName = randomName.randomUUID();
			String crd ="./tmp/";
			
			crd=	new File(crd).getCanonicalPath()+"\\";
			String fileName = randomName + ".txt";
			File fileWholePath = new File(fileName);

			try {

				writer = new BufferedWriter(new FileWriter(crd + fileName));
				for (int i = 0; i < files.size(); i++) {
					writer.write("file '" + files.get(i) + "'\r\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}

			String pathToFFMPEg = SettingsPath.WIN32_FFMPEG;
			String outPutFilename = crd + randomName + ".mp4";
			String command = pathToFFMPEg + " -f concat -i \"" +  new File(crd + fileName).getPath() 
					+ "\" -c copy"
					+ " \"" + new File(outPutFilename).getPath()
					
					
					
					
					
					+"\"";

			try {
				new EnterisUtils().execShell(command);

				File textFile = new File(crd + fileName);
				textFile.delete();
				
			//	 textFile.delete();

			} catch (Exception e) {
			}
			try {
				for (String file : files) {
				//	if (
				//	new File(file).delete();//)
			//		System.out.println("File deleted ");
			//		else
				//		System.out.println("File deletion failed ");
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}

			return outPutFilename;
		}
		return null;
	}
	
	public static String concatenateM(final List<String> mp4Videos) throws IOException {

		if (mp4Videos == null)
			return null;

		if (mp4Videos.size() > 0) {

			StringBuilder sb=new StringBuilder();
			for (Iterator iterator = mp4Videos.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				sb.append(" \""+string+"\"");
			}
			UUID randomName = new UUID(64, 64);
			randomName = randomName.randomUUID();
			String crd ="./tmp/";
			
			crd=	new File(crd).getCanonicalPath()+"\\";
			
			String pathToFFMPEg = SettingsPath.MENCODER;
			String outPutFilename = crd + randomName + ".mp4";
			String command = pathToFFMPEg + " -oac pcm -ovc x264 " + sb.toString()
					+ " -o "
					+ " \"" + new File(outPutFilename).getPath()
					+"\"";

			try {
				new EnterisUtils().execShell(command);

			} catch (Exception e) {
			}
			try {
				for (String file : mp4Videos) {
				//	if (
					new File(file).delete();//)
			//		System.out.println("File deleted ");
			//		else
				//		System.out.println("File deletion failed ");
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}

			return outPutFilename;
		}
		return null;
	}
	
	
	
	
	public static String convert(String inputFile,String outPutMp4) throws Exception {

		EnterisUtils utils = new EnterisUtils();
		

		String commandConvertToTs=SettingsPath.WIN32_FFMPEG + " -i " + "\"" + inputFile + "\""//+" -vf scale=320:240 "
				//+ " -vf scale=320:240 -codec:v libx264 -preset ultrafast -deadline realtime -cpu-used -16 -pix_fmt yuv420p -codec:a libvo_aacenc -f mp4"
				+ " -vf -y -vf setsar=1:1,scale=480x480,fade=in:0:70"
//				+ " -y -vf fade=in:0:30"
				+ " "
				+ "\"" + outPutMp4 + "\"";
		
	String output=	utils.execShell(commandConvertToTs);
	new File(inputFile).delete();
	System.out.println(output);
	return outPutMp4;
	}
	
	public static String Finalize(String inputFile) throws Exception {
System.out.println("input"+inputFile);

		EnterisUtils utils = new EnterisUtils();
		UUID randomName = new UUID(64, 64);
		randomName = randomName.randomUUID();
		String crd = "./tmp/";
		crd=	new File(crd).getCanonicalPath()+"\\";
		String fileName = crd+randomName + ".mp4";

		
		
		utils.execShell(SettingsPath.WIN32_FFMPEG + " -i " + "\"" + inputFile + "\""//+" -vf scale=320:240 "
				+ " -codec:v libx264 -preset ultrafast -deadline realtime -cpu-used -16 -pix_fmt yuv420p -codec:a libvo_aacenc -f mp4"
				//+" -c copy"  //for disabling orientation comment this line for enabling uncomment this line
				+ " "
				+ "\"" + fileName + "\"");
	new File(inputFile).delete();
		return fileName;
	}

	
	public static int framesPerSec=30;
	public static int secondsEachImage=5;
	
	public static int FADEFRAMES=30;
	
	//both should must be evens
	public static int IMAGEWIDTH=1280;
	public static int IMAGEHEIGHT=720;
	
	public static String ImageToMp4(String inputFile,String outPutMp4) throws Exception {

		EnterisUtils utils = new EnterisUtils();
		

		String commandConvertToTs=SettingsPath.WIN32_FFMPEG + " -r 1/"
				+ secondsEachImage
				+ " -i " + "\"" + inputFile + "\""//+" -vf scale=320:240 "
				//+ " -vf scale=320:240 -codec:v libx264 -preset ultrafast -deadline realtime -cpu-used -16 -pix_fmt yuv420p -codec:a libvo_aacenc -f mp4"
				+ " -c:v libx264 -r "
				+ framesPerSec
				+ " -pix_fmt yuv420p "
//				+ " -y -vf fade=in:0:30"
				+ " "
				+ "\"" + outPutMp4 + "\"";
		UUID randomName = new UUID(64, 64);
		randomName = randomName.randomUUID();
		String crd = "./tmp/";
		crd = new File(crd).getCanonicalPath() + "\\";
		String fileName = crd + randomName;
	String output=	utils.execShell(commandConvertToTs);
	commandConvertToTs=SettingsPath.WIN32_FFMPEG +" -i \""
			+ outPutMp4
			+ "\" -y -vf fade=in:0:"
			+ FADEFRAMES
			+ " \""
			+ fileName
			+ ".mp4\"";
	
	
	output=	utils.execShell(commandConvertToTs);
	
	commandConvertToTs=SettingsPath.WIN32_FFMPEG +" -i \""
			+ fileName+".mp4"
			+ "\" -y -vf fade=out:"
			+ ((secondsEachImage*framesPerSec)-FADEFRAMES)//"0"
			+ ":"
			+ FADEFRAMES
			+ " \""
			+ outPutMp4
			+ "\"";
	
	
	output=	utils.execShell(commandConvertToTs);
	
	new File(fileName+".mp4").delete();
	//ffmpeg -i slide_fade_in.mp4 -y -vf fade=out:120:30 slide_fade_in_out.mp4
	
	System.out.println(output);
	return outPutMp4;
	/*try {
		utils.getP().destroy();
	} catch (Exception e) {
		// TODO: handle exception
	}
		utils=null;
		EnterisUtils	utils2 = new EnterisUtils();
		UUID randomName = new UUID(64, 64);
		randomName = randomName.randomUUID();
		String crd = "./tmp/";
		crd=	new File(crd).getCanonicalPath()+"\\";
		String fileName = crd+randomName + ".ts";
		// for removing fade in just return the outputmp4  and remove remainging likes
	//	return outPutMp4;
		utils2.execShell(SettingsPath.WIN32_FFMPEG + " -i " + "\"" + outPutMp4 + "\""//+" -vf scale=320:240 "
				//+ " -vf scale=320:240 -codec:v libx264 -preset ultrafast -deadline realtime -cpu-used -16 -pix_fmt yuv420p -codec:a libvo_aacenc -f mp4"
				
				+ " -y -vf fade=in:0:"
				+ FADE_IN_FRAMES
				
				+ " "
				+ "\"" + fileName + "\"");
		//new File(outPutMp4).delete();
//		ffmpeg -i slide.mp4 -y -vf fade=in:0:30 slide_fade_in.mp4
		
		return fileName;
*/	}
	public static String getExtension(String url){
		String extension = "";
		if (url.toLowerCase().contains(".jpg")) {
			extension = ".jpg";
		} else if (url.toLowerCase().contains(".png")) {
			extension = ".png";
		} else if (url.toLowerCase().contains(".bmp")) {
			extension = ".bmp";
		}else if (url.toLowerCase().contains(".jpeg")) {
			extension = ".jpeg";
		}else  {
			extension = ".jpg";
		}
		return extension;
	}
	public static void reScaleImage(String fileName,String extension){
		

	    try {System.out.println("Extension is:"+extension);

	        BufferedImage originalImage = ImageIO.read(new File(fileName));//change path to where file is located
	        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

	        BufferedImage resizeImageJpg = resizeImage(originalImage, type, IMAGEWIDTH, IMAGEHEIGHT);
	        ImageIO.write(resizeImageJpg, extension.substring(1), new File(fileName)); //change path where you want it saved

	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	    }
	}
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
	    BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
	    g.dispose();

	    return resizedImage;
	}
}
