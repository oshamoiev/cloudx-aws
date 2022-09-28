#cloud-config
repo_update: true
repo_upgrade: all

packages:
 - httpd

runcmd:
 - systemctl start httpd
 - sudo systemctl enable httpd
 - [ sh, -c, "usermod -a -G apache ec2-user" ]
 - [ sh, -c, "chown -R ec2-user:apache /var/www" ]
 - chmod 2775 /var/www
 - [ find, /var/www, -type, d, -exec, chmod, 2775, {}, \; ]
 - [ find, /var/www, -type, f, -exec, chmod, 0664, {}, \; ]
 - [ sh, -c, "aws s3 cp s3://oleksandr-shamoiev-bucket/ /var/www/html/ --recursive" ]
