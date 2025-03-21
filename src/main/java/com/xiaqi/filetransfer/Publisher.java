package com.xiaqi.filetransfer;

import java.util.ArrayList;
import java.util.List;

public abstract class Publisher {

    private List<Listener> listeners = new ArrayList<Listener>();

    public void subscribe(Listener listener) {
        listeners.add(listener);
    }

    protected void update(String code, Object... args) {
        for (Listener listener : listeners) {
            listener.update(code, args);
        }
    }

}
