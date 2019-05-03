package io.neverstoplearning.acviewmodel.home.base;

import javax.inject.Singleton;

import dagger.Component;
import io.neverstoplearning.acviewmodel.home.details.DetailsFragment;
import io.neverstoplearning.acviewmodel.home.home.ListFragment;
import io.neverstoplearning.acviewmodel.home.networking.NetworkModule;
import io.neverstoplearning.acviewmodel.home.viewmodel.ViewModelModule;

@Singleton
@Component(modules = {
        NetworkModule.class,
        ViewModelModule.class
})
public interface ApplicationComponent {
    void inject(ListFragment listFragment);

    void inject(DetailsFragment detailsFragment);
}
