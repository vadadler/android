package vad.adler.newsapp.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * This is a Dagger module. It is used to bind Application class as a Context in the AppComponent
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 * One of the advantages of Dagger.Android is that your Application & Activities are provided into
 * your graph for you {@link AppComponent}.
 */
@Module
public abstract class ApplicationModule {
    //Expose Application as an injectable context.
    @Binds
    abstract Context bindContext(Application application);
}

