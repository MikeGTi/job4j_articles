package ru.job4j.articles.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.articles.model.Article;
import ru.job4j.articles.model.Word;
import ru.job4j.articles.service.generator.ArticleGenerator;
import ru.job4j.articles.store.Store;

import java.util.ArrayList;
import java.util.List;

public class SimpleArticleService implements ArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleArticleService.class.getSimpleName());

    private final ArticleGenerator articleGenerator;

    public SimpleArticleService(ArticleGenerator articleGenerator) {
        this.articleGenerator = articleGenerator;
    }

    @Override
    public void generate(Store<Word> wordStore, int count, Store<Article> articleStore) {
        LOGGER.info("Генерация статей в количестве {}", count);
        var words = wordStore.findAll();

        int generateByPart = 50_000;
        int upperEnd;
        int innerEnd;
        List<Article> articles;

        if (count == 1) {
            upperEnd = 1;
            innerEnd = 1;
            articles = new ArrayList<>(1);
        } else if (count < generateByPart) {
            upperEnd = 1;
            innerEnd = count;
            articles = new ArrayList<>(count);
        } else {
            upperEnd = count / generateByPart;
            innerEnd = generateByPart;
            articles = new ArrayList<>(generateByPart);
        }

        int articlesCount = 0;
        for (int i = 0; i < upperEnd; i++) {
            if (i == upperEnd - 1 && count % generateByPart > 0) {
                innerEnd += count % generateByPart;
                articles = new ArrayList<>(innerEnd);
            }
            for (int j = 0; j < innerEnd; j++) {
                articlesCount++;
                LOGGER.info("Сгенерирована статья № {}", articlesCount);
                articles.add(articleGenerator.generate(words));
            }
            if (articles.size() > 1) {
                articleStore.saveAll(articles);
            } else {
                articleStore.save(articles.get(0));
            }
            articles.clear();
        }
        words.clear();
    }
}
