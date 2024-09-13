City Construction
=================

This is a [Gradle][]-based Java project structure. Provided you have the [OpenJDK][] installed, the `gradlew` script will take care of all other dependencies.

[Java]: https://docs.oracle.com/javase/tutorial/
[Gradle]: https://gradle.org/
[OpenJDK]: https://adoptium.net/temurin/releases/

Put your source code in `src/main/java` (or in further subdirectories inside that, according to package). To change the name or package of the main class, make sure to update the `mainClass` line in the configuration file `build.gradle`.


## Running

To run your code (for debugging purposes), invoke the `gradlew` script with a `run` argument:

```
./gradlew run
```

If you need to provide command-line arguments:

```
./gradlew run --args="arg1 arg2 arg3"
```

If you run into permission problems:

```
bash gradlew run
```


## Linting and Testing

This project has been configured to use [PMD][] to check code quality, and (though you don't need it for OOSE) [JUnit 5][] to perform unit testing. To perform these steps:

[PMD]: https://docs.pmd-code.org/latest/
[JUnit 5]: https://junit.org/junit5/

```
./gradlew check
```

(Alternatively, run "`./gradlew build`".)

Look up and fix any of the [PMD warnings](), or [suppress][] them if/where appropriate.

[PMD warnings]: https://docs.pmd-code.org/latest/pmd_rules_java.html
[suppress]: https://docs.pmd-code.org/latest/pmd_userdocs_suppressing_warnings.html


## Logging

This project configures logging ([java.util.logging][]) so that you don't need to. Simply obtain logger objects with `Logger.getLogger()`, and insert logging statements where appropriate.

[java.util.logging]: https://docs.oracle.com/en/java/javase/21/core/java-logging-overview.html

# Criteria Discussion
#### For my program I have implemented the strategy pattern to enforce as my programs option 2 function BuildCity where it has 3 different approaches (Uniform, Random and Central) address similar goals (get number of floors, get foundation and get material) but using slightly different algorithms. Each of these algorithms get their own class and implement a common interface which allows for better maintenence and cleaner code. New approaches can be added without modifying any original code (follows open/closed principle). As each approach gets its own class, this allows for the selection of different approaches at runtime phase based on the user input.

#### A single interface "Structure" has abstract methods. These abstract methods are implemented in the Uniform, Random and Central classes with their own unique algorithms. These 3 classes can then be instantiated as type "Structure" and stored in a container and called depending on the program conditions.

#### My program also implements a decorator pattern which allows for different "behaviours" to be added to an object during runtime. The object "GridSquare" is wrapped in ZoningRule objects (Heritage, Heightlimit, Contamination and FloodRisk) which describe the zoning rules of the square in the grid. This strategy avoids to many classes having to be being created for every possible combination of zoning rules. More zoning rule classes (functionality) can easily be added at any time making the code much more flexible and clean.


#### A single interface "GridSquare"  has abstract methods to be implemented by (Swampy, Flat or Rocky). After the type of square is instantiated by the abstract decorator class "ZoningRules" which holds a reference to the component object which can be either another decorator component (heritage, heightlimit, contamination or floodrisk) or original component (swampy, flat or rocky).
