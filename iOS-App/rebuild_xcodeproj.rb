require 'xcodeproj'
project_path = 'PetConnect.xcodeproj'
project = Xcodeproj::Project.open(project_path)

# Verify Build Configurations
if project.build_configurations.empty?
  project.add_build_configuration('Debug', :debug)
  project.add_build_configuration('Release', :release)
end

project.targets.each do |target|
  if target.build_configurations.empty?
    target.add_build_configuration('Debug', :debug)
    target.add_build_configuration('Release', :release)
  end
  
  target.build_configurations.each do |config|
    config.build_settings['TARGETED_DEVICE_FAMILY'] = '1,2'
    config.build_settings['SDKROOT'] = 'iphoneos'
    config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '14.0'
    config.build_settings['SWIFT_VERSION'] = '5.0'
    config.build_settings['PRODUCT_BUNDLE_IDENTIFIER'] = 'com.example.PetConnect'
    config.build_settings['PRODUCT_NAME'] = '$(TARGET_NAME)'
    config.build_settings['GENERATE_INFOPLIST_FILE'] = 'YES'
    config.build_settings['CODE_SIGN_STYLE'] = 'Automatic'
  end
end

# Check if app product exists
target = project.targets.first
unless target.product_reference
  product = project.main_group.groups.find { |g| g.name == 'Products' }&.files&.find { |f| f.path == 'PetConnect.app' }
  unless product
    products_group = project.main_group.groups.find { |g| g.name == 'Products' } || project.main_group.new_group('Products')
    product = products_group.new_product_ref_for_target(target.name, :application)
  end
  target.product_reference = product
end

# Ensure swift files are in the Build Phase 
def ensure_file_in_phase(target, file_path)
    file_ref = target.project.main_group.recursive_children.find { |c| c.respond_to?(:path) && c.path == file_path.split('/').last }
    if file_ref
        unless target.source_build_phase.files_references.include?(file_ref)
            target.source_build_phase.add_file_reference(file_ref)
            puts "Added missing reference: #{file_path}"
        end
    end
end

ensure_file_in_phase(target, 'Models/FeedPost.swift')
ensure_file_in_phase(target, 'Network/FeedService.swift')
ensure_file_in_phase(target, 'ViewModels/FeedViewModel.swift')
ensure_file_in_phase(target, 'Views/PostCardView.swift')
ensure_file_in_phase(target, 'Views/DiscoveryFeedView.swift')

project.save
puts "Successfully regenerated configs and saved!"

# Auto-generate scheme
Xcodeproj::XCScheme.share_scheme(project.path, target.name)
puts "Re-created scheme!"
