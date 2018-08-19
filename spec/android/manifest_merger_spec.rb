require 'spec_helper'
require_relative '../../lib/android/boilerplate/generator'
require_relative '../../lib/android/boilerplate/manifest_merger'
describe AndroidBoilerplate::ManifestMerger do

  def input_file
    @input_file = File.read(File.join(File.dirname(__FILE__), 'input', 'AndroidManifest.xml'))
  end

  def output_file
    @output_file = File.read(File.join(File.dirname(__FILE__), 'output', 'AndroidManifest.xml'))
  end

  def manifest_merger
    op = Hash.new
    op[:input_file] = input_file
    op[:output_file] = output_file
    @manifest_merger = AndroidBoilerplate::ManifestMerger.new(op)
  end

  it 'should return true if activity already exists' do
   expect(manifest_merger.exist_in_target?('<activity android:name=".MainActivity"/>', output_file)).to eq(false)
  end

  it 'should return false if activity doesn"t exist' do
    expect(manifest_merger.exist_in_target?('<activity android:name=".ui.LoginActivity"/>', output_file)).to eq(false)
  end

  it 'should extract dependencies name correctly' do
    expect(manifest_merger.extract_name('<activity android:name=".ui.LoginActivity"/>')[0]).to eq('.ui.LoginActivity')
  end

end