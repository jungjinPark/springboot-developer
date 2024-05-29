package com.example.springbootdeveloper.service;

import com.example.springbootdeveloper.domain.Article;
import com.example.springbootdeveloper.dto.AddArticleRequest;
import com.example.springbootdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NonNull인 필드만 생성자를 만들어줍니다.
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article addArticle(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> getArticles() {
        return blogRepository.findAll();
    }

    public Article getArticle(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다."));
    }

    public Article updateArticle(Long id, AddArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다."));

        article.update(request);

        return blogRepository.save(article);
    }

    public void deleteArticle(Long id) {
        blogRepository.deleteById(id);
    }
}
