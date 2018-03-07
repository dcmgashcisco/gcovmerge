package gcovmerge;


/**
 * UnitCompareProcess: processes merging of units of data.
 *
 */
public class UnitCompareProcess {

	/** mergerFactory. */
	private transient final IMergerFactory mergerFactory;
	/** media. */
	private transient final IMedia media;
	
	/**
	 * @param factoryParam
	 * @param readerParam
	 * @param logParam
	 */
	public UnitCompareProcess(
			final IMergerFactory factoryParam,
			final IMedia readerParam
			) {
		
		mergerFactory = factoryParam;
		media = readerParam;
	}
	
	/**
	 * @param config
	 */
	public void execute(final Config config) {

		final IMerger source = mergerFactory.getMerger();			
		final IMerger mergeTarget = mergerFactory.getMerger();
		
		source.fromString(media.read(config.getInPath()));
		mergeTarget.fromString(media.read(config.getOutPath()));
		
		final GcovMergeDetail sourceDetail = new GcovMergeDetail();
		final GcovMergeDetail mergeDetail = new GcovMergeDetail();
		
		mergeTarget.mergeFrom(source, sourceDetail, mergeDetail);
		
		media.write(config.getOutPath(), mergeTarget.toString());
	}
}
