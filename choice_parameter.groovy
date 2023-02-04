#!/usr/bin/env groovy


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
        
        def roleMap = authStrategy.getRoleMap(com.synopsys.arc.jenkins.plugins.rolestrategy.RoleType.Project)

        def sids = roleMap.getSidsForRole("devops")
        if(sids != null && sids.contains("Devops-team")) {
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
