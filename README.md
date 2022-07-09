![](phobos.jpg)

## Earthhack Installer (dump)
### Dumped it from 1.6.2 "leaked" .jar
yes i know there are the "open source" phobos code, but I decompiled and extracted from the first .jar leak

### Building
- Download as .zip
- Extract
- `cd` in the extracted folder
- `gradlew build` or `chmod +x ./gradlew build`

The jar will be on `build/libs`

### How I did it
Downloaded a random .jar that I've found on github, supposedly the first leak of the jar, then I extracted the classes one per one using [JByteCustom](https://github.com/synnkfps/JByteCustom/) then I did `gradle init` to initialize the gradle project, then I opened on Intellij IDEA and fixed file per file. Then I simply fixed the `build.gradle` stuff and done.

I did this for testing purposes, and for learning Java and Gradle too! <br>
**Note: The "installer" isn't working at moment because I still didn't added the server connection system, later I do that!** <br>
Enjoy!
