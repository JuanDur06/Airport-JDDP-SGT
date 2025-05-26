package core.models.dataLoader;

import java.util.List;

public class DataLoaderManager {

    private final List<IDataLoader> loaders;

    public DataLoaderManager(List<IDataLoader> loaders) {
        this.loaders = loaders;
    }

    public void loadAll() {
        for (IDataLoader loader : loaders) {
            loader.load();
        }
    }
}
