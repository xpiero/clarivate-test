- Installation:
1) Create a new postgres db
2) Run postgresScript.sql
3) Modify application.properties with your postgres credentials
4) Run mvn clean package
5) Run mvn clean spring-boot:run

- Usage:
1) Login:
> curl -X POST http://localhost:8080/login -H "Content-Type:application/json" -d "{\"username\":\"Luke\", \"password\":\"1234\"}"
>> {"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMdWtlIiwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTU1NTQxNzc5MiwiZXhwIjoxNTU1NDIxMzkyfQ.KQ337BlM21sNJOUm_N0nn9seb9PhNBm5kC52TGGG8tU"}

2) Add Level Score: 
> curl -X GET http://localhost:8080/level/1/score/1500 -H "Session-Key: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMdWtlIiwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTU1NTQxNzc5MiwiZXhwIjoxNTU1NDIxMzkyfQ.KQ337BlM21sNJOUm_N0nn9seb9PhNBm5kC52TGGG8tU"
>> No Content

3) High score list for a level
curl -X GET level/1/score?filter=highestscore -H "Session-Key: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMdWtlIiwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTU1NTQxNzc5MiwiZXhwIjoxNTU1NDIxMzkyfQ.KQ337BlM21sNJOUm_N0nn9seb9PhNBm5kC52TGGG8tU"
>> [{"score":1500,"username":"Liam"},{"score":103,"username":"Luke"}]

4) All Scores for a level
curl -X GET level/1/score -H "Session-Key: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMdWtlIiwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTU1NTQxNzc5MiwiZXhwIjoxNTU1NDIxMzkyfQ.KQ337BlM21sNJOUm_N0nn9seb9PhNBm5kC52TGGG8tU"
>> [{"score":1500,"username":"Liam"},{"score":103,"username":"Luke"},{"score":30,"username":"Liam"},{"score":5,"username":"Liam"}]