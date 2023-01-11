#!/usr/bin/env groovy

// initialization
env.STAGE_INPROCESS = 'loading'

notesString = '''Building Docker image will assign the tag <b>dev_latest</b>, \
besides the above selected ENVIRONMENT checkboxes; <i>qa_latest, stg_latest and prod_latest.</i></p> \
'''

node {
  wrap([$class: 'BuildUser']) {
    def currentUser1 = env.BUILD_USER_ID
    env.currentUser=currentUser1
    echo currentUser
  }
}

import com.michelin.cio.hudson.plugins.rolestrategy.*
def List JustTest() {
        def result = ['-- Please Select --']
       // echo ${result}
        def authStrategy = jenkins.model.Jenkins.instance.getAuthorizationStrategy()

        if(authStrategy instanceof RoleBasedAuthorizationStrategy){
        //def currentUser = jenkins.model.Jenkins.instance.getAuthentication().getName();
        echo currentUser
        
        def roleMap = authStrategy.getRoleMap(com.synopsys.arc.jenkins.plugins.rolestrategy.RoleType.Global)

        def sids = roleMap.getSidsForRole("devops")
        if(sids != null && sids.contains(currentUser)) {
            //result.add('qa,stg,prod')
            result=["qa","stg","prod"]
            
        }

        sids = roleMap.getSidsForRole("developer")
        if (sids != null && sids.contains(currentUser)) {
            result.add('dev')

        }
      }
      return result
}

    properties([
        parameters([
            choice(name: 'environment', choices: JustTest().join('\n'), description: 'Choice')
        ])
    ])

pipeline {
    agent any
    parameters {
        booleanParam(name: 'Refresh',
                    defaultValue: false,
                    description: 'Read Jenkinsfile and exit.')
    }
    stages {
        stage('Read Jenkinsfile') {
            when {
                expression { return params.Refresh == true }
            }
            steps {
              echo("stop")
            }
        }
        stage('Run Jenkinsfile') {
            when {
                expression { return params.Refresh == false }
            }
            stages {
              stage('Build') {
                  steps {
                    echo("build")
                  }
              }
              stage('Test') {
                  steps {
                    echo("test")
                  }
              }
              stage('Deploy') {
                  steps {
                    echo("deploy")
                  }
              }
            }
        }
    }
}
