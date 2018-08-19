require 'thor'
require_relative 'generator'
require 'json'
require_relative 'utilities'
require_relative 'custom_erubis'
##
#Class' description
#
#@author::          Ethan Le
#@usage::           CLI endpoint
#@revision::        28/3/2017
#@todo::
#@fixme::
##

module AndroidBoilerplate
  class AndroidGenerator < Thor

    desc 'generate', 'generate boilerplate code with config file'

    def generate()
      config = JSON.parse(File.read(File.join(File.dirname(__FILE__), 'templates', 'boilerplate-config.json')));
      begin
        new_options = Hash.new
        config.each do |key, value|
          new_options[key] = value
        end
        config_file = File.read(File.join(File.dirname(__FILE__), 'templates', 'boilerplate-config.json'));
        param_json = JSON.parse(config_file)
        param_json['params'].keys.each { |param|
          next if !param_json['params'][param]['require_true'].nil? && !new_options[param_json['params'][param]['require_true']]
          next if !param_json['params'][param]['require_false'].nil? && new_options[param_json['params'][param]['require_false']]
          next if !new_options[param].nil?
          say param_json['params'][param]['message']
          if param_json['params'][param]['type'] == 'model'
            select_model(new_options)
          elsif !param_json['params'][param]['limited_to'].nil?
            command_option = Hash.new
            command_option[:limited_to] = param_json['params'][param]['limited_to'].split(',').map(&:strip)
            home_template = ask(param, command_option)
            puts home_template
            new_options[home_template] = true
          else
            command_option = Hash.new
            command_option[:limited_to] = %w(yes no) if param_json['params'][param]['type'] == 'boolean'
            if command_option.size!=0
              new_options[param] = ask(param, command_option)
            else
              new_options[param] = ask(param)
            end

            if param_json['params'][param]['type'] == 'boolean'
              new_options[param] = new_options[param] == 'yes'
            end
          end
        }
        new_options['base_dir'] = File.join(File.dirname(__FILE__), 'templates', 'boilerplate')
        new_options['directory']
        if new_options['app_name'].empty?
          new_options['app_name'] = 'boilerplate';
        end

        if new_options['package_name'].empty?
          new_options['package_name'] = 'com.innovatube.boilerplate';
        end
        
        if new_options['compileSdkVersion'].empty?
          new_options['compileSdkVersion'] = 27;
        end

        if new_options['buildToolsVersion'].empty?
          new_options['buildToolsVersion'] = '27.0.3';
        end

        if new_options['minSdkVersion'].empty?
          new_options['minSdkVersion'] = 21;
        end

        if new_options['targetSdkVersion'].empty?
          new_options['targetSdkVersion'] = 27;
        end

        if new_options['supportLibraryVersion'].empty?
          new_options['supportLibraryVersion'] = '27.1.1'
        end

        if new_options['colorPrimary'].empty?
          new_options['colorPrimary'] = "#2980b9";
        end

        if new_options['colorPrimaryDark'].empty?
          new_options['colorPrimaryDark'] = "#2980b9";
        end

        if new_options['colorAccent'].empty?
          new_options['colorAccent'] = "#444444";
        end
        
        if new_options['directory'] == ''
          new_options['target_dir'] = new_options['app_name']
        else
          new_options['target_dir'] = "#{new_options['directory']}/#{new_options['app_name']}"
        end
        erubis = CustomErubis.new(config_file)
        param_json = JSON.parse(erubis.result(new_options))
        generator = AndroidBoilerplate::Generator.new(new_options)
        task_list = %w(copy_template_directory copy_template_file copy_file copy_directory merge_template_file)
        task_list.each do |task_name|
          next if param_json['tasks'][task_name].nil?
          param_json['tasks'][task_name].each do |task|
            next unless AndroidBoilerplate::Utilities.task_satisfy?(task, new_options)
            if task_name == 'merge_template_file'
              generator.merge_template_file(task['from'], task['to'], task['merge_type'])
            else
              generator.send(task_name, task['from'], task['to'], task['exclude']) unless param_json['tasks'][task_name].nil?
            end
          end
        end
        puts "Let's the party begin, but you might need to replace google-services.json by your own config file :tada: :tada:"
      rescue Error
        puts 'unable to generate code'
      end
    end
  end
end