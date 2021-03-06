[![Chat at https://gitter.im/jvm-brotli/community](https://badges.gitter.im/jvm-brotli/community.svg)](https://gitter.im/jvm-brotli/community)

# Jvm-Brotli: Lightweight, cross-platform Java library for the Brotli compression format
Making Brotli usable in Java can be a tricky and time consuming exercise. The Brotli code is written in c/c++ and is platform dependent. This makes the use of JNI bindings mandatory. 

The bindings are provided by Google, but it is still left to the Java developer to compile the Brotli and JNI source files individually for every platform they wish to support. Anybody who has dealt with JNI bindings and native code in the past, will already be familiar with the problems and complexity this approach can add to a Java project.

**This is where Jvm-Brotli comes in**. The goal for this project is to provide easy access to the Brotli compression algorithm for Java developers and users on all platforms. 

Jvm-Brotli aims to:

- Take the up-to-date c/c++ and Java code from [google/brotli](https://github.com/google/brotli).
- Compile it on multiple platforms.
- Package the pre-compiled native libraries into JARs and upload them to maven central
- Automatically download the correct native library from maven central based on the user's current platform.
- Provide source code with project breakdown and build instructions, to make forking and building as easy as possible.

## General Information
* Project website: https://jvmbrotli.com
* Chat: https://gitter.im/jvm-brotli/community

## Supported Platforms
* Windows (32 and 64 bit) - Tested on Windows 7 and 10
* Linux (64 bit) - Tested on Ubuntu 18.04
* Mac OSX - Tested on Mojave

**Please be aware** that "platform" in this context means JVM version, and not your operating system! (e.g. Windows 64-bit running a 32-bit version of JVM will identify as 32-bit!)

#### Support Coming Soon
* Linux on ARM processors (64 bit) - The build files are ready, just haven't had the opportunity to do the actual build

#### Other Platforms
If you are uncertain about your platform being supported, we encourage you to clone the [example project](https://github.com/nixxcode/jvm-brotli-example) and try running it. This will give you a definitive answer.

If your platform is not supported, we would really appreciate if you could create an issue and request for support to be added. The goal for this project is to make Brotli readily available to Java developers and users on as many platforms as possible. We can make it happen with your help!

## Getting Started
Jvm-Brotli is designed with ease of use in mind. To include it in your Maven project, simply add the following dependency to your pom.xml

```xml
<dependency>
    <groupId>com.nixxcode.jvmbrotli</groupId>
    <artifactId>jvmbrotli</artifactId>
    <version>0.2.0</version>
    <optional>true</optional>
</dependency>
```
That's all! You don't need to worry about what platform you're on, as long as it's supported. The correct native library will be downloaded automatically as an additional Maven dependency. 

**The same applies for transitive dependencies as well!** You don't need to worry about others getting the wrong native library when they include your project.

The **optional** tag is not a must, but is highly recommended given the JNI-dependent nature of Jvm-Brotli. It means your dependents will also need to add Jvm-Brotli as a maven or gradle dependency if they wish to include it in their project. This way, you are not forcing JNI code on your dependents.

### Usage Examples
The code snippets below should get you started. If you are looking for code that's ready to execute, we have an [example project](https://github.com/nixxcode/jvm-brotli-example) you can clone.

#### Loading Jvm-Brotli:
You MUST call the following method at least once during your application's runtime if you wish to use Brotli:

```java
BrotliLoader.isBrotliAvailable();
```

This static method call attempts to load the native Brotli library into the current JVM runtime when invoked **for the first time**. If loading succeeds, it silently returns true. If loading fails, it prints an exception to console and returns false. Subsequent calls only return true/false, depending if the original load attempt was successful or not. No further load attempts are made unless the application is restarted.

#### Encoding a stream:
```java
// Init file input and output
FileInputStream inFile = new FileInputStream(filePath);
FileOutputStream outFile = new FileOutputStream(filePath + ".br");

// If being used to compress streams in real-time, I do not advise a quality setting above 4 due to performance
Encoder.Parameters params = new Encoder.Parameters().setQuality(4);

// Initialize compressor by binding it to our file output stream
BrotliOutputStream brotliOutputStream = new BrotliOutputStream(outFile, params);

int read = inFile.read();
while(read > -1) { // -1 means EOF
    brotliOutputStream.write(read);
    read = inFile.read();
}

// It's important to close the BrotliOutputStream object. This also closes the underlying FileOutputStream
brotliOutputStream.close();
inFile.close();

```

#### Decoding a stream:
```java
// Init file input and output
FileInputStream inFile = new FileInputStream(filePath);
FileOutputStream outFile = new FileOutputStream(decodedfilePath);

// Initialize decompressor by binding it to our file input stream
BrotliInputStream brotliInputStream = new BrotliInputStream(inFile);

int read = brotliInputStream.read();
while(read > -1) { // -1 means EOF
    outFile.write(read);
    read = brotliInputStream.read();
}

// It's important to close the BrotliInputStream object. This also closes the underlying FileInputStream
brotliInputStream.close();
outFile.close();
```

## Project Structure & Build Instructions
Please see [PROJECT_STRUCTURE.md](https://github.com/nixxcode/jvm-brotli/blob/master/PROJECT_STRUCTURE.md)

## Contributing
First of all, thank you for your interest in Jvm-Brotli! 

Contributions are very welcome and highly appreciated. If you are interested, please have a quick read through our [short contribution guide](https://github.com/nixxcode/jvm-brotli/blob/master/CONTRIBUTING.md). 

Thank you!

## Licensing
Google Brotli and corresponding Java code are licensed under the [MIT License](https://opensource.org/licenses/MIT)

Code belonging to the project author ([Nixxcode](https://github.com/nixxcode)) and MeteoGroup Deutschland GmbH is licensed under the [Apache License](https://www.apache.org/licenses/LICENSE-2.0)

If in doubt, refer to individual source file headers

## Thanks
- [Google Brotli](https://github.com/google/brotli) team for creating Brotli.
- [Martin W. Kirst](https://github.com/nitram509) for creating the original jBrotli that inspired this project.
- [David Åse](https://github.com/tipsy) for his help and guidance in publishing my first OS project.
