require 'xcodeproj'
project_path = 'PetConnect.xcodeproj'
project = Xcodeproj::Project.open(project_path)
target = project.targets.first

project.build_configurations.each do |config|
  config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '16.0'
end

target.build_configurations.each do |config|
  config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '16.0'
end

project.save
puts "Deployment target bumped to iOS 16.0"
