This project runs on two environments: __dev__ and __staging__. The first one leverages a fake API, while the latter effectively uses
the [Github REST API][1].

### Installation

If you're not on Android Studio, you can build & install this app using gradle:


```
./gradlew installDevDebug
```

or


```
./gradlew installStagingDebug
```

[1]: https://docs.github.com/en/rest/overview