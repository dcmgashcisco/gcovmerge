package gcovmerge;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gcovmerge.FileMedia;
import gcovmerge.FileMediaItem;
import gcovmerge.IMediaItem;
import gcovmerge.MergeException;

class TestFileMedia {

	private static final String TEST_CONTENT = "TEST_CONTENT";

	@Test
	void BasicItemTest() {
		FileMediaItem item = new FileMediaItem("/");
	}

	@Test
	void BasicMediaTest() {
		FileMedia media = new FileMedia();

		String path = System.getProperty("user.dir");
		String testDir = path + "/test/ut_" + new Date().toString();
		media.createDestDirectoryIfNeeded(testDir);
		
		String testFile = testDir + "/testFile.gcov";
		
		media.write(testFile, TEST_CONTENT);
		String content  = media.read(testFile);
		assertEquals(content, TEST_CONTENT);
		
		media.isUnitType(testFile);
		media.unitExists(testFile);
		IMediaItem[] units = media.listUnits(testDir);
		
		assertEquals(units.length, 1);
		
	}
	
	@Test
	void NegativeMediaCreateDirectoryTest() {
		FileMedia media = new FileMedia();
		
		Assertions.assertThrows(
				MergeException.class,
				() -> {
					media.createDestDirectoryIfNeeded("/test");
				});
	}
}


