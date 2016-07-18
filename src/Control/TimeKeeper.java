package Control;

import java.awt.Graphics;

class TimeKeeper implements Runnable{

	private final Controller	Class_Controller;
	int			i			= 0;

	private boolean		threadSuspended;
	private Thread		t			= null;
	private int			Interval	= 0;
	private int			WaitFor		= 0;

	public TimeKeeper( Controller Class_Controller, int Interval ) {
		//System.out.println("TimeKeeper created");
		this.Class_Controller = Class_Controller;
		this.Interval = Interval;
		this.WaitFor = Interval;

		start();
	}

	public void Startup() {

		start();
	}

	public void init() {

	}

	public void destroy() {
	}

	void start() {

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

				WaitFor--;
				if ( WaitFor <= 0 ) {
					Class_Controller.TimeTick( "Timekeeper" );
					WaitFor = Interval;

				}

				if ( threadSuspended ) {

					synchronized ( this ) {
						while ( threadSuspended ) {

							Class_Controller.TimeTick( "Timekeeper" );
							wait();
						}
					}
				}

				t.sleep( Interval ); // interval given in milliseconds
			}
		}
		catch ( InterruptedException T ) {
			System.out.println( "Kunne ikke loope timer" );
			System.out.println( "Throwable message: " + T.getMessage() );
			System.out.println( "Throwable cause: " + T.getCause() );
			System.out.println( "Throwable class: " + T.getClass() );
		}
	}

	public void paint( Graphics g ) {

	}
}
