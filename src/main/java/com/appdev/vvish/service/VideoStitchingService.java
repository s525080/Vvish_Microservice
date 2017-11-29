package com.appdev.vvish.service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class VideoStitchingService {
    
    private int imageCount;

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }
	
	public void surpriseFlow(String imagesPath) throws IOException, InterruptedException {
		FFmpeg ffmpeg = new FFmpeg("./lib/ffmpeg");
		FFprobe ffprobe = new FFprobe("./lib/ffprobe");
                FFmpegFormat ffformat = new FFmpegFormat();
		ffformat.duration = 4;
		FFmpegBuilder builder = new FFmpegBuilder();
                FFmpegBuilder builder2 = new FFmpegBuilder();
	
		File imgDir = new File(imagesPath);
		if(!imgDir.isDirectory()) {
			return;
		}
		
           
            //video
                 builder.setFormat("concat").setInput(imagesPath+"/videos_list.txt").addExtraArgs("-safe").addExtraArgs("0").addExtraArgs("-protocol_whitelist").addExtraArgs("\"file,http,https,tcp,tls\"").overrideOutputFiles(true).addOutput(imagesPath+"/outputVideo.mp4").setFormat("mp4").setVideoCodec("libx264").setDuration(2, TimeUnit.MINUTES).setVideoResolution(1024,720)
        .setVideoFrameRate(24,1).done();
		
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		executor.createJob(builder).run();
//		 String cmd[] = new String[]{
//	                "./lib/ffmpeg","-f", "concat", "-safe","0","-protocol_whitelist", "\"file,http,https,tcp,tls\"",
//				"-i", "./surprise_media/videos_list.txt",
//				"-c:v", "libx264", "-r","80",
//	        "-pix_fmt", "yuv420p",
//	"./surprise_media/outputVideo.mp4"};
//////	          
//	       // Process ffmpeg2 = Runtime.getRuntime().exec(cmd);
//	   commandProcess(cmd);
           
            
           double duration = ffprobe.probe(imagesPath+"/outputVideo.mp4").getFormat().duration;
                System.out.println("duration is "+duration);
		
		// createImageVideo(2);
		 createImageVideo(duration);
         // ffmpeg -stream_loop -1 -i input.mp4 -c copy -fflags +genpts output.mp4 
         
         //Final job- Overlay
            String fnlcmd[] = new String[]{
                "ffmpeg",
			"-i", "./surprise_media/img_video2.mp4",
			"-i", "./surprise_media/outputVideo.mp4", "-filter_complex" ,
        "[0:v][1:v]overlay","-pix_fmt", "yuv420p" ,"./surprise_media/final_video.mp4"};
            
            commandProcess(fnlcmd);          

	}
	
    public void createImageVideo(double videoduration) throws IOException, InterruptedException {
        FFprobe ffprobe = new FFprobe("./lib/ffprobe");
        
        //new code for image video    
        
        String cmd[] = new String[]{
                "./lib/ffmpeg", "-r","1/2","-f", "concat", "-safe","0","-protocol_whitelist", "\"file,http,https,tcp,tls\"",
			"-i", "./surprise_media/images_list.txt",
			"-c:v", "libx264", "-r","80",
        "-pix_fmt", "yuv420p",
"./surprise_media/images_video.mp4"};
//          
       // Process ffmpeg2 = Runtime.getRuntime().exec(cmd);
   commandProcess(cmd);
   
 double imageVideoDuration = ffprobe.probe("./surprise_media/images_video.mp4").getFormat().duration;
 System.err.println("duration is "+imageVideoDuration);
double finaDuration = Math.round(videoduration/imageVideoDuration);
System.out.println("final duration is"+Math.round(finaDuration));
////ffmpeg -i input -filter_complex loop=3:75:25 output
////clearing the content of video_list.txt
     PrintWriter pwriter = new PrintWriter("./surprise_media/images_video_list.txt");
     pwriter.print("");
     pwriter.close();
FileWriter writer;
 try {
     
     writer = new FileWriter("./surprise_media/images_video_list.txt", true);
     for(int i=0;i<=finaDuration;i++){
         writer.write("file images_video.mp4 \n");
     }
        
     writer.close();
 } catch (IOException ex) {
     System.err.println("exception" + ex);
 }
// //ffmpeg -f concat -i list.txt -c copy output.mp4
String finalCmd[] = new String[]{
 "ffmpeg",
"-f", "concat",
"-i", "./surprise_media/images_video_list.txt",
         "-c", "copy",
         "./surprise_media/img_video2.mp4"};
commandProcess(finalCmd);


    }
	public void stitchImagesToVideo(String imagesPath) throws IOException, InterruptedException {
		FFmpeg ffmpeg = new FFmpeg("./lib/ffmpeg");
		FFprobe ffprobe = new FFprobe("./lib/ffprobe");
                FFmpegFormat ffformat = new FFmpegFormat();
		ffformat.duration = 4;
		FFmpegBuilder builder = new FFmpegBuilder();
                FFmpegBuilder builder2 = new FFmpegBuilder();
	
		File imgDir = new File(imagesPath);
		if(!imgDir.isDirectory()) {
			return;
		}
		

         //images
//            builder2.setFormat("concat").setInput("./images/images_list.txt").overrideOutputFiles(true).addOutput("./images/output2.mpeg").setFormat("mpeg").addExtraArgs("-aspect").addExtraArgs("16:9").setDuration(2, TimeUnit.MINUTES).setVideoResolution(780, 1024)
//        .setVideoFrameRate(24,1).done();
//            
            //video
                 builder.setFormat("concat").setInput(imagesPath+"/videos_list.txt").overrideOutputFiles(true).addOutput("./tmp/output.mp4").setFormat("mp4").setVideoCodec("libx264").setDuration(2, TimeUnit.MINUTES).setVideoResolution(780,1024)
        .setVideoFrameRate(24,1).done();
					
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		//executor.createJob(builder).run();
            // executor.createJob(builder2).run();
            
          /* double duration = ffprobe.probe("./tmp/output.mp4").getFormat().duration;
                System.err.println("duration is "+duration);*/
		
		memoriesFlow(2);
         // ffmpeg -stream_loop -1 -i input.mp4 -c copy -fflags +genpts output.mp4 
         
         //Final job- Overlay
            /*String cmd[] = new String[]{
                "ffmpeg",
			"-i", "./tmp/img_video.mp4",
			"-i", "./tmp/output.mp4", "-filter_complex" ,
        "[0:v][1:v]overlay","-pix_fmt", "yuv420p" ,"./tmp/final_video.mp4"};*/
            
         //   commandProcess(cmd);          

	}
        
        
