/*
 * Base Presenter interface.
 */

package vad.adler.newsapp;

public interface BasePresenter {
    /**
     * Start requesting data from Model.
     */
    void subscribe();

    /**
     * Clear all subscriptions to Presenter.
     */
    void unsubscribe();

}
