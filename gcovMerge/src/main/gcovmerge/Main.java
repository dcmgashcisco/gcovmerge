package gcovmerge;

/**
 * Main: CLI entrypoint
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		if (Config.validArgs(args)) {
			performMerge(Config.createFrom(args));
		} else {
			displayUsage();
		}
	}

	/**
	 * performMerge.
	 * @param config
	 */
	private static void performMerge(final Config config) {
		if (config.isUnitType()) {
			unitTypeMerge(config);
		} else {
			folderTypeMerge(config);
		}
	}

	/**
	 * displayUsage.
	 */
	private static void displayUsage() {
		System.out.println("Usage:\n" +
				"java -jar gcovmerge -f source mergeTarget\n" + 
				"java -jar gcovmerge -d sourcedir mergeTargetDir");
	}

	/**
	 * @param config
	 */
	private static void unitTypeMerge(final Config config) {
		final IMergerFactory mergerFactory = new GcovMergerFactory();
		final IMedia mediaParam = new FileMedia();
		final UnitCompareProcess executor = new UnitCompareProcess(
				mergerFactory,
				mediaParam);
		executor.execute(config);
	}

	/**
	 * folderTypeMerge.
	 * @param config
	 */
	private static void folderTypeMerge(final Config config) {
		final IMergerFactory mergerFactory = new GcovMergerFactory();
		final IMedia mediaParam = new FileMedia();
		final ILog log = new StdOutLog();
		log.logItem(new LogItem());
		final FolderCompareProcess executor = new FolderCompareProcess(
				mergerFactory,
				mediaParam,
				log);
		executor.execute(config);
	}
}
