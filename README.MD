# Final Project Book Shop  
## Functional Requirements
1. Create a Book
2. Read a Book
3. Update a Book
4. Delete a Book  
   - A book can be deleted only if it was **created at least one (1) week ago**
   - A book cannot be deleted if it is **older than one (1) year**
   - If the above conditions are not met, the ***delete operation must fail*** with an *appropriate error message*

## Technical & Development Requirements
The solution must adhere to the following requirements:  
- Must be reusable and scalable
- Use Spring annotations
- Use logging
- Use PostgreSQL as the database
- Implement exception handling
- Use Bean-level validation
- Include code comments
- Include Javadoc documentation
- Implement unit testing
- Follow best practices
- Implement simple login (basic security)

## Testing
- Test the API using Postman or your preferred API testing tool
- Ensure all CRUD operations and validation rules are properly verified
---