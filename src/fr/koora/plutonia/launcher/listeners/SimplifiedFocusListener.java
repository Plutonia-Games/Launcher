package fr.koora.plutonia.launcher.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;

public class SimplifiedFocusListener implements FocusListener {

	private final Consumer<FocusEvent> action;

	public SimplifiedFocusListener(Consumer<FocusEvent> action) {
		this.action = action;
	}

	@Override
	public void focusGained(FocusEvent event) {
		this.action.accept(event);
	}

	@Override
	public void focusLost(FocusEvent event) {
		this.action.accept(event);
	}

}