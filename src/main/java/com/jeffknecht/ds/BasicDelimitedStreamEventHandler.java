package com.jeffknecht.ds;

import java.util.ArrayList;


public class BasicDelimitedStreamEventHandler implements DelimitedStreamParserEventHandler {
	ArrayList<DelimitedStreamEventListener> listeners = new ArrayList<DelimitedStreamEventListener>();
	
	StringBuilder field = new StringBuilder();
	private ArrayList<String> record = new ArrayList<String>();
	
	public void addListener(DelimitedStreamEventListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(DelimitedStreamEventListener listener) {
		this.listeners.remove(listener);
	}
	
	public void startStream() {
		record = new ArrayList<String>();
		field = new StringBuilder();
	}

	public void character(char c) {
		if (!Character.isISOControl(c))
			field.append(c);
	}

	public void endField() {
		String value = field.toString();
		record.add(value);
		for (DelimitedStreamEventListener listener : listeners) {
			listener.field(value);
		}
		
		field = new StringBuilder();
	}

	public void endRecord() {
		for (DelimitedStreamEventListener listener : listeners) {
			listener.record(record);
		}
		
		record = new ArrayList<String>();
	}

	public void endStream() {
		if (record.size() > 0) {
			endRecord();
		}
		
		for (DelimitedStreamEventListener listener : listeners) {
			listener.endOfStream();
		}
		
	}


}
