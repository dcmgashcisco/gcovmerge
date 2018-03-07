package gcovmerge;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gcovmerge.GcovFileMerger;
import gcovmerge.IMerger;

class TestMergeFile {



	@Test
	void TestMergeEmptyFileWithSelf() {
		IMerger file = new GcovFileMerger();
		
		final GcovMergeDetail sourceDetail = new GcovMergeDetail();
		final GcovMergeDetail mergeDetail = new GcovMergeDetail();
		
		file.mergeFrom(file, sourceDetail, mergeDetail);
		String out = file.toString();
		
		assertTrue(out.length() == 0);
	}

	@Test 
	void TestMergeNonEmotyFileWithSelf() {
		IMerger file = new GcovFileMerger();
		String in = FILE1;
		file.fromString(in);
		
		String out = file.toString();
		assertTrue(in.equals(out));
	}
	
	@Test 
	void TestSummarizer() {
		IMerger file = new GcovFileMerger();
		file.fromString(SMALL_FILE1);
		final GcovMergeDetail sourceDetail = new GcovMergeDetail();
		file.summarize(sourceDetail);
		
		String summary = sourceDetail.toString();
		
		assertEquals(summary,SMALL_FILE_1_SUMMARY);
	}
	
	@Test 
	void TestMergeSmallFileWithOther() {
		IMerger file = new GcovFileMerger();
		file.fromString(SMALL_FILE1);
		IMerger merger = new GcovFileMerger();
		merger.fromString(SMALL_FILE2);
		
		final GcovMergeDetail sourceDetail = new GcovMergeDetail();
		final GcovMergeDetail mergeDetail = new GcovMergeDetail();
		
		file.mergeFrom(merger, sourceDetail, mergeDetail);
		
		String out = file.toString();
		boolean eq = SMALL_FILE3.equals(out);
		
		assertTrue(eq);
	}
	
	@Test 
	void TestMergeRealFileWithOther() {
		IMerger file = new GcovFileMerger();
		file.fromString(FILE1);
		IMerger merger = new GcovFileMerger();
		merger.fromString(FILE2);
		
		final GcovMergeDetail sourceDetail = new GcovMergeDetail();
		final GcovMergeDetail mergeDetail = new GcovMergeDetail();
		
		file.mergeFrom(merger, sourceDetail, mergeDetail);
		
		String out = file.toString();
		boolean eq = FILE3.equals(out);
		
		assertTrue(eq);
	}
	
	private static final String SMALL_FILE1 = 
			"        -:    1:line1\n" + 
			"        3:    2:line2\n" + 
			"        3:    3:line3\n" + 
			"        -:    4:line4\n" + 
			"    #####:    5:line5\n" + 
			"    #####:    6:line6\n" + 
			"        -:    7:line7\n" + 
			"        -:    8:line8\n" + 
			"        -:    9:line9";
	private static final String SMALL_FILE_1_SUMMARY = "9, 4, 2, 50";
	
	private static final String SMALL_FILE2 = 
			"        3:    1:line1\n" + 
			"        3:    2:line2\n" + 
			"        3:    3:line3\n" + 
			"        -:    4:line4\n" + 
			"        -:    5:line5\n" + 
			"        4:    6:line6\n" + 
			"        -:    7:line7\n" + 
			"        -:    8:line8\n" + 
			"        -:    9:line9";
	
