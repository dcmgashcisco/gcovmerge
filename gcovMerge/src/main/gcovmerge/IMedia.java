package gcovmerge;


/**
 * IMedia: interface for operations on media (for example, files).
 *
 */
public interface IMedia {

	
	/**
	 * @return
	 */
	IMediaItem[] listUnits(final String path);
	
	/**
	 * @param path
	 * @return
	 */
	String read(final String path);
	
	/**
	 * @param path
	 * @param data
	 */
	void write(final String path, final String data);
	
	/**
	 * @param path
	 * @return
	 */
	boolean isUnitType(final String path);
	
	/**
	 * @param path
	 * @return
	 */
	boolean unitExists(final String path);
	
	/**
	 * @param mergeToRoot
	 */
	void createDestDirectoryIfNeeded(final String mergeToRoot);
}
