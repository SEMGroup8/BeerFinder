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
	private Webcam webcam = null;
	protected WebcamPanel panel = null;
	protected JTextArea textarea = new JTextArea();

	public BeerScanner() {
		super();

		Dimension size = WebcamResolution.QVGA.getSize();
		webcam = Webcam.getWebcams().get(0);
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
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Result result = null;
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
				System.out.println("This is from BeerScan: " + result.getText());
			}
		} while (true);
	}


	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "example-runner");
		t.setDaemon(true);
		return t;
	}

	@Override
	public BufferedImage transform(BufferedImage bufferedImage) {
		return GRAY.filter(bufferedImage, null);
	}
}