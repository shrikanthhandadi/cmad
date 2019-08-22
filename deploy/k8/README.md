Open your windows/mac terminal 

- Start the minikube if not started. The default name of the minikbe will be _minikube_

```
minikube status
```
```
minikube --memory 8192 --cpus 2 start
```

- Create platform specific  depoymet 

__Windows__

```  
kubectl create -f logserver-win.yml -f mysql.yml -f cassandra.yml -f mongodb.yml
```
__Mac__

```
kubectl create -f mysql.yml -f mongodb.yml
```

- Start customer service once above pods/deployments/services are up. Checkout steps below.

```
kubectl create -f ccs -f ccsui.yml
```

- Check the status of deployment, pods, service & logs

```
kubectl get pods
```
```
kubectl get deployment
```
```
kubectl get svc
```
```
kubectl logs -f <pod name>
```

```
kubectl describe pods <pod_name>
```

- Open the service created in the browser

```
minikube service customerservice
```
to only print URL

```
minikube service customerservice --url
```

- Delete the deployment

```
kubectl delete -f .
```

- Stop the minikube

```
kubectl stop
```
