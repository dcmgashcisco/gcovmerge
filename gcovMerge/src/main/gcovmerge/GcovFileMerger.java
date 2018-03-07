package gcovmerge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * GcovFileMerger: Specifically merges Gcov data.
 *
 */
public class GcovFileMerger implements IMerger {

	/** NEWLINE. */
	private static final String NEWLINE = "\n";
	/** lines. */
	private final List <GcovLineData> lines;
	
	/**
	 * GcovFileMerger.
	 */
	public GcovFileMerger() {
		lines = new ArrayList<> ();
	}

	/* (non-Javadoc)
	 * @see gcovmerge.IMerger#fromString(java.lang.String)
	 */
	public void fromString(final String fileData) {
		lines.clear();
		if (fileData != null) {
			final String[] fileLines = fileData.split(NEWLINE);
			for (final String fileLine:fileLines) {
				final GcovLineData gcovLineData = GcovLineData.gcovLineDataFactory(fileLine);
				if (null != gcovLineData) {
					lines.add(gcovLineData);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see gcovmerge.IMerger#mergeFrom(gcovmerge.IMerger)
	 */
	public void mergeFrom(
			final IMerger fileToMergeFrom,
			final GcovMergeDetail sourceDetail,
			final GcovMergeDetail mergeDetail) throws MergeException {
				
		final Iterator <GcovLineData> myLines = lines.iterator();
		for (final GcovLineData line : fileToMergeFrom.getLines()) {
			final GcovLineData myLine = myLines.next();
			myLine.mergeFrom(line, sourceDetail, mergeDetail);
		}
	}
	
	/* (non-Javadoc)
	 * @see gcovmerge.IMerger#summarize()
	 */
	public void summarize(
			final GcovMergeDetail sourceDetail) throws MergeException {

		for (final GcovLineData line : lines) {
			sourceDetail.incrementLines(line.getCoverage());
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		final StringBuffer file = new StringBuffer();
		for (final GcovLineData line : lines) {
			if (file.length()!=0) {
				file.append(NEWLINE);
			}
			file.append(line.toString());
			
		}
		return file.toString();
	}
	
	/* (non-Javadoc)
	 * @see gcovmerge.IMerger#getLines()
	 */
	public List <GcovLineData> getLines() {
		return lines;
	}

}