	private static final String SMALL_FILE3 = 
			"        3:    1:line1\n" + 
			"        6:    2:line2\n" + 
			"        6:    3:line3\n" + 
			"        -:    4:line4\n" + 
			"    #####:    5:line5\n" + 
			"        4:    6:line6\n" + 
			"        -:    7:line7\n" + 
			"        -:    8:line8\n" + 
			"        -:    9:line9";
	
	
	private static final String FILE1 = 
			"        -:    0:Source:EapFastConfigManager.cpp\n" + 
			"        -:    0:Graph:./Debug/EapFastConfigManager.gcno\n" + 
			"        -:    0:Data:./Debug/EapFastConfigManager.gcda\n" + 
			"        -:    0:Runs:4\n" + 
			"        -:    0:Programs:1\n" + 
			"        -:    1:///////////////////////////////////////////////////////////////////////////////\n" + 
			"        -:    2:// Copyright (c) 2008, 2009 Cisco Systems, Inc.  This program contains          //\n" + 
			"        -:    3:// proprietary and confidential information.  All rights reserved except     //\n" + 
			"        -:    4:// as may be permitted by prior written consent.                             //\n" + 
			"        -:    5:///////////////////////////////////////////////////////////////////////////////\n" + 
			"        -:    6:\n" + 
			"        -:    7:#include \"EapFastConfigManager.h\"\n" + 
			"        -:    8:#include \"EapFastConfigObject.h\"\n" + 
			"        -:    9:\n" + 
			"        -:   10:#include \"AcsConfig.h\"\n" + 
			"        -:   11:#include \"GenericConfigObject.h\"\n" + 
			"        -:   12:#include \"ConfigManager.h\"\n" + 
			"        -:   13:#include \"ServiceLayer.h\"\n" + 
			"        -:   14:#include \"CryptoLib.h\"\n" + 
			"        -:   15:\n" + 
			"        -:   16:#include \"EapFramework.h\"\n" + 
			"        -:   17:#include \"EapFastProtocol.h\"\n" + 
			"        -:   18:\n" + 
			"        -:   19:EapFastConfigManager *EapFastConfigManager::s_pInstance = NULL ;\n" + 
			"        -:   20:\n" + 
			"        -:   21:EapFastConfigManager::EapFastConfigManager() :\n" + 
			"        4:   22:    EapConfigManagerBase() \n" + 
			"        -:   23:{\n" + 
			"        4:   24:    EAPLOG ( __FILE__, __LINE__, AcsDebugLogAAA::VERBOSE, NULL, \"EapFastConfigManager - subscribing for notification\");\n" + 
			"        -:   25:\n" + 
			"        -:   26:    // Register\n" + 
			"        4:   27:    ConfigManager::instance()->subscribeNotification(this,ConfigTrustCertificate::typeID) ;\n" + 
			"        4:   28:    ConfigManager::instance()->subscribeNotification(this,ConfigEapFastGlobalSettings::typeID) ;\n" + 
			"        4:   29:    ConfigManager::instance()->subscribeNotification(this,ConfigCertificateTrustedListHasCertificate::typeID) ;\n" + 
			"        -:   30:    ConfigManager::instance()->subscribeNotification(this,ConfigAcsNodeHasEapCertificate::typeID) ;\n" + 
			"        -:   31:    //ConfigManager::instance()->subscribeNotification(this,ConfigACSCertificate::typeID) ;\n" + 
			"        -:   32:\n" + 
			"        4:   33:    EAPLOG ( __FILE__, __LINE__, AcsDebugLogAAA::VERBOSE, NULL, \"EapFastConfigManager - subscription done\");\n" + 
			"    #####:   34:}";
	
	
	private static final String FILE2 = 
			"        -:    0:Source:EapFastConfigManager.cpp\n" + 
			"        -:    0:Graph:./Debug/EapFastConfigManager.gcno\n" + 
			"        -:    0:Data:./Debug/EapFastConfigManager.gcda\n" + 
			"        -:    0:Runs:4\n" + 
			"        -:    0:Programs:1\n" + 
			"        -:    1:///////////////////////////////////////////////////////////////////////////////\n" + 
			"        -:    2:// Copyright (c) 2008, 2009 Cisco Systems, Inc.  This program contains          //\n" + 
			"        -:    3:// proprietary and confidential information.  All rights reserved except     //\n" + 
			"        -:    4:// as may be permitted by prior written consent.                             //\n" + 
			"        -:    5:///////////////////////////////////////////////////////////////////////////////\n" + 
			"        -:    6:\n" + 
			"        -:    7:#include \"EapFastConfigManager.h\"\n" + 
			"        -:    8:#include \"EapFastConfigObject.h\"\n" + 
			"        -:    9:\n" + 
			"        -:   10:#include \"AcsConfig.h\"\n" + 
			"        -:   11:#include \"GenericConfigObject.h\"\n" + 
			"        -:   12:#include \"ConfigManager.h\"\n" + 
			"        -:   13:#include \"ServiceLayer.h\"\n" + 
			"        -:   14:#include \"CryptoLib.h\"\n" + 
			"        -:   15:\n" + 
			"        -:   16:#include \"EapFramework.h\"\n" + 
			"        -:   17:#include \"EapFastProtocol.h\"\n" + 
			"        -:   18:\n" + 
			"        -:   19:EapFastConfigManager *EapFastConfigManager::s_pInstance = NULL ;\n" + 
			"        -:   20:\n" + 
			"        4:   21:EapFastConfigManager::EapFastConfigManager() :\n" + 
			"        4:   22:    EapConfigManagerBase() \n" + 
			"        -:   23:{\n" + 
			"        4:   24:    EAPLOG ( __FILE__, __LINE__, AcsDebugLogAAA::VERBOSE, NULL, \"EapFastConfigManager - subscribing for notification\");\n" + 
			"        -:   25:\n" + 
			"        -:   26:    // Register\n" + 
			"        -:   27:    ConfigManager::instance()->subscribeNotification(this,ConfigTrustCertificate::typeID) ;\n" + 
			"        4:   28:    ConfigManager::instance()->subscribeNotification(this,ConfigEapFastGlobalSettings::typeID) ;\n" + 
			"        4:   29:    ConfigManager::instance()->subscribeNotification(this,ConfigCertificateTrustedListHasCertificate::typeID) ;\n" + 
			"        4:   30:    ConfigManager::instance()->subscribeNotification(this,ConfigAcsNodeHasEapCertificate::typeID) ;\n" + 
			"        -:   31:    //ConfigManager::instance()->subscribeNotification(this,ConfigACSCertificate::typeID) ;\n" + 
			"        -:   32:\n" + 
			"        4:   33:    EAPLOG ( __FILE__, __LINE__, AcsDebugLogAAA::VERBOSE, NULL, \"EapFastConfigManager - subscription done\");\n" + 
			"        4:   34:}";
	
