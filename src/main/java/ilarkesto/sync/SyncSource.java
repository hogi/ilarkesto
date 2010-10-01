package ilarkesto.sync;

public interface SyncSource<I extends SyncItem> {

    void iterate(SyncItemProcessor processor);

    I getSyncItem(String id);

    void updateSyncItem(I item, I other);

    void deleteSyncItem(I m);

}
