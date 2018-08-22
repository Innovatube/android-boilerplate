#!/bin/bash
# setup directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
current_branch_name="$(git name-rev --name-only HEAD)"
git fetch

# remove old resources
rm -rf boilerplate
rm -rf boilerplate.zip

# archive template
git archive -o boilerplate.zip HEAD

# switch branch 
git checkout feature/codegen
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
find $DIR/boilerplate -type f | xargs perl -pi -e 's/com.innovatube.boilerplate/<%= package_name %>/g;'

echo "⚙️ Updating color code"
find $DIR/boilerplate -type f | egrep -i ".xml" | xargs perl -pi -w -e 's/(?<=colorPrimary">).*(?=<\/color>)/<%= colorPrimary %>/g;'
find $DIR/boilerplate -type f | egrep -i ".xml" | xargs perl -pi -w -e 's/(?<=colorPrimaryDark">).*(?=<\/color>)/<%= colorPrimaryDark %>/g;'
find $DIR/boilerplate -type f | egrep -i ".xml" | xargs perl -pi -w -e 's/(?<=colorAccent">).*(?=<\/color>)/<%= colorAccent %>/g;'

echo "♻️ Removing old template"
rm -rf $DIR/lib/android/boilerplate/templates/boilerplate

echo "🏭 Coping template"
cp -a boilerplate $DIR/lib/android/boilerplate/templates

# cleaning up
echo "♻️ cleaning up"
rm -rf boilerplate
rm -rf boilerplate.zip
rm -rf $DIR/lib/android/boilerplate/templates/boilerplate/update_template.sh

echo "🍻 🍻 Due to the complexibility of ruby packaging, You must prepare new codegen version by yourself 🍻🍻"