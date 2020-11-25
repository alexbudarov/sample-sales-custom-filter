package com.company.sales.web.filter;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.FilterDataContext;
import com.haulmont.cuba.gui.components.filter.FilterDelegateImpl;
import com.haulmont.cuba.gui.components.filter.ParamEditor;
import com.haulmont.cuba.gui.components.filter.condition.AbstractCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FilterDelegateExt extends FilterDelegateImpl {

    private Map<Class<? extends Entity>, Function<LoadContext<? extends Entity>, List<? extends Entity>>> delegates = new HashMap<>();

    @Override
    protected ParamEditor createParamEditor(AbstractCondition condition, FilterDataContext filterDataContext) {
        FilterDataContext proxy = new DelegatingFilterDataContext(filterDataContext, delegates);
        return super.createParamEditor(condition, proxy);
    }

    public <E extends Entity> void addOptionsLoader(Class<E> entityClass, Function<LoadContext<E>, List<E>> delegate) {
        delegates.put(entityClass, (Function) delegate);
    }
}
