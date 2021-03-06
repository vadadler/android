package vad.adler.newsapp.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import vad.adler.newsapp.NewsApplication;
import vad.adler.newsapp.data.NewsRepository;

/**
 * This is a Dagger component. Refer to {@link NewsApplication} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link NewsApplication}.
 * {@link AndroidSupportInjectionModule} is the module from Dagger.Android that helps with the generation
 * and location of subcomponents.
 */
@Singleton
@Component(modules = {NewsRepositoryModule.class,
        ApplicationModule.class,
        NewsModule.class,
        AndroidSupportInjectionModule.class,
        NetworkModule.class})
public interface AppComponent extends AndroidInjector<NewsApplication> {

    NewsRepository getNewsRepository();

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
