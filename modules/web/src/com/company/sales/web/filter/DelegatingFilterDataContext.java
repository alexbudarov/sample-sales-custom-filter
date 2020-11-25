package com.company.sales.web.filter;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.FilterDataContext;
import com.haulmont.cuba.gui.components.filter.Param;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataLoader;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class DelegatingFilterDataContext extends FilterDataContext {
    private final FilterDataContext delegate;

    private Map<Class<? extends Entity>, Function<LoadContext<? extends Entity>, List<? extends Entity>>> loaders;

    public DelegatingFilterDataContext(FilterDataContext delegate,
            Map<Class<? extends Entity>, Function<LoadContext<? extends Entity>, List<? extends Entity>>> loaders) {
        super(null);
        this.delegate = delegate;
        this.loaders = loaders;
    }

    @Override
    public void registerCollectionLoader(Param param, DataLoader loader) {
        if (loader instanceof CollectionLoader) {
            CollectionLoader collectionLoader = (CollectionLoader) loader;
            for (Map.Entry<Class<? extends Entity>, Function<LoadContext<? extends Entity>, List<? extends Entity>>> entry
                    : loaders.entrySet()) {
                if (param.getJavaClass() != null && entry.getKey().isAssignableFrom(param.getJavaClass())) {
                    collectionLoader.setLoadDelegate(entry.getValue());
                }
            }
        }

        delegate.registerCollectionLoader(param, loader);
    }

    @Override
    public void registerContainerCollectionChangeListener(Param param, CollectionContainer container, Consumer<CollectionContainer.CollectionChangeEvent<?>> listener) {
        delegate.registerContainerCollectionChangeListener(param, container, listener);
    }

    @Override
    public void loadAll() {
        delegate.loadAll();
    }

    @Override
    public void loadForParam(Param param) {
        delegate.loadForParam(param);
    }

    @Override
    public void unregisterParam(Param param) {
        delegate.unregisterParam(param);
    }

}
