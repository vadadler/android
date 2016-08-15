# Design Patterns

The purpose of this project is to learn how to implement MVC, MVP and MVVM design patterns is Android.
This [article](https://medium.com/android-news/android-architecture-2f12e1c7d4db#.v9nwsx4z8) is used as
the source of explanation for these patterns. As well as source for some images below.

## Application

The goal of the project is to show how to implement the same idea using three different patterns. 
The application uses [Open Library API](https://openlibrary.org/developers/api) to search for books 
and allows to drill down to a book to show details.

## Model View Controller

This pattern forces a separation of concerns, it means domain model and controller logic are decoupled 
from user interface (view).

![alt text](https://cdn-images-1.medium.com/max/1600/1*U6JRenliQAVEsdD7YZuv1g.png "MVC")

## Model View Presenter

![alt text](https://cdn-images-1.medium.com/max/1600/1*1P4n9JkHChEUVr5umQx4Zw.png "MVP")

## Model View ViewModel

This pattern supports two-way data binding between view and View model. This enables automatic 
propagation of changes, within the state of view model to the View. Typically, the view model uses 
the observer pattern to notify changes in the view model to model.

![alt text](https://cdn-images-1.medium.com/max/1600/1*fpTUAtCz8iiWU90WM7Pj4w.png "MVVM")
