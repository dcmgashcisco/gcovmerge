package gcovmerge;

/**
 * GcovMergeDetail: holds data for presentation about merging gcov units
 *
 */
public class GcovMergeDetail {

	/** sourceCoverage. */
	private transient int totalLines;
	/** toMergeCoverage. */
	private transient int usableLines;
	/** mergedCoverage. */
	private transient int coveredLines;
	
	
	/**
	 * @param coverage
	 */
	public void incrementLines(final Integer coverage) {
		totalLines += 1;
		if (coverage != null) {
			usableLines += 1;
			if (coverage > 0) {
				coveredLines += 1;
			}
		}
	}
	
	public void addTotals(final GcovMergeDetail detail) {
		totalLines += detail.totalLines;
		usableLines += detail.usableLines;
		coveredLines += detail.coveredLines;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		int percentage = 0;
		if (usableLines > 0) {
			percentage = (100 * coveredLines) / usableLines;
		}
		return  totalLines + ", " +
				usableLines + ", " +
				coveredLines + ", " +
				percentage;
		
	}
}
