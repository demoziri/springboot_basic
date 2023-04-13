package com.pjs.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pjs.exam.demo.vo.Article;

@Controller
public class UsrArticleController {
	int articlesLastId;
	private List<Article> articles;
	
	public UsrArticleController() {
		articlesLastId = 0;
		articles = new ArrayList<>();
		
		makeTestData();
	}
	
	private void makeTestData() {
		for(int i=1;i<11;i++) {
			int id = articlesLastId ++;
			Article article = new Article(id+1,"제목"+i,"내용"+i);
			articles.add(article);
		}
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = articlesLastId + 1;
		Article article = new Article(id, title, body);
		articles.add(article);
		articlesLastId = id;
		
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		
		return articles;
	}
	
}






