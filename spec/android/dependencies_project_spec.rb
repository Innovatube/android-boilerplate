require_relative '../spec_helper'
require_relative '../../lib/android/boilerplate/generator'
require_relative '../../lib/android/boilerplate/project_dependencies_merger'
describe AndroidBoilerplate::ProjectDependenciesMerger do

  def input_file
    @input_file = File.read(File.join(File.dirname(__FILE__), 'input', 'build.gradle'))
  end

  def output_file
    @output_file = File.read(File.join(File.dirname(__FILE__), 'output', 'build.gradle'))
  end

  def project_dependencies_merger
    op = Hash.new
    op[:enable_google => false]
    op[:enable_facebook => true]
    op[:input_file] = input_file
    op[:output_file] = output_file
    @project_dependencies_merger = AndroidBoilerplate::ProjectDependenciesMerger.new(op)
  end


end