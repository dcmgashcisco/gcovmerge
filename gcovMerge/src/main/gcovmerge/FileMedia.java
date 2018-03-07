package gcovmerge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileMedia.
 *
 */
public class FileMedia implements IMedia {
	
	/** GCOV. */
	private static final String GCOV = ".gcov";
	
	
	/**
	 * @return
	 */
	public IMediaItem[] listUnits(final String path) {
		final File root = new File(path);
		final File fileItems[] = root.listFiles();
		FileMediaItem[] items;
		if (null == fileItems) {	
			items = setNull();
		} else {
			items = new FileMediaItem[fileItems.length];
			for (int i=0; i< fileItems.length; i++) {
				items[i] = new FileMediaItem(fileItems[i].getPath());
			}
		}
		return items;
	}

	private FileMediaItem[] setNull() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gcovmerge.IMedia#read(java.lang.String)
	 */
	@Override
	public String read(final String path) {
		String content;
		try {
			content = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			throw new MergeException("Could not Read path: " + path);
		}
		return content;
	}
	
	/* (non-Javadoc)
	 * @see gcovmerge.IMedia#write(java.lang.String, java.lang.String)
	 */
	public void write(final String path, final String content) {
		try {
			Files.write(Paths.get(path), content.getBytes());
		} catch (IOException e) {
			throw new MergeException("Could not Write to path: " + path);
		}
	}
	
	/* (non-Javadoc)
	 * @see gcovmerge.IMedia#isUnitType(java.lang.String)
	 */
	public boolean isUnitType(final String path) {
		return path != null && path.endsWith(GCOV);
	}
	
	/* (non-Javadoc)
	 * @see gcovmerge.IMedia#unitExists(java.lang.String)
	 */
	public boolean unitExists(final String path) {
        final File file = new File(path);
        return file.exists();
	}
	
	/* (non-Javadoc)
	 * @see gcovmerge.IMedia#createDestDirectoryIfNeeded(java.lang.String)
	 */
	public void createDestDirectoryIfNeeded(final String mergeToRoot) {
		final File file = new File(mergeToRoot);
		if (!file.isDirectory()
		 && !file.mkdirs()) {
			throw new MergeException("Cannot create directory un destination for merge:" + mergeToRoot);
		}
	}
}
