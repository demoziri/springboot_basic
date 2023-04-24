package com.pjs.exam.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pjs.exam.demo.service.ArticleService;
import com.pjs.exam.demo.service.BoardService;
import com.pjs.exam.demo.util.Ut;
import com.pjs.exam.demo.vo.Article;
import com.pjs.exam.demo.vo.Board;
import com.pjs.exam.demo.vo.ResultData;
import com.pjs.exam.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrArticleController {
	private ArticleService articleService;
	private BoardService boardService;
	
	
	public UsrArticleController(ArticleService articleService, BoardService boardService) {
		this.articleService = articleService;
		this.boardService = boardService;
	}
	
	
	//액션 메서드 시작
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		if(Ut.empty(title)) {
			return rq.jsHistoryBack("title(을)를 입력해주세요");
		}
		if(Ut.empty(body)) {
			return rq.jsHistoryBack("body(을)를 입력해주세요");
		}
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title,body);
		int id = (int)writeArticleRd.getData1();
		
		if(Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}
		
		
		return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다.", id), replaceUri);
	}
	
	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "usr/article/write";
	}
	
	
	
	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model, int boardId) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Board board = boardService.getBoardById(boardId);
		
		if(board == null) {
			return rq.historyBackJsOnview(Ut.f("%d번 게시판은 존재하지 않습니다.", boardId));
		}
		int articlesCount = articleService.getArticlesCount(boardId);
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(),boardId);
		model.addAttribute("articlesCount",articlesCount);
		model.addAttribute("board",board);
		model.addAttribute("articles",articles);
		return "usr/article/list";
	}
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		model.addAttribute(article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpServletRequest req, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article",article);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		if ( article == null ) {
			ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		if(article.getMemberId() != rq.getLoginedMemberId()) {
			return rq.jsHistoryBack("권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		return rq.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req,Model model, int id,String title, String body) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		if(article == null) {
			return rq.historyBackJsOnview(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(),article);
		
		if(actorCanModifyRd.isFail()) {
			return rq.historyBackJsOnview(actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article",article);
		
		return "usr/article/modify";
	}
	
	
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id,String title, String body) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		if(article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(),article);
		
		if(actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return rq.jsReplace(Ut.f("%d번 글이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}
	
	
}






