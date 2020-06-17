# TodoApp
TodoApp is a todo-list android app to keep record of todo task using Java. This project also helps to demonstrate how to use Android Architecture Components (View Model, Data Binding, Live Data, lifecycle-aware) along with SQLite database build a robust application (scalable, testable, less bug and easy to maintain). 
This project has implemented MVVM architecture pattern using android architecture components such as Room, Lifecycle-aware components, ViewModels, LiveData and many more.

# Table of Content
- [Package Name](#Package-Name)
- [Folder Overview](#Folder-Overview)
- [App Overview](#App-Overview)
- [Android Architecture Components Overview](#Android-Architecture-Component-overview)
    - [Model](#Model)
    - [View](#View)
    - [ViewModel](#ViewModel)
    - [Room Persistence Library](#)
- [Lifecycle-aware](#Lifecycle-aware)
    - [LifecycleObserver](#LifecycleObserver) 
    - [LiveData](#LiveData)
- [RecyclerView](#RecyclerView)
- [Fragment](#Fragment)
- [Todo]()

# Package Name
- tbc.dma.todo.adapter
- tbc.dma.todo.helperClasses
- tbc.dma.todo.model
    - dao
    - entity
    - room
    - repository
- tbc.dma.todo.view
    - Activity
    - Fragment
- tbc.dma.todo.viewModel

# Folder Overview
![image](https://github.com/mk1995/TodoApp/blob/step4-adding-search-feature/app/README_ASSETS/package_name.png?raw=true)

# App Overview
![image](https://github.com/mk1995/TodoApp/blob/step4-adding-search-feature/app/README_ASSETS/AppOverview.gif)


# Andriod Architecture Component overview
MVVM architecture is a Model-View-ViewModel architecture that removes the tight coupling between each component. Most importantly, in this architecture, the children don't have the direct reference to the parent, they only have the reference by observables.
each component depends only on the component one level below it. For example, activities and fragments depend only on a view model. The repository is the only class that depends on multiple other classes; in this example, the repository depends on a persistent data model and a remote backend data source.
![image](https://s3.ap-south-1.amazonaws.com/mindorks-server-uploads/mvvm.png)
#### Model
Models are components that are responsible for handling the data for an app. They're independent from the View objects and app components in your app, so they're unaffected by the app's lifecycle and the associated concerns. To drive the UI, our data model needs to hold the following data elements:
It represents the data and the business logic of the Android Application. It consists of the business logic - local and remote data source, model classes, repository.
![image](https://github.com/mk1995/TodoApp/blob/step4-adding-search-feature/app/README_ASSETS/modelVideo.gif)

#### View
It consists of the UI Code(Activity, Fragment), XML. It sends the user action to the ViewModel but does not get the response back directly. To get the response, it has to subscribe to the observables which ViewModel exposes to it.
#### ViewModel
A ViewModel object provides the data for a specific UI component, such as a fragment or activity, and contains data-handling business logic to communicate with the model. For example, the ViewModel can call other components to load the data, and it can forward user requests to modify the data. The ViewModel doesn't know about UI components, so it isn't affected by configuration changes, such as recreating an activity when rotating the device. It is a bridge between the View and Model(business logic). It does not have any clue which View has to use it as it does not have a direct reference to the View. So basically, the ViewModel should not be aware of the view who is interacting with. It interacts with the Model and exposes the observable that can be observed by the View.

>In ViewModel, Repository modules handle the data operations. They provide a clean API so that the rest of the app can retrieve this data easily. They know where to get the data from and what API calls to make when data is updated. You can consider repositories to be mediators between different data sources, such as persistent models, web services, and caches.



#### Room Persistence Library
 Room is an object-mapping library that provides local data persistence with minimal boilerplate code. At compile time, it validates each query against your data schema, so broken SQL queries result in compile-time errors instead of runtime failures. Room abstracts away some of the underlying implementation details of working with raw SQL tables and queries. It also allows you to observe changes to the database's data, including collections and join queries, exposing such changes using LiveData objects. It even explicitly defines execution constraints that address common threading issues, such as accessing storage on the main thread.
 ![image]()
    
# Lifecycle-aware
An object is said to be lifecycle-aware if it is able to detect and respond to changes in the lifecycle state of other objects within an app. Some Android components, **LiveData** being a prime example, are already lifecycle-aware. It is also possible to configure any class to be lifecycle-aware by implementing the LifecycleObserver interface within the class.
#### LifecycleObserver
In **OnCreate()** method of MainActivity, implement **LifecycleObserver** and add this activity as Observer.
```java
     getLifecycle().addObserver(this);
 ```
 Now we can observer the lifecycle event as folows: 
 ```java
@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
public void doSomethingOnLifeCycleStateChanged() {
    Toast.makeText(this, "Welcome to TODO. CurrentState: "+getLifecycle().getCurrentState(), Toast.LENGTH_LONG).show();
}
 ```
 Here we can do some usefull stuff when lifecycle is onResume state similarly we can perform desired task for any lifecycle event.

#### LiveData
LiveData is one of the newly introduced architecture components. LiveData is an observable data holder. This allows the components in your app to be able to observe LiveData objects for changes without creating explicit and rigid dependency paths between them. LiveData field is lifecycle aware of the app components (activities, fragments, services), utomatically cleans up references after they're no longer needed and hence prevents object leaking and excessive memory consumption.

# RecyclerView
RecyclerView s a ViewGroup that renders any adapter-based view in a similar way which is flexible and efficient version of ListView and GridView. It is an container for rendering larger data set of views that can be recycled and scrolled very efficiently. RecyclerView is like traditional ListView widget, but with more flexibility to customizes and optimized to work with larger datasets.
![image](https://developer.android.com/training/material/images/RecyclerView.png)
```xml
<androidx.recyclerview.widget.RecyclerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/recyclerViewAllTasksList">
</androidx.recyclerview.widget.RecyclerView>
```

# RecyclerView Adapter
RecyclerView includes a new kind of adapter. It’s a similar approach to the ones you already used, but with some peculiarities, such as a required ViewHolder. You will have to override two main methods: one to inflate the view and its view holder, and another one to bind data to the view. The good thing about this is that the first method is called only when we really need to create a new view. No need to check if it’s being recycled.

![image](https://github.com/mk1995/TodoApp/blob/step4-adding-search-feature/app/README_ASSETS/recyckerviewadapter.gif)

# Fragment

# Todo

# Reference
- [MVVM arch]() 
https://blog.mindorks.com/mvvm-architecture-android-tutorial-for-beginners-step-by-step-guide#:~:text=MVVM%20architecture%20is%20a%20Model,have%20the%20reference%20by%20observables.)
- [Android Architecture Component]() https://developer.android.com/topic/libraries/architecture
- [Guid to app architecture component]() https://developer.android.com/jetpack/docs/guide
- [ViewModel]()
https://developer.android.com/reference/androidx/lifecycle/ViewModel
- [Fragment and FragmentManager]() https://developer.android.com/training/animation/screen-slide
- [RecyclerView and Adpater]() https://android.developreference.com/article/16271268/What+is+lifecycle+for+RecyclerView+adapter%3F
- [On Configuration Changes]() https://developer.android.com/guide/topics/resources/runtime-changes
- [Lifecycle and Lifecycle Observer]() https://developer.android.com/reference/androidx/lifecycle/LifecycleObserver
- [Room Persistence Library]() https://developer.android.com/topic/libraries/architecture/room
- [Android Obeserver pattern]()
https://code.tutsplus.com/tutorials/android-design-patterns-the-observer-pattern--cms-28963#:~:text=What%20Is%20the%20Observer%20Pattern,depend%20on%20it%20are%20notified.
- [Lifecycle-aware Components]()
https://androidwave.com/lifecycle-aware-components-architecture-components/
- [Prepare for release apk]()
https://developer.android.com/studio/publish/preparing
- [Android Dialog]()
https://developer.android.com/reference/android/app/Dialog
- [DatePickerDialogue]() 
https://developer.android.com/reference/android/app/DatePickerDialog#DatePickerDialog(android.content.Context)




