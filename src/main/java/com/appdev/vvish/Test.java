package com.appdev.vvish;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.appdev.vvish.service.VideoStitchingService;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
//                firbaseConfig fconfig = new firbaseConfig();
//                fconfig.firebaseConfiguration();
                
		VideoStitchingService vSS = new VideoStitchingService();
		try {
                    
                    //deleting all files in tmp folder
                    Arrays.stream(new File("./tmp").listFiles()).forEach(File::delete);
                    //converting and transferrring  video files from images folder to tmp folder
                    listVideoFiles("./images");
                    //converting and transferrring  image files from images folder to tmp folder
                    listImageFiles("./images");
                    
                    //sample to dounload media from url and save in a folder
                    URL url = new URL("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4");
                    FileUtils.copyURLToFile(url, new File("./images", "a.mp4"));
                    
                    //main Job
			  vSS.stitchImagesToVideo("./tmp");
                    
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
        
        public static void listVideoFiles(String directoryName) throws IOException{
            int video_count = 0;
        FFmpeg ffmpeg = new FFmpeg("./lib/ffmpeg");
        FFprobe ffprobe = new FFprobe("./lib/ffprobe");
        FFmpegFormat ffformat = new FFmpegFormat();
         ffformat.duration = 2;
         
	
        File imgDir = new File("./images");
		if(!imgDir.isDirectory()) {
			return;
		}
                
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            
            if (file.isFile()&& (file.getName().endsWith(".mp4") ||file.getName().endsWith(".MOV") ||file.getName().endsWith(".mpg"))){
                System.out.println(file.getName());
                
               FFmpegBuilder builder = new FFmpegBuilder();
              // .addExtraArgs("-target").setFormat("pal-dvd").addExtraArgs("-aspect 4:3")
             builder.addInput("./images/"+file.getName()).addOutput("./tmp/"+video_count+".mpeg").addExtraArgs("-aspect").addExtraArgs("16:9");
             FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		executor.createJob(builder).run();
                
               FileWriter writer;
                try {
                    
                    writer = new FileWriter("./tmp/videos_list.txt", true);
                    writer.write("file "+video_count+".mpeg \n");
                    
                   video_count++;
                   
                    writer.close();
                } catch (IOException ex) {
                    System.err.println("exception" + ex);
                }
            
            }
        }
    }

    private static void listImageFiles(String imagesPath) throws IOException {
         int image_count = 0;
        FFmpeg ffmpeg = new FFmpeg("./lib/ffmpeg");
        FFprobe ffprobe = new FFprobe("./lib/ffprobe");
        FFmpegFormat ffformat = new FFmpegFormat();
         ffformat.duration = 2;
//         
	
        File imgDir = new File("./images");
		if(!imgDir.isDirectory()) {
			return;
		}
                
        File directory = new File(imagesPath);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            
            if (file.isFile()&& (file.getName().endsWith(".jpg") ||file.getName().endsWith(".png") ||file.getName().endsWith(".jpeg"))){
                System.out.println(file.getName());
                
               FFmpegBuilder builder = new FFmpegBuilder();
               
              // .addExtraArgs("-target").setFormat("pal-dvd").addExtraArgs("-aspect 4:3")
             builder.addInput("./images/"+file.getName()).addOutput("./tmp/img"+image_count+".jpg").addExtraArgs("-aspect").addExtraArgs("16:9");
             FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		executor.createJob(builder).run();
                image_count++;
                
                
//               FileWriter writer;
//                try {
//                    
//                    writer = new FileWriter("./tmp/images_list.txt", true);
//                    writer.write("file "+"'img"+image_count+".jpg'"+" duration 2 \n");
//                    
//                   image_count++;
//                   
//                    writer.close();
//                } catch (IOException ex) {
//                    System.err.println("exception" + ex);
//                }
            
            }
        }
    }

}
