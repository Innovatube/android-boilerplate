require_relative 'mergeable'

##
#Class' description
#
#@author::          Ethan Le
#@usage::           An implementation of Mergeable to merge dependencies
#@revision::        date of the 20/3/2017
#@todo::            fix append_to_report
#@fixme::
##
module AndroidBoilerplate
  class DependenciesMerger
    include Mergeable

    # initialize
    # options            :  The hash contains attribute (input and output location...)
    # dependencies_type  :  a dependencies type (for testing purpose)
    def initialize(options, dependencies_type = '')
      super(options)
      self.name_regex = /(?<=#{dependencies_type}\s'|").+(?=:)/
      self.resource_regex = /(?<!{)\s+compile.+/
      self.holder_regex = /(?<=dependencies\s{.)(.+)(?=})/m
      @dependencies_types = %w(compile testCompile apt annotationProcessor provided androidTestCompile classpath)
    end

    # Merge input_file to output_file
    #  for each resource in input_file, extract its name
    #  if it already exists => append to output file
    #  if it doesn't exist  => replace output_file content = output_file content + resource
    #  then return a result hash contains merged file and report
    def run
      @dependencies_types.each do |dependencies_type|
        @resource_regex = /(?<!{).+#{dependencies_type}\s(?="|').+/
        @name_regex= /(?<=#{dependencies_type}\s'|").+(?=:)/
        input_file.scan(/#{@resource_regex}/) do |item|
          unless (exist_in_target?(item, output_file))
            merge(item, output_file)
          else
            append_to_report(item)
          end
        end
      end

      @dependencies_types.each do |dependencies_type|
        @resource_regex = /#{dependencies_type}\s*\(.*?\)\s*{.*?}/m
        @name_regex= /(?<=#{dependencies_type}\(").+(?=:)/
        input_file.scan(/#{@resource_regex}/) do |item|
          unless (exist_in_target?(item, output_file))
            merge(item, output_file)
          else
            append_to_report(item)
          end
        end
      end
      result = Hash.new
      result[:output_file] = output_file
      result[:report] = report
      return result
    end

    # Merge a single dependency to target
    # item    :   a single dependency to be merged
    # target  :   a target to merge
    def merge(item, target)
      target.gsub!(holder_regex, "\\1#{item}\n")
    end
  end
end
