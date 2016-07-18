package Threads;

class Thread_TimeKeeper implements Runnable{

	private final Thread_Searcher	Owner;

	private boolean			threadSuspended;
	private Thread			t			= null;
	//int i = 0;
	int				Interval	= 999;

	public Thread_TimeKeeper( Thread_Searcher thread_Searcher ) {

		this.Owner = thread_Searcher;

		start();
	}

	public void Startup() {

	}

	public void init() {

	}

	public void destroy() {
	}

	public void start() {
		if ( t == null ) {
			t = new Thread( this );
			threadSuspended = false;
			t.start();
		}
		else {
			if ( threadSuspended ) {
				threadSuspended = false;
				synchronized ( this ) {
					notify();
				}
			}
		}
	}

	public void stop() {
		threadSuspended = true;
	}

	public void run() {

		try {
			while ( true ) {

				if ( threadSuspended ) {
					synchronized ( this ) {
						while ( threadSuspended ) {
							wait();
						}
					}
				}
				Owner.TimePast();
				threadSuspended = true;
			}
		}
		catch ( InterruptedException T ) {
			System.err.println( "Thread_TimeKeeper" );
			System.err.println( "Throwable message: " + T.getMessage() );
			System.err.println( "Throwable cause: " + T.getCause() );
			System.err.println( "Throwable class: " + T.getClass() );
		}
	}

}
