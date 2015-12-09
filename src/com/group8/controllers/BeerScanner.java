package com.group8.controllers;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.jh.JHGrayFilter;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class BeerScanner extends JFrame implements Runnable, ThreadFactory, WebcamImageTransformer {

	private static final long serialVersionUID = 6441489157408381878L;
	private static final JHGrayFilter GRAY = new JHGrayFilter();
	private Executor executor = Executors.newSingleThreadExecutor(this);
	private static Webcam webcam = null;
	protected WebcamPanel panel = null;
	protected JTextArea textarea = new JTextArea();
	private static Result result;
	private Thread t;

	public BeerScanner() {
		super();

		Dimension size = WebcamResolution.QVGA.getSize();
		webcam = webcam.getWebcams().get(0);
		webcam.setViewSize(size);
		webcam.setImageTransformer(this);
		panel = new WebcamPanel(webcam);
		panel.setPreferredSize(size);

		textarea = new JTextArea();
		textarea.setEditable(false);
		textarea.setPreferredSize(size);

		executor.execute(this);
	}

	@Override
	public void run(){

		boolean running = true;

		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result = null;
			BufferedImage image = null;
			if (webcam.isOpen()) {
				if ((image = webcam.getImage()) == null) {
					continue;
				}
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
				try {
					result = new MultiFormatReader().decode(bitmap);
				} catch (NotFoundException e) {
					// fall through, it means there is no QR code in image
				}
			}
			if (result != null){
				textarea.setText(result.getText());
				HomeCenter.setBarcode(result.getText());
				//HomeCenter.webcamLabel.setText(result.getText().toString());
				System.out.println("This is from BeerScan: " + result.getText());

				HomeCenter.checkIfbarcodeIsSet();




				// Stopped the thread !!! but something's still running
				if(t != null){
					System.out.println("Thread 31 before: " + t.isAlive());
					try {
						running = false;
						t.join();
					}
					catch (InterruptedException e){
					}
				}
				System.out.println("Thread 31 after: " + t.isAlive());


			}
		} while (running);
	}


	@Override
	public Thread newThread(Runnable r) {
		t = new Thread(r, "BeerScanner-thread");
		t.setDaemon(false); // originally was set to true
		//System.out.println("BeerScanner thread name " + Thread.currentThread().getName());
		//System.out.println("BeerScanner thread ID " + Thread.currentThread().getId());
		return t;
	}

	@Override
	public BufferedImage transform(BufferedImage bufferedImage) {
		return GRAY.filter(bufferedImage, null);
	}

	public static void disconnectWebcam(){
		if(System.getProperty("os.name").equals("Mac OS X")) {
			webcam.getDevice().close();
			System.out.println("u run shitty OS");
		}
		// Close the webcam service
		webcam.close();
	}

}