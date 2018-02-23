package vad.adler.newsapp.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Implementation of model interface.
 */

public class NewsRepository implements NewsDataSource {
    @Inject
    public NewsRepository() {};

//    @Inject
//    Retrofit retrofit;

    /**
     * Current implementation fetches latest news from Newsapi (REST), then stores them in SQLite
     * database and then returns results from that SQLite db.
     * @return
     */
    @Override
    public Flowable<List<Article>> getNews() {
        Flowable<List<Article>> articles2 = null;
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        compositeDisposable.add(retrofit.create(NewsApi.class).getHeadlinesCountry("us", Constants.API_KEY)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Article>>() {
//                    @Override
//                    public void accept(
//                            @io.reactivex.annotations.NonNull final List<Article> articles)
//                            throws Exception {
//                        for(Article article : articles) {
//                            System.out.println(article.getDescriotion());
//                        }
//                    }
//                })
//        );


        return articles2;
    }

    @Override
    public Flowable<Article> getArticle(@NonNull String articleId) {
        Article article = new Article();
        return Flowable.just(article);
    }
}
