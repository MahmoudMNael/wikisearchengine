public class SourceData {
	private Integer sourceId;
	private String url;
	private String title;
	private String content;
	private Integer contentLength;
	
	public SourceData(Integer sourceId, String url, String title, String content) {
		this.sourceId = sourceId;
		this.url = url;
		this.title = title;
		this.content = content;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getContentLength() {
		return contentLength;
	}
	
	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}
}
