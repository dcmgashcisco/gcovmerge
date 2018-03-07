package gcovmerge;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import gcovmerge.Config;
import gcovmerge.GcovFileMerger;
import gcovmerge.GcovLineData;
import gcovmerge.GcovMergerFactory;
import gcovmerge.IMergerFactory;
import gcovmerge.UnitCompareProcess;
import gcovmerge.mocks.TestMedia;

class TestUnitMerge {

	private static final String SOURCE_FILE_PATH = "/opt/source/feature1/component1/file1_1_1";
	private static final String DEST_FILE_PATH = "/opt/source/feature1/component1/file1_1_2";
	
	@Test
	void TestUnit() {
		IMergerFactory mergerFactory = new GcovMergerFactory();
		final TestMedia media= new TestMedia();

		UnitCompareProcess dmp = new UnitCompareProcess(
				mergerFactory,
				media);
		
		String[] args = {"-f", SOURCE_FILE_PATH, DEST_FILE_PATH };
		Config config = Config.createFrom(args);
		
		testMergeExecution(media, 1);
		
		dmp.execute(config);
		testMergeExecution(media, 2);
		
		dmp.execute(config);
		testMergeExecution(media, 3);
	}
	
	private void testMergeExecution(
			final TestMedia media,
			int expected) {
		
		String data2 = media.read(DEST_FILE_PATH);
		GcovFileMerger merger2 = new GcovFileMerger();
		merger2.fromString(data2);
		List <GcovLineData> lines = merger2.getLines();
		GcovLineData dataLine = lines.get(0);
		int coverage = dataLine.getCoverage();
		
		assertEquals(coverage, expected);
	}

}
