
checkout([$class: 'GitSCM', 
    branches: [[name: '*/master']], 
    doGenerateSubmoduleConfigurations: false, 
    extensions: [[$class: 'CleanCheckout']], 
    submoduleCfg: [], 
    userRemoteConfigs: [[credentialsId: 'cnehru', url: 'https://github.com/cnehru/ScheduledEmailNotifier.git']]
])