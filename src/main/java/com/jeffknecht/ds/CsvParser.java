package com.jeffknecht.ds;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;


public class CsvParser {
	DelimitedStreamParser parser;
	
	public CsvParser(InputStream input, DelimitedStreamEventListener listener) {
		BasicDelimitedStreamEventHandler handler = new BasicDelimitedStreamEventHandler();
		handler.addListener(listener);
		this.parser = new DelimitedStreamParser(',', '\n', '"', input, handler);
	}
	
	public CsvParser(Reader input, DelimitedStreamEventListener listener) {
		BasicDelimitedStreamEventHandler handler = new BasicDelimitedStreamEventHandler();
		handler.addListener(listener);
		this.parser = new DelimitedStreamParser(',', '\n', '"', input, handler);
	}
	
	public void parse() throws IOException {
		parser.parse();
	}

}
