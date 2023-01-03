package com.example.wagbaproj;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mRepository;
    private User mAllUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getUsers();
    }

    public User getAllUsers() { return mAllUsers; }

    public void insert(User user) { mRepository.insert(user); }
}

