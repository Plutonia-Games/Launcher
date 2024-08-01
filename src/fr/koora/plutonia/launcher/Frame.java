package fr.koora.plutonia.launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import fr.koora.plutonia.launcher.frames.LoginPanel;
import fr.koora.plutonia.launcher.workers.AuthWorker;
import fr.koora.plutonia.launcher.workers.LaunchWorker;
import fr.koora.plutonia.launcher.workers.UpdateWorker;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

public class Frame extends JFrame {

	private static final int TIMER_DELAY = 50;
	private static final float OPACITY_INCREMENT = 0.05f;

	private static final long serialVersionUID = 1L;

	private AuthWorker authWorker;

	public AuthWorker getAuth() {
		if (this.authWorker == null)
			this.authWorker = new AuthWorker();

		return this.authWorker;
	}

	private LaunchWorker launchWorker;

	public LaunchWorker getLaunch() {
		if (this.launchWorker == null)
			this.launchWorker = new LaunchWorker(this);

		return this.launchWorker;
	}

	private UpdateWorker updateWorker;

	public UpdateWorker getUpdate() {
		if (this.updateWorker == null)
			this.updateWorker = new UpdateWorker(this);

		return this.updateWorker;
	}

	private LoginPanel loginPanel;

	public LoginPanel getPanel() {
		return this.loginPanel;
	}

	public Frame() {
		System.setProperty("apple.awt.transparentTitleBar", "true");
		System.setProperty("apple.awt.fullWindowContent", "true");

		this.setTitle("Plutonia - Launcher");
		this.setSize(761, 824);
		this.setIconImage(Swinger.getResource("icon.png"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setBackground(Swinger.TRANSPARENT);
		this.setOpacity(0f);
		this.setContentPane(this.loginPanel = new LoginPanel(this));

		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);

		this.setVisible(true);
		this.requestFocusInWindow();

		this.fadeIn();
	}

	private void fadeIn() {
		Timer fadeInTimer = new Timer(TIMER_DELAY, new ActionListener() {
			private float opacity = 0.0f;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity += OPACITY_INCREMENT;
				setOpacity(Math.min(opacity, 1.0f));

				if (opacity >= 1.0f) {
					((Timer) e.getSource()).stop();
				}
			}
		});

		fadeInTimer.start();
	}

	public void fadeOut() {
		Timer fadeOutTimer = new Timer(TIMER_DELAY, new ActionListener() {
			private float opacity = 1.0f;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity -= OPACITY_INCREMENT;
				setOpacity(Math.max(opacity, 0.0f));

				if (opacity <= 0.0f) {
					((Timer) e.getSource()).stop();
					dispose();
					System.exit(0);
				}
			}
		});

		fadeOutTimer.start();
	}

}