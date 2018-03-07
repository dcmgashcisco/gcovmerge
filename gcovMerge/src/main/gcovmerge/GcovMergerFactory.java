package gcovmerge;

/**
 * gcovmergerFactory.
 *
 */
public class GcovMergerFactory implements IMergerFactory {

	/**
	 * gcovmergerFactory.
	 */
	public GcovMergerFactory() {
		super();
	}

	/* (non-Javadoc)
	 * @see gcovmerge.IMergerFactory#getMerger()
	 */
	@Override
	public IMerger getMerger() {
		return new GcovFileMerger();
	}
}
