require 'json'

def sync_template
  puts 'Removing mvp-boilerplate'
  Dir.chdir 'lib/android/boilerplate/templates'
  FileUtils.remove_dir('mvp-boilerplate') if File.exist?('mvp-boilerplate')
  system('git clone -b develop https://github.com/innovatube/android-template mvp-boilerplate')
  FileUtils.remove_dir('mvp-boilerplate/.git') if File.exist?('mvp-boilerplate/.git')
  template_files = Dir['mvp-boilerplate/**/*']
  template_files.each do |template|
    if !File.directory?(template) && code_file?(template)
      content = File.read(template)
      regex_array = Array.new
      content.scan(/(?<=regex\s=\s).*?}/) do |item|
        regex_array.push JSON.parse(item)
      end
      content.gsub!(/\/\*\*regex.*?\*\//m, '')
      content.gsub!(/.+regex =.*+/, '')
      regex_array.each do |regex|
        content.gsub!(regex['from'], regex['to'])
      end
      # rename package name
      content.gsub!(/com.innovatube.carbid/, '<%= package_name %>')
      content.gsub!(/\.template\./, '.')
      content.gsub!(/\.template_/, '.')
      # move erb syntax out of xml comment
      content.gsub!(/<!--(<%.*%>)-->/, '\1')
      # move erb syntax out of java comment
      content.gsub!(/\/\/(<%.*%>)/, '\1')
      # remove dependencies
      content.gsub!(/(\/\/start-of-template-dependencies.+\/\/end-of-template-dependencies)/m, '')
      # overwrite old file
      File.open(template, 'w') { |file| file.puts content }
    end
  end
  puts 'Template was synced successfully'
end

def code_file?(file_name)
  !(file_name =~ /(\.java|\.xml|\.gradle|\.json)/).nil?
end