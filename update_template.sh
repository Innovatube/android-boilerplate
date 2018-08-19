#!/bin/bash
# setup current directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# get required path
read -e -p "🤔 Enter codegen folder: " codegen_folder
codegen_template_directory="${codegen_folder}/lib/app/boilerplate/templates/"

# remove old resources
rm -rf boilerplate
rm -rf boilerplate.zip

# archive template
git archive -o boilerplate.zip HEAD

# extract new template
cd $DIR
mkdir boilerplate && unzip -qq boilerplate.zip -d boilerplate

# playing with regex
echo "⚙️ Updating build.gradle"
perl -pi -w -e 's/(?<=compileSdkVersion = ).*/<%= compileSdkVersion %>/g;' $DIR/boilerplate/build.gradle
perl -pi -w -e 's/(?<=minSdkVersion = ).*/<%= minSdkVersion %>/g;' $DIR/boilerplate/build.gradle
perl -pi -w -e 's/(?<=targetSdkVersion = ).*/<%= targetSdkVersion %>/g;' $DIR/boilerplate/build.gradle
perl -pi -w -e "s/(?<=buildToolsVersion = ).*/'<%= buildToolsVersion %>'/g;" $DIR/boilerplate/build.gradle
perl -pi -w -e "s/(?<=support_lib_version = ).*/'<%= supportLibraryVersion %>'/g;" $DIR/boilerplate/build.gradle

echo "⚙️ Updating package name"
find $DIR/boilerplate -type f | xargs perl -pi -e 's/<%= package_name %>/<%= package_name %>/g;'

echo "⚙️ Updating color code"
find $DIR/boilerplate -type f | egrep -i ".xml" | xargs perl -pi -w -e 's/(?<=colorPrimary">).*(?=<\/color>)/<%= colorPrimary %>/g;'
find $DIR/boilerplate -type f | egrep -i ".xml" | xargs perl -pi -w -e 's/(?<=colorPrimaryDark">).*(?=<\/color>)/<%= colorPrimaryDark %>/g;'
find $DIR/boilerplate -type f | egrep -i ".xml" | xargs perl -pi -w -e 's/(?<=colorAccent">).*(?=<\/color>)/<%= colorAccent %>/g;'

echo "♻️ Removing old template"
rm -rf $codegen_template_directory/boilerplate

echo "🏭 Coping template"
cp -a boilerplate $codegen_template_directory

# cleaning up
echo "♻️ cleaning up"
rm -rf boilerplate
rm -rf boilerplate.zip

echo "🍻 🍻 All set! 🍻🍻"