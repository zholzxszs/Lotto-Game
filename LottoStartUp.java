import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.*;
import java.util.Timer;
import java.util.TimerTask;

public class LottoStartUp extends JFrame {
	public AudioInputStream audioInputStream;
	private Clip mouseClick;
	private int progressValue;

	public LottoStartUp() {
		initComponents();
		pbStart.setVisible(false);
		mousePointer();

		try {
			final String soundFile = "BackgroundMusic/Click.wav";
			audioInputStream = AudioSystem.getAudioInputStream(LottoStartUp.class.getResource(soundFile));
			mouseClick = AudioSystem.getClip();
			mouseClick.open(audioInputStream);
		} catch(Exception e3) {
			e3.printStackTrace();
		}
	}

	private void btnStartMouseClicked(MouseEvent e) {
		btnStart.setVisible(false);
		pbStart.setVisible(true);
		mouseHover();
		mouseClick.start();

		Timer t=new Timer();
		t.schedule(new TimerTask(){
			public void run(){
				progressValue=progressValue + 2;
				pbStart.setValue(progressValue);
				if(progressValue==100){
					t.cancel();
					setVisible(false);	
					new LottoGame().setVisible(true);
<<<<<<< HEAD
=======
					// new LottoStartUp().setVisible(false);
>>>>>>> 88e1dc8af63f57af75be85d90db7ee1064955449
				}
			}
		}, 1000,100);
	}

	private void mousePointer() {
		try{
<<<<<<< HEAD
=======
			//loads the custom cursor in the folder
>>>>>>> 88e1dc8af63f57af75be85d90db7ee1064955449
			Image cursorImage = ImageIO.read(new File("Images/mousePointer.png"));
			Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "CustomCursor");
            setCursor(customCursor);
		}catch(Exception e){

		}
	}

	private void mouseHover() {
		try{
			Image cursorImage = ImageIO.read(new File("Images/mouseHover.png"));
			Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "CustomCursor");
            setCursor(customCursor);
		}catch(Exception e){

		}
	}

	private void btnStartMouseEntered(MouseEvent e) {
		mouseHover();
	}

	private void btnStartMouseExited(MouseEvent e) {
		mousePointer();
	}

	private void btnStartMousePressed(MouseEvent e) {
		mouseHover();
	}

	private void initComponents() {
<<<<<<< HEAD
=======
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		// Generated using JFormDesigner Educational license - Charlie Marzan (Charlie S Marzan)
>>>>>>> 88e1dc8af63f57af75be85d90db7ee1064955449
		pbStart = new JProgressBar();
		btnStart = new JButton();
		lblMainTitle = new JLabel();
		lblMainBackground = new JLabel();

		//======== this ========
		setTitle("Lucky Lotto");
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- pbStart ----
		pbStart.setBackground(Color.yellow);
		pbStart.setForeground(Color.red);
		pbStart.setFont(new Font("Arial", Font.PLAIN, 18));
		pbStart.setStringPainted(true);
		pbStart.setBorder(null);
		contentPane.add(pbStart);
		pbStart.setBounds(100, 520, 400, 40);

		//---- btnStart ----
		btnStart.setIcon(new ImageIcon("Images/btnStart.jpg"));
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnStartMouseClicked(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnStartMouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnStartMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnStartMousePressed(e);
			}
		});
		contentPane.add(btnStart);
		btnStart.setBounds(170, 505, 250, 70);

		//---- lblMainTitle ----
		lblMainTitle.setIcon(new ImageIcon("Images/mainTitle.png"));
		contentPane.add(lblMainTitle);
		lblMainTitle.setBounds(15, 145, 565, 345);

		//---- lblMainBackground ----
		lblMainBackground.setIcon(new ImageIcon("Images/bgMain.jpg"));
		lblMainBackground.setBackground(Color.yellow);
		lblMainBackground.setOpaque(true);
		lblMainBackground.setFont(lblMainBackground.getFont().deriveFont(lblMainBackground.getFont().getSize() + 5f));
		lblMainBackground.setForeground(Color.red);
		contentPane.add(lblMainBackground);
		lblMainBackground.setBounds(0, 0, 590, 830);

		{
			// compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		setSize(590, 860);
		setLocationRelativeTo(null);
<<<<<<< HEAD
	}
=======
		// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
	// Generated using JFormDesigner Educational license - Charlie Marzan (Charlie S Marzan)
>>>>>>> 88e1dc8af63f57af75be85d90db7ee1064955449
	private JProgressBar pbStart;
	private JButton btnStart;
	private JLabel lblMainTitle;
	private JLabel lblMainBackground;
<<<<<<< HEAD
=======
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
>>>>>>> 88e1dc8af63f57af75be85d90db7ee1064955449
}
