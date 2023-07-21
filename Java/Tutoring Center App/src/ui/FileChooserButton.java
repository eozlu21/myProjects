package ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import student.Student;
import tutor.Tutor;
import user.User;

/**
 * This button allows the user to choose an image to set as their portrait.
 * 
 * @author egeozlu
 *
 */
public class FileChooserButton extends JButton {

	private Image image;

	/**
	 * Creates a FileChooserButton for given panel and user
	 * 
	 * @param jPanel
	 * @param user
	 * @see FileChooserButton
	 */
	public FileChooserButton(JPanel jPanel, User user) {

		setText("Choose Portrait");
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				int result = fileChooser.showOpenDialog(getParent());
				if (result != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				BufferedImage bufferedImage;
				try {
					bufferedImage = ImageIO.read(file);
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(null, "Failed to load image", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				FileChooserButton.this.setImage(bufferedImage);
				if (jPanel instanceof TutorRegisterPanel) {
					((TutorRegisterPanel) (jPanel)).getPortraitJLabel().setIcon(new ImageIcon(getImage()));
					((TutorRegisterPanel) (jPanel)).getPortraitJLabel().repaint();
				} else if (jPanel instanceof PersonalInfoPanel) {
					((PersonalInfoPanel) (jPanel)).getPortraitJLabel().setIcon(new ImageIcon(getImage()));
					if (user instanceof Tutor) {
						((Tutor) user).setPortrait(getImage());
					} else if (user instanceof Student) {
						((Student) user).setPortrait(getImage());
					}
					((PersonalInfoPanel) (jPanel)).getPortraitJLabel().repaint();
				}

			}
		});
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setImage(BufferedImage bufferedImage) {
		// Determine the size of the image
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		// Calculate the size of the square
		int size = Math.min(width, height);

		// Determine the starting coordinates of the square
		int x = (width - size) / 2;
		int y = 0;

		// Crop the image to the square
		BufferedImage croppedImage = bufferedImage.getSubimage(x, y, size, size);
		ImageIcon icon = new ImageIcon(croppedImage);
		Image image = icon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
		setImage(image);
	}

}
