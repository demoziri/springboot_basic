package com.pjs.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pjs.exam.demo.repository.ArticleRepository;
import com.pjs.exam.demo.util.Ut;
import com.pjs.exam.demo.vo.Article;
import com.pjs.exam.demo.vo.ResultData;

@Service
public class ArticleService {
	
	private ArticleRepository articleRepository;
	
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository; //Autowired보다 속도면에서 좋다(?)
	}

	public List<Article> getForPrintArticles() {
		return articleRepository.getForPrintArticles();
	}
	
	public Article getForPrintArticle(int id) {
		return articleRepository.getForPrintArticle(id);
	}

	public ResultData<Integer> writeArticle(int memberId, String title, String body) {
		articleRepository.writeArticle(memberId, title, body);
		int id = articleRepository.getLastInsertId();
		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id),"id",id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public ResultData<Article> modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
		Article article = getForPrintArticle(id);
		return ResultData.from("S-1", Ut.f("%d번 게시물이 수정되었습니다.", id), "article",article);
	}

	public ResultData actorCanModify(int actorId, Article article) {
		if(article == null) {
			return ResultData.from("F-1", "권한이 없습니다.");
		}
		if(article.getMemberId()!=actorId) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}
		return ResultData.from("S-1", "수정이 가능합니다.");
	}

}
