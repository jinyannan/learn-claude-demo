require 'xcodeproj'

project_path = 'PetConnect.xcodeproj'
project = Xcodeproj::Project.open(project_path)
target = project.targets.first

# Clear existing source build phase
target.source_build_phase.files_references.dup.each do |ref|
  target.source_build_phase.remove_file_reference(ref)
end

# Keep only Products group, remove the rest
project.main_group.children.dup.each do |child|
  next if child.name == 'Products'
  child.remove_from_project
end

# Recreate everything cleanly from the current directory structure
root_group = project.main_group.new_group('PetConnect', '.')

Dir.glob("**/*").each do |file|
  next if File.directory?(file)
  next if file.start_with?('PetConnect.xcodeproj') || file.start_with?('.git') || file.start_with?('build')
  # Only add swift and entitlements
  next unless file.end_with?('.swift') || file.end_with?('.entitlements')
  
  parts = file.split('/')
  filename = parts.pop
  
  # Ensure group hierarchy exists
  current_group = root_group
  parts.each do |dir_name|
    found = current_group.groups.find { |g| g.display_name == dir_name }
    current_group = found || current_group.new_group(dir_name, dir_name)
  end
  
  # Add the file to the group
  file_ref = current_group.new_file(filename)
  
  # Add to build phase if swift
  if file.end_with?('.swift')
    target.source_build_phase.add_file_reference(file_ref, true)
  end
end

project.save
puts "Project fully reconstructed and saved."
