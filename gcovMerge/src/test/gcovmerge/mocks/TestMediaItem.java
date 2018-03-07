package gcovmerge.mocks;

import gcovmerge.IMediaItem;

public class TestMediaItem implements IMediaItem {

	String parent;
	String name;
	String content;
	
	public TestMediaItem(
			final String parentParam,
			final String nameParam,
			final String contentParam) {
		this.parent = parentParam;
		this.name = nameParam;
		this.content = contentParam;
	}

	public String getParent() {
		return parent;
	}
	
	public String getName() {
		return name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(final String content) {
		this.content = content;
	}
	
	@Override
	public String getAbsolutePath() {
		return parent + "/" + name;
	}

	@Override
	public boolean isDirectory() {
		return (null == content);
	}


}
