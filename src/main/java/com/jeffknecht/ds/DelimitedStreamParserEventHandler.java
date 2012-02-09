package com.jeffknecht.ds;

public interface DelimitedStreamParserEventHandler {

	void startStream();

	void character(char c);

	void endField();

	void endRecord();

	void endStream();

}
