## Filter customization - entity option loading

This branch of the sales sample application demonstrates how you can customize loading entity options for `Filter`'s fields. 

- Created extended Spring prototype bean `com.company.sales.web.filter.FilterDelegateExt`.

- Registered this bean in `web-spring.xml`:
```
<bean id="cuba_FilterDelegate" scope="prototype" class="com.company.sales.web.filter.FilterDelegateExt"/>
```

- Created extended `com.company.sales.web.filter.DelegatingFilterDataContext` class that delegates most of logic to the original class, but hooks into `void registerCollectionLoader(Param param, DataLoader loader)` method to assign load delegate to the data loader which is used to load entity options for filter field.   

- Used method provided by the extended filter delegate in the `OrderBrowse` screen to set custom entity option loader for the `Customer` entity.
- Note that you must register option loader in the `onInit` screen controller's event handler (because for default filters condition components are initialized right after `onInit` event).

# Sales

This is a "Hello World" application which is designed to show the core capabilities and features of both the [CUBA Platform](https://www.cuba-platform.com) and Studio.

The Sales application is a simple purchase management system that enables tracking orders made by customers. Each order consists of a number of products. Customers, products and orders can be created, edited and deleted through the system user interface.

The application project covers the following aspects:

- Data Model Design
    - Creating entities
    - Executing DDL scripts
    - Creating relationships in entities
- User Interface Design
    - Generating CRUD screens
    - Creating views for related entities
    - Creating data containers for screens to display composite screens
    - Visual editing of the existing screens
- Creating Integration Tests

Based on CUBA Platform 7.2

## Issues
Please use https://www.cuba-platform.com/discuss for discussion, support and reporting problems corresponding to this sample.
