package fr.koora.plutonia.launcher.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

import fr.koora.plutonia.launcher.Frame;
import fr.koora.plutonia.launcher.frames.textfields.JTextFieldLimit;
import fr.koora.plutonia.launcher.listeners.SimplifiedDocumentListener;
import fr.koora.plutonia.launcher.listeners.SimplifiedFocusListener;
import fr.koora.plutonia.launcher.managers.SettingsManager;
import fr.koora.plutonia.launcher.utils.JavaVersionUtil;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.theshark34.swinger.textured.STexturedProgressBar;

public class LoginPanel extends JPanel implements SwingerEventListener {

	private static final long serialVersionUID = 1L;

	private Image backgroundImage = Swinger.getResource("background.png");

	private Image usernameFieldImage = Swinger.getResource("user_tf.png");
	private Image passwordFieldImage = Swinger.getResource("pwd_tf.png");
	private Image emptyFieldImage = Swinger.getResource("empty_tf.png");

	private JTextFieldLimit usernameField = new JTextFieldLimit(16);
	private JPasswordField passwordField = new JPasswordField();

	private STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"), Swinger.getResource("play_hover.png"));
	private STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit.png"), Swinger.getResource("quit_hover.png"));

	private STexturedProgressBar progressBar = new STexturedProgressBar(Swinger.getResource("progressbar.png"), Swinger.getResource("progressbar_full.png"));

	private Saver saver = new Saver(new File(SettingsManager.GAME_INFOS.getGameDir(), "credentials.yml"));

	public STexturedProgressBar getProgressBar() {
		return this.progressBar;
	}

	private Frame frame;

	private Timer timer;

