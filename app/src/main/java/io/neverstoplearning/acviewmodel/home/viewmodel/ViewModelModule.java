package io.neverstoplearning.acviewmodel.home.viewmodel;


import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.neverstoplearning.acviewmodel.home.home.ListViewModel;
import io.neverstoplearning.acviewmodel.home.home.SelectedRepoViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindsListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedRepoViewModel.class)
    abstract ViewModel bindsSelectedRepoViewModel(SelectedRepoViewModel selectedRepoViewModel);
}
