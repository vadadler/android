
## Another coding assignment ##
Ok, I am on the market again. I am looking for an Android developer position. You've been there. You know the drill.
Below is copy and paste of an assignment I got. Seems fare? I don't know the expectations yet. My wildly incorrect estimate is a week. That is 7x24 hours to complete this. I know I am wrong. But sometimes it feels good to know that you are wrong. Excitimet comes not from what you know, but from what you don't know.

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
4. [RxJava and RxAndroid 2](https://github.com/ReactiveX/RxJava) RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM. RxAndroid is Android specific bindings for RxJava 2.

