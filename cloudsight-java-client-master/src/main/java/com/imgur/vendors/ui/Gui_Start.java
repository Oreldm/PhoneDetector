package com.imgur.vendors.ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.imgur.vendors.cloudsight_client.TestCSApi;

public class Gui_Start extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton button;
	JLabel label;
	JLabel textLabel;
	public static String path;
	ImageIcon loading = new ImageIcon("ajax-loader.gif");

	public Gui_Start() {
		super("Drivers Phone Detector");
		button = new JButton("Browse");
		button.setBounds(300, 300, 100, 40);
		textLabel = new JLabel();
		textLabel.setBounds(200, 230, 500, 100);

		label = new JLabel();
		label.setBounds(10, 10, 670, 250);
		add(button);
		add(textLabel);
		add(label);

		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				// filter the files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);
				// if the user click on save in Jfilechooser
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					label.setIcon(ResizeImage(path));
					// Uploading Object
					new UploadObject(path);

					TestCSApi algorithmResponse = new TestCSApi();
					textLabel.setText(algorithmResponse.output);
//					textLabel.setText("man sitting on gray car seat holding smartphone");

				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No File Select");
				}
			}
		});

		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(700, 400);
		setVisible(true);
	}

	// Methode to resize imageIcon with the same size of a Jlabel
	public ImageIcon ResizeImage(String ImagePath) {
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	public static void main(String[] args) {
		new Gui_Start();
	}
}
