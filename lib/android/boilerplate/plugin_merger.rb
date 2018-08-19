require_relative 'mergeable'

##
#Class' description
#
#@author::          Ethan Le
#@usage::           An implementation of Mergeable to merge plugin (in build.gradle module level)
#@revision::        date of the 20/3/2017
#@todo::            untested, not fully working
#@fixme::
##
module AndroidBoilerplate
  class PluginMergeable
    include Mergeable

    def initialize(options)
      super(options)
      self.resource_regex = /\s+classpath\s(?="|').+/
      self.name_regex= /(?<=classpath\s'|").+(?=:)/
    end

    # Merge input_file to output_file and return result
    def run
      input_file.scan(/#{resource_regex}/) do |item|
        unless (exist_in_target?(item, output_file))
          merge(item, output_file)
        else
          append_to_report(item)
        end
      end


      result = Hash.new
      result[:output_file] = output_file
      result[:report] = report
      return result
    end

    # Merge a single plugin to target
    # item   :  a single plugin to be merged
    # target :  a target file
    def merge(item, target)
      target.gsub!(/(?<=dependencies\s{)(.*?)(?=})/m, "\\1#{item}")
    end
  end
end