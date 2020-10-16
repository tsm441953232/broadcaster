package com.tsm.broadcaster;

import com.tsm.broadcaster.entity.Message;
import com.tsm.broadcaster.exception.NotifyException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public interface BroadcasterProvider <M extends Message>{
    void setParams(Map<String, Object> var1);

    void notify(M var1) throws NotifyException, IOException, TimeoutException;
}
