package com.diocn;

import java.io.File;

import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class NewsCrawler extends BreadthCrawler {

	//用于保存图片的文件夹
    File downloadDir;
    
//    DemoImageCrawler demoimagecrawler;
    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     * information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     * links which match regex rules from pag
     */
    public NewsCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*种子页面*/
        this.addSeed("http://www.coolapk.com/");

        /*正则规则设置*/
        /*爬取符合 http://news.hfut.edu.cn/show-xxxxxxhtml的URL*/
        this.addRegex("http://news.hfut.edu.cn/show-.*html");
        this.addRegex("http://www.coolapk.com/apk/com.*");
        this.addRegex("http://www.coolapk.com/game/com.*");
        /*不要爬取 jpg|png|gif*/
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*不要爬取包含 # 的URL*/
        this.addRegex("-.*#.*");
    }

    public void visit(Page page, CrawlDatums next) {
        String url = page.getUrl();
        String imageurl = null;
        String packageName = null;
        /*判断是否为新闻页，通过正则可以轻松判断*/
        if (page.matchUrl("http://www.coolapk.com/apk/com.*")){
//            System.out.println(page.getUrl().replace("http://www.coolapk.com/apk/", ""));
            packageName = page.getUrl().replace("http://www.coolapk.com/apk/", "");
            System.out.println(packageName+"搜索到了");
            
        	try {
        		imageurl = getimageurl("http://setting.smartisan.com/app/icon/","[{\"package\":\""+packageName+"\"}]");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(packageName +"下载出错了！-----");
			}
//            try {
//                DemoPostCrawler crawler = new DemoPostCrawler("json_crawler", true);
//                crawler.addSeed(new CrawlDatum("http://setting.smartisan.com/app/icon/")
//                        .meta("method", "POST")
//                        .meta("outputData", packageName));
//                crawler.start(3);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println(packageName +"下载出错了！-----");
//			}
//            try {
//				DemoImageCrawler.down(packageName);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println(packageName +"下载出错了！-----");
//			}
            		
        }
        if (page.matchUrl("http://www.coolapk.com/game/com.*")) {
            /*we use jsoup to parse page*/
//            Document doc = page.getDoc();

            /*extract title and content of news by css selector*/
//            String title = page.select("div[id=Article]>h2").first().text();
//            String content = page.select("div#artibody", 0).text();
//
//            System.out.println("URL:\n" + url);
//            System.out.println("title:\n" + title);
//            System.out.println("content:\n" + content);
        	packageName = page.getUrl().replace("http://www.coolapk.com/game/", "");
//            System.out.println(page.getUrl().replace("http://www.coolapk.com/game/", ""));
        	System.out.println(packageName+"搜索到了");

        	try {
        		imageurl = getimageurl("http://setting.smartisan.com/app/icon/","[{\"package\":\""+packageName+"\"}]");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(packageName +"下载出错了！-----");
			}
//            try {
//				DemoImageCrawler.down(packageName);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println(packageName +"下载出错了！-----");
//			}
            /*如果你想添加新的爬取任务，可以向next中添加爬取任务，
               这就是上文中提到的手动解析*/
            /*WebCollector会自动去掉重复的任务(通过任务的key，默认是URL)，
              因此在编写爬虫时不需要考虑去重问题，加入重复的URL不会导致重复爬取*/
            /*如果autoParse是true(构造函数的第二个参数)，爬虫会自动抽取网页中符合正则规则的URL，
              作为后续任务，当然，爬虫会去掉重复的URL，不会爬取历史中爬取过的URL。
              autoParse为true即开启自动解析机制*/
            //next.add("http://xxxxxx.com");
        }
//        if(page.matchUrl("http://setting.smartisan.com/app/icon.*")){
//        	
//        	Document doc = page.doc();
//        	String str = page.toString();
//        	System.out.println("111111111111111111111111111111111");
//        	System.out.println(str);
//        	System.out.println("222222222222222222222222222222222");
//        }
        if(imageurl!=null && !imageurl.equals("")) {
        	ImageDownload.downloadPicture(imageurl, packageName+".png");
        }
    }

    public static void main(String[] args) throws Exception {
        NewsCrawler crawler = new NewsCrawler("crawl", true);
        /*线程数*/
        crawler.setThreads(50);
        /*设置每次迭代中爬取数量的上限*/
        crawler.setTopN(99999);
        /*设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
           如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
           续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
           死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
           的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
           进行爬取。断点爬取默认为false*/
        crawler.setResumable(true);
        /*开始深度为4的爬取，这里深度和网站的拓扑结构没有任何关系
            可以将深度理解为迭代次数，往往迭代次数越多，爬取的数据越多*/
        crawler.start(99);
    }
    
    public String getimageurl(String path,String params) {
    	try {
			String url = DoPost.post(path, params);
			String[] arr = url.split("\"logo\":\"");
			if(arr.length>1){
				String[] arr2 = arr[1].split("\",\"md5\":");
				System.out.println(arr2[0]);
				return arr2[0];
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

}
