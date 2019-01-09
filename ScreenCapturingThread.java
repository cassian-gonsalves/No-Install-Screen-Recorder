package demo;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.xuggler.IRational;
import javax.sound.sampled.*;


public class ScreenCapturingThread extends Thread{
	public ScreenCapturingThread(String completeFileName,
			int frameRate,
			Icon cursor,
			Rectangle recordingArea){
		this.completeFileName = completeFileName;
		this.frameRate = frameRate;
		this.cursor = cursor;
		this.recordingArea = recordingArea;
		try{
			bot = new Robot();
		}catch(Exception e){
			e.printStackTrace();
		}
		sleepTime = 1/frameRate;
	}
	
	public void stopIt(){
		keepCapturing = false;
	}
	
	
	public BufferedImage convertToType(BufferedImage sourceImage,int targetType){
		BufferedImage image;
		if(sourceImage.getType()==targetType){
			image = sourceImage;
		}else{
			image = new BufferedImage(sourceImage.getWidth(),sourceImage.getHeight(),targetType);
			image.getGraphics().drawImage(sourceImage,0,0,null);
		}
		return image;
	}
	
	private AudioFormat getAudioFormat(){
	    float sampleRate = 8000.0F;
	    //8000,11025,16000,22050,44100
	    int sampleSizeInBits = 16;
	    //8,16
	    int channels = 1;
	    //1,2
	    boolean signed = true;
	    //true,false
	    boolean bigEndian = true;
	    //true,false
	    return new AudioFormat(
	                      sampleRate,
	                      sampleSizeInBits,
	                      channels,
	                      signed,
	                      bigEndian);
	}
	
	
	@Override
	public void run(){
		final IRational FRAME_RATE = IRational.make(frameRate, 1);
		final IMediaWriter writer = ToolFactory.makeWriter(completeFileName);
		fmt = getAudioFormat();
		writer.addVideoStream(0,0,FRAME_RATE, recordingArea.width, recordingArea.height);
		writer.addAudioStream(1,0,1,8000);
		
		info = new DataLine.Info(TargetDataLine.class, fmt);
		try{
			fromMic = (TargetDataLine) AudioSystem.getLine(info);
			fromMic.open(fmt);
			fromMic.start();
		}catch(LineUnavailableException e){
			e.printStackTrace();
		}
		
		long startTime = System.nanoTime();
		
		while(keepCapturing==true){
			
			image = bot.createScreenCapture(recordingArea);
			PointerInfo pointerInfo = MouseInfo.getPointerInfo();
			Point globalPosition = pointerInfo.getLocation();
			
			int relativeX = globalPosition.x - recordingArea.x;
			int relativeY = globalPosition.y - recordingArea.y;
			
			if(frameRate==10){
				fromMic.read(eachPass, 0, eachPass.length);
				buffer.put(eachPass);
			}
			BufferedImage bgr = convertToType(image,BufferedImage.TYPE_3BYTE_BGR);
			timeStamp = System.nanoTime()-startTime; //first time stamp
			
			if(cursor!=null){
				bgr.getGraphics().drawImage(((ImageIcon)cursor).getImage(), relativeX,relativeY,null);	
			}
			try{
				writer.encodeVideo(0,bgr,timeStamp,TimeUnit.NANOSECONDS);
				
				if(frameRate == 10){
					if(buffer.hasRemaining()==false){
						seconds++;
						buffer.flip();
						buffer.asShortBuffer().get(audioSamples);
						System.out.println(seconds);
						writer.encodeAudio(1, audioSamples, seconds-1, TimeUnit.SECONDS);
						buffer.clear();
						
						
					}
				}
				
			}catch(Exception e){
				if(e.getMessage().contains("xuggle.xuggler")){
					
				}else{
					e.printStackTrace();
					writer.close();
					JOptionPane.showMessageDialog(null, 
							"Recording will stop abruptly because" +
							"an error has occured", "Error",JOptionPane.ERROR_MESSAGE,null);
					System.exit(0);
				}
				
			}
			
			try{
				sleep(sleepTime);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		writer.close();
		
	}
	
	
	TargetDataLine fromMic;
	AudioInputStream audioIn;
	AudioSystem system;
	DataLine.Info info;
	AudioFormat fmt;
	
	byte[] eachPass = new byte[1600]; //used to store data from TargetDataLine for each pass
	byte[] backingArray = new byte[16000]; //the complete data for one second
	ByteBuffer buffer = ByteBuffer.wrap(backingArray); //buffer which stores the complete data
	short[] audioSamples = new short[16000/2]; //audio samples to be encoded
	int passCounter = 0; /* After 10th pass, convert the byte[] to short[]
						  * using ByteBuffer */
	int seconds = 0; // used to store the position of the packet
	
	long timeStamp;
	long lastTimeStamp;
	BufferedImage image;
	long sleepTime;
	String completeFileName;
	int frameRate;
	Icon cursor;
	boolean keepCapturing = true;
	Rectangle recordingArea;
	Robot bot;
}