	public LoginPanel(Frame frame) {
		this.frame = frame;

		this.setLayout(null);

		// Username field
		this.usernameField.setBounds(181, 284, 400, 54);
		this.usernameField.setOpaque(false);
		this.usernameField.setBorder(null);

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/fr/koora/plutonia/launcher/resources/lemonmilk.otf")).deriveFont(JavaVersionUtil.getJavaVersion() > 8 ? 23.0f : 12.0f);
			this.usernameField.setFont(font);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		String savedUsername = this.saver.get("username") == null ? "" : this.saver.get("username");
		this.usernameField.setText(savedUsername);

		if (!savedUsername.isEmpty()) {
			this.tempUserImage = this.updateTextFieldImage(this.usernameField, this.usernameFieldImage);
			this.repaint();
		}

		this.usernameField.setHorizontalAlignment(JTextField.CENTER);

		this.usernameField.getDocument().addDocumentListener(new SimplifiedDocumentListener(a -> {
			this.tempUserImage = this.updateTextFieldImage(this.usernameField, this.usernameFieldImage);
			this.repaint();
		}));

		this.usernameField.addFocusListener(new SimplifiedFocusListener(a -> {
			this.tempUserImage = this.updateTextFieldImage(this.usernameField, this.usernameFieldImage);
			this.repaint();
		}));

		this.add(this.usernameField);

		// Password field

		this.passwordField.setBounds(181, 374, 400, 54);
		this.passwordField.setOpaque(false);
		this.passwordField.setBorder(null);
		this.passwordField.setFont(this.passwordField.getFont().deriveFont(20f));

		String savedPassword = this.saver.get("password");
		String decodedPassword = (savedPassword != null) ? new String(Base64.getDecoder().decode(savedPassword)) : "";
		this.passwordField.setText(decodedPassword);

		if (!decodedPassword.isEmpty()) {
			this.tempPassImage = this.updateTextFieldImage(this.passwordField, this.passwordFieldImage);
			this.repaint();
		}

		this.passwordField.setHorizontalAlignment(JTextField.CENTER);

		this.passwordField.getDocument().addDocumentListener(new SimplifiedDocumentListener(a -> {
			this.tempPassImage = this.updateTextFieldImage(this.passwordField, this.passwordFieldImage);
			this.repaint();
		}));

		this.passwordField.addFocusListener(new SimplifiedFocusListener(a -> {
			this.tempPassImage = this.updateTextFieldImage(this.passwordField, this.passwordFieldImage);
			this.repaint();
		}));

		this.passwordField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginPanel.this.play();
			}
		});

		this.add(passwordField);

		// Play button

		this.playButton.setBounds(246, 550);
		this.playButton.setTextureDisabled(this.playButton.getTextureHover());
		this.playButton.addEventListener(this);

		this.add(this.playButton);

		// Quit button

		this.quitButton.setBounds(637, 113);
		this.quitButton.addEventListener(this);

		this.add(this.quitButton);

		// Progress bar

		this.progressBar.setStringPainted(true);
		this.progressBar.setFont(this.usernameField.getFont().deriveFont(JavaVersionUtil.getJavaVersion() > 8 ? 13.5f : 7.0f));
		this.progressBar.setBounds(150, 670, 449, 28);

		this.add(this.progressBar);

		if (JavaVersionUtil.getJavaVersion() <= 8) {
			this.setMessage("Veuillez mettre à jour votre version de Java.", Color.RED);

			this.timer = new Timer(5000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginPanel.this.setMessage("Veuillez entrer vos identifiants.", Color.WHITE);
				}
			});

			timer.setRepeats(false);
			timer.start();
		} else
			this.setMessage("Veuillez entrer vos identifiants.", Color.WHITE);
	}

	private Image tempUserImage = this.usernameFieldImage;
	private Image tempPassImage = this.passwordFieldImage;

	private Image updateTextFieldImage(JTextField jTextField, Image originalImage) {
		return jTextField.getText().isEmpty() && !jTextField.hasFocus() ? originalImage : emptyFieldImage;
	}

	public void setMessage(String message, Color color) {
		this.progressBar.setString(message);
		this.progressBar.setForeground(color);
	}

	public void setFieldEnabled(boolean enable) {
		this.usernameField.setEnabled(enable);
		this.passwordField.setEnabled(enable);

		this.playButton.setEnabled(enable);
	}

	@Override
	public void onEvent(SwingerEvent event) {
		if (event.getSource() == this.playButton) {
			this.play();
			return;
		}

		if (event.getSource() == this.quitButton) {
			this.frame.fadeOut();
			return;
		}
	}

	private void play() {
		if (this.timer != null)
			this.timer.stop();

		this.setFieldEnabled(false);

		char[] passwordChars = this.passwordField.getPassword();

		if (this.usernameField.getText().trim().length() == 0 || new String(passwordChars).length() == 0) {
			this.setMessage("Identifiants incorrects.", Color.RED);
			this.setFieldEnabled(true);
			return;
		}

		Thread thread = new Thread() {
			@Override
			public void run() {
				AuthInfos auth = null;

				try {
					char[] passwordChars = LoginPanel.this.passwordField.getPassword();
					auth = LoginPanel.this.frame.getAuth().auth(LoginPanel.this.usernameField.getText(), new String(passwordChars));
				} catch (IOException e) {
					e.printStackTrace();
					LoginPanel.this.setMessage(e.getMessage(), Color.RED);
					LoginPanel.this.setFieldEnabled(true);
					return;
				}

				LoginPanel.this.saver.set("username", LoginPanel.this.usernameField.getText());
				LoginPanel.this.saver.set("password", Base64.getEncoder().encodeToString(new String(LoginPanel.this.passwordField.getPassword()).getBytes()));
				LoginPanel.this.saver.save();

				LoginPanel.this.setMessage("Mise à jour des fichiers du jeu...", Color.WHITE);

				try {
					LoginPanel.this.frame.getUpdate().update();
				} catch (Exception e) {
					e.printStackTrace();
					LoginPanel.this.setMessage("Impossible de mettre à jour : " + e.getMessage(), Color.RED);
					LoginPanel.this.setFieldEnabled(true);
					LoginPanel.this.frame.getUpdate().interrupt();
					return;
				}

				LoginPanel.this.setMessage("Lancement du jeu...", Color.WHITE);

				try {
					LoginPanel.this.frame.getLaunch().launch(auth);
				} catch (LaunchException | InterruptedException e) {
					e.printStackTrace();
					LoginPanel.this.setMessage(e.getMessage(), Color.RED);
					LoginPanel.this.setFieldEnabled(true);
					return;
				}
			}
		};

		thread.start();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Swinger.drawFullsizedImage(graphics, this, this.backgroundImage);

		graphics.drawImage(this.tempUserImage, 172, 275, 417, 72, this);
		graphics.drawImage(this.tempPassImage, 172, 365, 417, 72, this);
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

}