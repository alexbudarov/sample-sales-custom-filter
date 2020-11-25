package com.company.sales.web.order;

import com.company.sales.entity.Customer;
import com.company.sales.web.filter.FilterDelegateExt;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.Filter;
import com.haulmont.cuba.gui.screen.*;
import com.company.sales.entity.Order;
import com.haulmont.cuba.web.gui.components.WebFilter;

import javax.inject.Inject;

@UiController("sales_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {
    @Inject
    private Filter filter;
    @Inject
    private DataManager dataManager;

    @Subscribe
    public void onInit(InitEvent event) {
        FilterDelegateExt filterDelegateExt = getFilterDelegateExt(filter);
        filterDelegateExt.addOptionsLoader(Customer.class, originalLoadContext -> {
            LoadContext<Customer> lc = new LoadContext<>(Customer.class);
            lc.setQueryString("select c from sales_Customer c order by c.name desc");
            lc.setView(View.MINIMAL);
            lc.getQuery().setMaxResults(500);
            return dataManager.loadList(lc);
        });
    }

    private FilterDelegateExt getFilterDelegateExt(Filter filter) {
        WebFilter webFilter = (WebFilter) filter;
        return (FilterDelegateExt) webFilter.getDelegate();
    }
}