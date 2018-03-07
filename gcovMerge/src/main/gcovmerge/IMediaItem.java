package gcovmerge;

/**
 * IMediaItem: Interface for one item on the media (i.e. a file).
 *
 */
public interface IMediaItem {

	/**
	 * @return
	 */
	String getAbsolutePath();
	
	/**
	 * @return
	 */
	boolean isDirectory();
	
	/**
	 * @return
	 */
	String getName();
}
