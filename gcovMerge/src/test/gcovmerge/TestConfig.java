package gcovmerge;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gcovmerge.Config;

class TestConfig {

	@Test
	void TestDirectoryConfig() {
		String[] args = {"-d", "/opt/source", "/opt/dest" };
		
		Config config = Config.createFrom(args);
		assertTrue(config != null);
		
		assertEquals(config.getInPath(), "/opt/source");
		assertEquals(config.getOutPath(), "/opt/dest");
		assertEquals(config.isUnitType(), false);
	}
	
	@Test
	void TestFileConfig() {
		String[] args = {"-f", "/opt/source", "/opt/dest" };
		
		Config config = Config.createFrom(args);
		assertTrue(config != null);
		
		assertEquals(config.getInPath(), "/opt/source");
		assertEquals(config.getOutPath(), "/opt/dest");
		assertEquals(config.isUnitType(), true);
	}
	
	@Test
	void TestFileConfigInsufficientArgs() {
		String[] args = {"-f", "/opt/source" };
		
		Config config = Config.createFrom(args);
		assertTrue(config == null);
	}
	
	@Test
	void TestFileConfigBadType() {
		String[] args = {"-y", "/opt/source", "/opt/dest" };
		
		Config config = Config.createFrom(args);
		assertTrue(config == null);
	}

}
