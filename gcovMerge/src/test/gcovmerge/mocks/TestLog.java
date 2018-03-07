package gcovmerge.mocks;

import java.util.ArrayList;
import java.util.List;

import gcovmerge.ILog;
import gcovmerge.LogItem;

public class TestLog implements ILog {

	List <LogItem> items = new ArrayList <> ();
	
	/**
	 * @param logItem
	 */
	public void logItem(final LogItem logItem) {
		items.add(logItem);
	}
}
