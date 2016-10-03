package de.elderbyte.auctionhelper.model;

public class QueryResult {
    private File[] files;

    public File[] getFiles() {
	return files;
    }

    public void setFiles(File[] files) {
	this.files = files;
    }

    public class File {
	private String url;
	private long lastModified;

	public String getUrl() {
	    return url;
	}

	public void setUrl(String url) {
	    this.url = url;
	}

	public long getLastModified() {
	    return lastModified;
	}

	public void setLastModified(long lastModified) {
	    this.lastModified = lastModified;
	}

    }
}
