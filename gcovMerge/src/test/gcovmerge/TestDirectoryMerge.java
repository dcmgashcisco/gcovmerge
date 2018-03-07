package gcovmerge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import gcovmerge.Config;
import gcovmerge.FolderCompareProcess;
import gcovmerge.GcovFileMerger;
import gcovmerge.GcovLineData;
import gcovmerge.GcovMergerFactory;
import gcovmerge.ILog;
import gcovmerge.IMergerFactory;
import gcovmerge.StdOutLog;
import gcovmerge.mocks.TestMedia;

class TestDirectoryMerge {

	private static final String TEST_FILE_PATH = "/opt/dest/feature1/component1/file1_1_2";

	@Test
	void test() {
		
		IMergerFactory mergerFactory = new GcovMergerFactory();
		final TestMedia media= new TestMedia();
		ILog log = new StdOutLog();
		
		FolderCompareProcess dmp = new FolderCompareProcess(
				mergerFactory,
				media,
				log);
		
		String[] args = {"-d", "/opt/source", "/opt/dest" };
		Config config = Config.createFrom(args);
		
		String data0 = media.read(TEST_FILE_PATH);
		assertTrue(data0 == null);
		
		dmp.execute(config);
		testMergeExecution(media, 1);
		
		dmp.execute(config);
		testMergeExecution(media, 2);
	}

	private void testMergeExecution(
			final TestMedia media,
			int expected) {
		
		String data2 = media.read(TEST_FILE_PATH);
		GcovFileMerger merger2 = new GcovFileMerger();
		merger2.fromString(data2);
		List <GcovLineData> lines = merger2.getLines();
		GcovLineData dataLine = lines.get(0);
		int coverage = dataLine.getCoverage();
		
		assertEquals(coverage, expected);
	}

}
