require 'spec_helper'
require_relative '../../lib/android/boilerplate/generator'
require_relative '../../lib/android/boilerplate/resources_merger'
describe AndroidBoilerplate::ResourcesMerger do

  def input_file
    @input_file = File.read(File.join(File.dirname(__FILE__), 'input', 'resources.xml'))
  end

  def output_file
    @output_file = File.read(File.join(File.dirname(__FILE__), 'output', 'resources.xml'))
  end

  def resources_merger
    op = Hash.new
    op[:input_file] = input_file
    op[:output_file] = output_file
    @resources_merger = AndroidBoilerplate::ResourcesMerger.new(op, 'string')
  end

  it 'should return true if resource already exist' do
    expect(resources_merger.exist_in_target?('<string name="app_name">App Name</string>', output_file)).to eq(true)
  end

  it "should return false if resource doesn't exist" do
    expect(resources_merger.exist_in_target?('<string name="login_activity_title">Login</string>', output_file)).to eq(false)
  end

  it 'should extract resource name correctly' do
    expect(resources_merger.extract_name('<string name="app_name">App Name</string>')[0]).to eq('app_name')
  end

  it 'should merge resources correctly' do
    merge_result = resources_merger.run
    expected_result = File.read(File.join(File.dirname(__FILE__), 'output', 'resources_result.xml'))
    expect(merge_result[:output_file]).to eq(expected_result)
  end
end