package Threads;

import java.net.HttpURLConnection;

class Thread_Searcher extends Thread{

	private HttpURLConnection	connection;
	int					RelationValue	= 0;

	Thread_Searcher getThread_Searcher() {
		return this;
	}

	public void TimePast() {
		if ( connection != null ) {
			connection.disconnect();
		}
	}
}
