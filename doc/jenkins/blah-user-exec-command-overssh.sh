export dest=$docker_repository:5001
export name=$JOB_NAME
export port=8879
export container_ids=$(docker ps -a | grep  $dest/$name:latest | awk '{print $1}')

docker pull $dest/$name:latest
docker stop $container_ids
docker rm $container_ids
docker run  -d -p $port:$port $dest/$name:latest
docker image prune -f