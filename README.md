# Initial release of codegen for boilerplate
# 1. Installation
## Codegen
Download *android-codegen-0.1-[your-os].tar* and extract to anywhere you want.
Open terminal and navigate to the folder you've just extracted above and run the following commands
```
chmod +x android-codegen
```
You should add android-codegen directory to `$PATH` so you can call `android-codegen` from anywhere.

# 2. Usages
## Codegen
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
