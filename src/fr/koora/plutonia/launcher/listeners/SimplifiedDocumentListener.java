package fr.koora.plutonia.launcher.listeners;

import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimplifiedDocumentListener implements DocumentListener {

	private final Consumer<DocumentEvent> action;

	public SimplifiedDocumentListener(Consumer<DocumentEvent> action) {
		this.action = action;
	}

	@Override
	public void insertUpdate(DocumentEvent event) {
		this.action.accept(event);
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
		this.action.accept(event);
	}

	@Override
	public void changedUpdate(DocumentEvent event) {
		this.action.accept(event);
	}

}