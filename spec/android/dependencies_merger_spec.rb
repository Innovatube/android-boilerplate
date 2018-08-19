require 'spec_helper'
require_relative '../../lib/android/boilerplate/generator'
require_relative '../../lib/android/boilerplate/dependencies_merger'
describe AndroidBoilerplate::DependenciesMerger do

  def input_file
    @input_file = File.read(File.join(File.dirname(__FILE__), 'input', 'dependencies.gradle'))
  end

  def output_file
    @output_file = File.read(File.join(File.dirname(__FILE__), 'output', 'dependencies.gradle'))
  end

  def dependencies_merger
    op = Hash.new
    op[:enable_google => false]
    op[:enable_facebook => true]
    op[:input_file] = input_file
    op[:output_file] = output_file
    @dependencies_merger = AndroidBoilerplate::DependenciesMerger.new(op, 'compile')
  end

  it 'should return true if dependencies is already exist' do
    expect(dependencies_merger.exist_in_target?('compile "com.android.support:appcompat-v7:25.2.0"', output_file)).to eq(true)
  end

  it 'should return false if dependencies is not existed' do
    expect(dependencies_merger.exist_in_target?("compile 'com.google.android.gms:play-services-auth:25.2.0'", output_file)).to eq(false)
  end

  it 'should extract dependencies name correctly' do
    expect(dependencies_merger.extract_name('compile "com.android.support:design:${supportLibrariesVersion}"')[0]).to eq('com.android.support:design')
  end

  it 'should merge dependencies correctly' do
    result = dependencies_merger.run
    expect(result[:output_file]).to eq(File.read(File.join(File.dirname(__FILE__), 'output', 'dependencies_result.gradle')))
  end

end