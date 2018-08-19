##
#Class' description
#
#@author::          Ethan Le
#@usage::           Base class for merge operation
#@revision::        date of the 20/3/2017
#@todo::            fix append_to_report
#@fixme::
##

module AndroidBoilerplate
  module Mergeable
    # Get/Set the options hash
    attr_accessor :options

    # Get/Set the regex for name
    # Example:
    #
    #  /(?<=string\sname=").+(?=")/ with <string name="login_activity"> will match login_activity
    attr_accessor :name_regex

    # Get/Set the regex for resources holder
    # Example
    #
    # <resources>
    #    ...
    #    ...
    # </resources>
    # will return value between <resources> and </resources> with holder_regex
    attr_accessor :holder_regex

    # Get/Set the regex for resource
    # Example
    #
    # <resources>
    #    <string name="login">Login</string>
    #    <integer name="age">18</string>
    # </resources>
    # (?<!>)\s+<string.*?<\/string> will return "   <string name="login">Login</string>"
    # (?<!>)\s+<integer.*?<\/integer> will return "   <integer name="age">18</integer>"
    attr_accessor :resource_regex

    # Get/Set the content for input file
    attr_accessor :input_file

    # Get/Set the content for output file
    attr_accessor :output_file

    # Get/Set the content for report
    attr_accessor :report

    # initialize
    # options : The hash contains attribute (input and output location...)
    def initialize(options)
      self.options = options
      self.input_file = options[:input_file]
      self.output_file = options[:output_file]
    end

    # extract name from resource with name_regex
    # item  :  The resources to be extracted
    # Example:
    #
    #    name_regex = /(?<=dimen\sname=").+(?=")/
    #    extract_name('<dimen name="activity_vertical_margin">16dp</dimen>')
    #    # => ['activity_vertical_margin']
    def extract_name(item)
      item.match(/#{@name_regex}/)
    end

    # Check if target contains item or not
    # item    :  The resource to be tested
    # target  :  The resource to check if it contains item
    # Example:
    #
    #    item = '<dimen name="activity_vertical_margin">16dp</dimen>'
    #    target = '<resources>
    #                <dimen name="activity_vertical_margin">16dp</dimen>
    #                <dimen name="activity_horizontal_margin">64dp</dimen>
    #             </resources>'
    #    exist_in_target?(item, target)
    #    # => true
    def exist_in_target?(item, target)
      item_name = extract_name(item)[0]
      !(target =~ /#{encode_string(item_name)}/).nil?
    end

    # Merge input_file to output_file
    #  for each resource in input_file, extract its name
    #  if it already exists => append to output file
    #  if it doesn't exist  => replace output_file content = output_file content + resource
    #  then return a result hash contains merged file and report
    def run
      input_file.scan(/#{resource_regex}/) do |item|
        unless (!extract_name(item).nil? && exist_in_target?(extract_name(item)[0], output_file))
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

    # Merge a single resource to target
    # item   :  a single resource to be merged
    # target :  a target to merge
    # Example:
    #   item = '<dimen name="activity_vertical_margin">16dp</dimen>'
    #   target = '<resources>
    #                <dimen name="activity_horizontal_margin">64dp</dimen>
    #             </resources>'
    #   merge(item, target)
    #   # => <resources>
    #            <dimen name="activity_horizontal_margin">64dp</dimen>
    #            <dimen name="activity_vertical_margin">16dp</dimen>
    #        </resources>
    def merge(item, target)

    end

    # Append fail operation to report
    # item    : a single resource to be reported
    # Example:
    #   item = '<dimen name="activity_vertical_margin">16dp</dimen>'
    #   append_to_report(item)
    #   # => Unable to merge <dimen name="activity_vertical_margin">16dp</dimen> in /home/users/quanlt/template/dimens.xml to
    #        /home/users/quanlt/template/dimens.xml, you might want to resolve it yourself.
    def append_to_report(item)
      self.report = "#{report}\nUnable to merge #{item} in #{options[:from]} to #{options[:to]}, you might want to resolve it yourself."
    end

    # Fix escape sequences for regular expression
    # Replace ),(,[,] by /),/(,/[,/]
    def encode_string(item)
      item.gsub(/(\)|\(|\[|\])/,'/1')
    end

  end
end