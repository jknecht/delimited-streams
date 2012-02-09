package com.jeffknecht.ds;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Push parser implementation for delimited input streams.
 * 
 * @author jeff
 *
 */
public class DelimitedStreamParser {
	Reader input;
	DelimitedStreamParserEventHandler eventHandler;
	char fieldDelimiter;
	char recordDelimiter;
	boolean useStringIdentifier = false;
	char stringIdentifier;

	public DelimitedStreamParser(char fieldDelimiter, char recordDelimiter, InputStream input, DelimitedStreamParserEventHandler eventHandler) {
		this.input = new InputStreamReader(input);
		this.eventHandler = eventHandler;
		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
	}

	public DelimitedStreamParser(char fieldDelimiter, char recordDelimiter, char stringIdentifier, InputStream input, DelimitedStreamParserEventHandler eventHandler) {
		this.input = new InputStreamReader(input);
		this.eventHandler = eventHandler;
		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
		this.stringIdentifier = stringIdentifier;
		this.useStringIdentifier = true;
	}

	public DelimitedStreamParser(char fieldDelimiter, char recordDelimiter, Reader input, DelimitedStreamParserEventHandler eventHandler) {
		this.input = input;
		this.eventHandler = eventHandler;
		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
	}

	public DelimitedStreamParser(char fieldDelimiter, char recordDelimiter, char stringIdentifier, Reader input, DelimitedStreamParserEventHandler eventHandler) {
		this.input = input;
		this.eventHandler = eventHandler;
		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
		this.stringIdentifier = stringIdentifier;
		this.useStringIdentifier = true;
	}
	

	public void parse() throws IOException {
		eventHandler.startStream();
		boolean inString = false;
		boolean atBeginningOfField = true;
		int b = 0;
		while ((b = input.read()) != -1) {
			//if this is a record delimiter, then it is also a column delimiter
			if (b == recordDelimiter) {
				eventHandler.endField();
				eventHandler.endRecord();
				atBeginningOfField = true;
				inString = false;
				continue;
			}

			if (b == fieldDelimiter && !inString) {
				eventHandler.endField();
				atBeginningOfField = true;
				continue;
			}

			if (useStringIdentifier && b == stringIdentifier) {
				if (atBeginningOfField) {
					atBeginningOfField = false;
					inString = true;
					continue;
				} else if (inString) {
					inString = false;
					atBeginningOfField = false;
					continue;
				} else {
					atBeginningOfField = false;
					eventHandler.character((char) b);
				}
			} else {
				atBeginningOfField = false;
				eventHandler.character((char) b);
			}
		}
		eventHandler.endStream();
	}
	

}
