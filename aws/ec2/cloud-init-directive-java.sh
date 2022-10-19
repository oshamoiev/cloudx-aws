#cloud-config
repo_update: true
repo_upgrade: all

runcmd:
 - [ sh, -c, "yum install -y java-17-amazon-corretto" ]
 - mkdir /cloudx-app
 - [ sh, -c, "aws s3 cp s3://oleksandr-shamoiev-bucket/cloudx-app/webapp.jar /cloudx-app/" ]
 - [ sh, -c, "java -Daws.accessKeyId=key -Daws.secretKey=secret -jar /cloudx-app/webapp.jar" ]
