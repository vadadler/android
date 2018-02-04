/**
 * Base View interface. All Views will be extended from this interface.
 *
 * View works with the Presenter to display data and it notifies the Presenter about the
 * user’s actions. In MVP Activities, Fragments and custom Android views can be Views.
 *
 */

package vad.adler.newsapp;

/**
 * View notifies Presenter that it is ready to be updated by calling the {@link vad.adler.newsapp.BasePresenter#subscribe() presenter.subscribe()}
 * method of the Presenter in onResume. The View calls {@link vad.adler.newsapp.BasePresenter#unsubscribe() presenter.unsubscribe()}
 * in onPause to tell the Presenter that it is no longer interested in being updated. If the
 * implementation of the View is an Android custom view, then the subscribe and unsubscribe methods
 * have to be called on onAttachedToWindow and onDetachedFromWindow. User actions, like button clicks,
 * will trigger corresponding methods in the Presenter, this being the one that decides what should
 * happen next.
 * @param <T> Presenter
 */
public interface BaseView<T> {

    /**
     * Set Presenter.
     * @param presenter
     */
    void setPresenter(T presenter);

}
