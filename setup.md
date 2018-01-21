
# Setup

## minishift

```
minishift config set memory 6000  
minishift config set cpus 2  
minishift config set disk-size 10G  
minishift config set vm-driver virtualbox
```  

`minishift start`

`eval $(minishift oc-env)`

`oc login -u developer -p developer`

`eval $(minishift docker-env)`

`docker login -u developer -p $(oc whoami -t) $(minishift openshift registry)`


