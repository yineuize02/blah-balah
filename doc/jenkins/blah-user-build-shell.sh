export name=$JOB_NAME
export imageId=$(cat $name/target/docker/image-id)
export tag=$(cat $name/target/docker/tag)
export dest=$docker_repository:5001
echo $imageId
docker images
docker login $dest -u user-docker -p 123456
docker push $dest/$name:$tag
docker rmi $imageId
