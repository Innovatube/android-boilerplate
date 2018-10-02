# ANDOID BOILERPLATE
## Use MVVM with DataBinding on presentation layer.
![MVVM](https://github.com/Innovatube/android-boilerplate/blob/kotlin-mvvm-clean/images/mvvm_architecture.svg)
### View
* View is the actual user interface in the app. It can be an Activity, a Fragment or any custom Android View. 
### ViewModel
* ViewModel is a model for the View of the app: an abstraction of the View. 
* ViewModel retrieves the necessary data from the Model, applies the UI logic and then exposes relevant data for the View to consume. 
* ViewModel should expose states for the View, rather than just events
* Any possible logic of the View is moved in the ViewModel.
### Model
* The Models hold the entire business logic.
* The Model exposes data easily consumable through event streams. 
* It composes data from multiple sources, like the network layer, database or shared preferences and exposes easily consumable data to whomever needs it. 
## Build based on Clean Architecture
![Clean Architecture](https://8thlight.com/blog/assets/posts/2012-08-13-the-clean-architecture/CleanArchitecture-8d1fe066e8f7fa9c7d8e84c1a6b0e2b74b2c670ff8052828f4a7e73fcbbc698c.jpg)
## Detail Data Flow Implementation
![Data Flow](https://github.com/Innovatube/android-boilerplate/blob/kotlin-mvvm-clean/images/clean_architecture_flow.svg)

## Folder structure
```
├── data… Data layer
│   ├── api… Packages for each API 
│   ├── mapper… Convert entity to model
│   └── repository… Implementation for Domain layer
│       ├── datasource… Implement local and remote datasource
│       └── storage… Share preferences data
├── domain… Implement business logic
│   ├── model… Business model
│   ├── repository… Interface to communicate with Data Layer
│   └── usecase… Implement business logic
├── presentation… Presentation layer
└── util… Utility functions every where in the app.
    ├── di… Dependencies Injection module
    ├── ext… Implement extension function 

```

## Coding Rule
* Should use Kotlin on new project
* Use Clean Architecture
* Use Functional Reactive Style (Observable/Flowable/Single/Compleable) to communicate between Domain/Data/Presentation layer 
* Use DI(Dagger2) to provide instance/object
* Write unit tests for usecase, repository, mapper at least.
* Don't prepare Base class, try to use delegation instead of inheritance as much as possible.	
* BaseActivity/BaseFragmen must not have any responsibility other than the process related with common process for Dagger.
* All object must treat as @NonNull, but if you want to be able to set Null on specific case, use @Nullable annotation.
* If test target method is package private, add @VisibleForTesting.
* Return value of Retrofit API interface must be Single/Completable.
* Use Navigator to navigate between Activity/Fragment
* Use DialogHelper to handle dialog.
* Format code and resolve lint check before committing the code
    * Use AndroidStudio standard formats (Reformat, Optimize import, Rearrange code)
      * Check abobe setting on setting dialog ( shift+opt+cmd+L to show it )
      * Use opt+cmd+L to re-format
      * Recommend to assign cmd+S as  re-format also
    * Resolve Lint alert/warnings as mush as you can. If you cannot resolve with reasonable reasons, need to write comment and @SuppressWarnings("") to hide it.
    * Remove all typo ( if you use any proper nouns or coin terms, add them to dictionary and share it with others)
* [Refer official Android Kotlin Guides by Google](https://android.github.io/kotlin-guides/style.html)
* [Project and code style guidelines](project_and_code_guidelines.md)
* [Architecture and Implementation](android_architecture.md)
* [Git commit references](git_template.md)
* [Support multi-language](https://medium.com/google-developer-experts/android-strings-xml-things-to-remember-c155025bb8bb#.t01sm5mi8)

## Test 
- To run the test
    - `./gradlew test`

## Codegen 
### 1. Download
Download *android-codegen-1.0-[your-os].tar* and extract to anywhere you want.
Open terminal and navigate to the folder you've just extracted above and run the following commands
```
chmod +x android-codegen
```
You should add android-codegen directory to `$PATH` so you can call `android-codegen` from anywhere.

### 2. Usages
Open `terminal` and change directory to your project folder, then execute `android-codegen generate`
```
cd ~/AndroidStudioProjects
android-codegen generate
```
then follow the instructions:
```
Enter relative path lead to your desired folder? (your directory should not start with `/`, leave it empty to use current directory)
directory 
What is your package name? (default is <%= package_name %>)
package_name 
What are you calling your app? (default is boilerplate)
app_name 
What is the compile sdk version? (default is 26)
compileSdkVersion 
What is the build tools version? (default is 26.0.2)
buildToolsVersion 
What is the minimum sdk you want to support? (default is 21)
minSdkVersion 
What is the target sdk version?(default is 26)
targetSdkVersion 
Enter color code for colorPrimary?(default is #2980b9)
colorPrimary 
Enter color code for colorPrimaryDark?(default is #2980b9)
colorPrimaryDark 
Enter color code for colorAccent?(default is #444444)
colorAccent 
```

### 3. Update template
update to existing codegen
```
chmod +x update_template.sh
./update_template.sh
```
update template to plugin
```
chmod +x update_template_plugin.sh
./update_template_plugin.sh
```

after update template in plugin folder, you must add, commit and prepare new release by yourself
## Code Status
[![Build Status](https://circleci.com/gh/Innovatube/android-boilerplate.svg?style=svg)](https://circleci.com/gh/Innovatube/android-boilerplate)

## License
[MIT License](https://opensource.org/licenses/MIT).