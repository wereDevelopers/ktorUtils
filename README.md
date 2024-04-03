# ktorUtils

[![Jitpack](https://jitpack.io/v/wereDevelopers/retrofitUtils.svg)](https://jitpack.io/#wereDevelopers/retrofitUtils)
[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/wereDevelopers/retrofitUtils/blob/main/LICENSE)

Utility functions for ktor

## How to implement:

add in the Gradle

```groovy
dependencies {
    implementation('com.github.wereDevelopers:ktorUtils:{LastTag}')
}
```


## How to use


### init client:
It is very important to initialize the ktor client before using the library's utility functions
```
KtorUtils.initClient(client)

```

## example for retrieve json as a String:
```
KtorUtils.executeCall<String>(HttpMethod.Get, endpoint = "https://jsonplaceholder.typicode.com/todos/1")

```

to make a call that returns only a boolean you can use executeLight only to verify the return of the server is status 200 ok

```
KtorUtils.executeCallLight(HttpMethod.Get, endpoint = "https://jsonplaceholder.typicode.com/todos/1")

```
