## Table of Contents

- [Overview](#overview)
- [Requirements](#requirements)
- [Bonus](#bonus)
- [What to build?](what-to-build?)
- [Architecture](#architecture)
- [MVP or MVVM](#mvp-or-mvvm)
- [Guidelines](#guidelines)
- [Screens](#screens)
- [High level architecture](#high-level-architecture)
- [UML](#uml)

## Overview ##
Build an Android application from scratch and be prepared to explain the purpose and decision-making behind
every part of your project.

## Requirements ##
1. Display a list of data from a public api of your choosing
Some ideas at https://github.com/toddmotto/public-apis
2. Schedule a background job to display a periodic notification with new data
The background job may be automatically scheduled or via user input.
3. Use any one or combination of the architecture styles employed by the sample projects at the
following links:
https://github.com/googlesamples/android-architecture
https://github.com/googlesamples/android-architecture-components
4. Regardless of the structure and organization you choose, you must use the following 3rd party
dependencies in some way:
* Retrofit 2
* ButterKnife
* Dagger 2
* RxJava 2 or Agera
5. Write at least one unit test using JUnit and at least one ui test using Espresso.
You are encouraged to mock dependencies and data sources for the tests.

## Bonus ##
Go above and beyond!
Some ideas:
* Implement local caching so that your application functions offline
* Gracefully handle errors
* Provide compatibility for older version of the Android platform
* Include a splash screen
* Use transitions/animations
* Choose an api that returns images and display them
* Write robust tests
* Verify that no errors are found by lint
* Provide good documentation
* ???

## What to build? ##

I decided to build a news app based on API available at https://newsapi.org/. Here are some useful links:
* You need a key. Get one [here](https://newsapi.org/register)
* [Here](https://newsapi.org/docs) is documentation

## Architecture ##

Based on the requirements the following packages are/will be be used:
1. [Retrofit 2](http://square.github.io/retrofit/) Retrofit is a REST Client for Android and Java developed by Square. It makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice. This library is being used to GET news articles.
2. [ButterKnife](https://github.com/JakeWharton/butterknife) is a viewbinding library for Android that uses annotation to generate boilerplate code.
3. [Dagger 2](https://google.github.io/dagger/) is a fully static, compile-time dependency injection framework for both Java and Android.
4. [RxJava](https://github.com/ReactiveX/RxJava) and and [RxAndroid 2](https://github.com/ReactiveX/RxAndroid) RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM. RxAndroid is Android specific bindings for RxJava 2.
5. [Mosby](https://github.com/sockeqwe/mosby) is library is to help building MVP based Android applicaitons. Mosby helps to handle screen orientation changes by introducing ViewState and retaining Presenters.

## [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) or [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)? ##
There are pros and cons for both architectual approaches. I decided to use MVP since I felt more comfortable with it.

## Guidelines ##
Below is list of guidelines I am trying to follow:
1. Make views dumb and passive. By following [Passive View](https://martinfowler.com/eaaDev/PassiveScreen.html) pattern testability is being improved as well as separation of concerns is being enforced.
2. Make presenter Android-free. Abstract presenter from implementation details (Android). By having pure Java implementation improves testability (no need for Android emulator).
3. 

## Screens ##
I decided to use Google News application as starting point.

| Main Screen   | Main Screen Details |
| ------------- |---------------------|
| ![](https://github.com/vadadler/android/blob/master/data-public-api/artifacts/newsapp.png) | ![](https://github.com/vadadler/android/blob/master/data-public-api/artifacts/newsapp_parts.png)       |

## High level architecture ##

Initial implementation of the application will always read current news from https://newsapi.org/ and persist them in SQLite instance. Views will be updated from that SQLite database. This approach leaves plenty of opportunities to optimize and enhance user experience. 
![](https://github.com/vadadler/android/blob/master/data-public-api/artifacts/high_level_architecture.png)

## UML ##
| Contract   | ![](https://github.com/vadadler/android/blob/master/data-public-api/artifacts/contract.png) |
| ------------- |---------------------|


