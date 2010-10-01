package ilarkesto.concurrent;

import java.util.concurrent.ThreadFactory;


public class DeamonThreadFactory implements ThreadFactory {

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }

}
