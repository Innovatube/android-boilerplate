pipeline {
  agent any
  environment {
    start_message = "STARTED: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\n<${env.BUILD_URL}|Open>"
    successful_message = "SUCCESSFUL: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\n<${env.BUILD_URL}|Open>"
  }
  stages {
    stage('Checkout') {
      steps {
        parallel(
          "Checkout": {
            checkout scm
          },
          "Send Notification": {
            script {
              sh 'printenv'
              slackSend baseUrl: 'https://innovatube-ext.slack.com/services/hooks/jenkins-ci/', channel: 'ci', color: '#FFFF00', message: "STARTED: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\n<${env.BUILD_URL}|Open>", teamDomain: 'Innovatube', token: '8i8EOjh62UJfsF070BX3PfCU'
            }
          }
        )
      }
    }
    stage('Run JUnit test') {
      steps {
          sh 'chmod a+x gradlew'
          sh './gradlew test jacocoTestReport'
      }
    }
    stage('SonarQube analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh './gradlew --info sonarqube'
        }
      }
    }
    stage("Quality Gate") {
      steps {
        script {
          timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
            def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
            if (qg.status != 'OK') {
              error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
          }
        }
      }
    }
    stage('Build Debug') {
      steps {
          sh 'chmod a+x gradlew'
          sh './gradlew assembleDebug'
      }
    }
    stage('Build Release') {
      when {
        expression {
            return env.BRANCH_NAME == 'master'
        }
      }
      steps {
          sh './gradlew assembleRelease'
      }
    }
    stage('Sign APK') {
      environment {
        KEYSTORE = credentials('release.jks')
        KEY_ALIAS = credentials('key-alias')
        KEYSTORE_PASSWORD = credentials('key-store-password')
        KEY_PASSWORD = credentials('key-password')
      }
      when {
        expression {
            return env.BRANCH_NAME == 'master'
        }
      }
      steps {
            sh "rm -rf app-release-signed.apk"
            sh "/opt/android-sdk-linux/build-tools/26.0.2/apksigner sign --ks '${KEYSTORE}' --ks-key-alias ${KEY_ALIAS} --ks-pass pass:${KEYSTORE_PASSWORD} --key-pass pass:${KEY_PASSWORD}  --out app-release-signed.apk app/build/outputs/apk/app-release-unsigned.apk"
      }
    }
    stage('Upload to S3') {
      steps {
        script {
          if (env.BRANCH_NAME == 'master') {
            withAWS(credentials: 'jenkinsci.aws', region: 'ap-southeast-1') {
              s3Upload(file: 'app-release-signed.apk', bucket: 'jenkinsci-delivery', path: "apk/${env.JOB_NAME}/${env.BUILD_NUMBER}/app-release-${env.BUILD_NUMBER}.apk")
              successful_message = "${successful_message}\nRelease APK: <https://s3-ap-southeast-1.amazonaws.com/jenkinsci-delivery/apk/${env.JOB_NAME}/${env.BUILD_NUMBER}/app-release-${env.BUILD_NUMBER}.apk|app-release-${env.BUILD_NUMBER}.apk>"
            }
          } else {
             withAWS(credentials: 'jenkinsci.aws', region: 'ap-southeast-1') {
               s3Upload(file: 'app/build/outputs/apk/app-debug.apk', bucket: 'jenkinsci-delivery', path: "apk/${env.JOB_NAME}/${env.BUILD_NUMBER}/app-debug-${env.BUILD_NUMBER}.apk")
               successful_message = "${successful_message}\nDebug APK: <https://s3-ap-southeast-1.amazonaws.com/jenkinsci-delivery/apk/${env.JOB_NAME}/${env.BUILD_NUMBER}/app-debug-${env.BUILD_NUMBER}.apk|app-debug-${env.BUILD_NUMBER}.apk>"
             }
          }
        }
      }
    }
  }
  post {
    success {
      script {
        // send to Slack
        slackSend baseUrl: 'https://innovatube-ext.slack.com/services/hooks/jenkins-ci/', channel: 'ci', color: '#FFFF00', message: successful_message, teamDomain: 'Innovatube', token: '8i8EOjh62UJfsF070BX3PfCU'

        // send to email
        emailext (
          to: "tony.dang@innovatube.com, tc-team@innovatube.com",
          subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
          body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                   <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>
                   <p>
                   <p>You can download Debug APK at "<a href="https://s3-ap-southeast-1.amazonaws.com/jenkinsci-delivery/apk/${env.JOB_NAME}/${env.BUILD_NUMBER}/app-debug-${env.BUILD_NUMBER}.apk">app-debug-${env.BUILD_NUMBER}.apk</a>""",
          recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        )
      }
    }

    failure {
        // send to Slack
        slackSend baseUrl: 'https://innovatube-ext.slack.com/services/hooks/jenkins-ci/', channel: 'ci', color: '#FF0000', message: "FAILED: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\n<${env.BUILD_URL}|Open>", teamDomain: 'Innovatube', token: '8i8EOjh62UJfsF070BX3PfCU'

        // send to email
        emailext (
          to: "tony.dang@innovatube.com, tc-team@innovatube.com",
          subject: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
          body: """<p>FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                   <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>""",
          recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        )
    }

    unstable {
        // send to Slack
        slackSend baseUrl: 'https://innovatube-ext.slack.com/services/hooks/jenkins-ci/', channel: 'ci', color: '#828282', message: "UNSTABLE: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\n<${env.BUILD_URL}|Open>", teamDomain: 'Innovatube', token: '8i8EOjh62UJfsF070BX3PfCU'

        // send to email
        emailext (
          to: "tony.dang@innovatube.com, tc-team@innovatube.com",
          subject: "UNSTABLE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
          body: """<p>UNSTABLE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                   <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>""",
          recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        )
    }
  }
}