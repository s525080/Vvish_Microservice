package com.appdev.vvish.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

@Service
public class VideoStitchingService {
	
	public void stitchImagesToVideo(String imagesPath) throws IOException {
		FFmpeg ffmpeg = new FFmpeg("./lib/ffmpeg");
		FFprobe ffprobe = new FFprobe("./lib/ffprobe");
		
		FFmpegBuilder builder = new FFmpegBuilder();
		
		File imgDir = new File(imagesPath);
		if(!imgDir.isDirectory()) {
			return;
		}
		
//		FilenameFilter fileFilter = new FilenameFilter() {
//			public boolean accept(File dir, String fileName) {
//				return (fileName.endsWith(".JPG") || fileName.endsWith(".jpg"));
//			}
//		};
//		
//		for(File file : imgDir.listFiles(fileFilter)) {
//			if(!file.isDirectory())
//				builder.addInput(file.getPath());
//		}
//		
		builder.addInput(imagesPath+"/img%d.jpg").addOutput("./images/output.wmv").setVideoFrameRate(FFmpeg.FPS_24).done();
						
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		executor.createJob(builder).run();
	}
}
