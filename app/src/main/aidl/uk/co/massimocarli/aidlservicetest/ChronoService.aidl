// ChronoService.aidl
package uk.co.massimocarli.aidlservicetest;

interface ChronoService {

	// Starts the Chrono if not already done
	void start();

	// Stops the Chrono if not already done
	void stop();

	// Resets the Chrono
	void reset();

	// Set the time of the chrono
	void setTime(long time);

	// Return the time of the Chrono
	long getTime();
}
