package gcovmerge;

/**
 * Config class: manages command line arguments
 *
 */
public final class Config {

	/** REQ_ARGS. */
	private static final int REQ_ARGS = 3;
	/** UNIT_TYPE. */
	private static final String UNIT_TYPE = "-f";
	/** FOLDER_TYPE. */
	private static final String FOLDER_TYPE = "-d";
	
	/** isUnit. */
	private final transient boolean isUnit;
	/** outPath. */
	private final String outPath;
	/** inPath. */
	private final String inPath;

	/**
	 * createFrom.
	 * @param args
	 * @return
	 */
	public static boolean validArgs(final String[] args) {

		return ((args != null && args.length == REQ_ARGS)
		  &&((UNIT_TYPE.equals(args[0])) 
		   ||(FOLDER_TYPE.equals(args[0]))));

	}
	
	
	/**
	 * createFrom.
	 * @param args
	 * @return
	 */
	public static Config createFrom(final String[] args) {
		if (!validArgs(args)) {
			return null;
		}
		return new Config(
					UNIT_TYPE.equals(args[0]),
					args[1],
					args[2]
					);
	}
	


	/**
	 * Private Constructor.
	 * @param isUnit
	 * @param inPath
	 * @param outPath
	 */
	private Config(
			final boolean isUnit,
			final String inPath,
			final String outPath) {

		this.isUnit = isUnit;
		this.outPath = outPath;
		this.inPath = inPath;
	}

	
	/**
	 * @return source path
	 */
	public String getInPath() {
		return inPath;
	}
	
	/**
	 * @return merge dest path
	 */
	public String getOutPath() {
		return outPath;
	}
	
	/**
	 * @return is merge unit (true).. is folder (false)
	 */
	public boolean isUnitType() {
		return isUnit;
	}
}
