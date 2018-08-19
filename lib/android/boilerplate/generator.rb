require 'erubis'
require_relative 'resources_merger'
require_relative 'dependencies_merger'
require_relative 'manifest_merger'
require_relative 'custom_erubis'

##
#Class' description
#Class' description
#
#@author::          Ethan Le
#@usage::           Provide Generate operations which contain copy file, generate template
#@revision::        28/3/2017
#@todo::
#@fixme::
##
module AndroidBoilerplate

  class Generator
    attr_accessor :options

    # Initialize new Generate
    #  options   :   The hash contains attributes for generate operation (package name, location, app name, etc...)
    def initialize(options = {})
      self.options = options
    end

    # copy file from source to target
    # source    :   input file location
    # target    :   output file location
    def copy_file(source, target, exclude = [])
      if File.exist? target
        raise IOError, "#{target} already exist!"
      end
      make_parent_dir target
      FileUtils.cp(source, target)
    end

    # copy directory from source to target
    # source           :   input folder location
    # target           :   output folder location
    # exclude          :   file/folder will be ignored
    # IOError will be raised if target folder already exists
    def copy_directory(source, target, exclude = [])
      raise IOError, "#{target} already exist!" if File.exist? target
      make_parent_dir target
      FileUtils.copy_entry(source, target)
      puts "\tcreate file \t#{target}"
    end

    # copy directory from source to target and generate file content with options
    # source           :   input folder location
    # target           :   output folder location
    # exclude          :   file/folder will be ignored
    # each file in input folder will be generated with erubis (template engine)
    def copy_template_directory(source, target, exclude = [])
      input_files = Dir["#{source}/**/*"]
      input_files.each do |input|
        next if exclude_match?(input, exclude)
        if File.file?(input)
          destination = "#{target}#{input.gsub(source, '')}"
          make_parent_dir "#{target}#{input.gsub(source, '')}"
          if binary_file?(input)
            FileUtils.cp(input, destination)
          else
            copy_template_file input, destination
          end
        end
      end
    end


    # Check if item should be ignored or not
    # item    :   item to be checked
    # exclude  :   exclude array
    def exclude_match?(item, exclude)
      return false if exclude.nil?
      exclude.each { |exclude_entry| return true if item.include?(exclude_entry) }
      return false
    end

    # generate file from source with erubis then copy it to target folder
    # source           :   input file location
    # target           :   output file location
    # exclude          :   file/folder will be ignored
    # IOError will be raised if target file already exists!
    def copy_template_file(source, target, exclude = [])
      if(File.exist?(source))
        begin
          raise IOError, "#{target} already exist!" if File.exist? target
          make_parent_dir target
          template = render_template(source)
          File.open(target, 'w') { |file| file.write(template) }
          puts "\tcreate file \t#{target}"
        end
      else
        puts "\tskipping \t#{source}"
      end
    end

    # generate input file with erubis then merger it with output file
    # source           :   input file location
    # target           :   output file location
    # IOError will be raised if target file already exists!
    # todo haven't implemented yet
    def merge_template_file(source, target, merge_type)
      new_options = self.options.dup
      new_options[:input_file] = render_template(source)
      unless File.exist?(target)
        copy_template_file(source, target)
      else
        new_options[:output_file] = File.read(target)
        new_options[:from] = source
        new_options[:to] = target
        merger = nil
        case merge_type
          when 'resources'
            merger = AndroidBoilerplate::ResourcesMerger.new(new_options)
          when 'app_dependencies'
            merger = AndroidBoilerplate::DependenciesMerger.new(new_options)
          when 'manifest'
            merger = AndroidBoilerplate::ManifestMerger.new(new_options)
        end
        merge_result = merger.run
        File.open(target, 'w') { |file| file.puts merge_result[:output_file] }
        puts "\tmerge file \t#{target}"
      end
    end

    # Make directory for file
    def make_parent_dir(file)
      dir = File.dirname file
      FileUtils.mkpath(dir) unless File.exist?(dir)
    end

    # Return a template for input file with erubis
    # item           :   input file location
    def render_template(input)
      template = File.read(File.join(input))
      erubis = CustomErubis.new(template)
      erubis.result(self.options)
    end

    # Check if file is binary file or not
    # file    :   file name
    # Example:
    def binary_file?(file)
      !(file=~ /(.jar|.png|.jpg|.jpeg|.gif|.mp3|.wav|.keystore)/).nil?
    end

  end
end