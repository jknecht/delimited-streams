package com.jeffknecht.ds;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class GenericDelimitedReader {
	Reader input;
	char fieldDelimiter;
	char recordDelimiter;
	boolean useStringIdentifier = false;
	char stringIdentifier;
	boolean eof = false;
	boolean endOfRecord = false;
	
	public GenericDelimitedReader(char fieldDelimiter, char recordDelimiter, InputStream input) {
		this.input = new InputStreamReader(input);
		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
	}

	public GenericDelimitedReader(char fieldDelimiter, char recordDelimiter, char stringIdentifier, InputStream input) {
		this.input = new InputStreamReader(input);
		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
		this.stringIdentifier = stringIdentifier;
		this.useStringIdentifier = true;
	}
	
	public GenericDelimitedReader(char fieldDelimiter, char recordDelimiter, Reader input) {
		this.input = input;
		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
	}

	public GenericDelimitedReader(char fieldDelimiter, char recordDelimiter, char stringIdentifier, Reader input) {
		this.input = input;
		this.fieldDelimiter = fieldDelimiter;
		this.recordDelimiter = recordDelimiter;
		this.stringIdentifier = stringIdentifier;
		this.useStringIdentifier = true;
	}

	public boolean isEof() {
		return eof;
	}

	public boolean isEndOfRecord() {
		return endOfRecord;
	}

	public String readString() throws IOException {
		endOfRecord = false;
		boolean stop = false;
		StringBuilder sb = new StringBuilder();
		boolean inString = false;
		int b = 0;
		while (!stop && (b = input.read()) != -1) {
			if (b == recordDelimiter) {
				endOfRecord = true;
				stop = true;
				continue;
			}

			if (b == fieldDelimiter && !inString) {
				stop = true;
				continue;
			}

			if (useStringIdentifier && b == stringIdentifier) {
				if (sb.length() == 0) {
					inString = true;
					continue;
				} else if (inString) {
					inString = false;
					continue;
				} else {
					if (!Character.isISOControl((char) b)) {
						sb.append((char) b);
					}
				}
			} else {
				if (!Character.isISOControl((char) b)) {
					sb.append((char) b);
				}
			}
		}
		
		if (b == -1) {
			endOfRecord = true;
			eof = true;
		}
		
		return sb.toString();
	}
	
	public Integer readInteger() throws NumberFormatException, IOException {
		String s = readString();
		if (s == null || s.trim().length() == 0) {
			return null;
		} else {
			return Integer.valueOf(s);
		}
	}
	
	public Long readLong() throws NumberFormatException, IOException {
		String s = readString();
		if (s == null || s.trim().length() == 0) {
			return null;
		} else {
			return Long.valueOf(s);
		}
	}
	
	public Short readShort() throws NumberFormatException, IOException {
		String s = readString();
		if (s == null || s.trim().length() == 0) {
			return null;
		} else {
			return Short.valueOf(s);
		}
	}

	public Float readFloat() throws NumberFormatException, IOException {
		String s = readString();
		if (s == null || s.trim().length() == 0) {
			return null;
		} else {
			return Float.valueOf(s);
		}
	}

	public Double readDouble() throws NumberFormatException, IOException {
		String s = readString();
		if (s == null || s.trim().length() == 0) {
			return null;
		} else {
			return Double.valueOf(s);
		}
	}

	public Boolean readBoolean() throws NumberFormatException, IOException {
		String s = readString();
		if (s == null || s.trim().length() == 0) {
			return null;
		} else {
			return Boolean.valueOf(s);
		}
	}

	public BigInteger readBigInteger() throws NumberFormatException, IOException {
		String s = readString();
		if (s == null || s.trim().length() == 0) {
			return null;
		} else {
			return new BigInteger(s);
		}
	}

	public BigDecimal readBigDecimal() throws NumberFormatException, IOException {
		String s = readString();
		if (s == null || s.trim().length() == 0) {
			return null;
		} else {
			return new BigDecimal(s);
		}
	}
}
