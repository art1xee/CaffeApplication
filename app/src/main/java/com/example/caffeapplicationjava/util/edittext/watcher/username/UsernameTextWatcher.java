package com.example.caffeapplicationjava.util.edittext.watcher.username;

import com.example.caffeapplicationjava.util.edittext.watcher.DefaultTextWatcher;

public class UsernameTextWatcher extends DefaultTextWatcher {

    private UsernameChangeListener listener;

    public UsernameTextWatcher(UsernameChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        listener.onNameChanged(s.toString());
    }
}
