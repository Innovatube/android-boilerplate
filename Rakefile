require "bundler/gem_tasks"
require "rspec/core/rake_task"
require_relative 'sync'

RSpec::Core::RakeTask.new(:spec)

task :default => :spec
namespace :template do
  desc 'Clone template repo from github then sync with generator'
  task :sync do
    sync_template
  end
end

PACKAGE_NAME = 'android-codegen'
VERSION = '1.2'
TRAVELING_RUBY_VERSION = '20150715-2.2.2'

desc 'Package android codegen'
task :package => %w(package:linux:x86_64 package:osx package:win32)

namespace :package do
  namespace :linux do
    desc "Package #{PACKAGE_NAME} for Linux x86_64"
    task :x86_64 => [:bundle_install, "packaging/traveling-ruby-#{TRAVELING_RUBY_VERSION}-linux-x86_64.tar.gz"] do
      create_package('linux-x86_64')
    end
  end

  desc "Package #{PACKAGE_NAME} for OS X"
  task :osx => [:bundle_install, "packaging/traveling-ruby-#{TRAVELING_RUBY_VERSION}-osx.tar.gz"] do
    create_package('osx')
  end

  desc "Package #{PACKAGE_NAME} for Windows x86"
  task :win32 => [:bundle_install, "packaging/traveling-ruby-#{TRAVELING_RUBY_VERSION}-win32.tar.gz"] do
    create_package("win32", :windows)
  end


  desc 'Install gems to local directory'
  task :bundle_install do
    if RUBY_VERSION !~ /^2\.2\./
      abort "You can only 'bundle install' using Ruby 2.2, because that's what Traveling Ruby uses."
    end
    sh 'rm -rf packaging/tmp'
    sh 'mkdir packaging/tmp'
    sh 'cp Gemfile Gemfile.lock packaging/tmp/'
    sh 'echo \'source "https://rubygems.org"\n\ngem "thor"\ngem "erubis"\' > packaging/tmp/Gemfile'
    Bundler.with_clean_env do
      sh 'cd packaging/tmp && env BUNDLE_IGNORE_CONFIG=1 bundle install --path ../vendor --without development'
    end
    sh 'cp packaging/tmp/Gemfile packaging/tmp/Gemfile.lock packaging/'
    sh 'rm -rf packaging/tmp'
    sh 'rm -f packaging/vendor/*/*/cache/*'
  end
end

file "packaging/traveling-ruby-#{TRAVELING_RUBY_VERSION}-linux-x86_64.tar.gz" do
  download_runtime("linux-x86_64")
end

file "packaging/traveling-ruby-#{TRAVELING_RUBY_VERSION}-osx.tar.gz" do
  download_runtime("osx")
end

file "packaging/traveling-ruby-#{TRAVELING_RUBY_VERSION}-win32.tar.gz" do
  download_runtime("win32")
end

def create_package(target, os_type = :unix)
  package_dir = "#{PACKAGE_NAME}-#{VERSION}-#{target}"
  sh "rm -rf #{package_dir}"
  sh "mkdir #{package_dir}"
  sh "mkdir -p #{package_dir}/lib/app"
  sh "cp -pR lib/android/boilerplate #{package_dir}/lib/app"
  sh "mkdir #{package_dir}/lib/ruby"
  sh "tar -xzf packaging/traveling-ruby-#{TRAVELING_RUBY_VERSION}-#{target}.tar.gz -C #{package_dir}/lib/ruby"
  if os_type == :unix
    sh "cp packaging/wrapper.sh #{package_dir}/#{PACKAGE_NAME}"
  else
    sh "cp packaging/wrapper.bat #{package_dir}/#{PACKAGE_NAME}.bat"
  end
  sh "cp -pR packaging/vendor #{package_dir}/lib/"
  sh "cp packaging/Gemfile packaging/Gemfile.lock #{package_dir}/lib/vendor/"
  sh "echo '\nAndroidBoilerplate::AndroidGenerator.start ARGV' >> #{package_dir}/lib/app/boilerplate/cli.rb"
  sh "mkdir #{package_dir}/lib/vendor/.bundle"
  sh "cp packaging/bundler-config #{package_dir}/lib/vendor/.bundle/config"
  if !ENV['DIR_ONLY']
    if os_type == :unix
      sh "tar -czf #{package_dir}.tar.gz #{package_dir}"
    else
      sh "zip -9r #{package_dir}.zip #{package_dir}"
    end
    sh "rm -rf #{package_dir}"
  end
end

def download_runtime(target)
  sh 'cd packaging && curl -L -O --fail ' +
         "https://d6r77u77i8pq3.cloudfront.net/releases/traveling-ruby-#{TRAVELING_RUBY_VERSION}-#{target}.tar.gz"
end