package gcovmerge;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gcovmerge.GcovLineData;
import gcovmerge.GcovMergeDetail;
import gcovmerge.MergeException;

public class TestGcovLineData {
	private static final String COVERED_4 = "        4:   16:#include \"EapFramework.h\"";
	private static final String UNCOVERED = "    #####:   16:#include \"EapFramework.h\"";
	private static final String UNREACHABLE = "        -:   16:#include \"EapFramework.h\"";
	
	@Test
	void TestGcovLineUnreachable() {
		GcovLineData lineData = GcovLineData.gcovLineDataFactory(UNREACHABLE);
		assertEquals(lineData.getCoverage(), null);
		assertEquals(lineData.getNumber(), 16);
		assertEquals(lineData.getSource(), "#include \"EapFramework.h\"");
	}
	
	@Test
	void TestGcovLineUncovered() {
		GcovLineData lineData = GcovLineData.gcovLineDataFactory(UNCOVERED);
		assertEquals(lineData.getCoverage(), new Integer(0));
		assertEquals(lineData.getNumber(), 16);
		assertEquals(lineData.getSource(), "#include \"EapFramework.h\"");
	}
	
	@Test
	void TestGcovLineWithCoverage() {
		GcovLineData lineData = GcovLineData.gcovLineDataFactory(COVERED_4);
		assertEquals(lineData.getCoverage(), new Integer(4));
		assertEquals(lineData.getNumber(), 16);
		assertEquals(lineData.getSource(), "#include \"EapFramework.h\"");
	}
	
	@Test
	void TestGcovLineNoSource() {
		
		GcovLineData lineData = GcovLineData.gcovLineDataFactory("        4:   16:");
		assertEquals(lineData.getCoverage(), new Integer(4));
		assertEquals(lineData.getNumber(), 16);
		assertEquals(lineData.getSource(), "");		
	}
	
	@Test
	void TestGcovLineMergeUnreachableWithUnreachable() {
		coverageLineMergeTest(
				UNREACHABLE,
				UNREACHABLE,
				null);
	}

	@Test
	void TestGcovLineMergeUnreachableWithUncovered() {
		coverageLineMergeTest(
				UNREACHABLE,
				UNCOVERED,
				new Integer(0));
	}
	
	@Test
	void TestGcovLineMergeUnreachableWithCovered() {
		coverageLineMergeTest(
				UNREACHABLE,
				COVERED_4,
				new Integer(4));

	}
	
	@Test
	void TestGcovLineMergeUncoveredWithUnreachable() {
		coverageLineMergeTest(
				UNCOVERED,
				UNREACHABLE,
				new Integer(0));

	}
	
	@Test
	void TestGcovLineMergeUncoveredWithUncovered() {
		coverageLineMergeTest(
				UNCOVERED,
				UNCOVERED,
				new Integer(0));
	}
	
	@Test
	void TestGcovLineMergeUncoveredWithCovered() {
		coverageLineMergeTest(
				UNCOVERED,
				COVERED_4,
				new Integer(4));
	}
	
	@Test
	void TestGcovLineMergeCoveredWithUncovered() {
		coverageLineMergeTest(
				COVERED_4,
				UNCOVERED,
				new Integer(4));
	}
	
	@Test
	void TestGcovLineMergeCoveredWithUnreachable() {
		coverageLineMergeTest(
				COVERED_4,
				UNREACHABLE,
				new Integer(4));
	}
	
	@Test
	void TestGcovLineMergeCoveredWithCovered() {
		coverageLineMergeTest(
				COVERED_4,
				COVERED_4,
				new Integer(8));
	}
	
	private void coverageLineMergeTest(String fileLine, String toMerge, Integer expected) {
		
		GcovLineData lineData = GcovLineData.gcovLineDataFactory(fileLine);
		GcovLineData lineToMerge = GcovLineData.gcovLineDataFactory(toMerge);
		final GcovMergeDetail sourceDetail = new GcovMergeDetail();
		final GcovMergeDetail mergeDetail = new GcovMergeDetail();
		lineData.mergeFrom(lineToMerge, sourceDetail, mergeDetail);
		assertEquals(lineData.getCoverage(), expected);
	}
	
	@Test
	void TestGcovMismatchLine() {
		
		Assertions.assertThrows(
				MergeException.class,
				() -> {
					GcovLineData lineData = GcovLineData.gcovLineDataFactory("        4:   16:#include \"EapFramework.h\"");
					GcovLineData lineToMerge = GcovLineData.gcovLineDataFactory("        4:   17:#include \"EapFramework.h\"");
					final GcovMergeDetail sourceDetail = new GcovMergeDetail();
					final GcovMergeDetail mergeDetail = new GcovMergeDetail();
					lineData.mergeFrom(lineToMerge, sourceDetail, mergeDetail);
				});
	}
	
	@Test
	void TestGcovMismatchSource() {
		
		Assertions.assertThrows(
				MergeException.class,
				() -> {
		
					GcovLineData lineData = GcovLineData.gcovLineDataFactory("        4:   16:#include \"EapFramework.h\"");
					GcovLineData lineToMerge = GcovLineData.gcovLineDataFactory("        4:   16:#include \"EapFork.h\"");
					final GcovMergeDetail sourceDetail = new GcovMergeDetail();
					final GcovMergeDetail mergeDetail = new GcovMergeDetail();
					lineData.mergeFrom(lineToMerge, sourceDetail, mergeDetail);
		
				});
	}
	
	@Test
	void TestGcovLineNoCoveragePrefix() {
		
		Assertions.assertThrows(
				MergeException.class,
				() -> {
					GcovLineData.gcovLineDataFactory("#include \"EapFramework.h\""); 
				});
		
	}
	
	@Test
	void TestGcovLineNoCoverage() {
		
		Assertions.assertThrows(
				MergeException.class,
				() -> {
					GcovLineData.gcovLineDataFactory(":   16:#include \"EapFramework.h\""); 
				});
		
	}
	
	@Test
	void TestGcovLineNoLine() {
		Assertions.assertThrows(
				MergeException.class,
				() -> {
					GcovLineData.gcovLineDataFactory("        4::#include \"EapFramework.h\"");  
				});
	}
	

	
	@Test
	void TestGcovLineNoSourceSeparator() {
		Assertions.assertThrows(
				MergeException.class,
				() -> {
					GcovLineData.gcovLineDataFactory("        4:   16");  
				});

	}
	
}
