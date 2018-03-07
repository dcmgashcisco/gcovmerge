package gcovmerge;


/**
 * StdOutLog: accepts log entries and serialises them when required.
 *
 */
public class StdOutLog implements ILog {


	/**
	 * logItem.
	 * @param logItem
	 */
	public void logItem(final LogItem logItem) {
		final String printable = logItem.toString();
		System.out.println(printable);
	}
}
