package Models;

import org.jsoup.nodes.Document;

public class SourceData {
	private Integer sourceId;
	private String url;
	private String title;
	private String content;
	private Integer contentLength;
	private Document doc;
	private static Integer docId = 0;
	
	public SourceData(Integer sourceId, String url, String title, String content) {
		this.sourceId = sourceId;
		this.url = url;
		this.title = title;
		this.content = content;
	}
	
	public SourceData(Document doc, String url){
		this.sourceId = docId++;
		this.url = url;
		this.title = doc.title();
		this.content = doc.text();
		this.doc = doc;
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
	
	public Document getDocument(){
		return this.doc;
	}
}