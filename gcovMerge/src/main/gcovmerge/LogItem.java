package gcovmerge;

/**
 * LogItem: an item to log a merging operation. 
 *
 */
public class LogItem {

	/** logLine. */
	private transient final String logLine;

	
	/**
	 * @param sourcePath
	 * @param mergeToPath
	 * @param mergeToExists
	 * @param summary
	 */
	public LogItem(
			final String sourcePath,
			final String mergeToPath,
			final boolean mergeToExists,
			final GcovMergeDetail sourceSummary,
			final GcovMergeDetail mergeSummary) {
		
		logLine = sourcePath + ", " +
				   mergeToPath + ", " +
				   (mergeToExists ? "YES" : "NO") + ", " +
				   sourceSummary.toString() + ", " +
				   mergeSummary.toString();
	}
	
	public LogItem () {
		logLine = "Source Path, " +
			"Merge Path, " + 
		    "Merge Exists, " +
		    "Source Total LOC, " +
		    "Source Reachable LOC, " + 
		    "Source Covered LOC, " +
		    "Source Percentage Reachable Covered, " +
		    "Merged Total LOC, " +
		    "Merged Reachable LOC, " + 
		    "Merged Covered LOC, " + 
		    "Merged Percentage Reachable Covered"; 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return logLine;
	}

}
