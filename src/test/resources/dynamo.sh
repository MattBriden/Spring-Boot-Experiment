awslocal dynamodb create-table \
  --table-name dynamo-entrys \
  --attribute-definitions \
       AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
