package gcovmerge;

/**
 * FolderCompareProcess: Processes the Folders recursively to merge.
 *
 */
public class FolderCompareProcess {
	/** mergerFactory. */
	private transient final IMergerFactory mergerFactory;
	/** media. */
	private transient final IMedia media;
	/** log. */
	private transient final ILog log;

	/**
	 * @param factoryParam
	 * @param mediumParam
	 * @param logParam
	 */
	public FolderCompareProcess(
			final IMergerFactory factoryParam,
			final IMedia mediumParam,
			final ILog logParam) {
		
		mergerFactory = factoryParam;
		media = mediumParam;
		log = logParam;
	}
	
	/**
	 * @param config
	 */
	public void execute(final Config config) {		
		
		final GcovMergeDetail sourceTotal = new GcovMergeDetail();
		final GcovMergeDetail mergeTotal = new GcovMergeDetail();
		
		final boolean mergeToExists = media.unitExists(config.getOutPath());

		processSourceFolder(
				config.getInPath(),
				config.getOutPath(),
				sourceTotal,
				mergeTotal);
		
		log.logItem(new LogItem(
				config.getInPath(),
				config.getOutPath(),
				mergeToExists,
				sourceTotal,
				mergeTotal
				));
	}
	
	/**
	 * @param sourceRoot
	 * @param mergeToRoot
	 */
	private void processSourceFolder(
			final String sourceRoot,
			final String mergeToRoot,
			final GcovMergeDetail sourceTotal,
			final GcovMergeDetail mergeTotal) {
		
		final IMediaItem[] list = media.listUnits(sourceRoot);

		if (list != null) {
			for (final IMediaItem entry : list) {
				processFolderItem(entry, mergeToRoot,sourceTotal,
						mergeTotal);
			}
		}
	}

	/**
	 * @param sourceItem
	 * @param mergeToPath
	 */
	private void processFolderItem(
			final IMediaItem sourceItem,
			final String mergeToPath,
			final GcovMergeDetail sourceTotal,
			final GcovMergeDetail mergeTotal) {
		final String sourcePath = sourceItem.getAbsolutePath();
		if (sourceItem.isDirectory()) {
			processSourceFolder(
					sourcePath,
					mergeToPath + "/" + sourceItem.getName(),
					sourceTotal,
					mergeTotal);
		} else {
			processUnit(
					sourcePath,
					mergeToPath,
					sourceItem.getName(),
					sourceTotal,
					mergeTotal);
		}
	}
	
	/**
	 * @param sourceUnit
	 * @param mergeToRPath
	 * @param unitName
	 */
	private void processUnit(
			final String sourceUnit,
			final String mergeToRPath,
			final String unitName,
			final GcovMergeDetail sourceTotal,
			final GcovMergeDetail mergeTotal) {
		
		if (media.isUnitType(sourceUnit)) {
			media.createDestDirectoryIfNeeded(mergeToRPath);
			mergeUnits(
					sourceUnit,
					mergeToRPath  + "/" + unitName,
					sourceTotal,
					mergeTotal);
		}
	}

	/**
	 * @param sourcePath
	 * @param mergeToPath
	 */
	private void mergeUnits(
			final String sourcePath,
			final String mergeToPath,
			final GcovMergeDetail sourceTotal,
			final GcovMergeDetail mergeTota) {
		final IMerger source = mergerFactory.getMerger();					
		final boolean mergeToExists = media.unitExists(mergeToPath);
		
		source.fromString(media.read(sourcePath));	
		
		final GcovMergeDetail sourceDetail = new GcovMergeDetail();
		final GcovMergeDetail mergeDetail = new GcovMergeDetail();
		
		if (mergeToExists)  {
			mergeUnitToTarget(mergeToPath, source, mergerFactory.getMerger(), sourceDetail, mergeDetail);
			sourceTotal.addTotals(sourceDetail);
			mergeTota.addTotals(mergeDetail);
		} else {
			mergeUnitNoTarget(mergeToPath, source, sourceDetail);
			sourceTotal.addTotals(sourceDetail);
		} 
		log.logItem(new LogItem(
				sourcePath,
				mergeToPath,
				mergeToExists,
				sourceDetail,
				mergeDetail
				));
	}

	/**
	 * @param mergeToPath
	 * @param source
	 * @param mergeTarget
	 * @return
	 */
	private void mergeUnitToTarget(
			final String mergeToPath,
			final IMerger source,
			final IMerger mergeTarget,
			final GcovMergeDetail sourceDetail,
			final GcovMergeDetail mergeDetail) {
		mergeTarget.fromString(media.read(mergeToPath));	
		try {
			mergeTarget.mergeFrom(
					source,
					sourceDetail,
					mergeDetail);
		} catch (MergeException e ) {
			throw new MergeException(e.getMessage() + " in file:" + mergeToPath);
		}
		media.write(mergeToPath, mergeTarget.toString());
	}

	/**
	 * @param mergeToPath
	 * @param source
	 * @return
	 */
	private void mergeUnitNoTarget(
			final String mergeToPath,
			final IMerger source,
			final GcovMergeDetail sourceSummary) {
		media.write(mergeToPath, source.toString());
		source.summarize(sourceSummary);
	}
}
