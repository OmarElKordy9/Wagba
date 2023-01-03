package com.example.wagbaproj;

import android.app.Application;
import android.os.AsyncTask;

public class UserRepository {
    private UserDao mUserDao;
    private User mUser;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mUser = mUserDao.getUsers();
    }

    User getUsers() {
        return mUser;
    }

    public void insert (User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;
        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }
}
