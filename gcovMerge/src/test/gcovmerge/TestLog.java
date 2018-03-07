package gcovmerge;


import org.junit.jupiter.api.Test;

import gcovmerge.ILog;
import gcovmerge.LogItem;
import gcovmerge.StdOutLog;

class TestLog {

	@Test
	void BasicLogTest() {
		ILog log = new StdOutLog();
		
		final GcovMergeDetail sourceDetail = new GcovMergeDetail();
		final GcovMergeDetail mergeDetail = new GcovMergeDetail();
		
		log.logItem(new LogItem("/opt/source", "/opt/dest/", true, sourceDetail, mergeDetail));
		log.logItem(new LogItem("/opt/source", "/opt/dest/", false, sourceDetail, mergeDetail));
		

		
	}

}
