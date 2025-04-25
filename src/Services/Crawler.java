package Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Models.SourceData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private List<SourceData> _docs = new ArrayList<>();
	
	public Crawler(List<String> startPoint, Integer maxDocuments) throws IOException{
		
		for(int i = 0 ; startPoint.size()>i && _docs.size() < maxDocuments; ++i){
			
			// empty try catch so if connection fails it re tries again
			try{
				_docs.add(new SourceData(Jsoup.connect(startPoint.get(i)).get(), startPoint.get(i)));
			}
			catch(Exception e){ }
		}
		
		for(int i = 0; maxDocuments > _docs.size(); ++i){
			
			// this will specify the search to target only pargraphs not the whole html page
			Elements container = _docs.get(i).getDocument().select("p");
			Elements links = container.select("a[href]");
			
			for (Element link : links) {
				String href = "https://en.wikipedia.org" + link.attr("href");
				try{
					Boolean exists = _docs.stream().anyMatch(obj -> href.equals(obj.getUrl()));
					if (!exists) {
						_docs.add(new SourceData(Jsoup.connect(href).get(), href));
					}
				}
				catch(Exception e){}
				
				if (_docs.size() >= maxDocuments) {
					break;
				}
			}
		}
	}
	
	public List<SourceData> getDocs(){
		return _docs;
	}
}