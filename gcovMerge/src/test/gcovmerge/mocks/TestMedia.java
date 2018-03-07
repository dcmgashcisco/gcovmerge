package gcovmerge.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gcovmerge.IMedia;
import gcovmerge.IMediaItem;

public class TestMedia implements IMedia {


	List <TestMediaItem> mockFSNodes = new ArrayList <TestMediaItem> (Arrays.asList(
		new TestMediaItem(
			"",
			"opt",
			null
		),
		new TestMediaItem(
			"/opt",
			"source",
			null
		),
		new TestMediaItem(
			"/opt/source",
			"feature1",
			null
		),
		new TestMediaItem(
			"/opt/source/feature1",
			"component1",
			null
		),
		new TestMediaItem(
			"/opt/source/feature1/component1",
			"file1_1_1",
			"1:1:Line1\n" +
			"1:2:Line1\n" +
			"1:3:Line1\n"		
		),
		new TestMediaItem(
			"/opt/source/feature1/component1",
			"file1_1_2",
			"1:1:Line1\n" +
			"1:2:Line1\n" +
			"1:3:Line1\n"	
		),
		new TestMediaItem(
			"/opt/source/feature1",
			"component2",
			null
		),
		new TestMediaItem(
			"/opt/source/feature1/component2",
			"file1_2_1",
			"1:1:Line1\n" +
			"1:2:Line1\n" +
			"1:3:Line1\n"	
		),
		new TestMediaItem(
			"/opt/source/feature1/component2",
			"file1_2_2",
			"1:1:Line1\n" +
			"1:2:Line1\n" +
			"1:3:Line1\n"	
		),
		new TestMediaItem(
			"/opt/source",
			"feature2",
			null
		),
		new TestMediaItem(
			"/opt/source/feature2",
			"component1",
			null
		),
		new TestMediaItem(
			"/opt/source/feature2/component1",
			"file2_1_1",
			"1:1:Line1\n" +
			"1:2:Line1\n" +
			"1:3:Line1\n"	
		),
		new TestMediaItem(
			"/opt/source/feature2/component1",
			"file2_1_2",
			"1:1:Line1\n" +
			"1:2:Line1\n" +
			"1:3:Line1\n"	
		),
		new TestMediaItem(
			"/opt/source/feature2",
			"component2",
			null
		),
		new TestMediaItem(
			"/opt/source/feature2/component2",
			"file2_2_1",
			"1:1:Line1\n" +
			"1:2:Line1\n" +
			"1:3:Line1\n"	
		),
		new TestMediaItem(
			"/opt/source/feature2/component2",
			"file2_2_2",
			"1:1:Line1\n" +
			"1:2:Line1\n" +
			"1:3:Line1\n"	
		)
	));

	
	
	@Override
	public IMediaItem[] listUnits(final String path) {
		List <IMediaItem> items = new ArrayList<IMediaItem> ();
		for (TestMediaItem mockFSNode : mockFSNodes) {
			if (mockFSNode.getParent().equals(path)) {
				items.add(mockFSNode);
			}
		}
		
		IMediaItem[] units = new IMediaItem[items.size()];
		units = items.toArray(units);
		
		return units;
	}

	@Override
	public String read(String path) {
		String content = null;
		for (TestMediaItem mockFSNode : mockFSNodes) {
			String thisPath = mockFSNode.getParent() + "/" + mockFSNode.getName();
			if (thisPath.equals(path)) {
				content = mockFSNode.getContent();
				break;
			}
		}
		return content;
	}

	@Override
	public void write(String path, String data) {
		boolean updated = false;
		for (TestMediaItem mockFSNode : mockFSNodes) {
			String thisPath = mockFSNode.getParent() + "/" + mockFSNode.getName();
			if (thisPath.equals(path)) {
				mockFSNode.setContent(data);
				updated = true;
				break;
			}
		}
		
		if (!updated) {
			int relPos = path.lastIndexOf("/");
			String parent = "";
			String name = path;
			if (relPos != -1) {
				parent = path.substring(0, relPos);
				name = path.substring(relPos + 1);
			}
			TestMediaItem newItem = new TestMediaItem(
					parent,
					name,
					data);
			mockFSNodes.add(newItem);
		}
		
	}

	@Override
	public boolean isUnitType(String path) {
		boolean isUnit = false;
		for (TestMediaItem mockFSNode : mockFSNodes) {
			String thisPath = mockFSNode.getParent() + "/" + mockFSNode.getName();
			if (thisPath.equals(path)) {
				isUnit = !mockFSNode.isDirectory();
				break;
			}
		}
		
		return isUnit;
	}

	@Override
	public boolean unitExists(String path) {
		boolean isUnit = false;
		for (TestMediaItem mockFSNode : mockFSNodes) {
			String thisPath = mockFSNode.getParent() + "/" + mockFSNode.getName();
			if (thisPath.equals(path)) {
				isUnit = !mockFSNode.isDirectory();
				break;
			}
		}
		
		return isUnit;
	}

	@Override
	public void createDestDirectoryIfNeeded(String path) {
		boolean found = false;
		for (TestMediaItem mockFSNode : mockFSNodes) {
			String thisPath = mockFSNode.getParent() + "/" + mockFSNode.getName();
			if (thisPath.equals(path)) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			int relPos = path.lastIndexOf("/");
			String parent = "";
			String name = path;
			if (relPos != -1) {
				parent = path.substring(0, relPos);
				name = path.substring(relPos + 1);
			}
			TestMediaItem newItem = new TestMediaItem(
					parent,
					name,
					null);
			mockFSNodes.add(newItem);
		}
	}

}
