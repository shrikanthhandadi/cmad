Jenkins CI/CD related steps

Course reference: https://github.com/schoolofdevops/learn-jenkins

- Start Jenkins job

```
docker run -idt --name jenkins -v ~/cmad/cicd/jenkins_home:/var/jenkins_home  -v /var/run/docker.sock:/var/run/docker.sock  -p 1010:8080 -p 50000:50000 jenkins/jenkins:2.178-slim
```

- Install docker inside container

```
docker exec -it -u root jenkins sh

curl https://gist.githubusercontent.com/initcron/feb53b3b8b0e45225dcd1a438768ec81/raw/2d0c93db8dec1621068309fff6d422e685f66f74/docker_install_debian.sh > /tmp/docker_install_debian.sh
sh /tmp/docker_install_debian.sh

docker exec -it jenkins sh
docker ps

#if there is trouble with above step
docker exec -it -u root jenkins sh
chmod 777 /var/run/docker.sock
```