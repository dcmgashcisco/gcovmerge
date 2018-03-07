package gcovmerge;

/**
 * GcovLineData represents and merges a line of gcov file.
 *
 */
public final class GcovLineData {

	private static final int MIN_SEP = 2;
	/** coverage. */
	private transient Integer coverage;
	/** number. */
	private final int number;
	/** source. */
	private final String source;
	
	/** UNCOVERED. */
	private static final String UNCOVERED = "#####";
	/** SEP. */
	private static final String SEP = ":";
	/** UNREACHABLE. */
	private static final String UNREACHABLE = "-";
	
	/**
	 * @param fileLine
	 * @return
	 */
	public static GcovLineData gcovLineDataFactory(final String fileLine) {		
		final String[] segments = fileLine.split(SEP);
		if (segments.length < MIN_SEP) {
			throw new MergeException("Invalid Gcov File Line (no separator for coverage): " + fileLine);
		}

		return new  GcovLineData(
				getCoverageCount(fileLine), 
				getLineNumber(fileLine),
				getSource(fileLine));
	}



	/**
	 * @param fileLine
	 * @return
	 */
	private static String getSource(final String fileLine) {
		String sourceParam = null;
		final int linePos = fileLine.indexOf(SEP);
		if (linePos != -1) {
			final int sourcePos = fileLine.indexOf(SEP, linePos + 1);
			if (sourcePos > linePos && fileLine.length() > sourcePos) {
				sourceParam = fileLine.substring(sourcePos + 1);
			} else {
				throw new MergeException("Invalid Gcov File Line: (no source data" + fileLine);
			}
		}
		return sourceParam;
	}



	/**
	 * @param fileLine
	 * @return
	 */
	private static int getLineNumber(final String fileLine) {
		final String[] segments = fileLine.split(":");
		final String numberString = segments[1].trim();
		if (numberString == null || numberString.isEmpty()) {
			throw new MergeException("Invalid Gcov File Line: (no coverage data)" + fileLine);
		}
		return Integer.parseInt(numberString);
	}



	/**
	 * @param fileLine
	 * @return
	 */
	private static Integer getCoverageCount(final String fileLine) {
		final String[] segments = fileLine.split(":");
		final String coverageString =segments[0].trim();
		if (coverageString == null || coverageString.isEmpty()) {
			throw new MergeException("Invalid Gcov File Line: (no coverage data" + fileLine);
		}
		
		Integer coverageParam;
		if (coverageString.equals(UNREACHABLE)) {
			coverageParam = null;
		} else if (coverageString.equals(UNCOVERED)) {
			coverageParam = 0;
		} else {
			coverageParam = Integer.parseInt(coverageString);
		}
		return coverageParam;
	}

	/**
	 * @param coverageParam
	 * @return
	 */
	private static String coverageToString(
			final Integer coverageParam) {
		return coverageParam==null ? UNREACHABLE : coverageParam == 0 ? UNCOVERED : String.format("%9s", coverageParam);
	}

	/**
	 * @param coverageParam
	 * @param numberParam
	 * @param sourceParam
	 */
	private GcovLineData(
			final Integer coverageParam,
			final int numberParam,
			final String sourceParam) {
		this.coverage = coverageParam;
		this.number = numberParam;
		this.source = sourceParam;
	}

	/**
	 * @param lineToMerge
	 * @param detail
	 * @throws MergeException
	 */
	public void mergeFrom(
			final GcovLineData lineToMerge,
			final GcovMergeDetail sourceDetail,
			final GcovMergeDetail mergeDetail) throws MergeException{
		
		if (number > 0) {	
			checkLineInvariants(lineToMerge);
			final Integer coverageToMerge = lineToMerge.getCoverage();
			sourceDetail.incrementLines(coverageToMerge);
			addCoverage(coverageToMerge);
			mergeDetail.incrementLines(coverage);
		}
	}

	/**
	 * addCoverage.
	 * @param coverageToMerge
	 */
	private void addCoverage(final Integer coverageToMerge) {
		if (coverageToMerge != null) {
			if (null == coverage) {
				coverage = 0;
			}
			coverage += coverageToMerge;
		}
	}

	/**
	 * @param lineToMerge
	 */
	private void checkLineInvariants(final GcovLineData lineToMerge) {
		if (!source.equals(lineToMerge.getSource())) {
			throw new MergeException("Differing source for line when trying to merge gcov files.");
		}
		
		if (number != lineToMerge.getNumber()) {
			throw new MergeException("Differing line for line when trying to merge gcov files.");
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		final String coverageString = coverageToString(coverage);
		return String.format("%9s:%5d:%s", coverageString, number, source);
	}

	/**
	 * @return
	 */
	protected Integer getCoverage() {
		return coverage;
	}

	/**
	 * @return
	 */
	protected int getNumber() {
		return number;
	}

	/**
	 * @return
	 */
	protected String getSource() {
		return source;
	}
}
