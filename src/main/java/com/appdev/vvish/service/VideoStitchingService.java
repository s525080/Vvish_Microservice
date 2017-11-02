package com.appdev.vvish.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;

@Service
public class VideoStitchingService {
    
    private int imageCount;

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }
	
	public void stitchImagesToVideo(String imagesPath) throws IOException, InterruptedException {
		FFmpeg ffmpeg = new FFmpeg("./lib/ffmpeg");
		FFprobe ffprobe = new FFprobe("./lib/ffprobe");
                FFmpegFormat ffformat = new FFmpegFormat();
		ffformat.duration = 2;
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
		executor.createJob(builder).run();
            // executor.createJob(builder2).run();
            
           double duration = ffprobe.probe("./tmp/output.mp4").getFormat().duration;
                System.err.println("duration is "+duration);
		
            convertImage(duration);
         // ffmpeg -stream_loop -1 -i input.mp4 -c copy -fflags +genpts output.mp4 
         
         //Final job- Overlay
            String cmd[] = new String[]{
                "ffmpeg",
			"-i", "./tmp/img_video.mp4",
			"-i", "./tmp/output.mp4", "-filter_complex" ,
        "[0:v][1:v]overlay","-pix_fmt", "yuv420p" ,"./tmp/final_video.mp4"};
            
            commandProcess(cmd);          

	}
        
        
//ffmpeg -r 1/5 -i e%02d.png -c:v libx264 -r 30 -pix_fmt yuv420p EinsteinSlideShow.mp4
    public void convertImage(double videoduration) throws IOException, InterruptedException {
        FFprobe ffprobe = new FFprobe("./lib/ffprobe");
        
        //new code for image video
         int image_count = 0;
                
        File directory = new File("./tmp");
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            
            if (file.isFile()&& (file.getName().endsWith(".jpg") ||file.getName().endsWith(".png") ||file.getName().endsWith(".jpeg"))){
                System.out.println(file.getName());
                image_count++;
           
            }
        }
        
        int count = image_count;
        int j=0;
        FileWriter writer2;
        for(int k=2;k<videoduration;k=k+2){
            //               
                try {
                    
                    writer2 = new FileWriter("./tmp/images_list.txt", true);
                    writer2.write("file "+"'img"+j+".jpg'"+" duration "+2+" \n");
                   j++;
                   if(j==count-1){
                       j=0;
                   }
                   if((k+2) >= videoduration){
                       writer2.write("file "+"'img0.jpg'"+" duration "+(videoduration-k)+" \n");         
                   }
                   
                    writer2.close();
                } catch (IOException ex) {
                    System.err.println("exception" + ex);
                }
        }
        
        
        
        
//     String cmd[] = new String[]{
//                "ffmpeg", "-r","1/2",
//			"-i", "./tmp/img%01d.jpg",
//			"-c:v", "libx264", "-r","80" ,
//        "-pix_fmt", "yuv420p",
//"./tmp/img_video.mp4"};
     
     String cmd[] = new String[]{
                "ffmpeg", "-r","1/2","-f", "concat",
			"-i", "./tmp/images_list.txt",
			"-c:v", "libx264", "-r","80" ,
        "-pix_fmt", "yuv420p",
"./tmp/img_video.mp4"};
//          
    commandProcess(cmd);


//         double imageVideoDuration = ffprobe.probe("./tmp/img_video.mp4").getFormat().duration;
//                System.err.println("duration is "+imageVideoDuration);
//	double finaDuration = Math.round(videoduration/imageVideoDuration);
//        System.out.println("final duration is"+Math.round(finaDuration));
//        //ffmpeg -i input -filter_complex loop=3:75:25 output
//        //clearing the content of video_list.txt
//                    PrintWriter pwriter = new PrintWriter("./tmp/images_video_list.txt");
//                    pwriter.print("");
//                    pwriter.close();
//        FileWriter writer;
//                try {
//                    
//                    writer = new FileWriter("./tmp/images_video_list.txt", true);
//                    for(int i=0;i<=finaDuration;i++){
//                        writer.write("file img_video.mp4 \n");
//                    }
//                       
//                    writer.close();
//                } catch (IOException ex) {
//                    System.err.println("exception" + ex);
//                }
//                //ffmpeg -f concat -i list.txt -c copy output.mp4
//        String finalCmd[] = new String[]{
//                "ffmpeg",
//			"-f", "concat",
//			"-i", "./tmp/images_video_list.txt",
//                        "-c", "copy",
//                        "./tmp/img_video2.mp4"};
//        commandProcess(finalCmd);
    }
    
    public void commandProcess(String[] cmd) throws IOException, InterruptedException{
            //  Process ffmpeg2 = Runtime.getRuntime().exec(cmd);
            System.out.println("in command process");
            Process processDuration = new ProcessBuilder(cmd).redirectErrorStream(true).start();

            StringBuilder strBuild = new StringBuilder();

            try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(processDuration.getInputStream(), Charset.defaultCharset()));) {

                String line;

                while ((line = processOutputReader.readLine()) != null) {

                    strBuild.append(line + System.lineSeparator());

                }

                processDuration.waitFor();

            }

            String outputJson = strBuild.toString().trim();

            System.out.println(outputJson);
    }
}
