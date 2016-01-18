# 1. Get Domain
Method : GET

`domain/{domainid}`
 
# 2. List State
Method : GET

`list/state`
 
# 3. Course catalog
Method : GET

`list/providers`

# 4. Course list
Method : GET

`course/list/{catalogId}`

# 5. List Districts / Schools
Method : GET

Parameters:

querystring : 0

searchtext: A - domain names starts with A

limit: 0 unlimited records or 10 - next 10 records

`list/{stateId}?querystring=0&searchtext={textToSearch}&limit=10`
 
# 6. Create District
Method: POST

data: createDomainData

content Type: application/json

`domain/create/district`

# 7. Edit District
Method: POST

data: createDomainData

content Type: application/json

`domain/edit/district/{domainid}`