require 'xcodeproj'
require 'fileutils'

project_path = 'PetConnect.xcodeproj'
project = Xcodeproj::Project.open(project_path)
target = project.targets.first

# 1. Update Deployment Target to iOS 15.0 to fix AsyncImage/dismiss errors
project.build_configurations.each do |config|
  config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '15.0'
end

target.build_configurations.each do |config|
  config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '15.0'
end

# 2. Re-add all .swift files to the compile sources phase
# Traverse the directory and construct groups dynamically
Dir.glob("**/*.swift").each do |swift_file|
  next if swift_file.start_with?('PetConnect.xcodeproj') || swift_file.start_with?('.git')
  
  parts = swift_file.split('/')
  filename = parts.pop
  
  group = project.main_group
  parts.each do |part|
    found_group = group.groups.find { |g| g.display_name == part || g.path == part }
    group = found_group || group.new_group(part, part)
  end
  
  file_ref = group.files.find { |f| f.path == filename }
  unless file_ref
    file_ref = group.new_file(filename)
  end
  
  # Ensure in Build Phase
  unless target.source_build_phase.files_references.include?(file_ref)
    target.source_build_phase.add_file_reference(file_ref, true)
    puts "Added to sources: #{swift_file}"
  end
end

project.save
puts "Successfully synchronized Swift files and set deployment target to 15.0."
