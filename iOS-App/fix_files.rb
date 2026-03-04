require 'xcodeproj'
project_path = 'PetConnect.xcodeproj'
project = Xcodeproj::Project.open(project_path)

target = project.targets.first

def add_file_to_project(project, target, file_path, group_path)
  group = project.main_group
  group_path.split('/').each do |subgroup_name|
    group = group.groups.find { |g| g.display_name == subgroup_name || g.path == subgroup_name } || group.new_group(subgroup_name)
  end
  
  file_ref = group.files.find { |f| f.path == file_path.split('/').last }
  unless file_ref
    file_ref = group.new_file(file_path)
    if file_path.end_with?('.swift')
      target.source_build_phase.add_file_reference(file_ref)
    end
    puts "Added #{file_path}"
  else
    if file_path.end_with?('.swift') && !target.source_build_phase.files_references.include?(file_ref)
      target.source_build_phase.add_file_reference(file_ref)
      puts "Added existing #{file_path}"
    end
  end
end

add_file_to_project(project, target, 'Models/FeedPost.swift', 'Models')
add_file_to_project(project, target, 'Network/FeedService.swift', 'Network')
add_file_to_project(project, target, 'ViewModels/FeedViewModel.swift', 'ViewModels')
add_file_to_project(project, target, 'Views/PostCardView.swift', 'Views')
add_file_to_project(project, target, 'Views/DiscoveryFeedView.swift', 'Views')

project.save
