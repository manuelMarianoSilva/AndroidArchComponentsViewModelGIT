package io.neverstoplearning.acviewmodel.home.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.neverstoplearning.acviewmodel.home.model.Repo;
import io.neverstoplearning.acviewmodel.home.networking.RepoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends ViewModel {

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final RepoService repoService;
    private Call<List<Repo>> repoCall;

    @Inject
    public ListViewModel(RepoService repoService) {
        this.repoService = repoService;
        fetchRepos();
    }

    public LiveData<Boolean> getRepoLoadError() {
        return repoLoadError;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    private void fetchRepos() {
        loading.setValue(true);
        repoCall = repoService.getRepositories();

        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                repoLoadError.setValue(false);
                repos.setValue(response.body());
                loading.setValue(false);
                repoCall = null;
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error loading repos", t);
                repoLoadError.setValue(true);
                loading.setValue(false);
                repoCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (repoCall != null)
            repoCall.cancel();
            repoCall = null;
    }
}