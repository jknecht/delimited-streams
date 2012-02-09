package com.jeffknecht.ds;

import java.io.InputStream;
import java.io.Reader;

public class CsvReader extends GenericDelimitedReader {
	
	public CsvReader(InputStream in) {
		super(',', '\n', '"', in);
	}
	
	public CsvReader(Reader in) {
		super(',', '\n', '"', in);		
	}
}
