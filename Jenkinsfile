#!/usr/bin/env groovy


node {
    properties([
        parameters([
            choice(name: 'environment', choices: load("$WORKSPACE/choice_parameter.groovy").JustTest().join('\n'), description: 'Choice')
        ])
    ])
}
	
