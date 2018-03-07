package gcovmerge;

import java.io.File;

/**
 * FileMediaItem.
 *
 */
public class FileMediaItem extends File implements IMediaItem {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5579688746904395223L;

	/**
	 * FileMediaItem.
	 * @param pathname
	 */
	public FileMediaItem(final String pathname) {
		super(pathname);
	}
}
