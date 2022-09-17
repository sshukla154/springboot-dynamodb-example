# springboot-dynamodb-example

## Local setup:
1. Download DynamoDB for free from one of the following locations. https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html#DynamoDBLocal.DownloadingAndRunning.title 
2. Configure AWS CLI (i.e Access Key, Secret Key, Region and Output format)
3. Tables:
> creates a table named Music. The partition key is Artist, and the sort key is SongTitle.
`aws dynamodb create-table \
--table-name Music \
--attribute-definitions \
AttributeName=Artist,AttributeType=S \
AttributeName=SongTitle,AttributeType=S \
--key-schema AttributeName=Artist,KeyType=HASH AttributeName=SongTitle,KeyType=RANGE \
--provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
--table-class STANDARD`

> Add new items to the table
`aws dynamodb put-item \
--table-name Music \
--item \
'{"Artist": {"S": "No One You Know"}, "SongTitle": {"S": "Call Me Today"}, "AlbumTitle": {"S": "Somewhat Famous"}}' \
--return-consumed-capacity TOTAL`

`aws dynamodb put-item \
--table-name Music \
--item '{ \
"Artist": {"S": "Acme Band"}, \
"SongTitle": {"S": "Happy Day"}, \
"AlbumTitle": {"S": "Songs About Life"} }' \
--return-consumed-capacity TOTAL`

## APIs:

Table:
POST: http://localhost:8080/v1/table/?tableName=my-table-name&partitionKey=my-partition-key&sortKey=my-sort-key&localSecondaryIndex=my-local-index
GET ALL: http://localhost:8080/v1/table/all/ 
DELETE : http://localhost:8080/v1/table/{tableName}/