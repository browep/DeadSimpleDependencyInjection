# Dead Simple Dependency Injection
A dependency injection library that you can actually understand and use.  

Some features:

* utilizes the standard `@Inject` annotation provided by JSR330
* inject any object
* ability to swap out Injectors for different uses ( Production / Development / Testing )
* provide real world examples of usages
* easy to understand and modify
* no code generation ( no more wondering why things aren't working )

### Why?
The impetus for this library has been the many failed attempts at trying to get Dagger or Dagger2 to work at all.  Getting frustrated when it doesn't work and wondering why there is so much boilerplate code to do something so simple.

### Getting Started

##### Gradle dependency

in the root `build.gradle`

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

in the project `build.gradle`

```
dependencies {
  ...
	compile 'com.github.browep:DeadSimpleDependencyInjection:release-beta-4'
}
```

##### Gradle library

add a submodule
`git submodule add git@github.com:browep/DeadSimpleDependencyInjection.git libraries/dsdi`

add the `libaries/dsdi/library` folder as dependency

in `/settings.gradle`

`include ':app', ':libraries:dsdi:library'`

in `/app/build.gradle`

````
dependencies {
       compile project(':libraries:dsdi:library')
}
````

##### Copy/Paste

The core of the library is just one file.  It can be copied into your project without anything else `/library/src/main/java/com/github/browep/dsdi/DependencySupplier.java`

### Injection

There are two components to DSDI:
* Injectee, a class that requires dependencies and uses `@Inject` to annotate them.
* Injector, a class that supplies those dependencies

We will setup our Injector in the Application object and then get a reference from it wherever we need it.  Right now the Injector only supplies one class, the `NetworkAdapter` class which will handle our network communication.

````
public class SampleApplication extends android.app.Application {

    private DependencySupplier dependencySupplier;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        // setup dependency injector
        dependencySupplier = new ProductionDependencySupplier();
    }

    public DependencySupplier getDependencySupplier() {
        return dependencySupplier;
    }

}
````
`ProductionDependencySupplier` is a class that extends DepedencySupplier.  In `supply` you override the functionality that creates the dependencies when you need them.  You can create singletons by keeping them as fields and then returning the fields when needed as done below.
````
public class ProductionDependencySupplier extends DependencySupplier {

    private NetworkAdapter networkAdapter;

    public ProductionDependencySupplier() {
        this(false);
    }

    public ProductionDependencySupplier(Boolean log) {
        super(log);

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkAdapter = new NetworkAdapter(retrofit.create(Server.class));
    }

    @Override
    public Object supply(Object injectee, Class injectionClass) {
        if (injectionClass.equals(NetworkAdapter.class)) {
           return networkAdapter;
        } else {
            throw new IllegalArgumentException("could not supply: " + injectionClass);
        }
    }
}

````
For the injectee, create a class with fields annotated with the `javax.inject.Inject` annotation and then call `DependencySupplier.inject` on it.

````
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    private NetworkAdapter networkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((SampleApplication) getApplicationContext()).getDependencySupplier().inject(this);
    }


}
````

That's it!

That field will then be supplied by the DependencySupplier.  

### Issues

Please use the issue tracker.

------

Copyright 2016 Paul Brower

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