	private static final String FILE3 = 
			"        -:    0:Source:EapFastConfigManager.cpp\n" + 
			"        -:    0:Graph:./Debug/EapFastConfigManager.gcno\n" + 
			"        -:    0:Data:./Debug/EapFastConfigManager.gcda\n" + 
			"        -:    0:Runs:4\n" + 
			"        -:    0:Programs:1\n" + 
			"        -:    1:///////////////////////////////////////////////////////////////////////////////\n" + 
			"        -:    2:// Copyright (c) 2008, 2009 Cisco Systems, Inc.  This program contains          //\n" + 
			"        -:    3:// proprietary and confidential information.  All rights reserved except     //\n" + 
			"        -:    4:// as may be permitted by prior written consent.                             //\n" + 
			"        -:    5:///////////////////////////////////////////////////////////////////////////////\n" + 
			"        -:    6:\n" + 
			"        -:    7:#include \"EapFastConfigManager.h\"\n" + 
			"        -:    8:#include \"EapFastConfigObject.h\"\n" + 
			"        -:    9:\n" + 
			"        -:   10:#include \"AcsConfig.h\"\n" + 
			"        -:   11:#include \"GenericConfigObject.h\"\n" + 
			"        -:   12:#include \"ConfigManager.h\"\n" + 
			"        -:   13:#include \"ServiceLayer.h\"\n" + 
			"        -:   14:#include \"CryptoLib.h\"\n" + 
			"        -:   15:\n" + 
			"        -:   16:#include \"EapFramework.h\"\n" + 
			"        -:   17:#include \"EapFastProtocol.h\"\n" + 
			"        -:   18:\n" + 
			"        -:   19:EapFastConfigManager *EapFastConfigManager::s_pInstance = NULL ;\n" + 
			"        -:   20:\n" + 
			"        4:   21:EapFastConfigManager::EapFastConfigManager() :\n" + 
			"        8:   22:    EapConfigManagerBase() \n" + 
			"        -:   23:{\n" + 
			"        8:   24:    EAPLOG ( __FILE__, __LINE__, AcsDebugLogAAA::VERBOSE, NULL, \"EapFastConfigManager - subscribing for notification\");\n" + 
			"        -:   25:\n" + 
			"        -:   26:    // Register\n" + 
			"        4:   27:    ConfigManager::instance()->subscribeNotification(this,ConfigTrustCertificate::typeID) ;\n" + 
			"        8:   28:    ConfigManager::instance()->subscribeNotification(this,ConfigEapFastGlobalSettings::typeID) ;\n" + 
			"        8:   29:    ConfigManager::instance()->subscribeNotification(this,ConfigCertificateTrustedListHasCertificate::typeID) ;\n" + 
			"        4:   30:    ConfigManager::instance()->subscribeNotification(this,ConfigAcsNodeHasEapCertificate::typeID) ;\n" + 
			"        -:   31:    //ConfigManager::instance()->subscribeNotification(this,ConfigACSCertificate::typeID) ;\n" + 
			"        -:   32:\n" + 
			"        8:   33:    EAPLOG ( __FILE__, __LINE__, AcsDebugLogAAA::VERBOSE, NULL, \"EapFastConfigManager - subscription done\");\n" + 
			"        4:   34:}";
	
}
	
	
	
