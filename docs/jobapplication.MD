# JobPosting Routes

## All routes starts with `/job-application`

--- 

### POST `/create`

Request body expected:

   ```
   {
        "jobpostingId": Long
        "candidateId": Long
        "candidateName": String
        "hardskills": List<String>
        "softskills": {
            "skill name": Long  //Long is a level 1 to 5
        }
  
   }
   ```

Response body expected:

   ```
   {
        "id": Long
        "jobpostingId": Long
        "candidateId": Long
        "candidateName": String
        "hardskills": List<String>
        "softskills": {
            "skill name": Long  //Long is a level 1 to 5
        }
        "createAt": String/LocalDate
  
   }
   ```


#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `409 - CONFLICT` - Already exists a jobapplication for this jobposting and candidateId.

--- 

### POST `/jobposting/{id}`

### Company Only

Response body expected:

   ```
   [
        {
            "candidateName": String,
            "matchSoftSkillsPercent: Int
            "matchHardSkillsPercent: Int
        }
   ]
   ```


#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `403 - FORBIDDEN` - Company is not owner of this JobPosting.
* `404 - NOT FOUND` - JobPosting not found.

--- 

### POST `/create`

Request body expected:

   ```
   {
        "jobpostingId": Long
        "candidateId": Long
        "candidateName": String
        "hardskills": List<String>
        "softskills": {
            "skill name": Long  //Long is a level 1 to 5
        }
  
   }
   ```

Response body expected:

   ```
   {
        "id": Long
        "jobpostingId": Long
        "candidateId": Long
        "candidateName": String
        "hardskills": List<String>
        "softskills": {
            "skill name": Long  //Long is a level 1 to 5
        }
        "createAt": String/LocalDate
  
   }
   ```


#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `409 - CONFLICT` - Already exists a jobapplication for this jobposting and candidateId.

