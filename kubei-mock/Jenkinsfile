pipeline {
  agent any
  stages {
    stage('检出') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: env.GIT_BUILD_REF]], 
                                                                                            userRemoteConfigs: [[url: env.GIT_REPO_URL, credentialsId: env.CREDENTIALS_ID]]])
      }
    }
    stage('构建') {
      steps {
        echo '构建中...'
        sh '''npm install
npm run build
cd dist
tar -zcvf dist.tar.gz *'''
        echo '构建完成.'
        script {
          def exists = fileExists 'README.md'
          if (!exists) {
            writeFile(file: 'README.md', text: 'Helloworld')
          }
        }

        archiveArtifacts(artifacts: 'dist/dist.tar.gz', fingerprint: true, onlyIfSuccessful: true)
        echo '构建物收集完成'
        npmAuditInDir(directory: '/', collectResult: true)
      }
    }
    stage('部署') {
      steps {
        echo '部署中...'
        echo '部署完成'
        sh 'scp -r dist root@172.30.254.65:/app/coding'
      }
    }
  }
}