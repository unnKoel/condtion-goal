package com.wind.goal.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物品组VO
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-24
 */
public class ArticleGroupVO {
	private Map<String, List<Object>> articleMap = new HashMap<String, List<Object>>();

	public void putArticle(String articleName, Object article) {
		if (articleMap.containsKey(articleName)) {
			List<Object> articles = articleMap.get(articleName);
			articles.add(article);
		} else {
			List<Object> articles = new ArrayList<Object>();
			articles.add(article);
			articleMap.put(articleName, articles);
		}
	}

	public void putArticles(String articleName, List<Object> articles) {
		if (articleMap.containsKey(articleName)) {
			List<Object> articleList = articleMap.get(articleName);
			articleList.addAll(articles);
		} else {
			articleMap.put(articleName, articles);
		}
	}

	public List<Object> getArticleByClassify(String classifyName) {
		return articleMap.get(classifyName);
	}

	public Map<String, List<Object>> getArticleMap() {
		return articleMap;
	}

	public void setArticleMap(Map<String, List<Object>> articleMap) {
		this.articleMap = articleMap;
	}
}
