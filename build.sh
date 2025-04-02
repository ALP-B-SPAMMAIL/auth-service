az acr login --name team04registry
docker build --platform linux/amd64 -t team04registry.azurecr.io/auth-service:latest .
docker push team04registry.azurecr.io/auth-service:latest
kubectl delete -f kube/deployment.yaml
kubectl apply -f kube/deployment.yaml
