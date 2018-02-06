package vad.adler.newsapp.data;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.Observable;

/**
 * Implementation of model interface.
 */

public class NewsRepository implements NewsDataSource {

    @Override
    public Observable<List<Article>> getNews() {
        List<Article> articles = new List<Article>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Article> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(Article article) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Article> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends Article> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Article get(int index) {
                return null;
            }

            @Override
            public Article set(int index, Article element) {
                return null;
            }

            @Override
            public void add(int index, Article element) {

            }

            @Override
            public Article remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Article> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Article> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Article> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        return Observable.just(articles);
    }

    @Override
    public Observable<Article> getArticle(@NonNull String articleId) {
        Article article = new Article();
        return Observable.just(article);
    }
}
