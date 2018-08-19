require_relative 'mergeable'

##
#Class' description
#
#@author::          Ethan Le
#@usage::           An implementation of Mergeable to merge resources file
#@revision::        21/3/2017
#@todo::            untested
#@fixme::
##
module AndroidBoilerplate
  class ResourcesMerger
    include Mergeable

    # initialize
    # options : The hash contains attribute (input and output location...)
    # resources_type: resources type to be merger (for testing purpose)
    def initialize(options, resource_type = '')
      super(options)
      self.name_regex = /(?<=#{resource_type}\sname=").+(?=")/
      self.holder_regex = /(?<=<resources>.)(.+)(?=<\/resources>)/m
      self.resource_regex = /(?<!>)\s+<#{resource_type}.*?<\/#{resource_type}>/
      @resources_types = %w(string integer dimen)
    end

    # Merge input_file to output_file
    #  for each resource in input_file, extract its name
    #  if it already exists => append to output file
    #  if it doesn't exist  => replace output_file content = output_file content + resource
    #  then return a result hash contains merged file and report
    def run
      @resources_types.each do |resource_type|
        self.name_regex = /(?<=#{resource_type}\sname=").+(?=")/
        self.resource_regex = /(?<!>)\s+<#{resource_type}.*?<\/#{resource_type}>/m
        input_file.scan(/#{resource_regex}/) do |item|
          unless (!extract_name(item).nil? && exist_in_target?(item, output_file))
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

    # Merge a single resource to target
    # item   :  a single resource to be merged
    # target :  a target file
    def merge(item, target)
      target.gsub!(/(?<=<resources>.)(.+)(?=<\/resources>)/m, "\\1#{item}")
      target.gsub!(/<\/resources>/, "\n</resources>")
    end
  end
end