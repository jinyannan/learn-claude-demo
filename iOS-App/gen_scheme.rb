require 'xcodeproj'
project_path = 'PetConnect.xcodeproj'
project = Xcodeproj::Project.open(project_path)

# Auto-generate scheme
Xcodeproj::XCScheme.share_scheme(project.path, 'PetConnect')
puts "Re-created scheme!"
project.save
