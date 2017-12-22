package com.appdev.vvish.service.stitching;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;



public class EnterisUtils {

	private BufferedReader reader = null;
	private BufferedReader error = null;
	private Process p = null;
	public Process getP() {
		return p;
	}
	public void setP(Process p) {
		this.p = p;
	}
	public String execShell(String command) throws Exception {

		String commandOutput = "";

		try {
			
			p = Runtime.getRuntime().exec(command);
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			System.out.println(command);
			
			BufferedWriter writter=	new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			//Thread.sleep(2000);
		//	writter.write("y");
			boolean flag=true;
			try {
				
				//while(flag){
			//	if(reader.ready()){
			//		flag=false;
			//	}
			//	while ((commandOutput = reader.readLine()) != null) {
			//		System.out.println(commandOutput);
			//	}
		//	}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			StringBuilder ln = new StringBuilder();
			try {
				while ((commandOutput = error.readLine()) != null) {
					ln.append(commandOutput);
				}
				error.close();
				commandOutput = ln.toString();
				return ln.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return commandOutput;
		} finally {
			p.destroy();
		}

	}
/*	public boolean execShellCommand(String command) throws Exception {

		try {
			p = Runtime.getRuntime().exec(command);
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			new Thread() {
				@Override
				public void run() {
					String line;
					try {
						while ((line = reader.readLine()) != null) {
							System.out.println(line);
						}
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					String line;
					try {
						while ((line = error.readLine()) != null) {
							System.out.println(line);
						}
						error.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();

			p.waitFor();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}

		System.out.println("Done");

		return true;
	}
*/

}