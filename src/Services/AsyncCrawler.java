package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Models.SourceData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AsyncCrawler implements ICrawler{
	private List<SourceData> _docs = new ArrayList<>();
	private final List<String> _startPoint;
	private final Integer _maxDocuments;
	private final Integer _numThreads;
	private final Logger _logger;
	
	public AsyncCrawler(List<String> startPoint, Integer maxDocs , Logger logger){
		this._startPoint = startPoint;
		this._maxDocuments = maxDocs;
		Integer numThreads = (int)Math.ceil((double)(maxDocs)/10) < 50 ? (int)Math.ceil((double)(maxDocs)/10) : 50;
		this._numThreads = numThreads;
		this._logger = logger;
		crawl();
	}
	
	public AsyncCrawler(List<String> startPoint, Integer maxDocs, Integer numThreads , Logger logger){
		this._startPoint = startPoint;
		this._maxDocuments = maxDocs;
		this._numThreads = numThreads;
		this._logger = logger;
		crawl();
	}
	
	private void crawl(){
		for(Integer i = 0 ; _startPoint.size()>i && _docs.size() < _numThreads; ++i){
			String url = _startPoint.get(i);
			try{
				_docs.add(new SourceData(Jsoup.connect(url).get(), url));
			}
			catch(Exception e){
				_logger.log(Level.SEVERE, "Error fetching URL: " + url, e);
			}
		}
		
		for(Integer i = 0; _numThreads+1 > _docs.size() && _docs.size() > i; ++i){
			
			// this will startPointecify the search to target only pargraphs not the whole html page
			Elements container = _docs.get(i).getDocument().select("p");
			Elements links = container.select("a[href]");
			
			for (Element link : links) {
				String url;
				if(link.attr("href").startsWith("http"))
					url = link.attr("href");
				else
					url = "https://" + _docs.get(i).getUrl().split("//")[1].split("/")[0] + link.attr("href");
				try{
					Boolean exists = _docs.stream().anyMatch(obj -> url.equals(obj.getUrl()));
					if (!exists) {
						_docs.add(new SourceData(Jsoup.connect(url).get(), url));
					}
				}
				catch(Exception e){
					_logger.log(Level.SEVERE, "Error fetching URL: " + url, e);
				}
				
				if (_docs.size() >= _numThreads+1) {
					break;
				}
			}
		}
		
		List<Thread> threads = new ArrayList<>();
		for(int i = _numThreads ; i > 0 ; --i){
			double strictiness = 1.5;
			int targetThreads = (int)Math.ceil((double)_maxDocuments/(double)_numThreads*strictiness);
			Thread thread = new Handler(_docs.get(i).getUrl(), targetThreads, _logger, this);
			threads.add(thread);
			thread.start();
		}
		
		try{
			for (Thread thread : threads) {
				thread.join();
			}
		}
		
		catch(Exception e){
			_logger.log(Level.SEVERE, "thread inturpted", e);
		}
	}
	
	public synchronized void addToDocs(List<SourceData> list){
		for (SourceData sourceData : list) {
			Boolean exists = _docs.stream().anyMatch(obj -> sourceData.getUrl().equals(obj.getUrl()));
			if (!exists && _docs.size() < _maxDocuments) {
				_docs.add(sourceData);
			}
		}
	}
	
	public List<SourceData> getDocs() {
		return _docs;
	}
	
	private class Handler extends Thread{
		private List<SourceData> _docs = new ArrayList<>();
		private final String _startPoint;
		private final Integer _maxTarget;
		private final Logger _logger;
		private AsyncCrawler crawler;
		
		public Handler(String startPoint, Integer maxTarget, Logger logger, AsyncCrawler crawler){
			this._startPoint = startPoint;
			this._maxTarget = maxTarget;
			this._logger = logger;
			this.crawler = crawler;
		}
		
		@Override
		public void run(){
			
			try{
				_docs.add(new SourceData(Jsoup.connect(_startPoint).get(), _startPoint));
			}
			catch(Exception e){
				_logger.log(Level.SEVERE, "Error fetching URL: " + _startPoint, e);
			}
			
			for(Integer i = 0; _maxTarget > _docs.size() && _docs.size() > i; ++i){
				
				Elements container = _docs.get(i).getDocument().select("p");
				Elements links = container.select("a[href]");
				
				for (Element link : links) {
					String url;
					if(link.attr("href").startsWith("http"))
						url = link.attr("href");
					else
						url = "https://" + _docs.get(i).getUrl().split("//")[1].split("/")[0] + link.attr("href");
					try{
						Boolean exists = _docs.stream().anyMatch(obj -> url.equals(obj.getUrl()));
						if (!exists) {
							_docs.add(new SourceData(Jsoup.connect(url).get(), url));
						}
					}
					catch(Exception e){
						_logger.log(Level.SEVERE, "Error fetching URL: " + url, e);
					}
					
					if (_docs.size() >= _maxTarget) {
						break;
					}
				}
			}
			crawler.addToDocs(_docs);
		}
	}
}