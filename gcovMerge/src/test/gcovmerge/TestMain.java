package gcovmerge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gcovmerge.Main;
import gcovmerge.MergeException;

class TestMain {

	@Test
	void IncorrectTypeTest() {
		String[] args = {"-y", "/opt/source", "/opt/dest" };
		Main.main(args);
	}

	@Test
	void ValidTypeFileTest() {
		
		Assertions.assertThrows(
				MergeException.class,
				() -> {
					String[] args = {"-f", "/nonexist", "/nonexist" };
					Main.main(args);
				});
	
	}
	
	@Test
	void ValidTypeDirectoryTest() {
		String[] args = {"-d", "/nonexist", "/nonexist" };
		Main.main(args);

	}
}
