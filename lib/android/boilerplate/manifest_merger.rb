require_relative 'mergeable'

##
#Class' description
#
#@author::          Ethan Le
#@usage::           An implementation of Mergeable to merge AndroidManifest.xml
#@revision::        22/3/2017
#@todo::            untested, not fully working
#@fixme::
##

module AndroidBoilerplate
  class ManifestMerger
    include Mergeable

    # initialize
    # options  : The hash contains attributes to merge AndroidManifest.xml (input and output location...)
    def initialize(options)
      super(options)
      self.resource_regex = /<activity[^>]*?\/>/m
      self.name_regex= /(?<=android:name=").*?(?=")/
      self.holder_regex = /(<\/application>)/m
      @exist_regex = 'activity'
      @attributes = %w(activity extra_activity meta_data permission)
    end

    # Merge input_file to output_file
    def run
      @attributes.each do |attribute|
        create_regex attribute
        input_file.scan(/#{resource_regex}/) do |item|
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

    def create_regex(attribute)
      case attribute
        when 'activity'
          self.resource_regex = /(?<!>)\s+<activity[^>]*?\/>/m
          self.name_regex= /(?<=android:name=").*?(?=")/
          @exist_regex = 'activity'
        when 'extra_activity'
          self.resource_regex = /(?<!>)\s+<activity.*?(?!=\/>)<\/activity>/m
          self.name_regex= /(?<=android:name=").*?(?=")/
          @exist_regex = 'activity'
        when 'meta_data'
          self.resource_regex = /(?<!>)\s+<meta-data.*?\/>/m
          self.name_regex= /(?<=android:name=").*?(?=")/
          @exist_regex = 'meta-data'
        when 'permission'
          self.resource_regex = /\s+<uses-permission.*?\/>/
          self.name_regex= /(?<=android:name=").*?(?=")/
          self.holder_regex = /(<uses-permission.*?\/>?)/m
          @exist_regex = 'uses-permission'
      end
    end

    # Check if target file contain item or not
    # item    :   The activity to be tested
    # target  :   The manifest file to check if it contains item
    def exist_in_target?(item, target)
      item_name = extract_name(item)[0]
      !(target =~ /#{@exist_regex}\s+android:name="#{encode_string(item_name)}/).nil?
    end

    # Merge a single activity to target
    # item   :   a single activity to be merged
    # target :   a target file
    def merge(item, target)
      target.gsub!(holder_regex, "#{item}\n\\1")
    end
  end
end