package com.appdev.vvish;

import java.io.IOException;

import com.appdev.vvish.service.VideoStitchingService;

public class Test {

	public static void main(String[] args) {
		VideoStitchingService vSS = new VideoStitchingService();
		try {
			vSS.stitchImagesToVideo("./images/");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
