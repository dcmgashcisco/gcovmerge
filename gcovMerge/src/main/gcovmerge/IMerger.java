package gcovmerge;

import java.util.List;

/**
 * IMerger: Interface for a merging operator.
 *
 */
public interface IMerger {
	/**
	 * @param unitData
	 */
	void fromString(
			final String unitData);
	/**
	 * @return
	 */
	String toString();
	/**
	 * @param unitToMergeFrom
	 * @return
	 * @throws MergeException
	 */
	void mergeFrom(
			final IMerger unitToMergeFrom,
			final GcovMergeDetail sourceDetail,
			final GcovMergeDetail mergeDetail) throws MergeException;
	/**
	 * @return
	 * @throws MergeException
	 */
	void summarize(
			final GcovMergeDetail sourceDetail) throws MergeException;
	/**
	 * @return
	 */
	List <GcovLineData> getLines();
}
