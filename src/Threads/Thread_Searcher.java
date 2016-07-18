package Threads;

import java.net.HttpURLConnection;

public class Thread_Searcher extends Thread{

	HttpURLConnection	connection;
	int					RelationValue	= 0;

	public Thread_Searcher getThread_Searcher() {
		return this;
	}

	public void TimePast() {
		if ( connection != null ) {
			connection.disconnect();
		}
	}
}
