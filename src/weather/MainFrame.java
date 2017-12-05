package weather;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainFrame {

	private JFrame frame;
	private WeatherData wd;

	/**
	 * Launch the application.
	 */
	public static void launch(WeatherData wd) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame(wd);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	/**
	 * Create the application.
	 */
	public MainFrame(WeatherData wd) {
		this.wd = wd;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("The current weather for "+wd.getCity()+", "+wd.getState()+
				" is "+wd.getTemp() + " F and the forecast is "+wd.getWeather()+
				". Would you like to change your desktop background to this picture?", SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 6, 988, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File("wallpapers/"+wd.choosePicture());
				String as[] = {
						"osascript", 
						"-e", "tell application \"Finder\"", 
						"-e", "set desktop picture to POSIX file \"" + f.getAbsolutePath() + "\"",
						"-e", "end tell"
				};
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec(as);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				frame.dispose();
			}
		});
		btnYes.setBounds(350, 46, 117, 29);
		frame.getContentPane().add(btnYes);
		
		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNo.setBounds(550, 46, 117, 29);
		frame.getContentPane().add(btnNo);
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("wallpapers/"+wd.choosePicture()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ImageIcon ic = new ImageIcon(bi);
		ic.setImage(this.getScaledImage(ic.getImage(), 988, 510));
		JLabel picLabel = new JLabel(ic);
		picLabel.setBounds(6, 87, 988, 510);
		frame.getContentPane().add(picLabel);
	}
}
