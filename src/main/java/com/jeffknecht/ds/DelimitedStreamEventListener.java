package com.jeffknecht.ds;

import java.util.List;

public interface DelimitedStreamEventListener {
	void field(String value);
	void record(List<String> values);
	void endOfStream();
}
