package io.neverstoplearning.acviewmodel.home.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

import javax.inject.Inject;

import io.neverstoplearning.acviewmodel.home.model.Repo;
import io.neverstoplearning.acviewmodel.home.networking.RepoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectedRepoViewModel extends ViewModel {

    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();
    private final RepoService repoService;
    private Call<Repo> repoCall;

    @Inject
    public SelectedRepoViewModel(RepoService repoService){
        this.repoService = repoService;

    }

    public LiveData<Repo> getSelectedRepo() {
        return selectedRepo;
    }

    void setRepo (Repo repo){
        selectedRepo.setValue(repo);
    }

    public void saveToBundle(Bundle outState) {
        if (selectedRepo != null) {
            outState.putStringArray("repo_details",
                    new String[]{selectedRepo.getValue().owner.login,
                            selectedRepo.getValue().name});
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        if (selectedRepo.getValue() == null){
            if (savedInstanceState != null && savedInstanceState.containsKey("repo_details")) {
                loadRepo(savedInstanceState.getStringArray("repo_details"));

            }
        }
    }

    private void loadRepo(String[] repoDetails) {
        repoCall = repoService.getRepo(repoDetails[0], repoDetails[1]);
        repoCall.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                selectedRepo.setValue(response.body());
                repoCall = null;
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                repoCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