//ffmpeg -r 1/5 -i e%02d.png -c:v libx264 -r 30 -pix_fmt yuv420p EinsteinSlideShow.mp4
    public void memoriesFlow(double videoduration) throws IOException, InterruptedException {
        FFprobe ffprobe = new FFprobe("./lib/ffprobe");
        
   

            
//        
//     String cmd3[] = new String[]{
//                "ffmpeg", "-r","1/2",
//			"-i", "./tmp/img%01d.jpg",
//			"-c:v", "libx264", "-r","80" ,
//        "-pix_fmt", "yuv420p",
//"./tmp/img_video.mp4"};
//      
//       // ffmpeg -r 60 -f image2 -s 1920x1080 -i pic%04d.png -vcodec libx264 -crf 25  -pix_fmt yuv420p test.mp4
//        String cmd2[] = new String[]{ 
//        "./lib/ffmpeg", "-r","1/5","-f", "concat", "-safe","0","-protocol_whitelist", "\"file,http,https,tcp,tls\"",
//        "-i", "./tmp/images_list.txt",
//        "-vf","fps=25",
//        "-c:v", "libx264", 
//        "-maxrate","5M",
//        "-q:v", "2",		
//"./tmp/img_video.mp4"};
        
 
    
         
        
     //   -filter_complex "[0]reverse[r];[0][r]concat,loop=2:80,setpts=N/13/TB" -vcodec libx264 -pix_fmt yuv420p -crf 17
       // -f image2 -r 1/5
        String cmd[] = new String[]{
                "./lib/ffmpeg", "-r","1/2","-f", "concat", "-safe","0","-protocol_whitelist", "\"file,http,https,tcp,tls\"",
			"-i", "./tmp/images_list.txt",
			"-c:v", "libx264", "-r","80",
        "-pix_fmt", "yuv420p",
"./tmp/img_finalvideo.mp4"};
//          
       // Process ffmpeg2 = Runtime.getRuntime().exec(cmd);
   commandProcess(cmd);



    }
    
    public void commandProcess(String[] cmd) throws IOException, InterruptedException{
            //  Process ffmpeg2 = Runtime.getRuntime().exec(cmd);
            System.out.println("in command process");
            Process processDuration = new ProcessBuilder(cmd).redirectErrorStream(true).start();

            StringBuilder strBuild = new StringBuilder();

            try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(processDuration.getInputStream(), Charset.defaultCharset()));) {
            	System.out.println("1");
                String line;

                while ((line = processOutputReader.readLine()) != null) {
                	System.out.println("2");
                    strBuild.append(line + System.lineSeparator());

                }

                processDuration.waitFor();

            }

            String outputJson = strBuild.toString().trim();

            System.out.println(outputJson);
    }
    public void createMediaTextFile(List<String> mediaFiles) throws UnsupportedEncodingException, FileNotFoundException, IOException{
    	
    	File file = new File("tmp/images_list.txt");
    	FileWriter fileWriter = new FileWriter(file, false); 
    	
    	for(String eachFile:mediaFiles) {
    		fileWriter.write("file "+eachFile+System.getProperty( "line.separator" ));
    		fileWriter.write("duration 2"+System.getProperty( "line.separator" ));
    	}
    	fileWriter.close();
    	
    	
    }
    
    public void createVideoTextFile(ArrayList<String> videoFiles) throws UnsupportedEncodingException, FileNotFoundException, IOException{

        

    	System.out.println("generating text file");

    	    File file = new File("surprise_media/videos_list.txt");

    	    FileWriter fileWriter = new FileWriter(file, false); 

    	    

    	    for(String eachFile:videoFiles) {
    	    	String result = eachFile.replace("\\", "");
                String tmp = result.replace("\"", "\'");
    	    fileWriter.write("file "+tmp+System.getProperty( "line.separator" ));

    	    //fileWriter.write("duration 2"+System.getProperty( "line.separator" ));

    	    }

    	    fileWriter.close();


    	    }

    	public void createImageTextFile(ArrayList<String> mediaFiles) throws UnsupportedEncodingException, FileNotFoundException, IOException{

    	    

    	System.out.println("generating text file");

    	    File file = new File("surprise_media/images_list.txt");

    	    FileWriter fileWriter = new FileWriter(file, false); 

    	    

    	    for(String eachFile:mediaFiles) {

    	    	String result = eachFile.replace("\\", "");
                String tmp = result.replace("\"", "\'");
    	    fileWriter.write("file "+tmp+System.getProperty( "line.separator" ));

    	    fileWriter.write("duration 2"+System.getProperty( "line.separator" ));

    	    }

    	    fileWriter.close();


    	    }
    


}
