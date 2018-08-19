require 'erubis'
##
#Class' description
#
#@author::          Ethan Le
#@usage::           Custom Erubis provides helper method
#@revision::        28/3/2017
#@todo::
#@fixme::
##

class CustomErubis < Erubis::Eruby

  # transform word to lower camel case
  # word           :   word to be transformed
  # Example:
  #   word = "UserCategory"
  #   to_lower_camel_case(word)
  #    => userCategory
  def to_lower_camel_case(word)
    word.tap { |e| e[0] = e[0].downcase }
  end

  # transform word to snake case
  # word           :   word to be transformed
  # Example:
  #   word = "UserCategory"
  #   to_snake_case(word)
  #    => user_category
  def to_snake_case(word)
    word.gsub(/::/, '/').
        gsub(/([A-Z]+)([A-Z][a-z])/,'\1_\2').
        gsub(/([a-z\d])([A-Z])/,'\1_\2').
        tr('-', '_').
        downcase
  end

  def sub_package_name
    ''
  end

  def activity_name
    ''
  end
  def enable_firebase_login
    false
  end
  def enable_google_login
    false
  end
  def enable_facebook_login
    false
  end
  def package_name
    '<%= package_name %>'
  end
  def app_name
    'boilerplate'
  end
  def compileSdkVersion
    26
  end
  def buildToolsVersion
    '26.0.2'
  end
  def minSdkVersion
    21
  end
  def targetSdkVersion
    26
  end
  def colorPrimary
    '#2980b9'
  end
  def colorPrimaryDark
    '#2980b9'
  end
  def colorAccent
    '#444444'
  end 
end